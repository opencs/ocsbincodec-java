package br.com.opencs.bincodec;

/**
 * This class implements an alphabet that uses a character array as
 * the mapping.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class StringAlphabet implements Alphabet {
	
	/**
	 * The map itself.
	 */
	protected final char[] map;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param map The set of characters to be used.
	 */
	public StringAlphabet(String map) {
		this.map = map.toCharArray();
	}
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param map The set of characters to be used.
	 */
	public StringAlphabet(char map[]) {
		this.map = map.clone();
	}	
	
	public int size() {
		return map.length;
	}

	public int getCharacter(int v) {
		return map[v];
	}
	
	/**
	 * Returns the 1st index of a given character in the map.
	 * 
	 * @param c The character.
	 * @return The index of the character in map or -1 if it cannot be found.
	 */
	protected int getIndexOf(int c){
		int i;

		// Since maps are small and out of order, the sequential scan is the best approach.
		i = 0;
		while (i < this.map.length) {
			if (this.map[i] == c) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}

	public int getValue(int c) throws IllegalArgumentException {
		int v;
		
		v = getIndexOf(c);
		if (v < 0) {
			throw new IllegalArgumentException("Invalid character.");
		} else {
			return v;
		}
	}
}
