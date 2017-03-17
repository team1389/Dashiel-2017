package org.usfirst.frc.team1389.autonomous;

import java.io.File;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.CommandScheduler;
import com.team1389.command_framework.CommandUtil;
import com.team1389.trajectory.PathFollowingSystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;

public class AutoDropOffGear extends AutoModeBase {
	CommandScheduler scheduler;
	PathFollowingSystem cont;
	Trajectory traj;
	RobotSoftware robot;

	public AutoDropOffGear(RobotSoftware robot) {
		System.out.println("constructed autonomous");
		this.robot = robot;
		PathFollowingSystem.Constants constants = new PathFollowingSystem.Constants(500, 240, 144, .02, .004, 0, 0.0065, 28);
		cont = new PathFollowingSystem(robot.voltageDrive.getAsTank(),
				robot.rearLeft.getPositionInput().adjustRange(0, 1024, 0, 1).scale(4 * Math.PI),
				robot.rearRight.getPositionInput().adjustRange(0, 1024, 0, 1).scale(4 * Math.PI),
				robot.gyro.getAngleInput().invert(), constants);

	}

	@Override
	protected void routine() throws AutoModeEndedException {
		runCommand(CommandUtil.combineSequential(cont.new PathFollowCommand("paths/path.csv", false, 180)));
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem;
	}

	@Override
	public String getIdentifier() {
		return "curvedGear";
	}

	public static void main(String[] args) {
		// new Waypoint(0, 0, 0), new Waypoint(-115.9375, 72.5, Pathfinder.d2r(300)) the one to rule them all
		// new Waypoint(0, 0, 0), new Waypoint(-136.4375, 277.125, Pathfinder.d2r(331.3)) boiler peg to enemy launchpad
		Pathfinder.writeToCSV(new File("path.csv"),
				
				Pathfinder.generate(
						new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(-115.9375, 72.5, Pathfinder.d2r(300)) },
						new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_HIGH, 1.0 / 20, 144, 240, 500)));
	}
}
