package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TestBedFunctions {

    double MAX_POWER = 0.5;
    double redPower;
    double bluePower;
    double greenPower;
    double yellowPower;

    private TestBedHardWare     myRobot;        // Access to the Robot hardware
    //a constructor for the class don't mess with it
    public TestBedFunctions(){ }
    //uses the class member to make an object
    public void initDrive(TestBedHardWare robot) {
        myRobot = robot;
    }

    //sends all power to move the robot
    public void moveRobot(double forw, double side, double spin) {
        //the math to drive the holonomic drive
        redPower = -forw - side + spin;
        bluePower = forw - side + spin;
        greenPower= forw + side + spin;
        yellowPower = -forw + side + spin;
        // normalize all motor speeds so no values exceeds 100%.
        redPower = Range.clip(redPower, -MAX_POWER, MAX_POWER);
        bluePower = Range.clip(bluePower, -MAX_POWER, MAX_POWER);
        greenPower = Range.clip(greenPower, -MAX_POWER, MAX_POWER);
        yellowPower = Range.clip(yellowPower, -MAX_POWER, MAX_POWER);
        // Set drive motor power levels.
        myRobot.leftRed.setPower(redPower);
        myRobot.leftBlue.setPower(bluePower);
        myRobot.rightGreen.setPower(greenPower);
        myRobot.rightYellow.setPower(yellowPower);
    }
}
