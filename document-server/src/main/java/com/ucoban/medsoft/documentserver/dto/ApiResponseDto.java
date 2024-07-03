package com.ucoban.medsoft.documentserver.dto;

public record ApiResponseDto<T>(int status, T data, Long time) {
}
