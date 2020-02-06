package com.harmony.core.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TwoWheelBot extends Robot {

    public final DcMotorEx left;
    public final DcMotorEx right;

    public TwoWheelBot(HardwareMap hardwareMap, Parameters parameters) {
        super(hardwareMap, parameters);

        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");

        switch (parameters.REVERSE_WHEEL) {
            case LEFT:
                left.setDirection(DcMotorSimple.Direction.REVERSE);
                right.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case RIGHT:
                left.setDirection(DcMotorSimple.Direction.FORWARD);
                right.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
        }

        if(parameters.reverse) {
            if(left.getDirection() == DcMotorSimple.Direction.FORWARD) {
                left.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                left.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            if(right.getDirection() == DcMotorSimple.Direction.FORWARD) {
                right.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                right.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }

        left.setZeroPowerBehavior(parameters.zeroPowerBehavior);
        right.setZeroPowerBehavior(parameters.zeroPowerBehavior);
    }

    @Override
    public void setMode(DcMotor.RunMode runMode) {
        left.setMode(runMode);
        right.setMode(runMode);
    }

    @Override
    public boolean isBusy() {
        return left.isBusy() || right.isBusy();
    }

    @Override
    public void stop() {
        left.setVelocity(0);
        right.setVelocity(0);
    }
}
