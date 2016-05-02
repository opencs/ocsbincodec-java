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

import junit.framework.TestCase;

public abstract class BaseAlphabetTest extends TestCase  {

	protected void testGetCharacterCore(Alphabet a, String characters) {

		for (int i = 0; i < characters.length(); i++) {
			junit.framework.Assert.assertEquals(characters.charAt(i), a.getCharacter(i));
		}
	}

	protected void testGetValueCore(Alphabet a, String characters) {

		for (int c = 0; c < 256; c++) {
			final int v = characters.indexOf(c);
			if (v < 0) {
				try {
					junit.framework.Assert.assertEquals(v, a.getValue(c));
					junit.framework.Assert.fail();
				} catch (final IllegalArgumentException e) {
				}
			} else {
				junit.framework.Assert.assertEquals(v, a.getValue(c));
			}
		}
	}

	protected void testGetValueCoreCaseInsensitive(Alphabet a, String characters) {

		characters = characters.toUpperCase();
		for (int c = 0; c < 256; c++) {
			final int v = characters.indexOf(Character.toUpperCase(c));
			if (v < 0) {
				try {
					junit.framework.Assert.assertEquals(v, a.getValue(c));
					junit.framework.Assert.fail();
				} catch (final IllegalArgumentException e) {
				}
			} else {
				junit.framework.Assert.assertEquals(v, a.getValue(c));
			}
		}
	}
}
