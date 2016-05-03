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

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import org.junit.Test;

public class IntegerFileReaderTest {

	@Test
	public void testIntegerFileReader() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		r.close();
	}

	@Test
	public void testClose() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		r.close();
		r.close();
	}

	@Test
	public void testNextInt() throws Exception {
		// 0-9, no newline on last line
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		// 0-9, new line on the no newline on last line
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest2.txt");
		// 0-9, with empty lines
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest3.txt");
		// 0-9, with empty lines and comments
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest4.txt");
	}

	protected void testNextIntCore(String resourceName) throws IOException, EOFException {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream(resourceName);
		assertNotNull(in);
		r = new IntegerFileReader(in);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, r.nextInt());
		}
		try {
			r.nextInt();
			fail();
		} catch (EOFException e) {}
		r.close();
	}
	
	@Test
	public void testNextIntBroken() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest5.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		for (int i = 0; i < 7; i++) {
			assertEquals(i, r.nextInt());
		}
		try {
			r.nextInt();
			fail();
		} catch (StreamCorruptedException e) {
			assertEquals("Invalid integer on line 10.", e.getMessage());
			
		}
		r.close();
	}

}
