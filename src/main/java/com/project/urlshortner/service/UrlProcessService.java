package com.project.urlshortner.service;

import com.project.urlshortner.aspectj.LogInputOutput;
import com.project.urlshortner.exception.InvalidURLRequestException;
import com.project.urlshortner.exception.ResourceAlreadyExistException;
import com.project.urlshortner.exception.ResourceNotFoundException;
import com.project.urlshortner.model.dto.UrlDetailDTO;
import com.project.urlshortner.model.entity.UrlDetailEntity;
import com.project.urlshortner.repository.UrlDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;


@Service
@Slf4j
public class UrlProcessService {

  private final UrlDetailRepository urlDetailRepository;
  private final BaseConversion conversion;

  public UrlProcessService(UrlDetailRepository urlDetailRepository, BaseConversion baseConversion) {
    this.urlDetailRepository = urlDetailRepository;
    this.conversion = baseConversion;
  }

  @Transactional
  @LogInputOutput
  public UrlDetailDTO createShortUrl(String originalUrl) throws ResourceAlreadyExistException {
    validateURL(originalUrl);
    checkIfUrlAlreadyExist(originalUrl);
    log.info("Save the URL in Database");
    UrlDetailEntity savedUrlDetailEntity = urlDetailRepository
        .save(generateUrlDetailEntity(originalUrl));
    return prepareDTO(originalUrl, savedUrlDetailEntity);
  }

  @LogInputOutput
  public String getOriginalUrlAndForwardRequest(String shortUrlCode)
      throws ResourceNotFoundException {

    log.info("Decode the ShortUrl Code to retrieve the associated ID from DB");
    long id = conversion.decode(shortUrlCode);
    UrlDetailEntity urlDetailEntity = urlDetailRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "There is no original Url associated with shortUrl Pattern '" + shortUrlCode + "'"));
    return urlDetailEntity.getOriginalUrl();
  }

  private void validateURL(String originalUrl) {
    log.info("Checking if the URL is Valid");
    try {
      URL url = new URL(originalUrl);
    } catch (MalformedURLException e) {
      throw new InvalidURLRequestException("The Original URL is not Valid");
    }
  }

  private void checkIfUrlAlreadyExist(String originalUrl) throws ResourceAlreadyExistException {
    log.info("Checking if the URL is already Existing in Database");
    UrlDetailEntity savedUrlDetailEntity = urlDetailRepository.findByOriginalUrl(originalUrl);
    if (savedUrlDetailEntity != null) {
      throw new ResourceAlreadyExistException(
          "The Short URL Pattern Already exists for the passed original URL");
    }
  }


  private UrlDetailDTO prepareDTO(String originalUrl, UrlDetailEntity savedUrlDetailEntity) {
    UrlDetailDTO urlDetailDTO = new UrlDetailDTO();
    if (savedUrlDetailEntity != null) {
      log.info("Encode to retrieve Short URL");
      urlDetailDTO = new UrlDetailDTO(conversion.encode(savedUrlDetailEntity.getId()), originalUrl);
      log.info("Encode Successful , Return the DTO");
      return urlDetailDTO;
    }
    return urlDetailDTO;
  }

  public UrlDetailEntity generateUrlDetailEntity(String originalUrl) {
    UrlDetailEntity urlDetailEntity = new UrlDetailEntity();
    urlDetailEntity.setOriginalUrl(originalUrl);
    urlDetailEntity.setCreatedDate(LocalDateTime.now());
    return urlDetailEntity;
  }


}
