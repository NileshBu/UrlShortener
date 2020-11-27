package com.project.urlshortner.utils;


import java.util.stream.Stream;

public class HelperTest {

  private static Stream<String> provideValidOriginalUrls() {
    return Stream.of(ConstantTest.GOOGLE, ConstantTest.YOUTUBE);
  }
 
  private static Stream<String> provideValidOriginalUrl() {
    return Stream.of(ConstantTest.GOOGLE);
  }

  private static Stream<String> provideInValidOriginalUrls() {
    return Stream.of(ConstantTest.GOOGLEINVALID, ConstantTest.YOUTUBEINVALID);
  }

  private static Stream<String> provideValidShortUrls() {
    return Stream.of("t4");
  }

  private static Stream<String> provideNotValidShortUrls() {
    return Stream.of("y*2");

  }

  static Stream<Long> provideCorrectNumberToEncode() {
    return Stream.of(1234L);
  }


}
