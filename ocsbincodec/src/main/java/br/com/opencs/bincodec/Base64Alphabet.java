package br.com.opencs.bincodec;

/**
 * The alphabet of Base64 as defined by the RFC4648. It can use the standard
 * and the URL safe variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class Base64Alphabet extends ArrayAlphabet {
	
	private static final String ALPHABET_PREFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
	private static final String STANDARD_POSTFIX = "+/";
	private static final String SAFE_POSTFIX = "-_";
	
	private final int c62;
	private final int c63;
	
	/**
	 * Creates a new instance of this class using the standard alphabet.
	 */
	public Base64Alphabet(){
		this(false);
	}
	
	/**
	 * Creates a new instance of this.
	 * 
	 * @param urlSafe If true, uses the URL safe alphabet otherwise used the standard one.
	 */
	public Base64Alphabet(boolean urlSafe){
		super(ALPHABET_PREFIX + (urlSafe ? SAFE_POSTFIX : STANDARD_POSTFIX));
		
		if (urlSafe) {
			c62 = '-';
			c63 = '_';
		} else {
			c62 = '+';
			c63 = '/';
		}
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

		if ((c >= 'A') && (c <= 'F')) {
			return c - 'A';
		} else if ((c >= 'a') && (c <= 'f')) {
			return c - 'a' + 26;
		} else  if ((c >= '0') && (c <= '9')) {
			return c - '0' + 52;
		} else if (c == c62) {
			return 62;
		} else if (c == c63) {
			return 63;
		} else {
			throw new IllegalArgumentException("Invalid Base64 character.");
		}
	}	
}
