package br.com.opencs.bincodec;

import java.io.IOException;

public abstract class AbstractCodec implements Codec {

	public abstract int getDecodedSize(int encSize);

	public abstract int getEncodedSize(int decSize);

	public byte[] decode(CharSequence src) {
		return decode(src, 0, src.length());
	}
	
	public byte[] decode(CharSequence src, int srcOffs, int srcSize) {
		byte ret[];
		
		ret = new byte[getDecodedSize(srcSize)];
		decode(src, srcOffs, srcSize, ret, 0);		
		return ret;
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

	public abstract void encode(byte[] src, int srcOffs, int srcSize, Appendable dst)
			throws IOException;
}
