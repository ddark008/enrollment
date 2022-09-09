package ru.ddark008.yadisk.services;


import ru.ddark008.yadisk.entities.Item;

import java.util.List;

public interface ItemService {
    void importItems(List<Item> importItems);
}
