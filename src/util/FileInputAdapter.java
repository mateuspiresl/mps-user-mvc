package util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileInputAdapter implements Closeable
{
	private ObjectInputStream in;
	
	/**
	 * TODO
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FileInputAdapter(String fileName) throws IOException
	{
		FileInputStream fin = new FileInputStream(fileName);
	    this.in = new ObjectInputStream(fin);
	}
	
	/**
	 * TODO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object read() throws ClassNotFoundException, IOException {
		return this.in.readObject();
	}

	/**
	 * TODO
	 */
	@Override
	public void close() throws IOException {
		this.in.close();
	}
}

