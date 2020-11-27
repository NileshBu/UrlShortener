package com.project.urlshortner.service;

import com.project.urlshortner.exception.ResourceAlreadyExistException;
import com.project.urlshortner.exception.ResourceNotFoundException;
import com.project.urlshortner.model.dto.UrlDetailDTO;
import com.project.urlshortner.utils.ConstantTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test class to test the methods in VesselCommunicationGroupService
 */
@SpringBootTest
public class UrlProcessServiceTest {
 
  @Autowired
  UrlProcessService urlProcessService;

  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidOriginalUrl"})
  @DisplayName("Method: createShortUrl And getOriginalUrlAndForwardRequest-  Type: Positive")
  @Transactional
  public void testCreateShortUrlAndGetOriginalUrlAndForwardRequest(String originalUrl)
      throws ResourceAlreadyExistException, ResourceNotFoundException {

    UrlDetailDTO urlDetailDTO = urlProcessService.createShortUrl(originalUrl);

    // Assertions
    assertNotNull(urlDetailDTO);
    assertEquals(urlDetailDTO.getShortPattern(), "b");

    urlProcessService.getOriginalUrlAndForwardRequest(urlDetailDTO.getShortPattern());
    assertEquals(urlDetailDTO.getOriginalUrl(), ConstantTest.GOOGLE);


  }


}

