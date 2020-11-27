package com.project.urlshortner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BaseConversionTest {
 
  @InjectMocks
  BaseConversion baseConversion;

  @ParameterizedTest(name = "Test The Encoding Logic")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideCorrectNumberToEncode"})
  @DisplayName("Method: Encode -  Type: Positive")
  public void testEncode(long id) {
    String encodedValue = baseConversion.encode(id);
    assertEquals(encodedValue, "t4");
  }

  @ParameterizedTest(name = "Test The Decoding Logic")
  @MethodSource({
      "com.project.urlshortner.utils.HelperTest#provideValidShortUrls"})
  @DisplayName("Method: Decode -  Type: Positive")
  public void testDecode(String shortUrl) {
    long decodedValue = baseConversion.decode("t4");
    assertEquals(decodedValue, 1234l);
  }


}
