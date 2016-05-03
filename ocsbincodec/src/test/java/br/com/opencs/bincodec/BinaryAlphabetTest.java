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

public class BinaryAlphabetTest {

	@Test
	public void testBinaryAlphabet() {
		BinaryAlphabet a;
		
		a = new BinaryAlphabet();
		testBinaryAlphabetCore(a, '0', '1');
	}
	
	@Test
	public void testBinaryAlphabetCharChar() {
		BinaryAlphabet a;
		
		a = new BinaryAlphabet('a', 'b');
		testBinaryAlphabetCore(a, 'a', 'b');
	}	
	
	protected void testBinaryAlphabetCore(BinaryAlphabet a, int zero, int one) {

		assertEquals(2, a.size());
		assertEquals(zero, a.getZero());
		assertEquals(one, a.getOne());
		
		assertEquals(zero, a.getCharacter(0));
		assertEquals(one, a.getCharacter(1));

		assertEquals(0, a.getValue(zero));
		assertEquals(1, a.getValue(one));
		
		for (int c = 0; c < 256; c++) {
			if (c == zero) {
				assertEquals(0, a.getValue(c));
			} else if (c == one) {
				assertEquals(1, a.getValue(c));
			} else {
				try {
					a.getValue(c);
					fail();
				} catch (Exception e) {}
			}
		}
	}
}
