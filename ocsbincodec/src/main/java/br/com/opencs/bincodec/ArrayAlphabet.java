package br.com.opencs.bincodec;

/**
 * This class implements an alphabet that is backed by a char
 * array.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.13
 */
public class ArrayAlphabet implements Alphabet {
	
	protected final char alphabet[];

	public ArrayAlphabet(char alphabet[]) {
		this.alphabet = alphabet.clone();
	}
	
	public ArrayAlphabet(String alphabet) {
		this.alphabet = alphabet.toCharArray();
	}

	@Override
	public int size() {
		return alphabet.length;
	}

	@Override
	public int getCharacter(int v) {
		return alphabet[v];
	}

	/**
	 * Returns the value of a given character.
	 * 
	 * <p>This implementation uses a simple sequential scan to map
	 * the character back to its value. Subclasses are encouraged to
	 * provide the most efficient implementation possible.</p>
	 * 
	 * @param c The character.
	 * @return The value of the character. 
	 * @throws IllegalArgumentException If c is not in the alphabet. 
	 */
	@Override
	public int getValue(int c) throws IllegalArgumentException {
		int v;
		
		// This implementation will use a very simple sequential scan
		for (v = 0; v < this.alphabet.length; v++) {
			if (c == this.alphabet[v]) {
				return v;
			}
		}
		throw new IllegalArgumentException("Invalid character.");
	}
}
