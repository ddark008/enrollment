package ru.ddark008.yadisk.api;

import ru.ddark008.yadisk.model.Error;
import ru.ddark008.yadisk.model.SystemItem;
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
 * A delegate to be called by the {@link NodesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public interface NodesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /nodes/{id}
     * Получить информацию об элементе по идентификатору. При получении информации о папке также предоставляется информация о её дочерних элементах.  - для пустой папки поле children равно пустому массиву, а для файла равно null - размер папки - это суммарный размер всех её элементов. Если папка не содержит элементов, то размер равен 0. При обновлении размера элемента, суммарный размер папки, которая содержит этот элемент, тоже обновляется. 
     *
     * @param id Идентификатор элемента (required)
     * @return Информация об элементе. (status code 200)
     *         or Невалидная схема документа или входные данные не верны. (status code 400)
     *         or Элемент не найден. (status code 404)
     * @see NodesApi#nodesIdGet
     */
    default ResponseEntity<SystemItem> nodesIdGet(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"id\" : \"элемент_1_2\", \"url\" : null, \"type\" : \"FOLDER\", \"parentId\" : null, \"date\" : \"2022-05-28T21:12:01.000Z\", \"size\" : 12, \"children\" : [ { \"url\" : \"/file/url1\", \"id\" : \"элемент_1_3\", \"size\" : 4, \"date\" : \"2022-05-28T21:12:01.000Z\", \"type\" : \"FILE\", \"parentId\" : \"элемент_1_2\" }, { \"type\" : \"FOLDER\", \"url\" : null, \"id\" : \"элемент_1_1\", \"date\" : \"2022-05-26T21:12:01.000Z\", \"parentId\" : \"элемент_1_2\", \"size\" : 8, \"children\" : [ { \"url\" : \"/file/url2\", \"id\" : \"элемент_1_4\", \"parentId\" : \"элемент_1_1\", \"date\" : \"2022-05-26T21:12:01.000Z\", \"size\" : 8, \"type\" : \"FILE\" } ] } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
