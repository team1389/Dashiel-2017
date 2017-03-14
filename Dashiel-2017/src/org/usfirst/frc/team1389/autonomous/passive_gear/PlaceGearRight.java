package org.usfirst.frc.team1389.autonomous.passive_gear;

import org.omg.Messaging.SyncScopeHelper;
import org.usfirst.frc.team1389.autonomous.AutoConstants;
import org.usfirst.frc.team1389.autonomous.command.RobotCommands;
import org.usfirst.frc.team1389.autonomous.command.RobotCommands.DriveStraight;
import org.usfirst.frc.team1389.autonomous.command.RobotCommands.TurnAngle;
import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.CommandUtil;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class PlaceGearRight extends AutoModeBase {
	private RobotSoftware robot;
	private RobotCommands commands;

	public PlaceGearRight(RobotSoftware robot) {
		this.robot = robot;
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(robot.voltageDrive);
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		System.out.println("driving first");
		runCommand(commands.new DriveStraight(3*12/(4*Math.PI),5));
		System.out.println("turning");
		runCommand(commands.new TurnAngle(45, true));
		runCommand(commands.new DriveStraight(2*12/(4*Math.PI),5));
	}

	@Override
	public String getIdentifier() {
		return "Passive Place Gear Left";
	}
}
