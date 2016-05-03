package br.com.opencs.bincodec;

import br.com.opencs.junit.TestCaseEx;

import java.util.Random;

public abstract class BaseCodecTest extends TestCaseEx {
	
	protected Random random = new Random();
	
	
	protected void testEncodeDecodeCore(Codec c) {
		
		for (int size = 1; size <= 1024; size++) {
			byte src[] = new byte[size];
			random.nextBytes(src);
			
			String enc = c.encode(src);
			byte dst[] = c.decode(enc);
			assertArrayEquals(src, dst);
		}		
	}
}
