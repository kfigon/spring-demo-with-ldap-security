package com.example.demo.repository;

import com.example.demo.dto.WorkReportResultDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WorkReportRepositoryTest {

    @Autowired
    private WorkReportRepository workReportRepository;

    @Test
    void getReportByPerson() {
        List<WorkReportResultDto> result = workReportRepository.getReportByPerson("Kamil", "Figoń",0,50);
        assertThat(result).isNotEmpty();
        assertThat(result).extracting(WorkReportResultDto::getSurname).allMatch("Figoń"::equals);
    }

    @Test
    void getReportForNonExistingPerson(){
        List<WorkReportResultDto> result = workReportRepository.getReportByPerson("dsa", "asd",0,1);
        assertThat(result).isEmpty();
    }

    @Test
    void getReportByClient() {
        List<WorkReportResultDto> result = workReportRepository.getReportByClient("VPS", 0,50);
        assertThat(result).isNotEmpty().hasSize(50);
        assertThat(result).extracting(WorkReportResultDto::getClient).allMatch("VPS"::equals);
    }

    @Test
    void getReportByProject() {
        List<WorkReportResultDto> result = workReportRepository.getReportByProject("VPS Services API", 0,50);
        assertThat(result).isNotEmpty().hasSize(50);
        assertThat(result).extracting(WorkReportResultDto::getClient).allMatch("VPS"::equals);
    }
}