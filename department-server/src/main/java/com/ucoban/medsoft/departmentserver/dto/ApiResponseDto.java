package com.ucoban.medsoft.departmentserver.dto;

public record ApiResponseDto<T> (long time, T data, int status) {
}
