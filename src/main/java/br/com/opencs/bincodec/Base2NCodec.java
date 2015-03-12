package br.com.opencs.bincodec;

import java.io.IOException;

public class Base2NCodec extends AbstractCodec {
	
	private final Alphabet alphabet;
	private final int size;
	private final int clearMask;
	private final int paddingChar;
	private final int paddingBLockSize;
	private final char ignored[];
	
	public Base2NCodec(Alphabet alphabet, int paddingChar, int paddingBlockSize, char ignored[]) {
		
		this.alphabet = alphabet;
		this.paddingChar = paddingChar;
		this.paddingBLockSize = paddingBlockSize;
		this.size = alphabet.size();
		this.clearMask = (1 << this.size) - 1;
		if (ignored != null) {
			this.ignored = ignored.clone();
		} else {
			this.ignored = null;
		}
	}
	
	public int getDecodedSize(int encSize) {
		return (encSize * size) / 8;
	}

	public int getEncodedSize(int decSize) {
		return (((decSize * 8) + size - 1) / size);
	}
	
	/**
	 * Verifies if c is inside the ignored list.
	 * 
	 * @param c The character to be verified.
	 * @return true if c must be ignored or false otherwise.
	 */
	protected boolean isIgnored(int c) {
		
		if (this.ignored != null) {
			for (int i = 0; i < this.ignored.length; i++) {
				if (c == this.ignored[i]) {
					return true;
				}
			}
		}		
		return false;			
	}
	
	public int decode(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs) {
		
		return this.decodeCore(src, srcOffs, srcSize, dst, dstOffs);
	}

	protected int decodeCore(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs) {
		int bitBuffer;
		int bitBufferSize;
		int srcEndOffs;
		int oldDstOffs;
		int c;
		
		bitBuffer = 0;
		bitBufferSize = 0;
		oldDstOffs = dstOffs;
		srcEndOffs = srcOffs + srcSize;
		while (srcOffs < srcEndOffs) {
			// Get a character from source
			c = src.charAt(srcOffs);
			srcOffs++;

			if (!isIgnored(c)) {
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
		
		return dstSize;
	}	
}
