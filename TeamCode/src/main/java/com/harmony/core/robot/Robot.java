package com.harmony.core.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Robot {

    protected HardwareMap hardwareMap;
    protected Parameters parameters;

    public Robot(HardwareMap hardwareMap, Parameters parameters) {
        this.hardwareMap = hardwareMap;
        this.parameters = parameters;
    }

    /**
     * Which wheel is reversed. eg. (If the power was set to one which wheel would make the robot
     * move backwards.
     */
    public enum ReverseWheel {
        LEFT, RIGHT
    }

    public abstract void setMode(DcMotor.RunMode mode);
    public abstract boolean isBusy();
    public abstract void stop();



    public Parameters getParameters() {
        return parameters;
    }
}