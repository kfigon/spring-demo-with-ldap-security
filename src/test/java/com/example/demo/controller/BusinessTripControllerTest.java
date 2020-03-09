package com.example.demo.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BusinessTripControllerTest extends BaseControllerTest{

    @Test
    void findBusinessTripsByPerson() throws Exception {
        String surname = "Figoń";
        String name = "Kamil";

        mockMvc.perform(get("/report/trip/person/{name}/{surname}", name, surname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].surname").value(everyItem(is(surname))));
    }

    @Test
    void findBusinessTripsByClient() throws Exception {
        String clientName = "LPP";

        mockMvc.perform(get("/report/trip/client/{client}", clientName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].client").value(everyItem(is(clientName))));

    }

    @Test
    void findBusinessTripsByProject() throws Exception {
        String projectName = "VPS Services API";

        mockMvc.perform(get("/report/trip/project/{project}", projectName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("[*].project").value(everyItem(is(projectName))));
    }
}