package br.com.opencs.bincodec;

/**
 * This interface must be implemented by all alphabets.
 * 
 * <p>
 * The each implementation of this interface is expected to be immutable.
 * </p>
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public interface Alphabet {

	/**
	 * Size of the alphabet.
	 * @return The size of the alphabet.
	 */
	public int size();
	
	/**
	 * Returns the character for a given value.
	 * 
	 * @param v The value. It must be a value between 0 and (2^size() - 1).
	 * @return The character for the given value. 
	 */
	public int getCharacter(int v);
	
	/**
	 * Returns the value of a given character.
	 * 
	 * @param c The character.
	 * @return The value of the character. 
	 * @throws IllegalArgumentException If c is not in the alphabet. 
	 */
	public int getValue(int c) throws IllegalArgumentException;
}
