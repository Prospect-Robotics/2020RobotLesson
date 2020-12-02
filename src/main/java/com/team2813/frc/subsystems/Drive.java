package com.team2813.frc.subsystems;

import edu.wpi.first.wpilibj.Joystick;

public class Drive extends Subsystem {
    Joystick joystick = new Joystick(0); // get the joystick

    double throttleDemand;

    @Override
    protected void initDefaultCommand() {}

    @Override
    public void readPeriodicInputs() {

    }

    @Override
    public void onEnabledLoop() {
        double x = joystick.getRawAxis(0);
        double throttle = joystick.getRawAxis(1);

        System.out.println("Moving forwards at " + throttle + " and steering at " + x);
    }
}
