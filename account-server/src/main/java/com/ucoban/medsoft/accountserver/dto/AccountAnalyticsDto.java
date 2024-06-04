package com.ucoban.medsoft.accountserver.dto;

import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

public record AccountAnalyticsDto(Map<String, Integer> accountsCount, List<Pair<Long, Date>> accountsCountWithDate) {
}
