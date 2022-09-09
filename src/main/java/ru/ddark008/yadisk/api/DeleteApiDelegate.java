package ru.ddark008.yadisk.api;

import org.springframework.format.annotation.DateTimeFormat;
import ru.ddark008.yadisk.model.Error;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link DeleteApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public interface DeleteApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /delete/{id}
     * Удалить элемент по идентификатору. При удалении папки удаляются все дочерние элементы. Доступ к истории обновлений удаленного элемента невозможен.  **Обратите, пожалуйста, внимание на этот обработчик. При его некорректной работе тестирование может быть невозможно.** 
     *
     * @param id Идентификатор (required)
     * @param date Дата и время запроса (required)
     * @return Удаление прошло успешно. (status code 200)
     *         or Невалидная схема документа или входные данные не верны. (status code 400)
     *         or Элемент не найден. (status code 404)
     * @see DeleteApi#deleteIdDelete
     */
    default ResponseEntity<Void> deleteIdDelete(String id,
        OffsetDateTime date) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
