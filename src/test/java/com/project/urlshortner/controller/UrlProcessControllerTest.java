package com.project.urlshortner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.urlshortner.exception.InvalidURLRequestException;
import com.project.urlshortner.exception.ResourceAlreadyExistException;
import com.project.urlshortner.exception.ResourceNotFoundException;
import com.project.urlshortner.model.dto.UrlDetailDTO;
import com.project.urlshortner.service.UrlProcessService;
import com.project.urlshortner.utils.ConstantTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UrlProcessControllerTest {

  private MockMvc mockMvc;

  @MockBean
  private UrlProcessService urlProcessService;

  @InjectMocks
  private UrlProcessController urlProcessController;

  @InjectMocks
  private MockHttpServletRequest request;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(urlProcessController).build();
  }

  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidOriginalUrls"})
  @DisplayName("Method: createShortUrl Valid Request-  Type: Positive")
  public void testCreateShortUrl(String originalUrl) throws ResourceAlreadyExistException {

    // When
    Mockito.when(urlProcessService.createShortUrl(originalUrl))
            .thenReturn(UrlDetailDTO.builder().shortPattern(ConstantTest.PATTERN).originalUrl(originalUrl).build());

    ResponseEntity<UrlDetailDTO> urlDetailDTO = urlProcessController.createShortUrl(originalUrl);

    // Assertions
    assertNotNull(urlDetailDTO);
    assertEquals(urlDetailDTO.getBody().getShortPattern(), ConstantTest.PATTERN);

    // verification
    verify(urlProcessService, times(1))
        .createShortUrl(originalUrl);

  }


  @ParameterizedTest(name = "Test with InValid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideInValidOriginalUrls"})
  @DisplayName("Method: createShortUrl -  Type: Negative to verify InvalidURLRequestException")
  public void testCreateShortUrlInvalidURLRequestException(String originalUrl) throws Exception {
    // When
    Mockito.when(urlProcessService.createShortUrl(originalUrl))
        .thenThrow(new InvalidURLRequestException("The Original URL is not Valid"));

    //Then
    Assertions.assertThrows(InvalidURLRequestException.class, () -> {
      urlProcessController.createShortUrl(
          originalUrl);
    });
    // verification
    verify(urlProcessService, times(1))
        .createShortUrl(originalUrl);

  }

  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidOriginalUrls"})
  @DisplayName("Method: createShortUrl Endpoint Call-  Type: Positive")
  public void testCreateShortUrlEndpointCall(String originalUrl) throws Exception {

    //Given
    ObjectMapper mapper = new ObjectMapper();

    // When
    Mockito.when(urlProcessService.createShortUrl(originalUrl))
            .thenReturn(UrlDetailDTO.builder().shortPattern(ConstantTest.PATTERN).originalUrl(originalUrl).build());

    // Then
    mockMvc.perform(post("/api/createShortUrl")
        .content(mapper.writeValueAsString(originalUrl)).accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
  }

  @ParameterizedTest(name = "Test with Url's that Already Exists")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidOriginalUrls"})
  @DisplayName("Method: createShortUrl -  Type: Negative test to verify ResourceAlreadyExistException")
  public void testCreateShortUrlResourceAlreadyExistException(String originalUrl) throws Exception {
    // When
    Mockito.when(urlProcessService.createShortUrl(originalUrl)).thenThrow(
        new ResourceAlreadyExistException(
            "The Short URL Pattern Already exists for the passed original URL"));
 
    //Then
    Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
      urlProcessController.createShortUrl(
          originalUrl);
    });
    // verification
    verify(urlProcessService, times(1))
        .createShortUrl(originalUrl);

  }


  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidShortUrls"})
  @DisplayName("Method: getOriginalUrlAndForwardRequest -  Type: Positive")
  public void testGetOriginalUrlAndForwardRequest(String shortUrl) throws Exception {

    // When
    Mockito.when(urlProcessService.getOriginalUrlAndForwardRequest(shortUrl))
            .thenReturn(ConstantTest.GOOGLE);

    ResponseEntity<?> originalUrlRetrieved = urlProcessController
        .getOriginalUrlAndForwardRequest(shortUrl);

    // Assertions
    assertNotNull(originalUrlRetrieved);
    assertEquals(originalUrlRetrieved.getHeaders().getLocation().toString(),
            ConstantTest.GOOGLE);

    // verification
    verify(urlProcessService, times(1))
        .getOriginalUrlAndForwardRequest(shortUrl);
  }


  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideNotValidShortUrls"})
  @DisplayName("Method: getOriginalUrlAndForwardRequest -  Type: Negative to verify ResourceNotFoundException for invalid shortUrl")
  public void testGetOriginalUrlAndForwardRequestException(String shortUrl) throws Exception {

    // When
    Mockito.when(urlProcessService.getOriginalUrlAndForwardRequest(shortUrl)).thenThrow(
        new ResourceNotFoundException(
            "There is no original Url associated with shortUrl Pattern '" + shortUrl + "'"));

    // Assertions
    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      urlProcessController.getOriginalUrlAndForwardRequest(
          shortUrl);
    });

    // verification
    verify(urlProcessService, times(1))
        .getOriginalUrlAndForwardRequest(shortUrl);
  }

  @ParameterizedTest(name = "Test with Valid Url's")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidShortUrls"})
  @DisplayName("Method: getOriginalUrlAndForwardRequest Endpoint Call -  Type: Positive")
  public void testGetOriginalUrlAndForwardRequestEndpointCall(String shortUrl) throws Exception {
    // When
    Mockito.when(urlProcessService.getOriginalUrlAndForwardRequest(shortUrl))
            .thenReturn(ConstantTest.GOOGLE);

    // Then
    mockMvc.perform(get("/api/" + shortUrl)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isMovedPermanently()).andReturn();

    // verification
    verify(urlProcessService, times(1))
        .getOriginalUrlAndForwardRequest(shortUrl);

  }


}
