package com.project.urlshortner;

import com.project.urlshortner.controller.UrlProcessController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
class UrlshortnerApplicationTests {

  @Autowired
  UrlProcessController controller;

  @Test
  void contextLoads() {
    assertNotNull(controller);

  }

}
