package ru.ddark008.yadisk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.repositories.ItemRepository;
import ru.ddark008.yadisk.services.ItemService;

import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public void importItems(List<Item> importItems) {

    }
}
