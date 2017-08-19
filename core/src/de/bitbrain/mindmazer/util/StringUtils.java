package de.bitbrain.mindmazer.util;

import java.util.Random;

public final class StringUtils {

	public static String generateRandomString(int length) {
		int leftLimit = 97; // letter 'a'
	   int rightLimit = 122; // letter 'z'
	   Random random = new Random();
	   StringBuilder buffer = new StringBuilder(length);
	   for (int i = 0; i < length; i++) {
	       int randomLimitedInt = leftLimit + (int) 
	         (random.nextFloat() * (rightLimit - leftLimit + 1));
	       buffer.append((char) randomLimitedInt);
	   }
	   return buffer.toString();
	}
}
