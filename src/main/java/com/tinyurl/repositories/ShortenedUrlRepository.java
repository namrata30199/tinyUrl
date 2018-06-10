package com.tinyurl.repositories;

import java.util.Map;
import java.util.Optional;

import com.tinyurl.entity.UrlToBeShortenedBean;

public interface ShortenedUrlRepository {
	  void save(String shortenedId, UrlToBeShortenedBean entityUrl);
	  Optional<UrlToBeShortenedBean> findURLById(String shortenedId);
	  Map<String, UrlToBeShortenedBean> getAllURLs();
}
