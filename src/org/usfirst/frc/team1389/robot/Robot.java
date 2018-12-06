
package org.usfirst.frc.team1389.robot;

import org.usfirst.frc.team1389.operation.TeleopMain;
import org.usfirst.frc.team1389.watchers.DashboardInput;
import org.usfirst.frc.team1389.watchers.DebugDash;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeExecuter;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {
	RobotSoftware robot;
	TeleopMain teleOperator;
	AutoModeExecuter autoModeExecuter;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		CameraServer.getInstance().addAxisCamera("10.13.89.86");
		DashboardInput.getInstance().init();
		robot = RobotSoftware.getInstance();
		teleOperator = new TeleopMain(robot);
		autoModeExecuter = new AutoModeExecuter();
		DebugDash.getInstance().watch(robot.rearLeft.getPositionInput().getWatchable("Left encoder"),
				robot.rearRight.getPositionInput().getWatchable("Right encoder"),
				robot.gyro.getAngleInput().getWatchable("gyro"));
		System.out.println("STARTING GYRO CALIBRATION");
		robot.gyro.calibrate();
		System.out.println("CALIBRATION FINISHED");
	}

	@Override
	public void autonomousInit() {
		autoModeExecuter.stop();
		AutoModeBase selectedAutonMode = DashboardInput.getInstance().getSelectedAutonMode();
		autoModeExecuter.setAutoMode(selectedAutonMode);
		DebugDash.getInstance().watch(selectedAutonMode);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void teleopInit() {
		DebugDash.getInstance().outputToDashboard();
		autoModeExecuter.stop();
		teleOperator.init();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		teleOperator.periodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testInit() {
	}
}
