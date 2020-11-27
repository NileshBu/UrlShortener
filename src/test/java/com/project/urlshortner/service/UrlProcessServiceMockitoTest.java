package com.project.urlshortner.service;

import com.project.urlshortner.exception.InvalidURLRequestException;
import com.project.urlshortner.exception.ResourceAlreadyExistException;
import com.project.urlshortner.exception.ResourceNotFoundException;
import com.project.urlshortner.model.dto.UrlDetailDTO;
import com.project.urlshortner.model.entity.UrlDetailEntity;
import com.project.urlshortner.repository.UrlDetailRepository;
import com.project.urlshortner.utils.ConstantTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlProcessServiceMockitoTest {

  @InjectMocks
  UrlProcessService urlProcessService;

  @Mock
  UrlDetailRepository urlDetailRepository;

  @Mock
  BaseConversion conversion;

  @Test
  public void testCreateShortUrlValid() throws ResourceAlreadyExistException {

    // When
    Mockito.when(urlDetailRepository.save(Mockito.any()))
            .thenReturn(
                    UrlDetailEntity.builder().id(1L).originalUrl(ConstantTest.GOOGLE).createdDate(
                            LocalDateTime.now()).build());
    Mockito.when(conversion.encode(1L))
            .thenReturn("g");

    //Then
    UrlDetailDTO urlDetailDTO = urlProcessService.createShortUrl(ConstantTest.GOOGLE);

    // Assertions
    assertNotNull(urlDetailDTO);
    assertEquals(urlDetailDTO.getShortPattern(), "g");
    assertEquals(urlDetailDTO.getOriginalUrl(), ConstantTest.GOOGLE);

    // verification
    verify(urlDetailRepository, times(1))
        .save(Mockito.any());

  }

  @Test
  public void testCreateShortUrlResourceAlreadyExistException() {

    // When
    Mockito.when(urlDetailRepository.findByOriginalUrl(Mockito.any()))
            .thenReturn(
                    UrlDetailEntity.builder().id(1L).originalUrl(ConstantTest.GOOGLE).createdDate(
                            LocalDateTime.now()).build());

    //Assertion
    Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
      urlProcessService.createShortUrl(
          "https://www.google.ie");
    });

  }

  @Test
  public void testCreateShortUrlInvalidURL() {
    //Assertion
    Assertions.assertThrows(InvalidURLRequestException.class, () -> {
      urlProcessService.createShortUrl(
              ConstantTest.GOOGLEINVALID);
    });

  }

  @Test
  public void testGetOriginalUrlAndForwardRequest() throws ResourceNotFoundException {

    // When
    Mockito.when(urlDetailRepository.findById(Mockito.any()))
            .thenReturn(
                    java.util.Optional.ofNullable(
                            UrlDetailEntity.builder().id(1L).originalUrl(ConstantTest.GOOGLE).createdDate(
                                    LocalDateTime.now()).build()));
    Mockito.when(conversion.decode("g8"))
            .thenReturn(12l);

    //Then
    String originalUrl = urlProcessService.getOriginalUrlAndForwardRequest("g8");

    // Assertions
    assertNotNull(originalUrl);
    assertEquals(originalUrl, ConstantTest.GOOGLE);

    // verification
    verify(urlDetailRepository, times(1))
        .findById(Mockito.any());

  }

  @Test
  public void testGetOriginalUrlAndForwardRequestResourceNotFound() {
    // When
    Mockito.when(conversion.decode("g8"))
        .thenReturn(12l);

    // Assertions
    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      urlProcessService.getOriginalUrlAndForwardRequest(
          "g8");
    });

  }

}
