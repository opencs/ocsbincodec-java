/*
 * Copyright (c) 2015, Open Communications Security
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of ocsbincodec-java nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
		assertEquals(CHARACTERS.length(), a.size());
		a = new ArrayAlphabet(CHARACTERS);
		assertEquals(CHARACTERS.length(), a.size());
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
