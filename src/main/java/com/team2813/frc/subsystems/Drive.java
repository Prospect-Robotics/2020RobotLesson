package com.team2813.frc.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Drive extends Subsystem {
    VictorSP driveLeft = new VictorSP(0);
    VictorSP driveRight = new VictorSP(1);

    Joystick joystick = new Joystick(0); // get the joystick

    private double steerDemand;
    private double throttleDemand;
    private boolean slowMode;

    @Override
    protected void initDefaultCommand() {}

    @Override
    public void readPeriodicInputs() {
        steerDemand = joystick.getRawAxis(0);
        throttleDemand = joystick.getRawAxis(4) * -1; // invert because forward is -1 and backward is 1

        slowMode = joystick.getRawButton(1);
    }

    @Override
    public void onEnabledLoop() {
        double modifier = 1;

        if (slowMode) modifier /= 2;

        // set motors
        driveLeft.set(throttleDemand * modifier);
        driveRight.set(throttleDemand * modifier);

        System.out.println("Moving forwards at " +
                Math.round(driveLeft.get() * 100) / 100.0 + " and steering at " +
                Math.round(driveRight.get() * 100) / 100.0 +
                " and slow mode " + slowMode);
    }
}
