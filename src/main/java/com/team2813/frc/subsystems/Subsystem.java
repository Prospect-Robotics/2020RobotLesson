package com.team2813.frc.subsystems;

/**PID Control Notes
 * Proportional Integral Derivative - PID
 * Accurate Common Way to control mtoors
 */
abstract public class Subsystem {
    // read joysticks
    // robotPeriodic
    abstract public void readPeriodicInputs();

    // output motors
    // output pneumatics
    // autonomousPeriodic teleopPeriodic
    abstract public void writePeriodicOutputs();

    public void simulationPeriodic() {

    }

    public void onDisabled() {

    }

    public void onEnabled() {
    }
}