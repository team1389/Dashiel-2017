package org.usfirst.frc.team1389.systems;

import java.util.function.Supplier;

import org.usfirst.frc.team1389.systems.GearIntakeSystem.State;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.system.Subsystem;
import com.team1389.trajectory.Kinematics;
import com.team1389.trajectory.RigidTransform2d;
import com.team1389.trajectory.RobotState;
import com.team1389.trajectory.RobotStateEstimator;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class PositionEstimatorSystem extends Subsystem{

	RobotStateEstimator estimator;
	NetworkPosition network;
	private Supplier<State> gearState;
	public PositionEstimatorSystem(Supplier<GearIntakeSystem.State> gearState, RangeIn<Position> leftIn, RangeIn<Position> rightIn, RangeIn<Speed> leftVel, RangeIn<Speed> rightVel, RobotState state, AngleIn<Position> degrees, Kinematics kinematics){
		estimator = new RobotStateEstimator(state, leftIn , rightIn, leftVel, rightVel, degrees, kinematics);
		network = new NetworkPosition(estimator);
		this.gearState = gearState;
	}
	
	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) { 
		return estimator.getSubWatchables(stem);
	}

	@Override
	public String getName() {
		return new String("Estimator");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		network.updateNetwork(gearState.get().ordinal());
	}
	
	private enum EstimatorTableKeys {
		X_POSITION("x"), Y_POSITION("y"), ANGLE_DEGREES("angle"), GEAR("gear"), GEARPLACED("placed");;
		protected final String key;
		private EstimatorTableKeys(String key) {
			this.key = key;
		}
	}
	
	private class NetworkPosition {

		RobotStateEstimator position;
		NetworkTable table;
		public NetworkPosition(RobotStateEstimator position){
			this.position = position;
			table = NetworkTable.getTable("estimator");
			table.putBoolean("placed", false);
		}
		
		public void updateNetwork(int gearState){
			RigidTransform2d transform = position.get();
			table.putNumber(EstimatorTableKeys.X_POSITION.key, transform.getTranslation().getX());
			table.putNumber(EstimatorTableKeys.Y_POSITION.key, transform.getTranslation().getY());
			table.putNumber(EstimatorTableKeys.ANGLE_DEGREES.key, transform.getRotation().getDegrees());
			table.putNumber(EstimatorTableKeys.GEAR.key, gearState);
			if(gearState == 3){
				table.putBoolean("placed", true);
			}
		}
		
		
		

	}

}
