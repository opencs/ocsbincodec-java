package br.com.opencs.bincodec;

import java.io.IOException;
import java.util.Arrays;

public abstract class AbstractCodec implements Codec {

	public abstract int getDecodedSize(int encSize);

	public abstract int getEncodedSize(int decSize);

	public byte[] decode(CharSequence src) {
		return decode(src, 0, src.length());
	}
	
	public byte[] decode(CharSequence src, int srcOffs, int srcSize) {
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
