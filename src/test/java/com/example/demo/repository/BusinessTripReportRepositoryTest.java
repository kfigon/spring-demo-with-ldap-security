package com.example.demo.repository;

import com.example.demo.dto.BusinessTripReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BusinessTripReportRepositoryTest {

    @Autowired
    private BusinessTripReportRepository reportRepository;

    @Test
    void findBusinessTripsByPerson() {
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByPerson("Kamil", "Figoń", 0, 50);
        assertThat(result).isNotEmpty().hasSize(1);

        BusinessTripReportDto el = result.get(0);
        assertThat(el.getNumberOfTrips()).isEqualTo(el.getHowManyByJit());
    }

    @Test
    void findBusinessTripsByPersonVariousProjects() {
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByPerson("Łukasz", "Drzewiecki", 0, 50);
        assertThat(result).isNotEmpty().hasSizeGreaterThan(1);
    }

    @Test
    void findBusinessTripsByPersonWithDifferentSponsor() {
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByPerson("Maciej", "Chludziński", 0, 50);
        assertThat(result).isNotEmpty().hasSize(1);

        BusinessTripReportDto el = result.get(0);
        assertThat(el.getNumberOfTrips())
                .isNotEqualTo(el.getHowManyByJit());
    }

    @Test
    void findBusinessTripsByClient() {
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByClient("VPS", 0, 50);
        assertThat(result).isNotEmpty();
    }

    @Test
    void findBusinessTripsByProject() {
        List<BusinessTripReportDto> result = reportRepository.findBusinessTripsByProject("VPS Services API", 0, 50);
        assertThat(result).isNotEmpty();
    }
}