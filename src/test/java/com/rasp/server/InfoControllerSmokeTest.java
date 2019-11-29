package com.rasp.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc
public class InfoControllerSmokeTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void populateData() throws Exception {
        mvc.perform(get("/test/populate/HUMIDITY"))
                .andExpect(status().is2xxSuccessful());
        mvc.perform(get("/test/populate/LIGHT"))
                .andExpect(status().is2xxSuccessful());
        mvc.perform(get("/test/populate/TEMPERATURE"))
                .andExpect(status().is2xxSuccessful());
        mvc.perform(get("/test/populate/settings"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void getHumidity() throws Exception {
        mvc.perform(get("/current/HUMIDITY"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getLightIndex() throws Exception {
        mvc.perform(get("/current/LIGHT"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getTemperature() throws Exception {
        mvc.perform(get("/current/TEMPERATURE"))
                .andExpect(status().is2xxSuccessful());
    }
}
