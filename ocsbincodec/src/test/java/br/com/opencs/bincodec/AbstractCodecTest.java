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

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class AbstractCodecTest {
	
	static public class TestCodec extends AbstractCodec {

		@Override
		public int getDecodedSize(int encSize) {
			return encSize;
		}

		@Override
		public int getEncodedSize(int decSize) {
			return decSize;
		}

		@Override
		public int decode(CharSequence src, int srcOffs, int srcSize, byte[] dst,
				int dstOffs) {
			int srcEnd;
			
			srcEnd = srcOffs + srcSize;
			while (srcOffs < srcEnd) {
				dst[dstOffs] = (byte) (src.charAt(srcOffs) & 0x7F);
				srcOffs++;
				dstOffs++;
			}
			return srcSize;
		}

		@Override
		public int encode(byte[] src, int srcOffs, int srcSize, Appendable dst)
				throws IOException {
			int srcEnd;
			
			srcEnd = srcOffs + srcSize;
			while (srcOffs < srcEnd) {
				dst.append((char)(src[srcOffs] & 0x7F));
				srcOffs++;
			}
			return srcSize;
		}

	}
	
	private static final byte BIN[] = {
		(byte)'J', (byte)'u', (byte)'s', (byte)'t', (byte)' ', (byte)'f', (byte)'o', (byte)'r',
		(byte)' ', (byte)'f', (byte)'u', (byte)'n', (byte)'!'};
	private static final String TXT = "Just for fun!";
	
	@Test
	public void testGetDecodedSize() {
		TestCodec c;
		
		c = new TestCodec();
		for (int size = 0; size < 1024; size++) {
			assertEquals(size, c.getDecodedSize(size));
		}
	}

	@Test
	public void testGetEncodedSize() {
		TestCodec c;
		
		c = new TestCodec();
		for (int size = 0; size < 1024; size++) {
			assertEquals(size, c.getEncodedSize(size));
		}
	}

	@Test
	public void testDecodeCharSequence() {
		TestCodec c;
		byte dst[];
		
		c = new TestCodec();
		dst = c.decode(TXT);
		assertArrayEquals(BIN, dst);		
	}
	
	@Test
	public void testDecodeCharSequenceIntInt() {
		TestCodec c;
		byte dst[];
		
		c = new TestCodec();

		dst = c.decode(TXT, 0, TXT.length());
		assertArrayEquals(BIN, dst);		

		dst = c.decode(" " + TXT + " ", 1, TXT.length());
		assertArrayEquals(BIN, dst);		
	}
	
	@Test
	public void testDecodeCharSequenceIntIntByteArrayInt() {
		TestCodec c;
		byte dst[];
		int size;
		
		c = new TestCodec();
		
		dst = new byte[BIN.length];
		size = c.decode(TXT, 0, TXT.length(), dst, 0);
		assertEquals(BIN.length, size);
		assertArrayEquals(BIN, dst);
		
		dst = new byte[BIN.length + 2];
		size = c.decode(TXT, 0, TXT.length(), dst, 1);
		assertEquals(BIN.length, size);
		assertEquals(0, dst[0]);
		assertEquals(0, dst[dst.length - 1]);
		assertArrayEquals(BIN, Arrays.copyOfRange(dst, 1, dst.length - 1));
		
		dst = new byte[BIN.length];
		size = c.decode(" " + TXT + " ", 1, TXT.length(), dst, 0);
		assertEquals(BIN.length, size);
		assertArrayEquals(BIN, dst);
		
		dst = new byte[BIN.length + 2];
		size = c.decode(" " + TXT + " ", 1, TXT.length(), dst, 1);
		assertEquals(BIN.length, size);
		assertEquals(0, dst[0]);
		assertEquals(0, dst[dst.length - 1]);
		assertArrayEquals(BIN, Arrays.copyOfRange(dst, 1, dst.length - 1));
	}
	
	@Test
	public void testEncodeByteArray() {
		TestCodec c;
		String dst;
		
		c = new TestCodec();
		
		dst = c.encode(BIN);
		assertEquals(TXT, dst);
	}
	
	@Test
	public void testEncodeByteArrayIntInt() {
		TestCodec c;
		String dst;
		
		c = new TestCodec();
		
		dst = c.encode(BIN, 0, BIN.length);
		assertEquals(TXT, dst);
		
		byte tmp[] = new byte[BIN.length + 2];
		System.arraycopy(BIN, 0, tmp, 1, BIN.length);
		dst = c.encode(tmp, 1, BIN.length);
		assertEquals(TXT, dst);
	}

	@Test
	public void testEncodeByteArrayIntIntAppendable() throws Exception {
		TestCodec c;
		StringBuilder dst;
		int size;
		
		c = new TestCodec();
		
		dst = new StringBuilder();
		size = c.encode(BIN, 0, BIN.length, dst);
		assertEquals(TXT.length(), size);
		assertEquals(TXT, dst.toString());
		
		byte tmp[] = new byte[BIN.length + 2];
		System.arraycopy(BIN, 0, tmp, 1, BIN.length);
		dst = new StringBuilder();
		size = c.encode(tmp, 1, BIN.length, dst);
		assertEquals(TXT.length(), size);
		assertEquals(TXT, dst.toString());
	}

}
