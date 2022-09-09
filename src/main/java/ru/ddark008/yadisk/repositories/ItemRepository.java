package ru.ddark008.yadisk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ddark008.yadisk.entities.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemStringId(String id);
}
