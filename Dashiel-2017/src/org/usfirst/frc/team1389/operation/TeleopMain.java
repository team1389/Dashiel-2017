package org.usfirst.frc.team1389.operation;

import java.util.function.Supplier;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.systems.FancyLightSystem;
import org.usfirst.frc.team1389.systems.GearIntakeSystem;
import org.usfirst.frc.team1389.systems.TeleopGearIntakeSystem;
import org.usfirst.frc.team1389.watchers.DebugDash;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.OctoMecanumSystem;
import com.team1389.system.drive.OctoMecanumSystem.DriveMode;

/**
 * system manager
 * 
 * @author Quunii
 *
 */
public class TeleopMain
{
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	DigitalIn timeRunning;

	/**
	 * 
	 * @param robot
	 *            container of all ohm streams
	 */
	public TeleopMain(RobotSoftware robot)
	{
		this.robot = robot;
	}

	/**
	 * initializes systems, and adds them to a list of systems to update
	 */
	public void init()
	{
		controls = ControlBoard.getInstance();
		OctoMecanumSystem drive = setupDrive();
		GearIntakeSystem gearIntake = setupGearIntake(drive.getDriveModeTracker());

		DebugDash.getInstance().watch( gearIntake);
		manager = new SystemManager(drive,  gearIntake);
		manager.init();
	}

	/**
	 * 
	 * @return a new OctoMecanumSystem
	 */
	private OctoMecanumSystem setupDrive()
	{
		return new OctoMecanumSystem(robot.voltageDrive, robot.pistons, robot.gyroInput, controls.driveXAxis(),
				controls.driveYAxis(), controls.driveYaw(), controls.driveTrim(), controls.driveModeBtn(),
				controls.driveModifierBtn());
	}

	/**
	 * 
	 * @return a new GearIntakeSystem
	 */
	private GearIntakeSystem setupGearIntake(Supplier<DriveMode> driveMode)
	{
		TeleopGearIntakeSystem Supplier = new TeleopGearIntakeSystem(robot.armAngle, robot.armVel,
				robot.armElevator.getVoltageOutput(), robot.gearIntake.getVoltageOutput(), robot.gearBeamBreak,
				robot.gearIntakeCurrent, driveMode, controls.aButton(), controls.bButton(), controls.xButton(),
				controls.yButton(), controls.leftStickYAxis(), controls.rightTrigger(), controls.setRumble(),
				controls.startButton());
		return Supplier;
	}

	/**
	 * 
	 * @return a new ClimberSystem
	 */


	/**
	 * periodically calls update method in all subsystems
	 */
	public void periodic()
	{
		manager.update();
	
	}

}
