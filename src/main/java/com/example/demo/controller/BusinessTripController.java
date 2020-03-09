package com.example.demo.controller;

import com.example.demo.dto.BusinessTripReportDto;
import com.example.demo.repository.BusinessTripReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report/trip")
@Slf4j
public class BusinessTripController {
    @Autowired
    private BusinessTripReportRepository reportRepository;


    @GetMapping(value = "/person/{name}/{surname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessTripReportDto> findBusinessTripsByPerson(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("findBusinessTripsByPerson called for: {} {}, pageStart: {}, pageSize: {}",
                name, surname, pageStart, pageSize);
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByPerson(name, surname, pageStart, pageSize);
        log.info("findBusinessTripsByPerson found {} elements", result.size());

        return result;
    }


    @GetMapping(value = "/client/{client}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessTripReportDto> findBusinessTripsByClient(@PathVariable String client,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("findBusinessTripsByClient called for: {}, pageStart: {}, pageSize: {}",
                client, pageStart, pageSize);
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByClient(client, pageStart, pageSize);
        log.info("findBusinessTripsByClient found {} elements", result.size());

        return result;
    }

    @GetMapping(value = "/project/{project}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessTripReportDto> findBusinessTripsByProject(@PathVariable String project,
                                                        @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                        @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("findBusinessTripsByProject called for: {}, pageStart: {}, pageSize: {}",
                project,  pageStart, pageSize);
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByProject(project, pageStart, pageSize);
        log.info("findBusinessTripsByProject found {} elements", result.size());

        return result;
    }

}
