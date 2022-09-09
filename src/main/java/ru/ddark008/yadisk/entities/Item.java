package ru.ddark008.yadisk.entities;

import lombok.*;
import org.hibernate.Hibernate;
import ru.ddark008.yadisk.model.SystemItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "filesystem",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_string_id"})
        })
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
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
     * id родительской папки
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
    private OffsetDateTime date;

    /**
     * Возвращает date со смещением UTC, так как Hibernate 5 не умеет сохранять часовой пояс в БД
     * @return
     */
    public OffsetDateTime getDate() {
        return date.withOffsetSameInstant(ZoneOffset.UTC);
    }

    /**
     * Помещает data в БД со смещением UTC, так как Hibernate 5 не умеет сохранять часовой пояс в БД
     * @param date
     */
    public void setDate(OffsetDateTime date) {
        this.date = date.withOffsetSameInstant(ZoneOffset.UTC);
    }

    /**
     * ID элемента родителя из БД
     */
    @Transient
    private Long parentId;

    /**
     * Родительский элемент
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Item parent;

    /**
     * Набор дочерних элементов
     */
    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Set<Item> children = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
