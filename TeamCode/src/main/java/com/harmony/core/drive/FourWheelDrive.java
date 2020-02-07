package com.harmony.core.drive;

import com.harmony.core.robot.FourWheelBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class FourWheelDrive extends Drive {

    private static final double MIN_SPEED = 0.2;

    private DcMotorEx fl;
    private DcMotorEx fr;
    private DcMotorEx bl;
    private DcMotorEx br;

    public FourWheelDrive(FourWheelBot robot, LinearOpMode opMode) {
        super(robot, opMode);

        fl = robot.fl;
        fr = robot.fr;
        bl = robot.bl;
        br = robot.br;
    }

    private double scalePower(double value, double max) {
        if (max == 0) {
            return 0;
        }
        return value / max;
    }

    @Override
    public void drive(double v, double h, double r) {
        // Add Vectors
        double frontLeft = v - h + r;
        double frontRight = v + h - r;
        double backRight = v - h - r;
        double backLeft = v + h + r;

        double max = Math.max(
                Math.abs(backLeft),
                Math.max(
                        Math.abs(backRight),
                        Math.max(
                                Math.abs(frontLeft), Math.abs(frontRight)
                        )
                )
        );

        // Only need to scale power if max is greater than one.
        if (max > 1) {
            frontLeft = scalePower(frontLeft, max);
            frontRight = scalePower(frontRight, max);
            backLeft = scalePower(backLeft, max);
            backRight = scalePower(backRight, max);
        }

        fl.setPower(frontLeft);
        fr.setPower(frontRight);
        bl.setPower(backLeft);
        br.setPower(backRight);
    }

    public void StandardDrive(Gamepad gamepad) {
        drive(-gamepad.left_stick_y, 0, gamepad.right_stick_x);
    }

    public void TankDrive(Gamepad gamepad) {
        double leftPower = Range.clip(-gamepad.left_stick_y, -1.0, 1.0);
        double rightPower = Range.clip(-gamepad.right_stick_y, -1.0, 1.0);

        fl.setVelocity(MAX_VELOCITY * leftPower);
        bl.setVelocity(MAX_VELOCITY * leftPower);
        fr.setVelocity(MAX_VELOCITY * rightPower);
        br.setVelocity(MAX_VELOCITY * rightPower);
    }


    /**
     *  Requires an FTCGamePad for better control options
     * @param driverGamepad requires an FTCGamPad for better control
     */
    public void MechDrive(org.firstinspires.ftc.teamcode.common.FtcGamePad driverGamepad){
        double h, v, r;

        h = Math.pow(-driverGamepad.getLeftStickX(), 3) + Math.pow(driverGamepad.getLeftTrigger(), 3)
                - Math.pow(driverGamepad.getRightTrigger(), 3);
        v = Math.pow(-driverGamepad.getLeftStickY(), 3);
        r = Math.pow(driverGamepad.getRightStickX(), 3);

        if(Math.abs(h) < MIN_SPEED) {
            h = 0;
        }
        if(Math.abs(v) < MIN_SPEED) {
            v = 0;
        }
        if(Math.abs(r) < MIN_SPEED){
            r = 0;
        }

        // add vectors
        double frontLeft =  v-h+r;
        double frontRight = v+h-r;
        double backRight =  v-h-r;
        double backLeft =   v+h+r;

        // since adding vectors can go over 1, figure out max to scale other wheels
        double max = Math.max(
                Math.abs(backLeft),
                Math.max(
                        Math.abs(backRight),
                        Math.max(
                                Math.abs(frontLeft), Math.abs(frontRight)
                        )
                )
        );
        // only need to scale power if max > 1
        if(max > 1){
            frontLeft = scalePower(frontLeft, max);
            frontRight = scalePower(frontRight, max);
            backLeft = scalePower(backLeft, max);
            backRight = scalePower(backRight, max);
        }

        fl.setVelocity(frontLeft * MAX_VELOCITY);
        fr.setVelocity(frontRight * MAX_VELOCITY);
        bl.setVelocity(backLeft * MAX_VELOCITY);
        br.setVelocity(backRight * MAX_VELOCITY);
    }

    @Override
    public void moveByInches(double power, double leftInches, double rightInches, double timeoutSeconds) {
        encoderDrive(power, COUNTS_PER_INCH * leftInches, COUNTS_PER_INCH * rightInches, timeoutSeconds);
    }

    @Override
    public void moveByInches(double power, double inches, double timeoutSeconds) {
        encoderDrive(power, COUNTS_PER_INCH * inches, COUNTS_PER_INCH * inches, timeoutSeconds);
    }

    private void setPower(double power) {
        fl.setVelocity(MAX_VELOCITY * power);
        fr.setVelocity(MAX_VELOCITY * power);
        bl.setVelocity(MAX_VELOCITY * power);
        br.setVelocity(MAX_VELOCITY * power);
    }

    @Override
    public void encoderDrive(double power, double leftTicks, double rightTicks, double timeoutSeconds) {
        fl.setTargetPosition(fl.getCurrentPosition() + (int) leftTicks);
        bl.setTargetPosition(bl.getCurrentPosition() + (int) leftTicks);
        fr.setTargetPosition(fr.getCurrentPosition() + (int) rightTicks);
        br.setTargetPosition(br.getCurrentPosition() + (int) rightTicks);

        robot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(opMode.opModeIsActive() && robot.isBusy() && timer.seconds() < timeoutSeconds) {}
        stop();
    }

}