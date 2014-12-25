#version 420

layout (location = 0) in vec2 inPosition;

smooth out vec2 outPosition;

void main() {
	gl_Position = inPosition;
}