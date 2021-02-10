package com.team2813.frc.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpiutil.math.VecBuilder;

public class Lifter extends Subsystem {
    Joystick joystick = new Joystick(0);
    private final PWMVictorSPX lifterMotor = new PWMVictorSPX(1);
    private static final int kEncoderAChannel = 0;
    private static final int kEncoderBChannel = 1;
    private final Encoder lifterEncoder = new Encoder(kEncoderAChannel, kEncoderBChannel);

    public double demand = 0; // 0 to 1.27 meter

    // Simulation Constants
    private final DCMotor kLifterGearbox = DCMotor.getVex775Pro(1);
    private static final double kLifterGearing = 12;
    private static final double kLifterDrumRadius = Units.inchesToMeters(2.0);
    private static final double kCarriageMass = 4.0;
    private static final double kLifterEncoderDistPerPulse = 2.0 * Math.PI * kLifterDrumRadius / 4096;

    private static final double kMinLifterHeight = 0;
    private static final double kMaxLifterHeight = Units.inchesToMeters(50); // this variable is in meters

    private final ElevatorSim lifterSim = new ElevatorSim(
            kLifterGearbox,
            kLifterGearing,
            kCarriageMass,
            kLifterDrumRadius,
            kMinLifterHeight,
            kMaxLifterHeight,
            VecBuilder.fill(0.01));
    private final EncoderSim encoderSim = new EncoderSim(lifterEncoder);

    PIDController controller = new PIDController(3.5, 1, 0.5);

    private ShuffleboardTab tab = Shuffleboard.getTab("Data");
    private final NetworkTableEntry lifterPositionEntry = tab.add("Lifter Position", 0).getEntry();
    private final NetworkTableEntry lifterDemandEntry = tab.add("Lifter Demand", 0).getEntry();

    public Lifter() {
        controller.setIntegratorRange(-2, 2);
        lifterEncoder.setDistancePerPulse(kLifterEncoderDistPerPulse);
    }

    @Override
    public void readPeriodicInputs() {
        double raw = Math.abs(joystick.getRawAxis(1)); // 0 to 1

        demand = raw * kMaxLifterHeight; // convert raw to desired position in meters.

        lifterPositionEntry.setDouble(lifterEncoder.getDistance());
        lifterDemandEntry.setDouble(demand);
    }

    @Override
    public void writePeriodicOutputs() {
        double pidOutput = controller.calculate(lifterEncoder.getDistance(), demand);
        pidOutput = MathUtil.clamp(pidOutput, -1, 1);
        if (demand > 0.01) {
            lifterMotor.set(pidOutput);
        } else {
            lifterMotor.set(0);
        }
    }

    @Override
    public void onDisabled() {
        lifterMotor.set(0);
    }

    @Override
    public void simulationPeriodic() {
        lifterSim.setInput(lifterMotor.get() * RobotController.getBatteryVoltage());

        lifterSim.update(0.020);

        encoderSim.setDistance(lifterSim.getPositionMeters());
        RoboRioSim.setVInVoltage(
                BatterySim.calculateDefaultBatteryLoadedVoltage(lifterSim.getCurrentDrawAmps()));
    }
}