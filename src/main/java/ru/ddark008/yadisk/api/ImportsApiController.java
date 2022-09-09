package ru.ddark008.yadisk.api;

import ru.ddark008.yadisk.model.Error;
import ru.ddark008.yadisk.model.SystemItemImportRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-09T09:13:57.219578200+03:00[Europe/Moscow]")
@Controller
@RequestMapping("${openapi.yetAnotherDiskOpen.base-path:}")
public class ImportsApiController implements ImportsApi {

    private final ImportsApiDelegate delegate;

    public ImportsApiController(@Autowired(required = false) ImportsApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ImportsApiDelegate() {});
    }

    @Override
    public ImportsApiDelegate getDelegate() {
        return delegate;
    }

}
