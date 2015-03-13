package br.com.opencs.bincodec;

/**
 * The hexadecimal alphabet.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class HexadecimalAlphabet extends ArrayAlphabet {
	
	private static final String ALPHABET = "0123456789ABCDEF";

	/**
	 * Creates a new instance of this class. The method 
	 * getCharacter() will always return upper case characters.
	 */
	public HexadecimalAlphabet() {
		this(false);
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param lowerCase Determines the behavior of getCharacter(). If true, it will
	 * return lower case characters otherwise it will return upper case characters.
	 */
	public HexadecimalAlphabet(boolean lowerCase) {
		super(lowerCase?ALPHABET.toLowerCase():ALPHABET);
	}

	/**
	 * Returns the value of a given character. This method is case
	 * insensitive.
	 * 
	 * @param c The character.
	 * @return The value of the character. 
	 * @throws IllegalArgumentException If c is not in the alphabet. 
	 */
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
