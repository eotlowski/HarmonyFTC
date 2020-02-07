package com.harmony.core.drive;

import com.harmony.core.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public abstract class Drive {

    protected final Robot robot;
    protected final LinearOpMode opMode;

    public final double WHEEL_DISTANCE_INCHES;
    public final double MAX_VELOCITY;

    public final double TICKS_PER_MOTOR_REV;
    public final double            DRIVE_GEAR_REDUCTION ;
    public final double            WHEEL_DIAMETER_INCHES;
    public final double     COUNTS_PER_INCH;

    public Drive(Robot robot, LinearOpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;

        WHEEL_DISTANCE_INCHES = robot.getParameters().WHEEL_DISTANCE_INCHES;
        MAX_VELOCITY = robot.getParameters().MAX_MOTOR_VELOCITY;
        TICKS_PER_MOTOR_REV = robot.getParameters().TICKS_PER_MOTOR_REV;
        DRIVE_GEAR_REDUCTION = robot.getParameters().DRIVE_GEAR_REDUCTION;
        WHEEL_DIAMETER_INCHES = robot.getParameters().WHEEL_DIAMETER_INCHES;
        COUNTS_PER_INCH = (TICKS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * Math.PI);
    }

    public abstract void drive(double v, double h, double r);

    public abstract void moveByInches(double power, double leftInches, double rightInches, double timeoutSeconds);
    public abstract void moveByInches(double power, double inches, double timeoutSeconds);

    public abstract void encoderDrive(double power, double leftTicks, double rightTicks, double timeoutSeconds);

    public void stop() {robot.stop();}

}
