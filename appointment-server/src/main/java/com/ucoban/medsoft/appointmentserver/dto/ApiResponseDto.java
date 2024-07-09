package com.ucoban.medsoft.appointmentserver.dto;

public record ApiResponseDto<T> (long time, T data, int status) {
}
