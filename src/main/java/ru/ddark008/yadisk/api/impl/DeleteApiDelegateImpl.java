package ru.ddark008.yadisk.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.api.DeleteApiDelegate;
import ru.ddark008.yadisk.services.ItemService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
        LocalDateTime localDate = date.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        itemService.removeItem(id, localDate);
        return ResponseEntity.ok().build();
    }
}
