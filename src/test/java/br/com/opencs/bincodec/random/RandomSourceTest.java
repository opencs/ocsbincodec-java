package br.com.opencs.bincodec.random;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class RandomSourceTest {

	@Test
	public void testRandomSource() {
		RandomSource r;
		
		r = new RandomSource(0);
		assertNotNull(r);
	}

	@Test
	public void testNext() throws Exception {
		testNextCore("/br/com/opencs/bincodec/random/RandomSequence1.txt");
		testNextCore("/br/com/opencs/bincodec/random/RandomSequence2.txt");
		testNextCore("/br/com/opencs/bincodec/random/RandomSequence3.txt");
	}
	
	public void testNextCore(String resourceName) throws Exception {
		RandomSource r;
		IntegerFileReader expected;
		
		expected = new IntegerFileReader(RandomSourceTest.class.getResourceAsStream(resourceName));
		try {
			r = new RandomSource(expected.nextInt());
			for (int i = 0; i < 1000; i++) {
				assertEquals(expected.nextInt(), r.next());
			}
			
		} finally {
			expected.close();
		}
	}	
	
	@Test
	public void testSync() {
		Random random = new Random();
		
		for (int i = 0; i < 1000; i++) {
			int seed = Math.abs(random.nextInt());
			RandomSource r1 = new RandomSource(seed);
			RandomSource r2 = new RandomSource(seed);
			for (int j = 0; j < 1000; j++) {
				assertEquals(r1.next(), r2.next());
			}
		}
	}
	
	

}
