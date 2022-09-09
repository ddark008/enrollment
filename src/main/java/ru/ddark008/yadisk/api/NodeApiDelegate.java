package ru.ddark008.yadisk.api;

import org.springframework.format.annotation.DateTimeFormat;
import ru.ddark008.yadisk.model.Error;
import java.time.OffsetDateTime;
import ru.ddark008.yadisk.model.SystemItemHistoryResponse;
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
 * A delegate to be called by the {@link NodeApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public interface NodeApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /node/{id}/history
     * Получение истории обновлений по элементу за заданный полуинтервал [from, to). История по удаленным элементам недоступна.  - размер папки - это суммарный размер всех её элементов - можно получить статистику за всё время. 
     *
     * @param id id элемента для которого будет отображаться история (required)
     * @param dateStart Дата и время начала интервала, для которого считается история. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400. (optional)
     * @param dateEnd Дата и время конца интервала, для которого считается история. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400. (optional)
     * @return История по элементу. (status code 200)
     *         or Некорректный формат запроса или некорректные даты интервала. (status code 400)
     *         or Элемент не найден. (status code 404)
     * @see NodeApi#nodeIdHistoryGet
     */
    default ResponseEntity<SystemItemHistoryResponse> nodeIdHistoryGet(String id,
        OffsetDateTime dateStart,
        OffsetDateTime dateEnd) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"items\" : [ { \"id\" : \"элемент_1_4\", \"url\" : \"/file/url1\", \"date\" : \"2022-05-28T21:12:01.000Z\", \"parentId\" : \"элемент_1_1\", \"size\" : 234, \"type\" : \"FILE\" }, { \"id\" : \"элемент_1_4\", \"url\" : \"/file/url1\", \"date\" : \"2022-05-28T21:12:01.000Z\", \"parentId\" : \"элемент_1_1\", \"size\" : 234, \"type\" : \"FILE\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
