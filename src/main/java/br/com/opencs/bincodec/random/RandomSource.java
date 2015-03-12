package br.com.opencs.bincodec.random;

/**
 * This class implements a simple random source.
 * 
 * <p>
 * Internally, this implementation uses a linear congruential generator with the same
 * parameters used by the glibc.
 * </p>
 * 
 * <p>
 * This implementation is not thread safe.
 * </p>
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class RandomSource {

	/**
	 * The module m.
	 */
	private static final long M = 0x7FFFFFFF;
	
	/**
	 * The multiplier a. 
	 */
	private static final long A = 1103515245;
	
	/**
	 * The constant c.
	 */
	private static final long C = 12345;
	
	/**
	 * The current state.
	 */
	private long state;
	
	/**
	 * Creates a new random source.
	 * 
	 * @param seed The initial seed.
	 */
	public RandomSource(long seed) {
		this.state = seed & M;
	}

	/**
	 * Returns the next 31 bit integer in the sequence.
	 * 
	 * @return the nex value.
	 */
	public int next(){
		
		state = ((state * A) + C) & M;
		return (int)state; 
	}
}
