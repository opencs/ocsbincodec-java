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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Test;

public class Base2NCodecTest extends BaseCodecTest {
	
	private static final Charset CHARSET = Charset.forName("utf-8");
	
	private static final Base64Alphabet ALPHABET = new Base64Alphabet();
	
	// Test vectors extracted from RFC4648 and other sources
	private static final String SAMPLES[][] = {
		{ "", "", "" },
		{ "f", "Zg==", "Zg" },
		{ "fo", "Zm8=", "Zm8" },
		{ "foo", "Zm9v", "Zm9v" },
		{ "foob", "Zm9vYg==", "Zm9vYg" },
		{ "fooba", "Zm9vYmE=", "Zm9vYmE" },
		{ "foobar", "Zm9vYmFy", "Zm9vYmFy"},
		{ "This is just a test...\n", "VGhpcyBpcyBqdXN0IGEgdGVzdC4uLgo=", "VGhpcyBpcyBqdXN0IGEgdGVzdC4uLgo"}
	};
	
	@Test
	public void testBase2NCodecAlphabet() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		assertFalse(c.usesPadding());
	}

	@Test
	public void testBase2NCodecAlphabetIntInt() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		assertTrue(c.usesPadding());
	}

	@Test
	public void testBase2NCodecAlphabetIntIntCharArray() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		assertTrue(c.usesPadding());
	}	

	@Test
	public void testGetDecodedSize() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		for (int encSize = 0; encSize < 1024; encSize++) {
			int expectedSize = (encSize * 6) / 8;
			assertEquals(expectedSize, c.getDecodedSize(encSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		for (int encSize = 0; encSize < 1024; encSize++) {
			int expectedSize = (encSize * 6) / 8;
			assertEquals(expectedSize, c.getDecodedSize(encSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		for (int encSize = 0; encSize < 1024; encSize++) {
			int expectedSize = (encSize * 6) / 8;
			assertEquals(expectedSize, c.getDecodedSize(encSize));
		}
	}

	@Test
	public void testGetEncodedSize() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		for (int decSize = 0; decSize < 1024; decSize++) {
			int expectedSize = ((decSize * 8) + 5) / 6;
			assertEquals(expectedSize, c.getEncodedSize(decSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		for (int decSize = 0; decSize < 1024; decSize++) {
			int expectedSize = ((decSize * 8) + 5) / 6;
			expectedSize = expectedSize + ((4 - expectedSize % 4) % 4);
			assertEquals(expectedSize, c.getEncodedSize(decSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		for (int decSize = 0; decSize < 1024; decSize++) {
			int expectedSize = ((decSize * 8) + 5) / 6;
			expectedSize = expectedSize + ((4 - expectedSize % 4) % 4);
			assertEquals(expectedSize, c.getEncodedSize(decSize));
		}
	}
	
	protected byte[] toBytes(String s, int startOffs, int addtionalSize) {
		ByteBuffer b;
		byte ret[];
		
		b = CHARSET.encode(s);
		ret = new byte[b.remaining() + addtionalSize];
		b.get(ret, startOffs, b.remaining());
		
		return ret;
	}
	
	private void testDecodeCharSequenceIntIntByteArrayIntCore(Base2NCodec c, int encIndex) {

		// Plain - no offset
		for (String s[]: SAMPLES) {
			int expectedSize = s[0].length();
			byte expected[] = toBytes(s[0], 0, 0);
			String enc = s[encIndex];
			byte dst[] = new byte[expected.length];
			
			int size = c.decode(enc, 0, enc.length(), dst, 0);
			assertEquals(enc, expectedSize, size);
			assertArrayEquals(expected, dst);
		}
	
		// Offset on output
		for (String s[]: SAMPLES) {
			int expectedSize = s[0].length();
			byte expected[] = toBytes(s[0], 1, 2);
			String enc = s[encIndex];
			byte dst[] = new byte[expected.length];
			
			int size = c.decode(enc, 0, enc.length(), dst, 1);
			assertEquals(enc, expectedSize, size);
			assertArrayEquals(expected, dst);
		}		
		
		// Plain - offset on input
		for (String s[]: SAMPLES) {
			int expectedSize = s[0].length();
			byte expected[] = toBytes(s[0], 0, 0);
			String enc = s[encIndex];
			byte dst[] = new byte[expected.length];
			
			int size = c.decode(" " + enc + " ", 1, enc.length(), dst, 0);
			assertEquals(enc, expectedSize, size);
			assertArrayEquals(expected, dst);
		}
	
		// Offset on input and output
		for (String s[]: SAMPLES) {
			int expectedSize = s[0].length();
			byte expected[] = toBytes(s[0], 1, 2);
			String enc = s[encIndex];
			byte dst[] = new byte[expected.length];
			
			int size = c.decode(" " + enc + " ", 1, enc.length(), dst, 1);
			assertEquals(enc, expectedSize, size);
			assertArrayEquals(expected, dst);
		}		
	}	
	
	private void testDecodeWithIgnoreCore(Base2NCodec c, char ignoreList[]) {
		Random random = new Random();
		
		// Plain - no offset
		for (int srcSize = 1; srcSize <= 1024; srcSize++) {
			byte src[];
			
			// Generate src
			src = new byte[srcSize];
			random.nextBytes(src);
			
			// Encode and add ignores
			StringBuilder enc = new StringBuilder(c.encode(src));
			for (int i = 0; i < ignoreList.length; i++) {
				int offs = random.nextInt(enc.length());
				enc.insert(offs, ignoreList[i]);
			}
			
			byte dst[] = c.decode(enc);
			assertArrayEquals(src, dst);
		}	
	}

	@Test
	public void testDecodeCharSequenceIntIntByteArrayIntNoPadding() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		testDecodeCharSequenceIntIntByteArrayIntCore(c, 2);
	}

	@Test
	public void testDecodeCharSequenceIntIntByteArrayIntPadding() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		testDecodeCharSequenceIntIntByteArrayIntCore(c, 1);		
	}
	
	
	@Test
	public void testDecodeWithIgnoreNoPadding() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 0, Base2NCodec.IGNORE_SPACES);
		testDecodeWithIgnoreCore(c, Base2NCodec.IGNORE_SPACES);
	}
	
	@Test
	public void testDecodeWithIgnorePadding() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		testDecodeWithIgnoreCore(c, Base2NCodec.IGNORE_SPACES);
	}	
	
	private void testEncodeByteArrayIntIntAppendableCore(Base2NCodec c, int encIndex) throws Exception {
		c = new Base2NCodec(ALPHABET);
		
		// Plain - no offset
		for (String s[]: SAMPLES) {
			int expectedSize = s[encIndex].length();
			String expected = s[encIndex];
			byte src[] = toBytes(s[0], 0, 0);
			int srcSize = s[0].length();
			int srcOffs = 0;
			
			StringBuilder sb = new StringBuilder();
			int size = c.encode(src, srcOffs, srcSize, sb);
			assertEquals(expected, expectedSize, size);
			assertEquals(expected, sb.toString());
		}
		
		// Plain - offset on src
		for (String s[]: SAMPLES) {
			int expectedSize = s[encIndex].length();
			String expected = s[encIndex];
			byte src[] = toBytes(s[0], 1, 2);
			int srcSize = s[0].length();
			int srcOffs = 1;
			
			StringBuilder sb = new StringBuilder();
			int size = c.encode(src, srcOffs, srcSize, sb);
			assertEquals(expected, expectedSize, size);
			assertEquals(expected, sb.toString());
		}		
	}
	
	
	@Test
	public void testEncodeByteArrayIntIntAppendableNoPadding() throws Exception {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		testEncodeByteArrayIntIntAppendableCore(c, 2);
	}
	
	@Test
	public void testEncodeByteArrayIntIntAppendablePadding() throws Exception {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		testEncodeByteArrayIntIntAppendableCore(c, 2);
	}
	
	@Test
	public void testEncodeDecodeNoPadding(){
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		testEncodeDecodeCore(c);
	}
	
	@Test
	public void testEncodeDecodePadding(){
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		testEncodeDecodeCore(c);
	}	
	
	@Test
	public void testEncodeDecodePadding2(){
		Base2NCodec c;
		
		// Exotic configuration
		c = new Base2NCodec(ALPHABET, '?', 8);
		testEncodeDecodeCore(c);
	}	

	@Test
	public void testGetPaddingSize() {
		Base2NCodec c;
		
		c = new Base2NCodec(ALPHABET);
		for (int encSize = 0; encSize < 1024; encSize++) {
			assertEquals(0, c.getPaddingSize(encSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4);
		for (int encSize = 0; encSize < 1024; encSize++) {
			int paddingSize = (4 - (encSize % 4)) % 4;
			assertEquals(paddingSize, c.getPaddingSize(encSize));
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		for (int encSize = 0; encSize < 1024; encSize++) {
			int paddingSize = (4 - (encSize % 4)) % 4;
			assertEquals(paddingSize, c.getPaddingSize(encSize));
		}
	}

	@Test
	public void testUsesPadding() {
		Base2NCodec c;

		c = new Base2NCodec(ALPHABET);
		assertFalse(c.usesPadding());

		c = new Base2NCodec(ALPHABET, '=', 4);
		assertTrue(c.usesPadding());
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		assertTrue(c.usesPadding());		
	}

	@Test
	public void testIsPadding() {
		Base2NCodec c;

		c = new Base2NCodec(ALPHABET);
		for (int ch = 0; ch < 256; ch++) {
			assertFalse(c.isPadding(ch));
		}

		c = new Base2NCodec(ALPHABET, '=', 4);
		for (int ch = 0; ch < 256; ch++) {
			if (ch != '=') {
				assertFalse(c.isPadding(ch));
			} else {
				assertTrue(c.isPadding(ch));
			}
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		for (int ch = 0; ch < 256; ch++) {
			if (ch != '=') {
				assertFalse(c.isPadding(ch));
			} else {
				assertTrue(c.isPadding(ch));
			}
		}
	}

	@Test
	public void testIsIgnored() {
		Base2NCodec c;

		c = new Base2NCodec(ALPHABET);
		for (int ch = 0; ch < 256; ch++) {
			assertFalse(c.isPadding(ch));
		}

		c = new Base2NCodec(ALPHABET, '=', 4);
		for (int ch = 0; ch < 256; ch++) {
			if (ch != '=') {
				assertFalse(c.isPadding(ch));
			} else {
				assertTrue(c.isPadding(ch));
			}
		}
		
		c = new Base2NCodec(ALPHABET, '=', 4, Base2NCodec.IGNORE_SPACES);
		for (int ch = 0; ch < 256; ch++) {
			if (ch != '=') {
				assertFalse(c.isPadding(ch));
			} else {
				assertTrue(c.isPadding(ch));
			}
		}
	}
}
