package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.autonomous.command.RobotCommands;
import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.command_base.Command;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class VoltCenterGear extends AutoModeBase
{
	RobotCommands commands;
	RobotSoftware robot;
	
	public VoltCenterGear(RobotSoftware robot)
	{
		this.robot = robot;
		commands = new RobotCommands(robot);
	}
	
	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {

		return stem;
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		//Command
		Command move = commands.new DriveStraightOpenLoop(0.3, 8);
		Command spin = commands.new TurnAngle(180, true);
		Command armMove1 = commands.new ArmMovement(94);
		Command armMove2 = commands.new ArmMovement(0);
		/*Dance Commands*
		Command armP = commands.new ArmMovement(0);
		Command armP2 = commands.new ArmMovement(30);
		Command armP3 = commands.new ArmMovement(50);
		Command armP4 = commands.new ArmMovement(90);*/
		//Run
		runCommand(armMove1);
		runCommand(move);
		runCommand(armMove2);
		runCommand(spin);
		runCommand(armMove1);
		runCommand(move);
		
		runCommand(armMove2);
		runCommand(spin);
		/*Dance Runs*
		runCommand(armP);
		runCommand(armP2);
		runCommand(armP);
		runCommand(armP3);
		runCommand(armP2);*/
	}

	@Override
	public String getIdentifier() {

		return "OpenLoopCenterGear";
	}
	
	
}