package com.project.urlshortner.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlDetailDTO {

  @NotNull
  private String shortPattern;
  @NotNull
  private String originalUrl;

}
