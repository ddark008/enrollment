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
     * Получение списка элементов типа {@code type}, которые были обновлены в интервале от {@code startDate} до {@code endDate} включительно
     *
     * @param startDate - начало интервала выборки данных (включительно)
     * @param endDate - окончание интервала выборки данных (включительно)
     * @return - выборка элементов по типу и интервалу обновления
     */
    List<Item> findByDateBetweenAndType(LocalDateTime startDate, LocalDateTime endDate, SystemItemType type);
}
