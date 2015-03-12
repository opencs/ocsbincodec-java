package br.com.opencs.bincodec;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryAlphabetTest {

	@Test
	public void testBinaryAlphabet() {
		BinaryAlphabet a;
		
		a = new BinaryAlphabet();
		testBinaryAlphabetCore(a, '0', '1');
	}
	
	@Test
	public void testBinaryAlphabetCharChar() {
		BinaryAlphabet a;
		
		a = new BinaryAlphabet('a', 'b');
		testBinaryAlphabetCore(a, 'a', 'b');
	}	
	
	protected void testBinaryAlphabetCore(BinaryAlphabet a, int zero, int one) {

		assertEquals(1, a.size());
		assertEquals(zero, a.getZero());
		assertEquals(one, a.getOne());
		
		assertEquals(zero, a.getCharacter(0));
		assertEquals(one, a.getCharacter(1));

		assertEquals(0, a.getValue(zero));
		assertEquals(1, a.getValue(one));
		
		for (int c = 0; c < 256; c++) {
			if (c == zero) {
				assertEquals(0, a.getValue(c));
			} else if (c == one) {
				assertEquals(1, a.getValue(c));
			} else {
				try {
					a.getValue(c);
					fail();
				} catch (Exception e) {}
			}
		}
	}
}
