package br.com.opencs.bincodec;

public class Assert extends junit.framework.Assert {

	public static void assertArrayEquals(byte[] first, byte[] second) {
		if ((first == null) || (second == null) || (first.length != second.length)) {
			fail("Arrays are different!");
		} else {
			for (int i = 0; i < second.length; i++) {
				if (first[i] != second[i])
					fail("Arrays are different!");
			}
		}
	}

	public static void assertArrayEquals(char[] first, char[] second) {
		if ((first == null) || (second == null) || (first.length != second.length)) {
			fail("Arrays are different!");
		} else {
			for (int i = 0; i < second.length; i++) {
				if (first[i] != second[i])
					fail("Arrays are different!");
			}
		}
	}

}
