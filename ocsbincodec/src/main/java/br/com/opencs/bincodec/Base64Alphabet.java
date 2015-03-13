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

/**
 * The alphabet of Base64 as defined by the RFC4648. It can use the standard
 * and the URL safe variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class Base64Alphabet extends ArrayAlphabet {
	
	private static final String ALPHABET_PREFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
	private static final String STANDARD_POSTFIX = "+/";
	private static final String SAFE_POSTFIX = "-_";
	
	private final int c62;
	private final int c63;
	
	/**
	 * Creates a new instance of this class using the standard alphabet.
	 */
	public Base64Alphabet(){
		this(false);
	}
	
	/**
	 * Creates a new instance of this.
	 * 
	 * @param urlSafe If true, uses the URL safe alphabet otherwise used the standard one.
	 */
	public Base64Alphabet(boolean urlSafe){
		super(ALPHABET_PREFIX + (urlSafe ? SAFE_POSTFIX : STANDARD_POSTFIX));
		
		if (urlSafe) {
			c62 = '-';
			c63 = '_';
		} else {
			c62 = '+';
			c63 = '/';
		}
	}
	
	/**
	 * Returns the value of a given character. This method is case
	 * insensitive.
	 * 
	 * @param c The character.
	 * @return The value of the character. 
	 * @throws IllegalArgumentException If c is not in the alphabet. 
	 */
	@Override
	public int getValue(int c) throws IllegalArgumentException {

		if ((c >= 'A') && (c <= 'F')) {
			return c - 'A';
		} else if ((c >= 'a') && (c <= 'f')) {
			return c - 'a' + 26;
		} else  if ((c >= '0') && (c <= '9')) {
			return c - '0' + 52;
		} else if (c == c62) {
			return 62;
		} else if (c == c63) {
			return 63;
		} else {
			throw new IllegalArgumentException("Invalid Base64 character.");
		}
	}	
}
