package com.ucoban.medsoft.accountserver.dao.client;

import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public class DocumentFeignClientFallBack implements IDocumentFeignClient{

    @Override
    public ResponseEntity<ApiResponseDto<String>> getUserProfilPhoto(String userId) {
        return ResponseEntity.ok(null);
    }

}
