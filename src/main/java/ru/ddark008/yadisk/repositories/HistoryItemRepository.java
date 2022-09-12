package ru.ddark008.yadisk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddark008.yadisk.entities.HistoryItem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфес для доступа к данным класса {@code HistoryItem} из базы данных
 */
public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
    /**
     * Удалить историю элемента по Id
     *
     * @param id - элемента для удаления
     */
    void deleteByItemStringId(String id);

    /**
     * Получение истории изменений элемента по {@code id} в интервале [{@code startDate};{@code endDate})
     *
     * @param id        элемента
     * @param startDate начало интервала выборки данных (включительно)
     * @param endDate   окончание интервала выборки данных (не включается)
     * @return выборка элементов по id и интервалу обновления
     */
    List<HistoryItem> findByItemStringIdAndDateGreaterThanEqualAndDateLessThan(String id, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Получение истории изменений элемента по {@code id} и метке времени {@code date}
     *
     * @param id   элемента
     * @param date метка времени
     * @return выборка  по {@code id} и метке времени {@code date}
     */
    List<HistoryItem> findByItemStringIdAndDate(String id, LocalDateTime date);
}
