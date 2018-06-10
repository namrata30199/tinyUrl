package com.tinyurl.repositories;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tinyurl.entity.UrlToBeShortenedBean;

@Repository
public class ShortenedUrlRepositoryImpl implements ShortenedUrlRepository{
	
	private final static Logger LOG = LoggerFactory.getLogger(ShortenedUrlRepositoryImpl.class);
	private Map<String, UrlToBeShortenedBean> shortenedUriStore = new ConcurrentHashMap<>();

	@Override
	public void save(String shortenedId, UrlToBeShortenedBean fullUrlEntity) {
	    shortenedUriStore.put(shortenedId, fullUrlEntity);
	    LOG.debug("Entry [{}, {}] successfully saved", shortenedId, fullUrlEntity);
	  }

	@Override
	public Optional<UrlToBeShortenedBean> findURLById(String shortenedId) {
		return Optional.ofNullable(shortenedUriStore.get(shortenedId));
	}

	@Override
	public Map<String, UrlToBeShortenedBean> getAllURLs() {
		return shortenedUriStore;
	}

}
