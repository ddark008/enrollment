package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.DeleteApiDelegate;
import ru.ddark008.yadisk.services.ItemService;

import java.time.OffsetDateTime;

@Slf4j
@Component
public class DeleteApiDelegateImpl implements DeleteApiDelegate {
    private final ItemService itemService;

    @Autowired
    public DeleteApiDelegateImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ResponseEntity<Void> deleteIdDelete(String id, OffsetDateTime date) {
        log.info("Delete item: {}, date: {}", id, date);
        itemService.removeItem(id);
        return ResponseEntity.ok().build();
    }
}
