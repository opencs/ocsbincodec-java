package br.com.opencs.bincodec;

/**
 * This class implements a StringAlphabet that does not generate errors
 * if the input of the functions are invalid.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2015.03.11
 */
public class FastStringAlphabet extends StringAlphabet {
	
	protected final int clearMask;

	public FastStringAlphabet(String map) {
		super(map);
		this.clearMask = (1 << size()) - 1;
	}

	@Override
	public int getValue(int c) throws IllegalArgumentException {
		return this.getIndexOf(c) & this.clearMask;
	}
}
