package de.bitbrain.mindmazer.levelgen;

import java.util.Random;

public class Seeder {

	private Random output;
	private long seed;
	private String seedString;
	
	public void regenerate(String seedString) {
		seed = seedString.hashCode();
		output = new Random(seed);
	}
	
	public long seed() {
		return seed;
	}
	
	public String string() {
		return seedString;
	}
	
	public Random output() {
		return output;
	}
}
