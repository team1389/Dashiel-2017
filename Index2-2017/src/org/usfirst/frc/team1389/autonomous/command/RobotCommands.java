package org.usfirst.frc.team1389.autonomous.command;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.command.DriveStraightCommand;
import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.value_types.Percent;

public class RobotCommands {
	RobotSoftware robot;

	public RobotCommands(RobotSoftware software) {
		this.robot = software;
	}

	public class DriveStraight extends DriveStraightCommand {
		public DriveStraight(double distance) {
			this(distance, 5);
		}

		public DriveStraight(double distance, double speed) {
			super(new PIDConstants(5, .1, 0), robot.voltageDrive.getAsTank(), robot.flPos, robot.frPos,
					robot.gyroInput, distance, 5, 5, speed, 0);
		}
	}

	public class TurnAngle extends TurnAngleCommand<Percent> {

		public TurnAngle(double angle, boolean absolute) {
			super(angle, absolute, 2, robot.gyro.getAngleInput(),
					TurnAngleCommand.createTurnController(robot.voltageDrive.getAsTank()),
					new PIDConstants(0.05, 0, 0.0));
		}

	}
}
