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
package br.com.opencs.bincodec.random;

import br.com.opencs.junit.TestCaseEx;

public class RandomAlphabetGeneratorTest extends TestCaseEx {

	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	// Shuffle of CHARS with seed as 12345 and 100 rounds
	private static final String SHUFFLE_STANDARD = "MBYXGZEJKRSNOLCFQTUDIHAVWP";
	
	public void testGenerateRandom() {
		char src[];
		char dst[]; 

		// Initialize the source
		src = CHARS.toCharArray();

		// Basic tests
		for (int size = 1; size < src.length; size++) {
			dst = (char[])src.clone();
			char m1[] = RandomAlphabetGenerator.generateRandom(size, 4, dst, size);
			assertArrayEquals(src, dst); // No changes in the input parameter
			assertEquals(size, m1.length);
			for (int i = 0; i < m1.length; i++) {
				char c = m1[i];
				assertTrue(CHARS.indexOf(c) >= 0);
			}
			
			char m2[] = RandomAlphabetGenerator.generateRandom(size, 4, dst, size);
			assertArrayEquals(m1, m2);			
		}
	}

	public void testShuffle() {
		int count;
		char src[];
		char dst[]; 

		// Initialize the source
		src = CHARS.toCharArray();
		
		// No shuffle at all
		dst = (char[])src.clone();
		RandomAlphabetGenerator.shuffle(0, 0, dst);
		count = 0;
		for (int i = 0; i < src.length; i++) {
			count += ((src[i] == dst[i])?0:1);
		}
		assertEquals(0, count);
		
		// For rounds
		dst = (char[])src.clone();
		RandomAlphabetGenerator.shuffle(0, 4, dst);
		count = 0;
		for (int i = 0; i < src.length; i++) {
			count += ((src[i] == dst[i])?0:1);
		}
		assertTrue(count > (src.length / 3));
		
		// Ensure that the same parameters will result in the same mapping
		dst = (char[])src.clone();
		char dst2[] = (char[])src.clone();
		
		RandomAlphabetGenerator.shuffle(0, 4, dst);
		RandomAlphabetGenerator.shuffle(0, 4, dst2);
		assertArrayEquals(dst, dst2);
	}

	public void testShuffleStandard() {
		int count;
		char src[];
		char dst[]; 

		// Initialize the source
		src = CHARS.toCharArray();
		
		// No shuffle at all
		dst = (char[])src.clone();
		RandomAlphabetGenerator.shuffle(0, 0, dst);
		assertArrayEquals(src, dst);
		
		// For rounds
		dst = (char[])src.clone();
		RandomAlphabetGenerator.shuffle(12345, 100, dst);
		assertArrayEquals(SHUFFLE_STANDARD.toCharArray(), dst);
	}	
}
