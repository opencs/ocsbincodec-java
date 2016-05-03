package br.com.opencs.bincodec;

import static org.junit.Assert.*;

import java.util.Random;

public class BaseCodecTest {
	
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
