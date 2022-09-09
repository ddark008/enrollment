package ru.ddark008.yadisk.validation;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.exceptions.ItemValidationException;
import ru.ddark008.yadisk.model.SystemItemType;

import java.util.Map;

@Component
public class ItemImportValidator {
    public void validate(Map<String, Item> importMap, Map<String, Item> parentMap, Map<String, Item> updateMap) {
        for (Item importItem : importMap.values()) {
            // Проверка, что не изменяет тип
            Item updateItem = updateMap.get(importItem.getItemStringId());
            if (updateItem != null) {
                if (updateItem.getType() != importItem.getType()) {
                    throw new ItemValidationException(updateItem.getItemStringId(), "Type can't change!");
                }
            }
            // Проверка, что родителем элемента может быть только папка
            String parentId = importItem.getParentStringId();
            if (parentId != null) {
                Item parentItem = parentMap.get(parentId);
                // Если ещё не добавлен в БД
                if (parentItem == null) {
                    parentItem = importMap.get(parentId);
                }
                // Если найден, то не должен быть папкой
                if (parentItem != null) {
                    if (parentItem.getType() != SystemItemType.FOLDER) {
                        throw new ItemValidationException(importItem.getItemStringId(), "Parent item must be FOLDER");
                    }
                } else {
                    throw new ItemValidationException(importItem.getItemStringId(), "Item have parent Id, but parent don't exist");
                }
            }

            // Проверка специфичных условий
            switch (importItem.getType()) {
                case FILE -> {
                    if (importItem.getSize() == null || importItem.getSize() < 0) {
                        throw new ItemValidationException(importItem.getItemStringId(), "File size must be >=0");
                    }
                    if (importItem.getUrl() == null || importItem.getUrl().length() > 255) {
                        throw new ItemValidationException(importItem.getItemStringId(), "File url length must be < 255");
                    }
                }
                case FOLDER -> {
                    if (importItem.getSize() != null) {
                        throw new ItemValidationException(importItem.getItemStringId(), "Folder size must be null");
                    }
                    if (importItem.getUrl() != null) {
                        throw new ItemValidationException(importItem.getItemStringId(), "Folder url must be null");
                    }
                }
            }
        }

    }

}
