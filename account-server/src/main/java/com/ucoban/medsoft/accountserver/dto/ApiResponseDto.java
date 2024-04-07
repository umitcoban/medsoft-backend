package com.ucoban.medsoft.accountserver.dto;

public record ApiResponseDto <T> (long time, T data, int status) {
}
