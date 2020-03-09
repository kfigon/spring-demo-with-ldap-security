package com.example.demo.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WorkReportControllerTest extends BaseControllerTest {

    @Test
    void getReportByPersonDefaultParams() throws Exception {
        String surname = "Figoń";
        String name = "Kamil";

        mockMvc.perform(get("/report/person/{name}/{surname}", name, surname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].surname").value(everyItem(is(surname))));
    }

    @Test
    void getReportByPersonInvalidValues() throws Exception {
        mockMvc.perform(get("/report/person/asdasd/fdsfds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(is(0)));
    }

    @Test
    void getReportByPersonChangedPage() throws Exception {
        String surname = "Figoń";
        String name = "Kamil";

        mockMvc.perform(get("/report/person/{name}/{surname}", name, surname)
                    .param("pageStart", "10")
                    .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", lessThanOrEqualTo(5)))
                .andExpect(jsonPath("[*].surname").value(everyItem(is(surname))));
    }

    @Test
    void getReportByPersonCsv() throws Exception {
        String surname = "Figoń";
        String name = "Kamil";

        mockMvc.perform(get("/report/person/{name}/{surname}/csv", name, surname))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    void getReportByClient() throws Exception {
        String clientName = "LPP";
        mockMvc.perform(get("/report/client/{client}", clientName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].client").value(everyItem(is(clientName))));
    }

    @Test
    void getReportByProject() throws Exception {
        String projectName = "VPS Services API";

        mockMvc.perform(get("/report/project/{project}", projectName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].project").value(everyItem(is(projectName))));
    }
}