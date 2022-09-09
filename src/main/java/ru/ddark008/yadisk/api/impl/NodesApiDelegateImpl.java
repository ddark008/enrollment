package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.NodesApiDelegate;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.mappers.ItemMapper;
import ru.ddark008.yadisk.model.SystemItem;
import ru.ddark008.yadisk.services.ItemService;

@Slf4j
@Component
public class NodesApiDelegateImpl implements NodesApiDelegate {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public NodesApiDelegateImpl(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    public ResponseEntity<SystemItem> nodesIdGet(String id) {
        log.info("Explore node: {}", id);
        Item item = itemService.findByIdItem(id);
        SystemItem systemItem = itemMapper.toSystemItem(item);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(systemItem);
    }
}
