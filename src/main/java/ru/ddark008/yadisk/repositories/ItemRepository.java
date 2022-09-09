package ru.ddark008.yadisk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItemType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Получить элемент из БД по Id
     * @param id - элемента для поиска
     * @return Optional содержащий результат поиска, если ничего не найдено он пустой
     */
    Optional<Item> findByItemStringId(String id);
    /**
     * Проверить наличие в БД элемента по Id
     * @param id - элемента для проверки
     */
    boolean existsByItemStringId(String id);
    /**
     * Удалить элемент по Id
     * @param id - элемента для удаления
     */
    void deleteByItemStringId(String id);

    // select id from filesystem where type = 'FILE' and "date" between '2022-02-03 10:00:00.000' and '2022-02-03 15:00:00.000'
    @Query(value = "from Item t where type = 'FILE' and date BETWEEN :startDate AND :endDate")
    List<Item> findAllFilesBetweenDates(@Param("startDate") OffsetDateTime startDate, @Param("endDate")OffsetDateTime endDate);

    List<Item> findByDateBetweenAndType(OffsetDateTime startDate, OffsetDateTime endDate, SystemItemType type);
}
