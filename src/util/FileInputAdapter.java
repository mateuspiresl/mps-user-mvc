package util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileInputAdapter implements Closeable
{
	private ObjectInputStream in;
	
	public FileInputAdapter(String fileName) throws IOException
	{
		FileInputStream fin = new FileInputStream(fileName);
	    this.in = new ObjectInputStream(fin);
	}
	
	public Object read() throws ClassNotFoundException, IOException {
		return this.in.readObject();
	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}
}

