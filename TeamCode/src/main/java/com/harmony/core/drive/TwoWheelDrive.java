package com.harmony.core.drive;

import com.harmony.core.robot.TwoWheelBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class TwoWheelDrive extends Drive {

    private DcMotorEx left;
    private DcMotorEx right;

    public TwoWheelDrive(TwoWheelBot robot, LinearOpMode opMode) {
        super(robot, opMode);

        left = robot.left;
        right = robot.right;
    }

    @Override
    public void drive(double v, double r) {
        double leftPower    = Range.clip(v + r, -1.0, 1.0) ;
        double rightPower   = Range.clip(v - r, -1.0, 1.0) ;

        left.setVelocity(MAX_VELOCITY * leftPower);
        right.setVelocity(MAX_VELOCITY * rightPower);
    }

    @Override
    public void drive(double v, double h, double r) {
        drive(v, r);
    }

    public void GTADrive(Gamepad gamepad) {
        drive(-gamepad.left_stick_y, gamepad.right_stick_x);
    }

    public void TankDrive(Gamepad gamepad) {
        double leftPower = Range.clip(-gamepad.left_stick_y, -1.0, 1.0);
        double rightPower = Range.clip(-gamepad.right_stick_y, -1.0, 1.0);

        left.setVelocity(MAX_VELOCITY * leftPower);
        right.setVelocity(MAX_VELOCITY * rightPower);
    }

    private void setPower(double power) {
        right.setVelocity(MAX_VELOCITY * power);
        left.setVelocity(MAX_VELOCITY * power);
    }

    @Override
    public void moveByInches(double power, double leftInches, double rightInches, double timeoutSeconds) {
        encoderDrive(power, COUNTS_PER_INCH * leftInches, COUNTS_PER_INCH * rightInches, timeoutSeconds);
    }

    @Override
    public void moveByInches(double power, double inches, double timeoutSeconds) {
        encoderDrive(power, COUNTS_PER_INCH * inches, COUNTS_PER_INCH * inches, timeoutSeconds);
    }

    @Override
    public void encoderDrive(double power, double leftTicks, double rightTicks, double timeoutSeconds) {
        left.setTargetPosition(left.getCurrentPosition() + (int) leftTicks);
        right.setTargetPosition(right.getCurrentPosition() + (int) rightTicks);

        robot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(opMode.opModeIsActive() && robot.isBusy() && timer.seconds() < timeoutSeconds) {}
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        stop();
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
