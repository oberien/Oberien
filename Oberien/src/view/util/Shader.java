/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL20;

public class Shader {

	private int type;
	private File file;
	private int id;
	private String text;
	
	private Shader() {
	}

	public Shader(int type, String filepath) {
		this.type = type;
		file = new File(filepath);
	}

	public void loadShader() {
		StringBuilder src = new StringBuilder();
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"))) {
				String line;
				while ((line = br.readLine()) != null) {
					src.append(line).append("\n");
				}
				br.close();
			}
			fis.close();
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
		}
		text = src.toString();
	}

	public int getType() {
		return type;
	}

	public File getFile() {
		return file;
	}

	public String getText() {
		return text;
	}

	public void createShader() {
		id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, text);
		GL20.glCompileShader(id);
	}

	public int getID() {
		return id;
	}
}
