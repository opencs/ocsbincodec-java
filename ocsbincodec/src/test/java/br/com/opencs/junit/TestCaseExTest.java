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
package br.com.opencs.junit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class TestCaseExTest extends TestCase {

	public void testAssertArrayEqualsCharArrayCharArray() {
		
		
	}

	public void testAssertArrayEqualsStringCharArrayCharArray() {
		char expected[] = "expected".toCharArray();
		char actual[] = (char[])expected.clone();
		
		TestCaseEx.assertArrayEquals("test", (char[])null, (char[])null);
		
		TestCaseEx.assertArrayEquals(null, expected, expected);
		TestCaseEx.assertArrayEquals("test", expected, expected);
		
		TestCaseEx.assertArrayEquals(null, expected, actual);
		TestCaseEx.assertArrayEquals("test", expected, actual);
	}

	public void testAssertArrayEqualsStringCharArrayCharArrayFail() {
		char expected[] = "expected".toCharArray();
		char actual[] = (char[])expected.clone();
		boolean error = false;
		actual[2]++;

		//
		try {
			TestCaseEx.assertArrayEquals(null, expected, actual);
			error = true;
		} catch (AssertionFailedError e){
		}
		assertFalse("AssertionFailedError expected.", error);

		try {
			TestCaseEx.assertArrayEquals("#@#@#@", expected, actual);
			error = true;
		} catch (AssertionFailedError e){
			assertTrue("Wrong message.", e.getMessage().startsWith("#@#@#@"));
		}
		assertFalse("AssertionFailedError expected.", error);
	}
	
	public void testAssertArrayEqualsStringByteArrayByteArray() {
		byte expected[] = "expected".getBytes();
		byte actual[] = (byte [])expected.clone();
		
		TestCaseEx.assertArrayEquals("test", (byte[])null, (byte[])null);
		
		TestCaseEx.assertArrayEquals(null, expected, expected);
		TestCaseEx.assertArrayEquals("test", expected, expected);
		
		TestCaseEx.assertArrayEquals(null, expected, actual);
		TestCaseEx.assertArrayEquals("test", expected, actual);
	}

	public void testAssertArrayEqualsStringByteArrayByteArrayFail() {
		byte expected[] = "expected".getBytes();
		byte actual[] = (byte [])expected.clone();
		boolean error = false;
		actual[2]++;

		try {
			TestCaseEx.assertArrayEquals(null, expected, actual);
			error = true;
		} catch (AssertionFailedError e){
		}
		assertFalse("AssertionFailedError expected.", error);

		try {
			TestCaseEx.assertArrayEquals("#@#@#@", expected, actual);
			error = true;
		} catch (AssertionFailedError e){
			assertTrue("Wrong message.", e.getMessage().startsWith("#@#@#@"));
		}
		assertFalse("AssertionFailedError expected.", error);
	}
	
	public void testCopyOfRangeByteArrayIntInt() {
		byte expected[] = "!expected!".getBytes();
		byte actual[];
		
		actual = TestCaseEx.copyOfRange(expected, 0, expected.length);
		assertNotSame(expected, actual);
		TestCaseEx.assertArrayEquals(expected, actual);

		actual = TestCaseEx.copyOfRange(expected, 1, expected.length - 1);
		TestCaseEx.assertNotSame(expected, actual);
		assertEquals(expected.length - 2, actual.length);
		for (int i = 0; i < actual.length; i++) {
			assertEquals("Offset " + i , expected[i + 1], actual[i]);
		}
	}
}
