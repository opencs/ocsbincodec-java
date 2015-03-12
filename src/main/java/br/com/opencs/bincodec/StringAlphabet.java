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
	 * The required number of bits.
	 */
	protected final int size;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param map The set of characters to be used.
	 */
	public StringAlphabet(String map) {
		this.map = map.toCharArray();
		this.size = this.computeSize(this.map.length);
	}
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param map The set of characters to be used.
	 */
	public StringAlphabet(char map[]) {
		this.map = map.clone();
		this.size = this.computeSize(map.length);
	}	

	/**
	 * Sets the size based on the size of the map.
	 * 
	 * @param mapSize The size of the map.
	 */
	private int computeSize(int mapSize) {
		int size;
		int tmpMapSize;
		
		// Check the limits
		if (mapSize < 2) {
			throw new IllegalArgumentException("The map must have at least 2 characters.");
		}
		if (mapSize > 128) {
			throw new IllegalArgumentException("The map must have at most 128 characters.");
		}
		
		// Find the actual size
		size = 0;
		do {
			size++;
			tmpMapSize = 1 << size;
		} while ((size < 8) && (tmpMapSize < mapSize));
		
		if (tmpMapSize != mapSize) {
			throw new IllegalArgumentException("The map must be a power of 2.");
		} else {
			return size;
		}
	}

	public int size() {
		return this.size;
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
