package ru.ddark008.yadisk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.exceptions.ItemValidationException;
import ru.ddark008.yadisk.repositories.ItemRepository;
import ru.ddark008.yadisk.services.ItemService;
import ru.ddark008.yadisk.validation.ItemImportValidator;

import java.util.*;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemImportValidator itemImportValidator;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemImportValidator itemImportValidator) {
        this.itemRepository = itemRepository;
        this.itemImportValidator = itemImportValidator;
    }


    @Override
    public void importItems(List<Item> importItems) {
        if (importItems.isEmpty()){
            return;
        }
        // Элементы из запроса
        Map<String, Item> importMap = new HashMap<>();
        // Родительские элементы, если есть
        Map<String, Item> parentMap = new HashMap<>();
        // Элементы из БД, которые обновляются элементами из запроса
        Map<String, Item> updateMap = new HashMap<>();
        // Новые элементы из запроса
        Set<Item> newItems = new HashSet<>();

        for (Item item: importItems) {
            // Проверка на уникальность ID в запросе
            Item oldItem = importMap.put(item.getItemStringId(), item);
            if (oldItem != null){
                throw new ItemValidationException(item.getItemStringId(), "Duplicate ID in request!");
            }
            // Коллекционирование родителей
            String parentID = item.getParentStringId();
            if (parentID != null){
                itemRepository.findByItemStringId(parentID).ifPresent(parentItem -> parentMap.put(parentID, parentItem));
            }
            // Сортировка на новые и обновленные
            itemRepository.findByItemStringId(item.getItemStringId()).ifPresentOrElse(existItem -> updateMap.put(existItem.getItemStringId(), existItem), () -> newItems.add(item));
        }
        // Проверка соблюдений условий импорта
        itemImportValidator.validate(importMap, parentMap, updateMap);
    }
}
