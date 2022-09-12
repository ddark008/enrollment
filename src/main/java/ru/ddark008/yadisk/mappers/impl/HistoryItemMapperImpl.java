package ru.ddark008.yadisk.mappers.impl;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.HistoryItemMapper;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;

import java.time.ZoneOffset;

@Component
public class HistoryItemMapperImpl implements HistoryItemMapper {
    @Override
    public HistoryItem toEntity(Item dto) {
        return HistoryItem.builder()
                .itemStringId(dto.getItemStringId())
                .url(dto.getUrl())
                .parentStringId(dto.getParentStringId())
                .type(dto.getType())
                .size(dto.getSize())
                .date(dto.getDate())
                .build();
    }

    /**
     * Преобразование в {@code SystemItemHistoryUnit}
     * @param entity элмент для преобразования
     * @return новый экземпляр класса {@code SystemItemHistoryUnit} c меткой времени в часовом поясе UTC
     */
    @Override
    public SystemItemHistoryUnit toSystemItemHistoryUnit(HistoryItem entity) {
        return new SystemItemHistoryUnit()
                .id(entity.getItemStringId())
                .url(entity.getUrl())
                .parentId(entity.getParentStringId())
                .size(entity.getSize())
                .date(entity.getDate().atOffset(ZoneOffset.UTC));
    }
}
