package ru.ddark008.yadisk.services;


import ru.ddark008.yadisk.entities.HistoryItem;
import ru.ddark008.yadisk.entities.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemService {
    /**
     * Импорт и валидация элементов
     *
     * @param importItems список элементов для импорта
     */
    void importItems(List<Item> importItems);

    /**
     * Удаление элемента и его потомков по Id
     *
     * @param id элемента для удаления
     */
    void removeItem(String id);

    /**
     * Поиск элемента по Id
     *
     * @param id элемента для поиска
     * @return - элемент, в пртивном случае выброшена ошибка 404
     */
    Item findByIdItem(String id);

    /**
     * Формирование списка файлов, которые были обновлены за последние 24 часа включительно [date - 24h, date] от времени переданном в запросе.
     *
     * @param endDate дата окончания 24 часового периода
     * @return список обновленных файлов за этот период
     */
    List<Item> findFilesByDayAgo(LocalDateTime endDate);

    /**
     * Формирование списка состояний элемента по его Id за указанный период
     *
     * @param id        элемента
     * @param startDate - начало периода (включительно)
     * @param endDate   - конец периода (включительно)
     * @return Список состояний элемента
     */
    List<HistoryItem> findHistoryByIdAndRange(String id, LocalDateTime startDate, LocalDateTime endDate);
}
