package ru.ddark008.yadisk.mappers;

import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.model.SystemItemImport;

import java.time.OffsetDateTime;

public interface ItemMapper {
    Item toEntity(SystemItemImport dto, OffsetDateTime date);

    SystemItem toSystemItem(Item entity);

    SystemItemHistoryUnit toSystemItemHistoryUnit(Item entity);
}
