package ru.ddark008.yadisk.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.format.annotation.DateTimeFormat;
import ru.ddark008.yadisk.model.SystemItemType;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * SystemItemHistoryUnit
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public class SystemItemHistoryUnit {

  @JsonProperty("id")
  private String id;

  @JsonProperty("url")
  private JsonNullable<String> url = JsonNullable.undefined();

  @JsonProperty("parentId")
  private JsonNullable<String> parentId = JsonNullable.undefined();

  @JsonProperty("type")
  private SystemItemType type;

  @JsonProperty("size")
  private JsonNullable<Long> size = JsonNullable.undefined();

  @JsonProperty("date")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime date;

  public SystemItemHistoryUnit id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Уникальный идентфикатор
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "элемент_1_1", description = "Уникальный идентфикатор", required = true)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SystemItemHistoryUnit url(String url) {
    this.url = JsonNullable.of(url);
    return this;
  }

  /**
   * Ссылка на файл. Для папок поле равнно null.
   * @return url
  */
  
  @Schema(name = "url", description = "Ссылка на файл. Для папок поле равнно null.", required = false)
  public JsonNullable<String> getUrl() {
    return url;
  }

  public void setUrl(JsonNullable<String> url) {
    this.url = url;
  }

  public SystemItemHistoryUnit parentId(String parentId) {
    this.parentId = JsonNullable.of(parentId);
    return this;
  }

  /**
   * id родительской папки
   * @return parentId
  */
  
  @Schema(name = "parentId", example = "элемент_1_1", description = "id родительской папки", required = false)
  public JsonNullable<String> getParentId() {
    return parentId;
  }

  public void setParentId(JsonNullable<String> parentId) {
    this.parentId = parentId;
  }

  public SystemItemHistoryUnit type(SystemItemType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @NotNull @Valid 
  @Schema(name = "type", required = true)
  public SystemItemType getType() {
    return type;
  }

  public void setType(SystemItemType type) {
    this.type = type;
  }

  public SystemItemHistoryUnit size(Long size) {
    this.size = JsonNullable.of(size);
    return this;
  }

  /**
   * Целое число, для папки - это суммарный размер всех её элементов.
   * @return size
  */
  
  @Schema(name = "size", description = "Целое число, для папки - это суммарный размер всех её элементов.", required = false)
  public JsonNullable<Long> getSize() {
    return size;
  }

  public void setSize(JsonNullable<Long> size) {
    this.size = size;
  }

  public SystemItemHistoryUnit date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Время последнего обновления элемента.
   * @return date
  */
  @NotNull @Valid 
  @Schema(name = "date", description = "Время последнего обновления элемента.", required = true)
  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemItemHistoryUnit systemItemHistoryUnit = (SystemItemHistoryUnit) o;
    return Objects.equals(this.id, systemItemHistoryUnit.id) &&
        Objects.equals(this.url, systemItemHistoryUnit.url) &&
        Objects.equals(this.parentId, systemItemHistoryUnit.parentId) &&
        Objects.equals(this.type, systemItemHistoryUnit.type) &&
        Objects.equals(this.size, systemItemHistoryUnit.size) &&
        Objects.equals(this.date, systemItemHistoryUnit.date);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, parentId, type, size, date);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemItemHistoryUnit {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

