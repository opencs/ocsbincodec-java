package br.com.opencs.bincodec;

import java.io.IOException;

/**
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.11
 */
public interface Codec {
	
	public int getDecodedSize(int encSize);

	public int getEncodedSize(int decSize);
	
	public byte[] decode(CharSequence src);
	
	public byte[] decode(CharSequence src, int srcOffs, int srcSize);

	public int decode(CharSequence src, int srcOffs, int srcSize, byte dst[], int dstOffs);
	
	public String encode(byte src[]);
	
	public String encode(byte src[], int srcOffs, int srcSize);
	
	public void encode(byte src[], int srcOffs, int srcSize, Appendable dst) throws IOException;
}
