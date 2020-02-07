package com.harmony.core.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FourWheelBot extends Robot {

    public final DcMotorEx fl;
    public final DcMotorEx fr;
    public final DcMotorEx bl;
    public final DcMotorEx br;

    public FourWheelBot(HardwareMap hardwareMap, Parameters parameters) {
        super(hardwareMap, parameters);

        fl = hardwareMap.get(DcMotorEx.class, "fl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");

        switch (parameters.REVERSE_WHEEL) {
            case LEFT:
                fl.setDirection(DcMotorSimple.Direction.REVERSE);
                bl.setDirection(DcMotorSimple.Direction.REVERSE);
                fr.setDirection(DcMotorSimple.Direction.FORWARD);
                br.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case RIGHT:
                fl.setDirection(DcMotorSimple.Direction.FORWARD);
                bl.setDirection(DcMotorSimple.Direction.FORWARD);
                fr.setDirection(DcMotorSimple.Direction.REVERSE);
                br.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
        }

        if(parameters.reverse) {
            if (fl.getDirection() == DcMotorSimple.Direction.FORWARD) {
                fl.setDirection(DcMotorSimple.Direction.REVERSE);
                bl.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                fl.setDirection(DcMotorSimple.Direction.FORWARD);
                bl.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            if (fr.getDirection() == DcMotorSimple.Direction.FORWARD) {
                fr.setDirection(DcMotorSimple.Direction.REVERSE);
                br.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                fr.setDirection(DcMotorSimple.Direction.FORWARD);
                br.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }

        fl.setZeroPowerBehavior(parameters.zeroPowerBehavior);
        bl.setZeroPowerBehavior(parameters.zeroPowerBehavior);
        fr.setZeroPowerBehavior(parameters.zeroPowerBehavior);
        br.setZeroPowerBehavior(parameters.zeroPowerBehavior);
    }

    @Override
    public boolean isBusy() {
        return fl.isBusy() || fr.isBusy() || bl.isBusy() || br.isBusy();
    }

    @Override
    public void setMode(DcMotor.RunMode mode) {
        fl.setMode(mode);
        bl.setMode(mode);
        fr.setMode(mode);
        br.setMode(mode);
    }

    @Override
    public void stop() {
        fl.setVelocity(0);
        fr.setVelocity(0);
        bl.setVelocity(0);
        br.setVelocity(0);
    }

    @Override
    public Parameters getParameters() {
        return super.getParameters();
    }
}
