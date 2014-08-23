package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;

public class SerializableStateIO extends Observable implements Runnable {	
	private boolean read, write;
	
	private SerializableState state;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private File file;
	
	public void readSerializableState(File file) {
		try {
			this.file = file;
			if (!file.exists()) {
				throw new FileNotFoundException(file.getAbsolutePath());
			}
			ois = new ObjectInputStream(new FileInputStream(file));
			read = true;
			new Thread(this).start();
		} catch (Exception e) {printException(e);}
	}
	
	public void writeSerializableState(File file, SerializableState state) {
		try {
			this.file = file;
			this.state = state;
			if (!file.exists()) {
				file.createNewFile();
			}
			oos = new ObjectOutputStream(new FileOutputStream(file));
			write = true;
			new Thread(this).start();
		} catch (Exception e) {printException(e);}
	}
	
	@Override
	public void run() {
		try {
			if ((write && !file.canWrite()) || (!write && !file.canRead())) {
				throw new IOException("No permissions to read or write to the specified file.");
			}
			if (read) {
				state = (SerializableState) ois.readObject();
				setChanged();
				notifyObservers(state);
				read = false;
			} else if (write) {
				oos.writeObject(state);
				setChanged();
				notifyObservers(null);
				write = false;
			}
			cleanUp();
		} catch (Exception e) {printException(e);}
		
	}
	
	private void printException(Exception e) {
		System.err.println("Exception caught during read/write process: ");
		e.printStackTrace();
		System.err.println("\nTerminating read/write module.");
		cleanUp();
	}
	
	private void cleanUp() {
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}
