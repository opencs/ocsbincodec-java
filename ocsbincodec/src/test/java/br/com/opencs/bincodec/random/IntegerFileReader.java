/*
 * Copyright (c) 2015, Open Communications Security
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of ocsbincodec-java nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.opencs.bincodec.random;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * This class implements a very simple integer sequence reader. It reads a 
 * stream that contains one integer per line.
 * 
 * <p>Empty lines and lines starting with '#' are ignored.</p>
 * 
 * @author Fabio Jun Takada Chino (fchino at opencs.com.br)
 * @since 2015.03.12
 */
public class IntegerFileReader {
	
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
				stop = ((line.length() != 0) && (!line.startsWith("#")));
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
				throw new StreamCorruptedException(
						MessageFormat.format("Invalid integer on line {0,number}.", new Object[]{new Integer(reader.getLineNumber())}));
			}
		}
	}
}
