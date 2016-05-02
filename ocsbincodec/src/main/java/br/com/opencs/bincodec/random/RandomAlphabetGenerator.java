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

/**
 * This class implements a random alphabet generator. It can be used to 
 * generate random alphabets that can be used to obfuscate data.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class RandomAlphabetGenerator {
	
	public static final char QRCODE_ALPHANUMERIC[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();
	
	public static final char QRCODE_ALPHANUMERIC_NO_SPACE[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ$%*+-./:".toCharArray();
	
	/**
	 * Generates a random alphabet.
	 * 
	 * <p>This algorithm is designed to guarantee that a given input will
	 * always generate the same result.</p>
	 * 
	 * @param seed A 32 bit random seed.
	 * @param rounds The number of rounds. To achieve better results, this
	 * value must be at least 4.
	 * @param candidates The list of candidates.
	 * @param size The size of the alphabet.
	 * @return The random alphabet.
	 */
	public static char[] generateRandom(long seed, int rounds, char candidates[], int size) {
		
		// Verify input
		if (candidates.length < size) {
			throw new IllegalArgumentException("The number of candidates must be at least as large as the target size.");
		}
		
		// Ensure that the original candidates will remain as it is
		candidates = (char[]) candidates.clone();
		
		// Shuffle the input and select the first ones...
		shuffle(seed, rounds, candidates);
		if (candidates.length == size) {
			return candidates;
		} else {
			char ret[] = new char[size];
			System.arraycopy(candidates, 0, ret, 0, ret.length);
			return ret;
		}
	}

	/**
	 * Shuffles the characters in the array using a pseudo random sequence.
	 *
	 * <p>This algorithm is designed to guarantee that a given input will
	 * always generate the same result.</p>
	 * 
	 * @param seed A 32 bit random seed.
	 * @param rounds The number of rounds. To achieve better results, this
	 * value must be at least 4.
	 * @param a The array to be shuffled.
	 */
	public static void shuffle(long seed, int rounds, char a[]) {
		RandomSource random = new RandomSource(seed);
		
		for (int round = 0; round < rounds; round++) {
			for (int pos = 0; pos < a.length; pos++) {
				// Select a random position and swap pos with it
				int newPos = (random.next() % a.length);
				char tmp = a[pos];
				a[pos] = a[newPos];
				a[newPos] = tmp;
			}
		}
	}
}
