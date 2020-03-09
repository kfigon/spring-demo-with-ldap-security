package com.example.demo.controller;

import com.example.demo.dto.WorkReportResultDto;
import com.example.demo.repository.WorkReportRepository;
import com.example.demo.service.CsvMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
@Slf4j
public class WorkReportController {

    @Autowired
    private WorkReportRepository workReportRepository;

    @Autowired
    private CsvMappingService csvMapper;

    @GetMapping(value = "/person/{name}/{surname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WorkReportResultDto> getReportByPerson(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("getReportByPerson called for: {} {}, pageStart: {}, pageSize: {}",
                name, surname, pageStart, pageSize);
        List<WorkReportResultDto> result = workReportRepository.getReportByPerson(name, surname, pageStart, pageSize);
        log.info("getReportByPerson found {} elements", result.size());

        return result;
    }

    @GetMapping(value = "/person/{name}/{surname}/csv", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getReportByPersonCsv(@PathVariable String name,
                                                       @PathVariable String surname,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                          HttpServletResponse response) throws IOException {

        log.info("getReportByPersonCsv called for: {} {}, pageStart: {}, pageSize: {}",
                name, surname, pageStart, pageSize);
        List<WorkReportResultDto> result = workReportRepository.getReportByPerson(name, surname, pageStart, pageSize);
        log.info("getReportByPersonCsv found {} elements", result.size());

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"persons-report.csv\"");

        //create a csv writer
       csvMapper.writeToCsv(result, WorkReportResultDto.class, response.getOutputStream());
    }

    @GetMapping(value = "/client/{client}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WorkReportResultDto> getReportByClient(@PathVariable String client,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("getReportByClient called for: {}, pageStart: {}, pageSize: {}",
                client, pageStart, pageSize);
        List<WorkReportResultDto> result = workReportRepository.getReportByClient(client, pageStart, pageSize);
        log.info("getReportByClient found {} elements", result.size());

        return result;
    }

    @GetMapping(value = "/project/{project}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WorkReportResultDto> getReportByProject(@PathVariable String project,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageStart,
                                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize){

        log.info("getReportByProject called for: {}, pageStart: {}, pageSize: {}",
                project, pageStart, pageSize);
        List<WorkReportResultDto> result = workReportRepository.getReportByProject(project, pageStart, pageSize);
        log.info("getReportByProject found {} elements", result.size());

        return result;
    }

}
