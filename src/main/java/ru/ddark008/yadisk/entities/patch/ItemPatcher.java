package ru.ddark008.yadisk.entities.patch;

import org.springframework.stereotype.Component;
import ru.ddark008.yadisk.entities.Item;
import ru.ddark008.yadisk.model.SystemItemType;

@Component
public class ItemPatcher {
    /**
     * Apply data from {@code patch} to {@code item}
     * @param item item for patching
     * @param patch item with new data
     */
    public void apply(Item item, Item patch){
        item.setUrl(patch.getUrl());
        item.setParentStringId(patch.getParentStringId());
        // Для папок размер не задается, а рассчитывается при импорте
        if (item.getType() == SystemItemType.FILE){
            item.setSize(patch.getSize());
        }
        item.setDate(patch.getDate());
    }
}
