package com.ucoban.medsoft.accountserver.dao.client;

import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "documentms", fallback = DocumentFeignClientFallBack.class)
public interface IDocumentFeignClient {

    @GetMapping(value = "/api/photos/profile/{userId}",consumes = "application/json")
    ResponseEntity<ApiResponseDto<String>> getUserProfilPhoto(@PathVariable("userId") String userId);

}
