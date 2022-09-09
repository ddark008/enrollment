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
 * A delegate to be called by the {@link UpdatesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public interface UpdatesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /updates
     * Получение списка **файлов**, которые были обновлены за последние 24 часа включительно [date - 24h, date] от времени переданном в запросе. 
     *
     * @param date Дата и время запроса. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400 (required)
     * @return Список элементов, которые были обновлены. (status code 200)
     *         or Невалидная схема документа или входные данные не верны. (status code 400)
     * @see UpdatesApi#updatesGet
     */
    default ResponseEntity<SystemItemHistoryResponse> updatesGet(OffsetDateTime date) {
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
