package br.com.opencs.bincodec;

import java.util.Random;

import junit.framework.TestCase;

public abstract class BaseCodecTest extends TestCase {

	protected Random random = new Random();

	protected void testEncodeDecodeCore(Codec c) {

		for (int size = 1; size <= 1024; size++) {
			final byte src[] = new byte[size];
			random.nextBytes(src);

			final String enc = c.encode(src);
			final byte dst[] = c.decode(enc);
			Assert.assertArrayEquals(src, dst);
		}
	}
}
