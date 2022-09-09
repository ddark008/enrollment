package ru.ddark008.yadisk.entities;

import lombok.*;
import org.hibernate.Hibernate;
import ru.ddark008.yadisk.model.SystemItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "history")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItem {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    /**
     * Уникальный идентификатор
     */
    @Column(name = "item_string_id", nullable = false, updatable = false)
    @NotNull
    private String itemStringId;

    /**
     * Ссылка на файл. Для папок поле равнно null.
     */
    @Column(name = "url")
    private String url;

    /**
     * Id родительской папки
     */
    @Column(name = "parent_string_id")
    private String parentStringId;

    /**
     * Тип элемента - папка или файл
     */
    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private SystemItemType type;

    /**
     * Целое число, для папок поле должно содержать размер содержимого
     */
    @Column(name = "size")
    private Long size;

    /**
     * Время обновления добавляемых элементов.
     */
    @Column(name = "DATE", nullable = false)
    @NotNull
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HistoryItem that = (HistoryItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
