package com.team2813.frc.subsystems;

public abstract class Subsystem extends edu.wpi.first.wpilibj.command.Subsystem {
    public abstract void readPeriodicInputs();

    public abstract void onEnabledLoop();
}
