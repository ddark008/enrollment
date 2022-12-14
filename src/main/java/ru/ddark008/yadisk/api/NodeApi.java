/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.ddark008.yadisk.api;

import org.springframework.format.annotation.DateTimeFormat;
import ru.ddark008.yadisk.model.Error;
import java.time.OffsetDateTime;
import ru.ddark008.yadisk.model.SystemItemHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
@Validated
@Tag(name = "node", description = "the node API")
public interface NodeApi {

    default NodeApiDelegate getDelegate() {
        return new NodeApiDelegate() {};
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
     */
    @Operation(
        operationId = "nodeIdHistoryGet",
        tags = { "Дополнительные задачи" },
        responses = {
            @ApiResponse(responseCode = "200", description = "История по элементу.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SystemItemHistoryResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректный формат запроса или некорректные даты интервала.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "404", description = "Элемент не найден.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/node/{id}/history",
        produces = { "application/json" }
    )
    default ResponseEntity<SystemItemHistoryResponse> nodeIdHistoryGet(
        @Parameter(name = "id", description = "id элемента для которого будет отображаться история", required = true) @PathVariable("id") String id,
        @Parameter(name = "dateStart", description = "Дата и время начала интервала, для которого считается история. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.") @Valid @RequestParam(value = "dateStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateStart,
        @Parameter(name = "dateEnd", description = "Дата и время конца интервала, для которого считается история. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.") @Valid @RequestParam(value = "dateEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateEnd
    ) {
        return getDelegate().nodeIdHistoryGet(id, dateStart, dateEnd);
    }

}
