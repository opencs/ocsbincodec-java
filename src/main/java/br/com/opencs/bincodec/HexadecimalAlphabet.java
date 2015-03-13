package br.com.opencs.bincodec;

public class HexadecimalAlphabet extends ArrayAlphabet {
	
	private static final String ALPHABET = "0123456789ABCDEF";

	public HexadecimalAlphabet() {
		this(false);
	}

	public HexadecimalAlphabet(boolean lowercase) {
		super(lowercase?ALPHABET.toLowerCase():ALPHABET);
	}

	@Override
	public int getValue(int c) throws IllegalArgumentException {

		if ((c >= '0') && (c <= '9')) {
			return c - '0';
		} else if ((c >= 'a') && (c <= 'f')) {
			return c - 'a' + 10;
		} else if ((c >= 'A') && (c <= 'F')) {
			return c - 'A' + 10;
		} else {
			throw new IllegalArgumentException("Invalid hexadecimal character.");
		}
	}
}
