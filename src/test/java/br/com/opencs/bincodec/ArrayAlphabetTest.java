package br.com.opencs.bincodec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayAlphabetTest extends BaseAlphabetTest {
	
	private static final String CHARACTERS = "abcdefghij";
	private static final char CHARACTERS_ARRAY[] = CHARACTERS.toCharArray();
	
	@Test
	public void testArrayAlphabetString() {
		ArrayAlphabet b = new ArrayAlphabet(CHARACTERS);
		
		assertArrayEquals(CHARACTERS_ARRAY, b.alphabet);
	}

	@Test
	public void testArrayAlphabetCharArray() {
		ArrayAlphabet b = new ArrayAlphabet(CHARACTERS_ARRAY);

		assertNotSame(CHARACTERS_ARRAY, b.alphabet);
		assertArrayEquals(CHARACTERS_ARRAY, b.alphabet);
	}

	@Test
	public void testSize(){
		ArrayAlphabet a;
		
		a = new ArrayAlphabet(CHARACTERS_ARRAY);
		testSizeCore(a, CHARACTERS);
		a = new ArrayAlphabet(CHARACTERS);
		testSizeCore(a, CHARACTERS);
	}
	
	@Test
	public void testGetValue(){
		ArrayAlphabet a;
		
		a = new ArrayAlphabet(CHARACTERS_ARRAY);
		testGetValueCore(a, CHARACTERS);
		a = new ArrayAlphabet(CHARACTERS);
		testGetValueCore(a, CHARACTERS);
	}
	
	@Test
	public void testGetCharacter(){
		ArrayAlphabet a;
		
		a = new ArrayAlphabet(CHARACTERS_ARRAY);
		testGetCharacterCore(a, CHARACTERS);
		a = new ArrayAlphabet(CHARACTERS);
		testGetCharacterCore(a, CHARACTERS);
	}
	

}
