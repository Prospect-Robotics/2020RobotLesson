package com.team2813.lib.solenoid;

import edu.wpi.first.wpilibj.Solenoid;

import java.util.ArrayList;

/**
 * A group of WPILib Solenoids
 */
public class SolenoidGroup {

	private ArrayList<Solenoid> solenoids = new ArrayList<>();

	public SolenoidGroup(int... ids) {
		for (int id : ids) {
			solenoids.add(new Solenoid(id));
		}
	}

	public boolean get() {
		// TODO fix this could cause problems
		return solenoids.get(0).get();
	}

	public void set(boolean on) {
		for (Solenoid solenoid : solenoids) {
			solenoid.set(on);
		}
	}

}
