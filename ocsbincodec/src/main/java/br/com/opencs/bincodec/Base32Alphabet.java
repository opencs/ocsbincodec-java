package br.com.opencs.bincodec;

/**
 * The alphabet of Base 32 as defined by the RFC4648.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class Base32Alphabet extends ArrayAlphabet {
	
	private static final String ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

	/**
	 * Creates a new instance of this class. The method 
	 * getCharacter() will always return upper case characters.
	 */
	public Base32Alphabet() {
		this(false);
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param lowerCase Determines the behavior of getCharacter(). If true, it will
	 * return lower case characters otherwise it will return upper case characters.
	 */
	public Base32Alphabet(boolean lowerCase) {
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

		if ((c >= 'a') && (c <= 'z')) {
			return c - 'a';
		} else if ((c >= 'A') && (c <= 'Z')) {
			return c - 'A';
		} else if ((c >= '0') && (c <= '9')) {
				return c - '0' + 26;
		} else {
			throw new IllegalArgumentException("Invalid Base 32 character.");
		}
	}	
}
