package com.tinyurl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinyurl.core.IdGenerator;
import com.tinyurl.entity.UrlToBeShortenedBean;
import com.tinyurl.repositories.ShortenedUrlRepository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class URLShortenerService {

  private final ShortenedUrlRepository shortenedUrlRepository;
  private final IdGenerator idGenerator;
  
  @Autowired
  public UrlToBeShortenedBean urlEntity;
 
  @Autowired
  public URLShortenerService(final ShortenedUrlRepository shortenedUrlRepository, IdGenerator idGenerator) {
    this.shortenedUrlRepository = shortenedUrlRepository;
    this.idGenerator = idGenerator;
  }

  public String shorten(String urlStringToBeShortened) throws MalformedURLException {
    URL urlToBeShortened = URI.create(urlStringToBeShortened).toURL();
    String shortenedUrlId = this.idGenerator.generateIdFrom(urlToBeShortened.toString());
    urlEntity.setFullUrl(urlStringToBeShortened);
    urlEntity.setTimeStamp(LocalDateTime.now());
    shortenedUrlRepository.save(shortenedUrlId, urlEntity);

    return shortenedUrlId;
  }

  public Optional<UrlToBeShortenedBean> findURLById(String shortUrlId) {
    return shortenedUrlRepository.findURLById(shortUrlId);
  }
  
  public Map<String, UrlToBeShortenedBean> getAllURLs(){
	  return shortenedUrlRepository.getAllURLs();
  }
}
