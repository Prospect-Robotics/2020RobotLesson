package com.team2813.frc2020.subsystems;

import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;
import com.team2813.lib.drive.CurvatureDrive;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.wpilibj.PWMTalonFX;

public class Drive extends Subsystem {
    private static final Axis CURVATURE_STEER = SubsystemControlsConfig.getDriveSteer();
    private static final Axis CURVATURE_FORWARD = SubsystemControlsConfig.getDriveForward();
    private static final Axis CURVATURE_REVERSE = SubsystemControlsConfig.getDriveReverse();
    private static final Button CURVATURE_PIVOT = SubsystemControlsConfig.getPivotButton();

    public CurvatureDrive curvatureDrive = new CurvatureDrive(0);

    private PWMTalonFX driveLeft = new PWMTalonFX(0);
    private PWMTalonFX driveRight = new PWMTalonFX(1);

    public DriveDemand demand = new DriveDemand(0, 0);

    @Override
    public void outputTelemetry() {
    }

    @Override
    public void teleopControls() {
        double steer = CURVATURE_STEER.get(); // -1 to 1
        double forward = CURVATURE_FORWARD.get();
        double reverse = CURVATURE_REVERSE.get();
        boolean pivot = CURVATURE_PIVOT.get();

        demand = curvatureDrive.getDemand(forward, reverse, steer, pivot);
    }

    @Override
    protected void readPeriodicInputs() {
        driveLeft.set(demand.getLeft());
        driveRight.set(demand.getRight());
    }

    @Override
    protected void writePeriodicOutputs() {
        super.writePeriodicOutputs();
    }

    @Override
    public void onEnabledStart(double timestamp) {

    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {

    }
}
