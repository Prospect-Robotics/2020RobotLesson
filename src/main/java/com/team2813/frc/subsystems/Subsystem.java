package com.team2813.frc.subsystems;

abstract public class Subsystem {
    // read joysticks
    // robotPeriodic
    abstract public void readPeriodicInputs();

    // output motors
    // output pneumatics
    // autonomousPeriodic teleopPeriodic
    abstract public void writePeriodicOutputs();

    public void onDisabled() {

    }

    public void onEnabled() {
    }
}