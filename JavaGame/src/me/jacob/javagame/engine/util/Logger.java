package me.jacob.javagame.engine.util;

public class Logger {
	
	// TODO add ability to hide these messages
	
	public static void debug(String message) {
		System.out.println("DEBUG: " + message);
	}
	
	public static void info(String message) {
		System.out.println("INFO: " + message);
	}
	
	public static void warn(String message) {
		System.out.println("WARN: " + message);
	}
	
	public static void error(String message) {
		System.err.println("ERROR: " + message);
	}
	
}