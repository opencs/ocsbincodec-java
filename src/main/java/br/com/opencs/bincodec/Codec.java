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
	 * @return The expected encoded size.
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
	 * @throws IllegalArgumentException If src contains invalid.
	 */
	public byte[] decode(CharSequence src) throws IllegalArgumentException;

	/**
	 * Decodes the encoded data.
	 * 
	 * @param src The encoded data.
	 * @param srcOffs The initial offset in src.
	 * @param srcSize The number of characters in src.
	 * @return The decoded value.
	 * @throws IllegalArgumentException If src contains invalid.
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
	 * @throws IllegalArgumentException If src contains invalid.
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
	public int encode(byte src[], int srcOffs, int srcSize, Appendable dst) throws IOException;
}
