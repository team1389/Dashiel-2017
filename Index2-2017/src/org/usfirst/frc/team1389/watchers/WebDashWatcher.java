package org.usfirst.frc.team1389.watchers;

import org.usfirst.frc.team1389.robot.RobotSoftware;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class WebDashWatcher extends RobotSoftware {
	NetworkTable dashTable;
	/*private static WebDashWatcher instance = new WebDashWatcher();
	public static WebDashWatcher getInstance(){
		instance.init();
		return instance;
	}*/

	private enum WebDashKeys {
		TIME("time_running"), FRONT_LEFT_CURRENT("front-left-current"), BACK_LEFT_CURRENT(
				"back-left-current"), FRONT_RIGHT_CURRENT(
						"front-right-current"), BACK_RIGHT_CURRENT("back-right-current"), GYRO_ANGLE("gyro-position");
		protected final String key;
		private WebDashKeys(String key) {
			this.key = key;
		}
	}

	public void init() {
		dashTable = NetworkTable.getTable("WebDashboard");
	}

	public void update() {
	dashTable.putBoolean(WebDashKeys.TIME.key, timeRunning.get());
	dashTable.putNumber(WebDashKeys.FRONT_LEFT_CURRENT.key, flCurrent.get());
	dashTable.putNumber(WebDashKeys.BACK_LEFT_CURRENT.key, blCurrent.get());
	dashTable.putNumber(WebDashKeys.FRONT_RIGHT_CURRENT.key, frCurrent.get());
	dashTable.putNumber(WebDashKeys.BACK_RIGHT_CURRENT.key, brCurrent.get());
	dashTable.putNumber(WebDashKeys.GYRO_ANGLE.key, gyroInput.get());
	}
}
