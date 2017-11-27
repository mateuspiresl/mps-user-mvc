package util;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileOutputAdapter implements Closeable
{
	private ObjectOutputStream out;
	
	public FileOutputAdapter(String fileName) throws IOException
	{
		FileOutputStream fout = new FileOutputStream(fileName);
	    this.out = new ObjectOutputStream(fout);
	}
	
	public void write(Object object) throws IOException {
		this.out.writeObject(object);
	}

	@Override
	public void close() throws IOException {
		this.out.close();
	}
}
