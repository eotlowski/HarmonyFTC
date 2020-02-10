package com.harmony.core.robot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Parameters {

    public final double WHEEL_DIAMETER_INCHES;
    public final double MAX_MOTOR_VELOCITY;
    public final double DRIVE_GEAR_REDUCTION;
    public final double WHEEL_DISTANCE_INCHES;
    public final int TICKS_PER_MOTOR_REV;
    public final Robot.ReverseWheel REVERSE_WHEEL;

    public DcMotor.ZeroPowerBehavior zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
    public boolean reverse = false;

    /**
     * @param wheelDiameterInches The diameter of the wheel
     * @param maxMotorVelocity The maximum velocity the motor has
     * @param ticksPerMotorRev How many ticks per motor revolution
     * @param driveGearReduction The drive gear reduction, eg. 2/1. (This is < 1.0 if geared up).
     * @param wheelDistanceInches The distance from the front right wheel to the back right from edge to edge.
     */
    public Parameters(double wheelDiameterInches, double maxMotorVelocity, int ticksPerMotorRev,
                      double driveGearReduction, double wheelDistanceInches, Robot.ReverseWheel reverseWheel) {
        WHEEL_DIAMETER_INCHES = wheelDiameterInches;
        MAX_MOTOR_VELOCITY = maxMotorVelocity;
        DRIVE_GEAR_REDUCTION = driveGearReduction;
        WHEEL_DISTANCE_INCHES = wheelDistanceInches;
        TICKS_PER_MOTOR_REV = ticksPerMotorRev;
        REVERSE_WHEEL = reverseWheel;
    }

    public void setReverse(boolean value) {
        reverse = value;
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        zeroPowerBehavior = behavior;
    }
}
