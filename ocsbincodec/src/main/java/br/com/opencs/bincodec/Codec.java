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

/**
 * This is the interface implemented by all binary-to-text codecs in this library.
 * 
 * <p>Instances of Codec are expected to be thread safe.</p>
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.11
 */
public interface Codec {
	
	/**
	 * Returns the expected number of bytes based on the size of the encoded size.
	 * This value is always greater or equal to the actual decoded data. 
	 * 
	 * @param encSize The encoded size characters.
	 * @return The expected decoded size.
	 */
	public int getDecodedSize(int encSize);

	/**
	 * Returns the size of the encoded data based on the decoded size.
	 * 
	 * @param decSize The decoded size in bytes.
	 * @return The number of characters required to encode the data.
	 */
	public int getEncodedSize(int decSize);
	
	/**
	 * Decodes the encoded data.
	 * 
	 * @param src The encoded data.
	 * @return The decoded value.
	 * @throws IllegalArgumentException If src is invalid.
	 */
	public byte[] decode(CharSequence src) throws IllegalArgumentException;

	/**
	 * Decodes the encoded data.
	 * 
	 * @param src The encoded data.
	 * @param srcOffs The initial offset in src.
	 * @param srcSize The number of characters in src.
	 * @return The decoded value.
	 * @throws IllegalArgumentException If src is invalid.
	 */
	public byte[] decode(CharSequence src, int srcOffs, int srcSize) throws IllegalArgumentException;

	/**
	 * Decodes the encoded data and puts the result in dst. The array dst must be large
	 * enough to hold the decoded data.
	 * 
	 * @param src The encoded data.
	 * @param srcOffs The initial offset in src.
	 * @param srcSize The number of characters in src.
	 * @param dst The output buffer.
	 * @param dstOffs The initial offset in the output buffer.
	 * @return The number of byes added to dst.
	 * @throws IllegalArgumentException If src is invalid.
	 * @see getDecodedSize(int)
	 */
	public int decode(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs) throws IllegalArgumentException;
	
	/**
	 * Encodes the given data.
	 * 
	 * @param src The data to be encoded.
	 * @return The encoded data.
	 */
	public String encode(byte src[]);

	/**
	 * Encodes the given data.
	 * 
	 * @param src The data to be encoded.
	 * @param srcOffs The offset of the data in src.
	 * @param srcSize The number of bytes in src.
	 * @return The encoded data.
	 */
	public String encode(byte src[], int srcOffs, int srcSize);

	/**
	 * Encodes the given data and put the result in dst.
	 * 
	 * @param src The data to be encoded.
	 * @param srcOffs The offset of the data in src.
	 * @param srcSize The number of bytes in src.
	 * @param dst The destination of the encoded data.
	 * @return The number of characters added to dst.
	 * @throws IOException
	 */
	public int encode(byte src[], int srcOffs, int srcSize, StringBuffer dst) throws IOException;
}
