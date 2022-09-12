package ru.ddark008.yadisk.mappers;

import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.model.SystemItemImport;

import java.time.OffsetDateTime;

/**
 * Преобразование @{code Item} в другие классы и обратно
 */
public interface ItemMapper {
    /**
     * Преобразование из импортируемого класса {@code SystemItemImport} и даты в класс {@code Item}
     * @param dto импортируемый элемент
     * @param date дата импорта
     * @return новый экземпляр класса {@code Item}
     */
    Item toEntity(SystemItemImport dto, OffsetDateTime date);
    SystemItem toSystemItem(Item entity);

    SystemItemHistoryUnit toSystemItemHistoryUnit(Item entity);
}
