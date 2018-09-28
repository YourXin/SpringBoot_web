package com.imooc.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GirlControllerTest {


    /**
     * 针对Controller进行测试
     */

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GirlController girlController;

    @Test
    public void girlList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/girls213"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("abc"));
    }

    @Test
    public void girlAdd() {
    }

    @Test
    public void girlFindOne() {
    }

    @Test
    public void girlUpdate() {
    }

    @Test
    public void girlDelete() {
    }

    @Test
    public void girlListByAge() {
    }

    @Test
    public void girlTwo() {
    }

    @Test
    public void getAge() {
    }
}