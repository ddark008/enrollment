package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.NodeApiDelegate;
import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.exceptions.ItemValidationException;
import ru.ddark008.yadisk.mappers.HistoryItemMapper;
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
public class NodeApiDelegateImpl implements NodeApiDelegate {
    private final ItemService itemService;
    private final HistoryItemMapper historyItemMapper;

    @Autowired
    public NodeApiDelegateImpl(ItemService itemService, HistoryItemMapper historyItemMapper) {
        this.itemService = itemService;
        this.historyItemMapper = historyItemMapper;
    }

    @Override
    public ResponseEntity<SystemItemHistoryResponse> nodeIdHistoryGet(String id,
                                                                      OffsetDateTime dateStart,
                                                                      OffsetDateTime dateEnd) {
        log.info("Get history for id: {} from {} til {}:", id, dateStart, dateEnd);
        if (dateStart == null || dateEnd == null) {
            throw new ItemValidationException(id, "Даты интервала должна соотвествовать ISO 8601");
        }
        LocalDateTime localStartDate = dateStart.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime localEndDate = dateEnd.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        // Запрос в БД списка в интервале [localStartDate; localEndDate]
        List<HistoryItem> itemList = itemService.findHistoryByIdAndRange(id, localStartDate, localEndDate);
        // Преобразование в SystemItemHistoryUnit и вывод в логи
        List<SystemItemHistoryUnit> systemItemHistoryUnitList = itemList.stream()
                .peek(item -> log.info("Id: {}, date: {}", item.getItemStringId(), item.getDate()))
                .map(historyItemMapper::toSystemItemHistoryUnit)
                .collect(Collectors.toList());
        SystemItemHistoryResponse response = new SystemItemHistoryResponse().items(systemItemHistoryUnitList);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);

    }
}
