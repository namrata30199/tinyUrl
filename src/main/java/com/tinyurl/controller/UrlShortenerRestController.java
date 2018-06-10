package com.tinyurl.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinyurl.entity.UrlToBeShortenedBean;
import com.tinyurl.service.URLShortenerService;

@RestController
public class UrlShortenerRestController {
	
	private final static Logger LOG = LoggerFactory.getLogger(UrlShortenerRestController.class);
	
	private final URLShortenerService urlShortenerService;
	
	  @Autowired
	  public UrlShortenerRestController(URLShortenerService urlShortenerService) {
	    this.urlShortenerService = urlShortenerService;
	  }
	  
	  @RequestMapping("/")
	  @ResponseBody
	  String init() {
	    return "URL Shortener up and running!";
	  }
	  
	  @RequestMapping(value = "/{shortUrlId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	  ResponseEntity<?> redirectToFullUrlFromOriginal(@PathVariable String shortUrlId, HttpServletResponse response) throws IOException {

	    LOG.debug("Received request to redirect to '{}'", shortUrlId);
	    Optional<UrlToBeShortenedBean> shortenedURLFound = urlShortenerService.findURLById(shortUrlId);
	    if (shortenedURLFound.isPresent()) {
	      LOG.info("Redirecting to {}...", shortenedURLFound.get());
	  
	      LocalDateTime fromDateTime = shortenedURLFound.get().getTimeStamp();
	      LocalDateTime toDateTime = LocalDateTime.now();
	      LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );
	      Long secondsElapsed = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
	      
	      //expiring the link in 60 minutes
	      if(secondsElapsed>3600) 
	      {
	    	  return new ResponseEntity<String>("Oops, The link expired",HttpStatus.BAD_REQUEST);
	      }
	      response.sendRedirect(shortenedURLFound.get().getFullUrl().toString());
	    } else {
	      LOG.error("The id '{}' was not found", shortUrlId);
	      response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    }
	    return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	  }
	  
	  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	  ResponseEntity<?> createShortUrl(@RequestBody UrlToBeShortenedBean uriStringToBeShortened, HttpServletRequest request) {

	    LOG.debug("Received request to shorten the URL '{}'", uriStringToBeShortened.toString());
	    try {
	      String shortenedUrlId = urlShortenerService.shorten(uriStringToBeShortened.getFullUrl().toString());
	      String shortenedUrl = request.getRequestURL() + shortenedUrlId;
	      return new ResponseEntity<String>(shortenedUrl, HttpStatus.CREATED);
	    } catch (IllegalArgumentException | MalformedURLException ex) {
	      LOG.error("Error when trying to create a shortened URL for {}: {}", uriStringToBeShortened, ex.getMessage());
	      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	      LOG.error("Ooopsss... something unexpected went wrong: " + ex.getMessage());
	      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	    
	    @RequestMapping(value = "/allShortURLS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    ResponseEntity<?> viewAllURLs(HttpServletRequest request) {

	        Map<String, UrlToBeShortenedBean> allURLEntries = urlShortenerService.getAllURLs();
	        if(!allURLEntries.isEmpty())
	        return new ResponseEntity<Map<String,UrlToBeShortenedBean>>(allURLEntries, HttpStatus.OK);
	        else {
	        	  LOG.error("Ooopsss...there are no URLs shortened"); 
	        }
	        return new ResponseEntity<String>("no records found", HttpStatus.BAD_REQUEST);
	 
	  }

}
