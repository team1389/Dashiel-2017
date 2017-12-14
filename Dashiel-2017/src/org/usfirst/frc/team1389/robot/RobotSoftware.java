package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.DigitalOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.hardware.value_types.Value;
import com.team1389.system.drive.FourDriveOut;

public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();
	public AngleIn<Position> gyroInput;
	public DigitalOut pistons;
	public FourDriveOut<Percent> voltageDrive;
	public AngleIn<Position> armAngle;
	public AngleIn<Position> armAngleNoOffset;
	public AngleIn<Speed> armVel;
	public RangeIn<Value> gearIntakeCurrent;
	public RangeIn<Value> climberCurrent;
	public RangeIn<Position> flPos, frPos;
	public RangeIn<Value> gearBeamBreak;

	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
		gyroInput = gyro.getAngleInput();
		pistons = flPiston.getDigitalOut().addFollowers(frPiston.getDigitalOut(), rlPiston.getDigitalOut(),
				rrPiston.getDigitalOut(), new DigitalOut(System.out::println));
		voltageDrive = new FourDriveOut<>(frontLeft.getVoltageOutput(), frontRight.getVoltageOutput(),
				rearLeft.getVoltageOutput(), rearRight.getVoltageOutput());
		armAngleNoOffset = armElevator.getAbsoluteIn().mapToAngle(Position.class).invert().scale(12.0 / 28.0);
		armAngle = armAngleNoOffset.copy().offset(-RobotConstants.armOffset);
		armVel = armElevator.getSpeedInput().scale(28 / 12).mapToAngle(Speed.class);
		gearIntakeCurrent = pdp.getCurrentIn(pdp_GEAR_INTAKE_CURRENT);
		flPos = rearLeft.getPositionInput().adjustRange(0, 1024, 0, 1);
		frPos = rearRight.getPositionInput().adjustRange(0, 1024, 0, 1);
	}

}
