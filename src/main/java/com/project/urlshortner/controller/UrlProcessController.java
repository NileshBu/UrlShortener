package com.project.urlshortner.controller;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import com.project.urlshortner.exception.ResourceAlreadyExistException;
import com.project.urlshortner.exception.ResourceNotFoundException;
import com.project.urlshortner.model.dto.UrlDetailDTO;
import com.project.urlshortner.service.UrlProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "UrlProcessService")
@Slf4j
public class UrlProcessController {

  private UrlProcessService urlProcessService;

  @Autowired
  public UrlProcessController(UrlProcessService urlProcessService) {

    this.urlProcessService = urlProcessService;
  }

  @ApiOperation(value = "Convert new url", notes = "Converts original url to short url", response = UrlDetailDTO.class)
  @PostMapping(path = "/createShortUrl")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<UrlDetailDTO> createShortUrl(
      @Valid @RequestBody(required = true) String originalUrl)
      throws ResourceAlreadyExistException {
    UrlDetailDTO urlDetailDTO = urlProcessService.createShortUrl(originalUrl);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/createShortUrl/")
        .buildAndExpand(urlDetailDTO).toUri();

    return ResponseEntity.created(location).body(urlDetailDTO);

  }

  @ApiOperation(value = "Forward short Url", notes = "Finds original url from short url and Forward the call to it")
  @GetMapping(value = "/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.MOVED_PERMANENTLY)
  @Cacheable(value = "urls", key = "#shortUrl", sync = true)
  public ResponseEntity<?> getOriginalUrlAndForwardRequest(
      @PathVariable String shortUrl) throws ResourceNotFoundException {
    String originalUrl = urlProcessService.getOriginalUrlAndForwardRequest(shortUrl);

    return ResponseEntity.status(MOVED_PERMANENTLY)
        .location(URI.create(originalUrl))
        .build();

  }


}
