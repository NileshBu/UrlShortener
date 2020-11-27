package com.project.urlshortner.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "URLDETAIL")
public class UrlDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", updatable = false, nullable = false, unique = true)
  private long id;

  @Column(name = "ORIGINALURL", length = 1337, nullable = false, unique = true)
  private String originalUrl;

  @Column(name = "CREATEDATE", nullable = false)
  private LocalDateTime createdDate;

}
