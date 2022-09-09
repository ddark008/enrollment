package ru.ddark008.yadisk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.entities.patch.ItemPatcher;
import ru.ddark008.yadisk.exceptions.ItemNotFoundException;
import ru.ddark008.yadisk.exceptions.ItemValidationException;
import ru.ddark008.yadisk.mappers.HistoryItemMapper;
import ru.ddark008.yadisk.model.SystemItemType;
import ru.ddark008.yadisk.repositories.HistoryItemRepository;
import ru.ddark008.yadisk.repositories.ItemRepository;
import ru.ddark008.yadisk.services.ItemService;
import ru.ddark008.yadisk.validation.ItemImportValidator;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final HistoryItemRepository historyItemRepository;
    private final ItemImportValidator itemImportValidator;
    private final ItemPatcher itemPatcher;
    private final HistoryItemMapper historyItemMapper;


    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, HistoryItemRepository historyItemRepository, ItemImportValidator itemImportValidator, ItemPatcher itemPatcher, HistoryItemMapper historyItemMapper) {
        this.itemRepository = itemRepository;
        this.historyItemRepository = historyItemRepository;
        this.itemImportValidator = itemImportValidator;
        this.itemPatcher = itemPatcher;
        this.historyItemMapper = historyItemMapper;
    }


    @Override
    public void importItems(List<Item> importItems) {
        if (importItems.isEmpty()) {
            return;
        }
        /*
          Импортируемые элементы из запроса
         */
        Map<String, Item> importMap = new HashMap<>();
        /*
          Родительские элементы импортируемых элементов, если есть
         */
        Map<String, Item> parentMap = new HashMap<>();
        /*
          Элементы из БД, которые обновляются элементами из запроса
         */
        Map<String, Item> updateMap = new HashMap<>();
        /*
          Новые элементы из запроса
         */
        Set<Item> newItems = new HashSet<>();

        for (Item item : importItems) {
            // Проверка на уникальность ID в запросе
            Item oldItem = importMap.put(item.getItemStringId(), item);
            if (oldItem != null) {
                throw new ItemValidationException(item.getItemStringId(), "Duplicate ID in request!");
            }
            // Коллекционирование родителей
            String parentID = item.getParentStringId();
            if (parentID != null) {
                itemRepository.findByItemStringId(parentID).ifPresent(parentItem -> parentMap.put(parentID, parentItem));
            }
            // Сортировка на новые и обновленные
            itemRepository.findByItemStringId(item.getItemStringId()).ifPresentOrElse(existItem -> updateMap.put(existItem.getItemStringId(), existItem), () -> newItems.add(item));
        }
        // Проверка соблюдений условий импорта
        itemImportValidator.validate(importMap, parentMap, updateMap);

        // Добавляем новые элементы в БД, чтобы им присвоили ID
        itemRepository.saveAll(newItems);

        // Набор папок для перерасчета размера
        Set<Item> sizeRecalculate = new HashSet<>();

        // Добавляем новые элементы в дочерние элементы, а его родителей в список на пересчет размера
        for (Item newItem : newItems) {
            addToParent(newItem);
            addParentsToSet(newItem, sizeRecalculate);
        }
        // Обновляем изменившиеся элементы
        for (Item updateItem : updateMap.values()) {
            Item patchItem = importMap.get(updateItem.getItemStringId());
            // Если поменяли родителя
            if (!(updateItem.getParentStringId() == null && patchItem.getParentStringId() == null) && !updateItem.getParentStringId().equals(patchItem.getParentStringId())) {
                // Добавление родителей в список на перерасчет размера и удаление из дочерних элементов
                addParentsToSet(updateItem, sizeRecalculate);
                removeFromParent(updateItem);
                // Применение изменений
                itemPatcher.apply(updateItem, patchItem);
                // Добавление в дочерние элементы и родителей на пересчет размера
                addToParent(updateItem);
                addParentsToSet(updateItem, sizeRecalculate);
                // Если изменился размер, но элемент не перенесли в другую папку
            } else if (updateItem.getType() == SystemItemType.FILE && !updateItem.getSize().equals(patchItem.getSize())) {
                itemPatcher.apply(updateItem, patchItem);
                addParentsToSet(updateItem, sizeRecalculate);
                // Если изменилось остальное, то просто применяем изменения
            } else {
                itemPatcher.apply(updateItem, patchItem);
            }
        }

        // В начале метода проверка, что список не пустой
        LocalDateTime date = importItems.get(0).getDate();
        // Список ещё не обработанных папок
        Set<Item> notVisitedList = new HashSet<>(sizeRecalculate);
        for (Item item : sizeRecalculate) {
            calculateSize(item, date, notVisitedList);
        }
        itemRepository.saveAll(sizeRecalculate);

        Set<Item> changedItems = new HashSet<>();
        changedItems.addAll(newItems);
        changedItems.addAll(updateMap.values());
        changedItems.addAll(sizeRecalculate);
        changedItems.stream().map(historyItemMapper::toEntity).forEach(historyItemRepository::save);
    }

    /**
     * Добавление элемента в родительскую папку
     *
     * @param item элемент для добавления
     */
    @Transactional
    private void addToParent(Item item) {
        if (item.getParentStringId() != null) {
            Item parent = itemRepository.findByItemStringId(item.getParentStringId()).orElse(null);
            if (parent != null) {
                parent.getChildren().add(item);
                item.setParent(parent);
            } else {
                throw new ItemNotFoundException(item.getItemStringId(), "Can't find parent");
            }
        }
    }

    /**
     * Удаляет элемент из списка дочерних элементов родителя
     *
     * @param item элемент для удаления
     */
    @Transactional
    private void removeFromParent(Item item) {
        if (item.getParent() != null) {
            item.getParent().getChildren().remove(item);
            item.setParent(null);
        }
    }

    /**
     * Рекурсивное добавление родителей элемента в список
     *
     * @param item элемент для получения родителей
     * @param set  список родителей
     */
    @Transactional
    private void addParentsToSet(Item item, Set<Item> set) {
        Item parent = item.getParent();
        if (parent != null) {
            // Если элемент уже в списке, то его родители точно там
            if (set.add(parent)) {
                addParentsToSet(parent, set);
            }
        }
    }

    @Transactional
    private void calculateSize(Item item, LocalDateTime date, Set<Item> notVisitedList) {
        if (item != null) {
            // Если файл или уже обработан, то ничего не делаем
            if (item.getType() == SystemItemType.FILE || !notVisitedList.contains(item)) return;

            Set<Item> children = item.getChildren();
            long size = 0L;
            if (children.size() > 0) {
                for (Item child : children) {
                    // Если ребенок изменен, но не перерасчитан - запускаем расчет для него
                    if (notVisitedList.contains(child)) {
                        calculateSize(child, date, notVisitedList);
                    }
                    Long child_size = child.getSize();
                    if (child_size == null) child_size = 0L;
                    size += child_size;
                }
            }
            // Добавляем данные
            item.setSize(size);
            item.setDate(date);
            // Удаляем из списка не обработанных
            notVisitedList.remove(item);
        }
    }

    @Override
    @Transactional
    public void removeItem(String id) {
        if (itemRepository.existsByItemStringId(id)) {
            itemRepository.deleteByItemStringId(id);
            historyItemRepository.deleteByItemStringId(id);
        } else {
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Item findByIdItem(String id) {
        return itemRepository.findByItemStringId(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findFilesByDayAgo(LocalDateTime endDate) {
        LocalDateTime startDate = endDate.minusHours(24);
        return itemRepository.findByDateBetweenAndType(startDate, endDate, SystemItemType.FILE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryItem> findHistoryByIdAndRange(String id, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)){
            log.warn("Item id: {} Start date {} must be before end date {}", id, startDate, endDate);
            throw new ItemValidationException(id, "Start date must be before end date");
        }
        if (!itemRepository.existsByItemStringId(id)){
            log.warn("Item id: {} can't found", id);
            throw new ItemNotFoundException(id);
        }
        return historyItemRepository.findByItemStringIdAndDateBetween(id, startDate, endDate);
    }


}
