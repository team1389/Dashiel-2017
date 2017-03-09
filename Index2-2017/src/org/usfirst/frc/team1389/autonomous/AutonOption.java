package org.usfirst.frc.team1389.autonomous;
public enum AutonOption {
    CROSS_BASELINE("Cross Baseline"), 
    PLACE_GEAR_LEFT_PASSIVE("Place Gear Left Passive"),//
    PLACE_GEAR("place"); //

    public final String name;

    AutonOption(String name) {
        this.name = name;
    }
}
