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

import java.io.IOException;
import java.util.Arrays;

/**
 * This class implements an abstract Codec. It provides generic implementations of the helper
 * methods but delegates the actual decoding to subclasses.
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.11
 */
public abstract class AbstractCodec implements Codec {

	public abstract int getDecodedSize(int encSize);

	public abstract int getEncodedSize(int decSize);

	public byte[] decode(CharSequence src) throws IllegalArgumentException {
		return decode(src, 0, src.length());
	}
	
	public byte[] decode(CharSequence src, int srcOffs, int srcSize) throws IllegalArgumentException {
		byte ret[];
		int decSize;
		
		// Allocate the output array based on the size of getDecodedSize()
		ret = new byte[getDecodedSize(srcSize)];
		decSize = decode(src, srcOffs, srcSize, ret, 0);

		// Truncate ret if required
		if (decSize == ret.length) {
			return ret;
		} else {
			return Arrays.copyOf(ret, decSize);
		}
	}

	public abstract int decode(CharSequence src, int srcOffs, int srcSize, byte[] dst,
			int dstOffs);
	
	public String encode(byte src[]) {
		return encode(src, 0, src.length);
	}
	
	public String encode(byte src[], int srcOffs, int srcSize) {
		
		try {
			StringBuilder sb = new StringBuilder(getEncodedSize(srcSize));
			encode(src, srcOffs, srcSize, sb);
			return sb.toString();
		} catch (IOException e) {
			return null;
		}
	}

	public abstract int encode(byte[] src, int srcOffs, int srcSize, Appendable dst)
			throws IOException;
}
