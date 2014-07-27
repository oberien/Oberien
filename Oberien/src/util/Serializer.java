package util;

import controller.Controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is used to save and load a Controller and thus a complete
 * gamesession.
 */
public class Serializer implements Runnable {

	private boolean terminate, write, updated, updateStreams;
	private FileOutputStream fout;
	private FileInputStream fin;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Controller controller;
	private File f;

	/**
	 * Use this method to shut down the thread. Please don't use the built-in
	 * methods from Thread.
	 */
	public void terminate() {
		terminate = true;
	}

	private void cleanUp() {
		try {
			fout.flush();
			out.flush();
			out.close();
			fout.close();
			in.close();
			fin.close();
		} catch (IOException ex) {
			printExMessage(ex);
		} finally {
			try {
				out.close();
				fout.close();
				in.close();
				fin.close();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * Tells the thread to write a Controller to the specified File.
	 *
	 * @param controller The Controller to write.
	 * @throws IllegalStateException when the File to write to wasn't specified
	 * with <code>setTargetFile(File f)</code> before.
	 */
	public void write(Controller controller) throws IllegalStateException {
		if (f != null) {
			this.controller = controller;
			write = true;
			notify();
		} else {
			throw new IllegalStateException("No file to write to was specified.");
		}
	}

	/**
	 * Reads a Controller from the specified File. This method behaves slightly
	 * different from other read methods, as it doesn't return the read object,
	 * but instead writes it to an internal object which can be accessed by
	 * calling <code>getController()</code>.
	 *
	 * @throws IllegalStateException when the File to read from wasn't specified
	 * with <code>setTargetFile(File f)</code> before.
	 */
	public void read() throws IllegalStateException {
		if (f != null) {
			write = false;
			notify();
		} else {
			throw new IllegalStateException("No file to read from was specified.");
		}
	}

	/**
	 * Getter for the Controller that was read with <code>read()</code> before.
	 *
	 * @return the Controller that was read before.
	 * @throws IllegalStateException when read() wasn't called first or the
	 * reading process wasn't done yet.
	 */
	public synchronized Controller getController() throws IllegalStateException {
		if (updated) {
			updated = false;
			return controller;
		} else {
			throw new IllegalStateException("Controller wasn't updated yet. Call read() first before you call this method.");
		}
	}

	/**
	 * Sets the target File to perform the next operations on. Must be invoked
	 * before all calls to <code>read()</code> or
	 * <code>write(Controller controller)</code>.
	 *
	 * @param f The new target File.
	 */
	public void setTargetFile(File f) {
		this.f = f;
		updateStreams = true;
	}

	/**
	 * WARNING: This method doesn't only print the error message, but also
	 * terminates the thread. Use with care.
	 */
	private void printExMessage(Exception ex) {
		System.err.println("Exception within the save module: " + ex.getMessage() + "\nTerminating save module.");
		terminate = true;
	}

	@Override
	public void run() {
		while (!terminate) {
			try {
				wait();
				if (updateStreams) {
					if (!f.exists()) {
						f.createNewFile();
					}
					if ((write && !f.canWrite()) || (!write && !f.canRead())) {
						throw new UnsupportedOperationException("No permissions to read or write to the specified file");
					}
					
					fin = new FileInputStream(f);
					in = new ObjectInputStream(fin);
					fout = new FileOutputStream(f);
					out = new ObjectOutputStream(fout);

				}
			} catch (Exception ex) {
				printExMessage(ex);
			}
		}
		cleanUp();
	}
}
