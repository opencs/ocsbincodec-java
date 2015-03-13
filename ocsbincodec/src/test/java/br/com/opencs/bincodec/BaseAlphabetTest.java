package br.com.opencs.bincodec;

import static org.junit.Assert.*;

public abstract class BaseAlphabetTest {

	protected void testSizeCore(Alphabet a, String characters) {
		assertEquals(characters.length(), a.size());
	}	
	
	protected void testGetCharacterCore(Alphabet a, String characters) {
		
		for (int i = 0; i < characters.length(); i++) {
			assertEquals(characters.charAt(i), a.getCharacter(i));
		}		
	}	

	protected void testGetValueCore(Alphabet a, String characters) {

		for (int c = 0; c < 256; c++) {
			int v = characters.indexOf(c);
			if (v < 0) {
				try {
					assertEquals(v, a.getValue(c));		
					fail();
				} catch (IllegalArgumentException e) {}
			} else {
				assertEquals(v, a.getValue(c));
			}
		}		
	}
	
	protected void testGetValueCoreCaseInsensitive(Alphabet a, String characters) {

		characters = characters.toUpperCase();
		for (int c = 0; c < 256; c++) {
			int v = characters.indexOf(Character.toUpperCase(c));
			if (v < 0) {
				try {
					assertEquals(v, a.getValue(c));		
					fail();
				} catch (IllegalArgumentException e) {}
			} else {
				assertEquals(v, a.getValue(c));
			}
		}		
	}
}
