package br.com.opencs.bincodec;

import static org.junit.Assert.*;

import org.junit.Test;

public class HexadecimalAlphabetTest extends BaseAlphabetTest {

	private static final String CHARACTERS = "0123456789ABCDEF";
	private static final char CHARACTERS_ARRAY[] = CHARACTERS.toCharArray();
	
	private static final String CHARACTERS_LC = "0123456789abcdef";
	private static final char CHARACTERS_ARRAY_LC[] = CHARACTERS_LC.toCharArray();
	

	@Test
	public void testHexadecimalAlphabet() {
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet();
		assertArrayEquals(CHARACTERS_ARRAY, a.alphabet);
	}
	
	@Test
	public void testHexadecimalBoolean() {
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		assertArrayEquals(CHARACTERS_ARRAY, a.alphabet);
		
		a = new HexadecimalAlphabet(true);
		assertArrayEquals(CHARACTERS_ARRAY_LC, a.alphabet);		
	}	

	
	@Test
	public void testSize(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet();
		testSizeCore(a, CHARACTERS);
	}
	
	@Test
	public void testGetValue(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		testGetValueCoreCaseInsensitive(a, CHARACTERS);
		a = new HexadecimalAlphabet(true);
		testGetValueCoreCaseInsensitive(a, CHARACTERS_LC);
	}
	
	@Test
	public void testGetCharacter(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		testGetCharacterCore(a, CHARACTERS);
		a = new HexadecimalAlphabet(true);
		testGetCharacterCore(a, CHARACTERS_LC);
	}
}
