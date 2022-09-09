package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.ImportsApiDelegate;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItemImportRequest;
import ru.ddark008.yadisk.services.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ImportsApiDelegateImpl implements ImportsApiDelegate {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ImportsApiDelegateImpl(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    public ResponseEntity<Void> importsPost(SystemItemImportRequest systemItemImportRequest) {
        List<Item> importItems = systemItemImportRequest.getItems().stream()
                .peek(itemImport -> log.info("Import item: {}", itemImport.getId()))
                .map(itemImport -> itemMapper.toEntity(itemImport, systemItemImportRequest.getUpdateDate())).collect(Collectors.toList());
        itemService.importItems(importItems);
        return ResponseEntity.ok().build();
    }
}
