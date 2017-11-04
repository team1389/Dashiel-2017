package org.usfirst.frc.team1389.robot.controls;

import com.team1389.hardware.inputs.controllers.LogitechExtreme3D;
import com.team1389.hardware.inputs.controllers.XBoxController;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.PercentIn;

/**
 * A basic framework for the robot controls. Like the RobotHardware, one
 * instance of the ControlBoard object is created upon startup, then other
 * methods request the singleton ControlBoard instance.
 * 
 * @author amind
 * @see ControlMap
 */
public class ControlBoard extends ControlMap
{
	private static ControlBoard mInstance = new ControlBoard();

	public static ControlBoard getInstance()
	{
		return mInstance;
	}

	private ControlBoard()
	{
	}

	private final LogitechExtreme3D driveController = new LogitechExtreme3D(DRIVE_CONTROLLER);
	private final XBoxController manipController = new XBoxController(MANIP_CONTROLLER);

	public PercentIn driveYAxis()
	{
		return driveController.yAxis().applyDeadband(.075).invert();
	}

	public DigitalIn armManualTrigger()
	{
		return manipController.startButton().latched();
	}

	public DigitalIn prepareArmBtn()
	{
		return manipController.bButton().latched();
	}

	public PercentIn driveXAxis()
	{
		return driveController.xAxis().applyDeadband(.075);
	}

	public PercentIn driveYaw()
	{
		return driveController.yaw().applyDeadband(.075);
	}

	public PercentIn driveTrim()
	{
		return driveController.throttle();
	}

	public DigitalIn driveModeBtn()
	{
		return driveController.thumbButton().latched();
	}

	public DigitalIn driveModifierBtn()
	{
		return driveController.trigger();
	}

	public DigitalIn intakeGearBtn()
	{
		return manipController.aButton().latched();
	}

	public DigitalIn stowArmBtn()
	{
		return manipController.yButton().latched();
	}

	public DigitalIn placeGearBtn()
	{
		return manipController.xButton().latched();
	}

	public PercentIn climberThrottle()
	{
		return manipController.leftTrigger();
	}

	public PercentIn armAngleAxis()
	{
		return manipController.leftStick.yAxis();
	}

	public DigitalIn ballIntakeBtn()
	{
		return manipController.rightBumper();
	}

}
