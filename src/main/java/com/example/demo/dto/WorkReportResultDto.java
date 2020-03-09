package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WorkReportResultDto {
    private String name;
    private String surname;
    private BigDecimal workedHours;
    private String project;
    private String client;
    private Integer month;
    private Integer year;
}
