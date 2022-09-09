package ru.ddark008.yadisk.mappers;

import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;

public interface HistoryItemMapper {
    HistoryItem toEntity(Item dto);

    SystemItemHistoryUnit toSystemItemHistoryUnit(HistoryItem entity);
}
