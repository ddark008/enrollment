package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.UpdatesApiDelegate;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItemHistoryResponse;
import ru.ddark008.yadisk.model.SystemItemHistoryUnit;
import ru.ddark008.yadisk.services.ItemService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UpdatesApiDelegateImpl implements UpdatesApiDelegate {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public UpdatesApiDelegateImpl(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    public ResponseEntity<SystemItemHistoryResponse> updatesGet(OffsetDateTime date) {
        log.info("Get list of files for 24 hours ago from {}:", date);
        LocalDateTime localDateTime = date.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        // Запрос в БД списка в дипазоне [localDateTime-24h; localDateTime]
        List<Item> itemList = itemService.findFilesByDayAgo(localDateTime);
        // Преобразование в SystemItemHistoryUnit и вывод в логи
        List<SystemItemHistoryUnit> systemItemHistoryUnitList = itemList.stream()
                .peek(item -> log.info("Id: {}, date: {}", item.getItemStringId(), item.getDate()))
                .map(itemMapper::toSystemItemHistoryUnit)
                .collect(Collectors.toList());
        SystemItemHistoryResponse response = new SystemItemHistoryResponse()
                .items(systemItemHistoryUnitList);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
