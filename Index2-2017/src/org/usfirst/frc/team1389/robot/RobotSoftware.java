package org.usfirst.frc.team1389.robot;

import java.util.function.Function;

import com.team1389.concurrent.OhmThreadService;
import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
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
	public AngleIn<Speed> armVel;
	public RangeIn<Value> gearIntakeCurrent;
	public RangeIn<Value> climberCurrent;
	public RangeIn<Position> flPos, frPos;
	public OhmThreadService threadManager;
	public RangeIn<Speed> rightSpeed;
	public RangeIn<Speed> leftSpeed;
	public RangeIn<Position> lPosInches;
	public RangeIn<Position> rPosInches;

	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
		 gyroInput = gyro.getAngleInput();
		 pistons = flPiston.getDigitalOut().addFollowers(frPiston.getDigitalOut(),
					rlPiston.getDigitalOut(), rrPiston.getDigitalOut(), new DigitalOut(System.out::println));
		 voltageDrive = new FourDriveOut<>(frontLeft.getVoltageOutput(),
					frontRight.getVoltageOutput(), rearLeft.getVoltageOutput(), rearRight.getVoltageOutput());
		 armAngle = armElevator
				.getAbsoluteIn()
						.adjustRange(RobotConstants.armAbsoluteMin, RobotConstants.armAbsoluteMax, 0, 90)
						.setRange(0, 360)
						.mapToAngle(Position.class);
		 armVel = armElevator.getSpeedInput().scale(28 / 12).mapToAngle(Speed.class);
		 gearIntakeCurrent = pdp.getCurrentIn(pdp_GEAR_INTAKE_CURRENT);
		 Function<PositionEncoderIn,RangeIn<Position>> posFunc = e -> e.<PositionEncoderIn>setTicksPerRotation(1024)
					.mapToRange(0, 1);
		 flPos = posFunc.apply(rearLeft.getPositionInput());
		 frPos = posFunc.apply(rearRight.getPositionInput());
		 lPosInches = flPos.copy().scale(RobotConstants.WheelDiameter * Math.PI);
		 rPosInches = frPos.copy().scale(RobotConstants.WheelDiameter * Math.PI);
		 leftSpeed = frontLeft.getSpeedInput().mapToRange(0, 1).scale(RobotConstants.WheelDiameter * Math.PI);
		 rightSpeed = frontRight.getSpeedInput().mapToRange(0, 1).scale(RobotConstants.WheelDiameter * Math.PI);
		 threadManager = new OhmThreadService(20);
	}

}