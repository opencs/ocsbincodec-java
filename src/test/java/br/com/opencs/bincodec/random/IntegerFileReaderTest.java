package br.com.opencs.bincodec.random;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import org.junit.Test;

public class IntegerFileReaderTest {

	@Test
	public void testIntegerFileReader() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		r.close();
	}

	@Test
	public void testClose() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		r.close();
		r.close();
	}

	@Test
	public void testNextInt() throws Exception {
		// 0-9, no newline on last line
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest1.txt");
		// 0-9, new line on the no newline on last line
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest2.txt");
		// 0-9, with empty lines
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest3.txt");
		// 0-9, with empty lines and comments
		testNextIntCore("/br/com/opencs/bincodec/random/IntegerFileReaderTest4.txt");
	}

	protected void testNextIntCore(String resourceName) throws IOException, EOFException {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream(resourceName);
		assertNotNull(in);
		r = new IntegerFileReader(in);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, r.nextInt());
		}
		try {
			r.nextInt();
			fail();
		} catch (EOFException e) {}
		r.close();
	}
	
	@Test
	public void testNextIntBroken() throws Exception {
		IntegerFileReader r;
		InputStream in;
		
		in = IntegerFileReaderTest.class.getResourceAsStream("/br/com/opencs/bincodec/random/IntegerFileReaderTest5.txt");
		assertNotNull(in);
		r = new IntegerFileReader(in);
		for (int i = 0; i < 7; i++) {
			assertEquals(i, r.nextInt());
		}
		try {
			r.nextInt();
			fail();
		} catch (StreamCorruptedException e) {
			assertEquals("Invalid integer on line 10.", e.getMessage());
			
		}
		r.close();
	}

}
