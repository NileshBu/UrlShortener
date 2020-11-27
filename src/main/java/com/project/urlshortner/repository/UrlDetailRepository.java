package com.project.urlshortner.repository;

import com.project.urlshortner.model.entity.UrlDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlDetailRepository extends JpaRepository<UrlDetailEntity, Long> {

  UrlDetailEntity findByOriginalUrl(String originalUrl);

}
