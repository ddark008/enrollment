package ru.ddark008.yadisk.mappers;

import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
/**
 * Преобразование @{code {@link HistoryItem}} в другие классы и обратно
 */
public interface HistoryItemMapper {
    /**
     * Преобразование из класса {@code Item} и даты в класс {@code HistoryItem}
     * @param dto изменный элемент
     * @return новый экземпляр класса {@code HistoryItem}
     */
    HistoryItem toEntity(Item dto);

    SystemItemHistoryUnit toSystemItemHistoryUnit(HistoryItem entity);
}
