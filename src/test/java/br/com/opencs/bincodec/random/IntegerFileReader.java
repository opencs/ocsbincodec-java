package br.com.opencs.bincodec.random;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;

/**
 * This class implements a very simple integer sequence reader. It reads a 
 * stream that contains one integer per line.
 * 
 * <p>Empty lines and lines starting with '#' are ignored.</p>
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.12
 */
public class IntegerFileReader implements Closeable {
	
	private LineNumberReader reader;
	
	public IntegerFileReader(InputStream in) throws IOException {
		reader = new LineNumberReader(new InputStreamReader(in, Charset.forName("utf-8")));
	}
	
	public void close() throws IOException {
		reader.close();		
	}
	
	protected String getValidLine() throws IOException {
		String line;
		boolean stop;
		
		stop = false;
		do {
			line = reader.readLine();
			if (line == null) {
				stop = true;
			} else {
				line = line.trim();
				stop = ((!line.isEmpty()) && (!line.startsWith("#")));
			}
		} while (!stop);
		return line;		
	}
	
	public int nextInt() throws IOException, EOFException {
		
		String line = getValidLine();
		if (line == null) {
			throw new EOFException();
		} else {
			try {
				return Integer.parseInt(line);
			} catch (NumberFormatException e) {
				throw new StreamCorruptedException(String.format("Invalid integer on line %1$d.", reader.getLineNumber()));
			}
		}
	}
}
