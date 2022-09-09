package ru.ddark008.yadisk.services;


import ru.ddark008.yadisk.entities.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemService {
    void importItems(List<Item> importItems);

    void removeItem(String id);

    Item findByIdItem(String id);

    /**
     * Получение списка файлов, которые были обновлены за последние 24 часа включительно [date - 24h, date] от времени переданном в запросе.
     *
     * @param endDate дата окончания 24 часового периода
     * @return список обновленных файлов за этот период
     */
    List<Item> findFilesByDayAgo(LocalDateTime endDate);
}
