package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class BusinessTripReportDto {
    private BigInteger numberOfTrips;
    private String name;
    private String surname;
    private String project;
    private String client;
    private BigInteger howManyByJit;
}
