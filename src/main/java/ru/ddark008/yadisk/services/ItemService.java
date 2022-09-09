package ru.ddark008.yadisk.services;


import ru.ddark008.yadisk.entities.Item;

import java.time.OffsetDateTime;
import java.util.List;

public interface ItemService {
    void importItems(List<Item> importItems);

    void removeItem(String id);

    Item findByIdItem(String id);

    List<Item> findFilesByDayAgo(OffsetDateTime date);
}
