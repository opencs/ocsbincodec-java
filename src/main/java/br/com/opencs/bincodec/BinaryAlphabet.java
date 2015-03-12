package br.com.opencs.bincodec;

/**
 * This class implements the binary alphabet.
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.11
 */
public class BinaryAlphabet implements Alphabet {
	
	private final int zero;
	private final int one;
	
	/**
	 * Creates a new instance of this class using '0' as zero and '1' as one.
	 */
	public BinaryAlphabet() {
		this('0', '1');
	}
	
	/**
	 * Creates a new instance of this class using custom characters for zero and one.
	 * 
	 * @param zero The character for zero.
	 * @param one The character for one.
	 */
	public BinaryAlphabet(char zero, char one) {
		
		if (zero == one) {
			throw new IllegalArgumentException("One must not be equal to zero.");
		}
		this.zero = zero;
		this.one = one;
	}

	/**
	 * Returns the character for zero.
	 * 
	 * @return The character for zero.
	 */
	public int getZero() {
		return zero;
	}

	/**
	 * Returns the character for one.
	 * 
	 * @return The character for one.
	 */
	public int getOne() {
		return one;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public int getCharacter(int v) {

		if (v == 0) {
			return zero;
		} else {
			return one;
		}
	}

	@Override
	public int getValue(int c) throws IllegalArgumentException {
		
		if (c == zero) {
			return 0;
		} else if (c == one) {
			return 1;
		} else {
			throw new IllegalArgumentException("Invalid character.");
		}
	}
}
