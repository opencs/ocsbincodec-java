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
 * This class implements a simple random source.
 * 
 * <p>
 * Internally, this implementation uses a linear congruential generator with the same
 * parameters used by the glibc.
 * </p>
 * 
 * <p>
 * This implementation is not thread safe.
 * </p>
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class RandomSource {

	/**
	 * The module m.
	 */
	private static final long M = 0x7FFFFFFF;
	
	/**
	 * The multiplier a. 
	 */
	private static final long A = 1103515245;
	
	/**
	 * The constant c.
	 */
	private static final long C = 12345;
	
	/**
	 * The current state.
	 */
	private long state;
	
	/**
	 * Creates a new random source.
	 * 
	 * @param seed The initial seed.
	 */
	public RandomSource(long seed) {
		this.state = seed & M;
	}

	/**
	 * Returns the next 31 bit integer in the sequence.
	 * 
	 * @return the nex value.
	 */
	public int next(){
		
		state = ((state * A) + C) & M;
		return (int)state; 
	}
}
