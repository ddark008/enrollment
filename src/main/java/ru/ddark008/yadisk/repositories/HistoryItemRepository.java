package ru.ddark008.yadisk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddark008.yadisk.entities.HistoryItem;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
    /**
     * Удалить историю элемента по Id
     *
     * @param id - элемента для удаления
     */
    void deleteByItemStringId(String id);

    /**
     * Получение истории изменений элемента по {@code id} в интервале от {@code startDate} до {@code endDate} включительно
     *
     * @param id        - элемента
     * @param startDate - начало интервала выборки данных (включительно)
     * @param endDate   - окончание интервала выборки данных (включительно)
     * @return - выборка элементов по типу и интервалу обновления
     */
    List<HistoryItem> findByItemStringIdAndDateBetween(String id, LocalDateTime startDate, LocalDateTime endDate);
}
