package com.shvaiale.irishpub.integration.http.controller;

import com.shvaiale.irishpub.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CustomerControllerIT extends IntegrationTestBase {

    private final Integer CUSTOMER_ID = 1;
    private final Integer WRONG_ID = 0;

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    void findById_Success() throws Exception {
        mockMvc.perform(get("/customers/" + CUSTOMER_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("customer/customer"));
    }

    @Test
    void findById_NotFound() throws Exception {
        mockMvc.perform(get("/customers/" + WRONG_ID))
                .andExpect(status().isNotFound());
    }


}