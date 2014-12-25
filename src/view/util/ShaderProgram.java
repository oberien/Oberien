/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {

	private int program;
	private Shader s0, s1;

	private ShaderProgram() {
	}

	public ShaderProgram(Shader s0, Shader s1) {
		this.s0 = s0;
		this.s1 = s1;
	}

	public void create() {
		program = GL20.glCreateProgram();
		GL20.glAttachShader(program, s0.getID());
		GL20.glAttachShader(program, s1.getID());
		GL20.glLinkProgram(program);
	}
	
	public int getID() {
		return program;
	}
	 public void use() {
		 GL20.glUseProgram(program);
	 }
	 
	 public static void useZero() {
		 GL20.glUseProgram(0);
	 }
}
