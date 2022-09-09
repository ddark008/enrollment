package ru.ddark008.yadisk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItemType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Получить элемент из БД по Id
     *
     * @param id - элемента для поиска
     * @return Optional содержащий результат поиска, если ничего не найдено он пустой
     */
    Optional<Item> findByItemStringId(String id);

    /**
     * Проверить наличие в БД элемента по Id
     *
     * @param id - элемента для проверки
     */
    boolean existsByItemStringId(String id);

    /**
     * Удалить элемент по Id
     *
     * @param id - элемента для удаления
     */
    void deleteByItemStringId(String id);

    /**
     * Получение списка элментов типа {@code type}, которые были обновлены в дипазоне от {@code startDate} до {@code endDate} включительно
     *
     * @param startDate - начало периода выборки данных (включительно)
     * @param endDate - окончание периода выборки данных (включительно)
     * @return - выборка элементов по типу и периоду обновления
     */
    List<Item> findByDateBetweenAndType(LocalDateTime startDate, LocalDateTime endDate, SystemItemType type);
}
