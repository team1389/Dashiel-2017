package org.usfirst.frc.team1389.autonomous;

import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.systems.GearIntakeSystem;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.command_base.Command;
import com.team1389.hardware.inputs.software.PositionEncoderIn;
import com.team1389.system.SystemManager;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

/**
 * This auto just triggers hoppers
 * @author raffi
 *
 */
public class AutonTriggerHoppers extends AutoModeBase {
	RobotSoftware robot;
	GearIntakeSystem gearSystem;
	SystemManager manager;
	
	public AutonTriggerHoppers(RobotSoftware robot) {
		PositionEncoderIn.setGlobalWheelDiameter(4);
		this.robot = robot;
		
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return null;
	}

	@Override
	public String getName() {
		return "Trigger Hoppers";
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		Command driving;
	}
}
