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
 * This class implements a generic base 2^n codec.
 * 
 * <h3>Alphabet</h3>
 * 
 * <p></p>
 *
 * <h3>Ignored characters</h3>
 * 
 * <p>This class optionally allows the definition of some characters to be ignored from
 * the input while decoding data. This can be enabled by providing a list of characters
 * as a char array.</p>
 * 
 * <p>Since this list is verified right after the character is extracted from the source,
 * it is very important to ensure that this list does not contain the padding character
 * or any other character valid for the specified alphabet.</p>
 * 
 * <h3>Padding</h3>
 * 
 * <p>This class implements a customized padding scheme. It requires
 * the definition of the size of the padding block and the padding character.
 * </p>
 * 
 * <p>If the padding is not enabled, the codec will add padding characters
 * to align the encoded data to the padding block size. It will also threat
 * the padding character as an invalid character while decoding the data.</p>
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class Base2NCodec extends AbstractCodec {
	
	/**
	 * Constant that
	 */
	public static final char IGNORE_SPACES[] = " \t\n\r".toCharArray();
	
	public static final char IGNORE_NONE[] = new char[0];
	
	private final Alphabet alphabet;
	private final int size;
	private final int clearMask;
	private final int paddingChar;
	private final int paddingBLockSize;
	private final char ignored[];
	
	/**
	 * Creates a new instance of this class. This constructor 
	 * disables the padding and the ignore list.
	 * 
	 * @param alphabet The alphabet to be used. The size of the alphabet must be a power of two.
	 */
	public Base2NCodec(Alphabet alphabet) {
		this(alphabet, 0, 0, null);
	}

	/**
	 * Creates a new instance of this class. This constructor 
	 * disables the ignore list.
	 * 
	 * @param alphabet The alphabet to be used. The size of the alphabet must be a power of two larger than zero 
	 * and less than or equal to 128.
	 * @param paddingChar The padding character.
	 * @param paddingBlockSize The size of the padding block. Any value smaller than 2 disables the padding.
	 */
	public Base2NCodec(Alphabet alphabet, int paddingChar, int paddingBlockSize) {
		this(alphabet, paddingChar, paddingBlockSize, null);
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param alphabet The alphabet to be used. The size of the alphabet must be a power of two.
	 * @param paddingChar The padding character.
	 * @param paddingBlockSize The size of the padding block. Any value smaller than 2 disables the padding.
	 * @param ignored List of characters to be ignored from the input. Use null to disable this feature.
	 */
	public Base2NCodec(Alphabet alphabet, int paddingChar, int paddingBlockSize, char ignored[]) {
		
		this.alphabet = alphabet;
		this.paddingBLockSize = paddingBlockSize;
		if (paddingBLockSize != 0) {
			this.paddingChar = paddingChar;
		} else {
			this.paddingChar = 0;
		}
		this.size = getCharacterSize(alphabet);
		this.clearMask = (1 << this.size) - 1;
		if (ignored != null) {
			this.ignored = ignored.clone();
		} else {
			this.ignored = IGNORE_NONE;
		}
	}
	
	private int getCharacterSize(Alphabet alphabet) throws IllegalArgumentException {
		
		switch (alphabet.size()) {
		case 2:
			return 1;
		case 4:
			return 2;
		case 8:
			return 3;
		case 16:
			return 4;
		case 32:
			return 5;
		case 64:
			return 6;
		case 126:
			return 7;
		default:
			throw new IllegalArgumentException("Invalid alphabet.");
		}
	}
	
	public int getDecodedSize(int encSize) {
		return (encSize * size) / 8;
	}
	
	/**
	 * Returns the size of the padding.
	 * 
	 * @param totalSize The total size of the output.
	 * @return The size of the padding. It is always zero if the padding is not used.
	 */
	protected int getPaddingSize(int totalSize) {
		
		if (usesPadding()) {
			return (this.paddingBLockSize - (totalSize % this.paddingBLockSize)) % this.paddingBLockSize;
		} else {
			return 0;
		}
	}

	public int getEncodedSize(int decSize) {
		int totalSize;
		
		totalSize = (((decSize * 8) + size - 1) / size); 
		return totalSize + getPaddingSize(totalSize);
	}
	
	/**
	 * Verifies if this instance uses padding or not.
	 * 
	 * @return true if the padding is used of false otherwise.
	 */
	public boolean usesPadding(){
		return (this.paddingBLockSize >= 2);
	}
	
	protected boolean isPadding(int c) {
		
		if (usesPadding()) {
			return (c == this.paddingChar);
		} else {
			return false;
		}
	}
	
	/**
	 * Verifies if c is inside the ignored list.
	 * 
	 * @param c The character to be verified.
	 * @return true if c must be ignored or false otherwise.
	 */
	protected boolean isIgnored(int c) {
		
		for (int i = 0; i < this.ignored.length; i++) {
			if (c == this.ignored[i]) {
				return true;
			}
		}
		return false;			
	}

	public int decode(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs) {
		int bitBuffer;
		int bitBufferSize;
		int srcEndOffs;
		int c;
		int oldDstOffs;
		boolean stop;
		int paddingSize;
		int srcTrueSize;
		
		// Scan the src
		bitBuffer = 0;
		bitBufferSize = 0;
		oldDstOffs = dstOffs;
		paddingSize = 0;
		srcTrueSize = 0;
		srcEndOffs = srcOffs + srcSize;
		stop = srcOffs >= srcEndOffs;
		while (!stop) {
			// Get a character from source
			c = src.charAt(srcOffs);
			srcOffs++;
			stop = srcOffs == srcEndOffs;
			
			// Check if the character must be ignored or not
			if (!isIgnored(c)) {
				srcTrueSize++;
				// Process the character.
				if (isPadding(c)) {
					stop = true;
					paddingSize = 1;
				} else {
					// Add it to the bit buffer
					bitBuffer = (bitBuffer << size) | (alphabet.getValue(c));
					bitBufferSize += size;
					
					// Add bytes to dst
					while (bitBufferSize >= 8) {
						bitBufferSize -= 8;
						dst[dstOffs] = (byte)((bitBuffer >> bitBufferSize) & 0xFF);
						dstOffs++;
					}
				}
			}
		}
		
		// Check padding, if required
		if (usesPadding()) {
			if (paddingSize > 0) {
				// Remove the padding
				stop = (srcOffs == srcEndOffs);
				while (!stop) {
					// Get a character from source
					c = src.charAt(srcOffs);
					srcOffs++;
					stop = (srcOffs == srcEndOffs);
					// Verify if it has a valid padding or not
					if (!isIgnored(c)) {
						srcTrueSize++;
						if (!isPadding(c)) {
							throw new IllegalArgumentException("Invalid padding.");
						}
					}					
				}
			}
			if ((srcTrueSize % this.paddingBLockSize) != 0) {
				throw new IllegalArgumentException("The input is not properly padded.");
			}
		}

		return  dstOffs - oldDstOffs;
	}
	
	public int encode(byte src[], int srcOffs, int srcSize, Appendable dst) throws IOException {
		int bitBuffer;
		int bitBufferSize;
		int srcEndOffs;
		int dstSize;
		
		bitBuffer = 0;
		bitBufferSize = 0;
		srcEndOffs = srcOffs + srcSize;
		dstSize = 0;
		while (srcOffs < srcEndOffs) {
			// Get byte from source
			bitBuffer = (bitBuffer << 8) | (src[srcOffs] & 0xFF);
			srcOffs++;
			bitBufferSize += 8;
			
			// Add characters to dst
			while (bitBufferSize >= size) {
				bitBufferSize -= size;
				dst.append((char)this.alphabet.getCharacter((bitBuffer >> bitBufferSize) & clearMask));
				dstSize++;
			}			
		}
		
		// Add the last one
		if (bitBufferSize > 0) {
			bitBuffer = bitBuffer << (size - bitBufferSize);
			dst.append((char)this.alphabet.getCharacter(bitBuffer & clearMask));
			dstSize++;
		}
		
		// Add padding if required
		int paddingSize = getPaddingSize(dstSize);
		dstSize += paddingSize;
		while (paddingSize > 0) {
			dst.append((char)this.paddingChar);
			paddingSize--;
		}
		
		return dstSize;
	}	
}
