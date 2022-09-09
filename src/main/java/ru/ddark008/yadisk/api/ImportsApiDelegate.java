package ru.ddark008.yadisk.api;

import ru.ddark008.yadisk.model.Error;
import ru.ddark008.yadisk.model.SystemItemImportRequest;
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
 * A delegate to be called by the {@link ImportsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
public interface ImportsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /imports
     * Импортирует элементы файловой системы. Элементы импортированные повторно обновляют текущие. Изменение типа элемента с папки на файл и с файла на папку не допускается. Порядок элементов в запросе является произвольным.    - id каждого элемента является уникальным среди остальных элементов   - поле id не может быть равно null   - родителем элемента может быть только папка   - принадлежность к папке определяется полем parentId   - элементы могут не иметь родителя (при обновлении parentId на null элемент остается без родителя)   - поле url при импорте папки всегда должно быть равно null   - размер поля url при импорте файла всегда должен быть меньше либо равным 255   - поле size при импорте папки всегда должно быть равно null   - поле size для файлов всегда должно быть больше 0   - при обновлении элемента обновленными считаются **все** их параметры   - при обновлении параметров элемента обязательно обновляется поле **date** в соответствии с временем обновления   - в одном запросе не может быть двух элементов с одинаковым id   - дата обрабатывается согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, ответом будет код 400.  Гарантируется, что во входных данных нет циклических зависимостей и поле updateDate монотонно возрастает. Гарантируется, что при проверке передаваемое время кратно секундам. 
     *
     * @param systemItemImportRequest  (optional)
     * @return Вставка или обновление прошли успешно. (status code 200)
     *         or Невалидная схема документа или входные данные не верны. (status code 400)
     * @see ImportsApi#importsPost
     */
    default ResponseEntity<Void> importsPost(SystemItemImportRequest systemItemImportRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
