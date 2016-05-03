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

public class HexadecimalAlphabetTest extends BaseAlphabetTest {

	private static final String CHARACTERS = "0123456789ABCDEF";
	private static final char CHARACTERS_ARRAY[] = CHARACTERS.toCharArray();
	
	private static final String CHARACTERS_LC = "0123456789abcdef";
	private static final char CHARACTERS_ARRAY_LC[] = CHARACTERS_LC.toCharArray();
	
	public void testHexadecimalAlphabet() {
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet();
		assertArrayEquals(CHARACTERS_ARRAY, a.alphabet);
	}
	
	public void testHexadecimalBoolean() {
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		assertArrayEquals(CHARACTERS_ARRAY, a.alphabet);
		
		a = new HexadecimalAlphabet(true);
		assertArrayEquals(CHARACTERS_ARRAY_LC, a.alphabet);		
	}	
	
	public void testSize(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet();
		assertEquals(CHARACTERS.length(), a.size());

		a = new HexadecimalAlphabet(false);
		assertEquals(CHARACTERS.length(), a.size());

		a = new HexadecimalAlphabet(true);
		assertEquals(CHARACTERS_LC.length(), a.size());
	}
	
	public void testGetValue(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		testGetValueCoreCaseInsensitive(a, CHARACTERS);
		a = new HexadecimalAlphabet(true);
		testGetValueCoreCaseInsensitive(a, CHARACTERS_LC);
	}
	
	public void testGetCharacter(){
		HexadecimalAlphabet a;
		
		a = new HexadecimalAlphabet(false);
		testGetCharacterCore(a, CHARACTERS);
		a = new HexadecimalAlphabet(true);
		testGetCharacterCore(a, CHARACTERS_LC);
	}
}
