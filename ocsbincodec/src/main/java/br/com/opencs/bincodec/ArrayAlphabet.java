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
 * This class implements an alphabet that is backed by a char
 * array.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class ArrayAlphabet implements Alphabet {
	
	protected final char alphabet[];

	public ArrayAlphabet(char alphabet[]) {
		this.alphabet = (char[])alphabet.clone();
	}
	
	public ArrayAlphabet(String alphabet) {
		this.alphabet = alphabet.toCharArray();
	}

	public int size() {
		return alphabet.length;
	}

	public int getCharacter(int v) {
		return alphabet[v];
	}

	/**
	 * Returns the value of a given character.
	 * 
	 * <p>This implementation uses a simple sequential scan to map
	 * the character back to its value. Subclasses are encouraged to
	 * provide the most efficient implementation possible.</p>
	 * 
	 * @param c The character.
	 * @return The value of the character. 
	 * @throws IllegalArgumentException If c is not in the alphabet. 
	 */
	public int getValue(int c) throws IllegalArgumentException {
		int v;
		
		// This implementation will use a very simple sequential scan
		for (v = 0; v < this.alphabet.length; v++) {
			if (c == this.alphabet[v]) {
				return v;
			}
		}
		throw new IllegalArgumentException("Invalid character.");
	}
}
