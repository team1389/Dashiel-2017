package org.usfirst.frc.team1389.operation;

import java.util.function.Supplier;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.systems.GearIntakeSystem;
import org.usfirst.frc.team1389.systems.TeleopGearIntakeSystem;
import org.usfirst.frc.team1389.watchers.DebugDash;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.OctocanumSystem;
import com.team1389.system.drive.OctocanumSystem.DriveMode;

/**
 * system manager
 * 
 * @author Quunii
 *
 */
public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	DigitalIn timeRunning;

	/**
	 * 
	 * @param robot container of all ohm streams
	 */
	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
	}

	/**
	 * initializes systems, and adds them to a list of systems to update
	 */
	public void init() {
		controls = ControlBoard.getInstance();
		OctocanumSystem drive = setupDrive();
		GearIntakeSystem gearIntake = setupGearIntake(drive.getDriveModeTracker());

		DebugDash.getInstance().watch(gearIntake);
		manager = new SystemManager(drive, gearIntake);
		manager.init();
	}

	/**
	 * 
	 * @return a new OctocanumSystem
	 */
	private OctocanumSystem setupDrive() {
		return new OctocanumSystem(robot.voltageDrive, robot.pistons, robot.gyroInput, controls.driveXAxis(),
				controls.driveYAxis(), controls.driveYaw(), controls.driveTrim(), controls.driveModeBtn(),
				controls.driveModifierBtn());
	}

	/**
	 * 
	 * @return a new GearIntakeSystem
	 */
	private GearIntakeSystem setupGearIntake(Supplier<DriveMode> driveMode) {

		// making assumption that there is no beambreak, setting up so it always thinks
		// the sensor is not triggered
		return new TeleopGearIntakeSystem(robot.armAngle, robot.armVel, robot.armVolt, robot.intakeVolt, () -> false,
				robot.gearIntakeCurrent, driveMode, controls.aButton(), controls.bButton(), controls.xButton(),
				controls.yButton(), controls.leftStickYAxis(), controls.rightTrigger(), controls.setRumble(),
				controls.startButton());
	}

	/**
	 * 
	 * @return a new ClimberSystem
	 */

	/**
	 * periodically calls update method in all subsystems
	 */
	public void periodic() {
		manager.update();

	}

}
