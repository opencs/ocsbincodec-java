package br.com.opencs.bincodec;

import java.io.IOException;

public class Base2NCodec extends AbstractCodec {
	
	private final Alphabet alphabet;
	private final int size;
	private final int clearMask;
	
	public Base2NCodec(Alphabet alphabet) {
		this.alphabet = alphabet;
		this.size = alphabet.size();
		this.clearMask = (1 << alphabet.size()) - 1;
	}
	
	public int getDecodedSize(int encSize) {
		return (encSize * size) / 8;
	}

	public int getEncodedSize(int decSize) {
		return (((decSize * 8) + size - 1) / size);
	}

	public void decode(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs) {
		
	}
	
	public void encode(byte src[], int srcOffs, int srcSize, Appendable dst) throws IOException {
		int bitBuffer;
		int bitBufferSize;
		int srcEndOffs;
		
		bitBuffer = 0;
		bitBufferSize = 0;
		srcEndOffs = srcOffs + srcSize;
		while (srcOffs < srcEndOffs) {
			// Get byte from source
			bitBuffer = (bitBuffer << 8) | (src[srcOffs] & 0xFF);
			srcOffs++;
			bitBufferSize += 8;
			
			// Add characters to dst
			while (bitBufferSize >= size) {
				bitBufferSize -= size;
				dst.append((char)this.alphabet.getCharacter((bitBuffer >> bitBufferSize) & clearMask));
			}			
		}
		
		// Add the last one
		if (bitBufferSize > 0) {
			bitBuffer = bitBuffer << (size - bitBufferSize);
			dst.append((char)this.alphabet.getCharacter(bitBuffer & clearMask));
		}		
	}	
}
