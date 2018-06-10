package com.tinyurl.core;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;
import com.google.common.hash.Hashing;


@Component
public class HashGenerator implements IdGenerator{

	@Override
	public String generateIdFrom(String textToBeShortened) {
		return Hashing.murmur3_32().hashString(textToBeShortened.toString(), StandardCharsets.UTF_8).toString();
	}

}
