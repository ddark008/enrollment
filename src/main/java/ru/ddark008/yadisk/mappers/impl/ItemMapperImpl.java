package ru.ddark008.yadisk.mappers.impl;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.model.SystemItemImport;

import java.time.OffsetDateTime;

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
        return null;
    }

    @Override
    public SystemItemHistoryUnit toSystemItemHistoryUnit(Item entity) {
        return null;
    }
}
