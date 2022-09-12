package ru.ddark008.yadisk.mappers.impl;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.model.SystemItemImport;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemMapperImpl implements ItemMapper {
    /**
     * @return новый экземпляр класса {@code Item} c {@link java.time.LocalDateTime} в часовом поясе UTC
     */
    @Override
    public Item toEntity(SystemItemImport dto, OffsetDateTime date) {
        return Item.builder()
                .itemStringId(dto.getId())
                .url(dto.getUrl().orElse(null))
                .parentStringId(dto.getParentId().orElse(null))
                .type(dto.getType())
                .size(dto.getSize().orElse(null))
                .date(date.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime())
                .build();
    }
    /**
     * Преобразование в {@code SystemItem}
     * @param entity элемент для преобразования
     * @return новый экземпляр класса {@code SystemItem} c меткой времени в часовом поясе UTC
     */
    @Override
    public SystemItem toSystemItem(Item entity) {
        SystemItem systemItem = new SystemItem()
                .id(entity.getItemStringId())
                .url(entity.getUrl())
                .date(entity.getDate().atOffset(ZoneOffset.UTC))
                .parentId(entity.getParentStringId())
                .type(entity.getType())
                .size(entity.getSize() == null?0:entity.getSize());
        // Рекурсивное добавление потомков
        Set<Item> childrenItem = entity.getChildren();
        if (childrenItem == null || childrenItem.size() == 0) {
            switch (entity.getType()) {
                case FILE -> systemItem.setChildren(null);
                case FOLDER -> systemItem.setChildren(new ArrayList<>());
            }
        } else {
            List<SystemItem> childrenSystemItem = childrenItem.stream().map(this::toSystemItem).collect(Collectors.toList());
            systemItem.setChildren(childrenSystemItem);
        }
        return systemItem;
    }
    /**
     * Преобразование в {@code SystemItemHistoryUnit}
     * @param entity элемент для преобразования
     * @return новый экземпляр класса {@code SystemItemHistoryUnit} c меткой времени в часовом поясе UTC
     */
    @Override
    public SystemItemHistoryUnit toSystemItemHistoryUnit(Item entity) {
        return new SystemItemHistoryUnit()
                .id(entity.getItemStringId())
                .url(entity.getUrl())
                .date(entity.getDate().atOffset(ZoneOffset.UTC))
                .parentId(entity.getParentStringId())
                .size(entity.getSize() == null?0:entity.getSize())
                .type(entity.getType());
    }
}
