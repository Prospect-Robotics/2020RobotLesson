package com.team2813.frc.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonFX;

public class Drive extends Subsystem {
    PWMTalonFX leftMotorMaster = new PWMTalonFX(0);
//    PWMTalonFX leftMotorSlave1 = new PWMTalonFX(1);
//    PWMTalonFX leftMotorSlave2 = new PWMTalonFX(2);

    PWMTalonFX rightMotorMaster = new PWMTalonFX(3);
//    PWMTalonFX rightMotorSlave1 = new PWMTalonFX(4);
//    PWMTalonFX rightMotorSlave2 = new PWMTalonFX(5);

    Joystick joystick = new Joystick(0);

    private double steerDemand; // -1 to 1
    private double throttleDemand; // -1 to 1
    private boolean slowMode; // true or false

    public Drive() {
        // leftMotorSlave1.follow(leftMotorMaster);
        // leftMotorSlave2.follow(leftMotorMaster);

        // rightMotorSlave1.follow(rightMotorMaster);
        // rightMotorSlave2.follow(rightMotorMaster);
    }

    @Override
    public void readPeriodicInputs() {
        steerDemand = joystick.getRawAxis(0); // -1 to 1
        throttleDemand = joystick.getRawAxis(4) * -1; // invert because forward is -1 and backward is 1

        slowMode = joystick.getRawButton(1);
    }

    @Override
    public void writePeriodicOutputs() {
        double modifier = 1; // 0 to 1

        if (slowMode) modifier /= 2;

        // set motors
        leftMotorMaster.set((throttleDemand + steerDemand) * modifier);
        rightMotorMaster.set((throttleDemand - steerDemand) * modifier);
    }
}
