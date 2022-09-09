package ru.ddark008.yadisk.mappers.impl;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.model.SystemItemImport;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemMapperImpl implements ItemMapper {
    @Override
    public Item toEntity(SystemItemImport dto, OffsetDateTime date) {
        return Item.builder()
                .itemStringId(dto.getId())
                .url(dto.getUrl().orElse(null))
                .parentStringId(dto.getParentId().orElse(null))
                .type(dto.getType())
                .size(dto.getSize().orElse(null))
                .date(date)
                .build();
    }

    @Override
    public SystemItem toSystemItem(Item entity) {
        SystemItem systemItem = new SystemItem()
                .id(entity.getItemStringId())
                .url(entity.getUrl())
                .date(entity.getDate())
                .parentId(entity.getParentStringId())
                .type(entity.getType())
                .size(entity.getSize());
        // Рекурсивное добавление потомков
        Set<Item> childrenItem = entity.getChildren();
        if (childrenItem == null || childrenItem.size() == 0){
            switch (entity.getType()){
                case FILE -> systemItem.setChildren(null);
                case FOLDER -> systemItem.setChildren(new ArrayList<>());
            }
        } else {
            List<SystemItem> childrenSystemItem = childrenItem.stream().map(this::toSystemItem).collect(Collectors.toList());
            systemItem.setChildren(childrenSystemItem);
        }
        return systemItem;
    }

    @Override
    public SystemItemHistoryUnit toSystemItemHistoryUnit(Item entity) {
        return null;
    }
}
