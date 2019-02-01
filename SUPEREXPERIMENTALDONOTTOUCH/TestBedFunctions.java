package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class TestBedFunctions {

    double MAX_POWER = 0.5;
    double redPower;
    double bluePower;
    double greenPower;
    double yellowPower;
    float rotate_angle = 0;
    double reset_angle = 0;

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

    public void lifting(boolean up, float down){
        if(up && !myRobot.Touch.isPressed()){
            myRobot.Lift.setPower(0.7);
        }
        else if(down != 0) {
            myRobot.Lift.setPower(-0.7);
        }else{
            myRobot.Lift.setPower(0.0);
        }
    }
    public void wenching(boolean up, float down){
        if(up){
            myRobot.Pull.setPower(0.9);
        }
        else if(down != 0){
            myRobot.Pull.setPower(-0.9);
        }
        else{
            myRobot.Pull.setPower(0.0);
        }
    }
    public void resetAngle(boolean a){
        if(a){
            reset_angle = getHeading() + reset_angle;
        }
    }
    public double getHeading(){
        Orientation angles = myRobot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = angles.firstAngle;
        if(heading < -180) {
            heading = heading + 360;
        }
        else if(heading > 180){
            heading = heading - 360;
        }
        heading = heading - reset_angle;
        return heading;
    }
    public void moveComplex(double rightx, double lefty, double leftx,boolean y, boolean dright, boolean dleft, boolean dup, boolean ddwn){
            double Protate = rightx/4;
            double stick_x = leftx * Math.sqrt(Math.pow(1-Math.abs(Protate), 2)/2); //Accounts for Protate when limiting magnitude to be less than 1
            double stick_y = lefty * Math.sqrt(Math.pow(1-Math.abs(Protate), 2)/2);
            double theta = 0;
            double Px = 0;
            double Py = 0;
            double gyroAngle = getHeading() * Math.PI / 180; //Converts gyroAngle into radians
            if (gyroAngle <= 0) {
                gyroAngle = gyroAngle + (Math.PI / 2);
            } else if (0 < gyroAngle && gyroAngle < Math.PI / 2) {
                gyroAngle = gyroAngle + (Math.PI / 2);
            } else if (Math.PI / 2 <= gyroAngle) {
                gyroAngle = gyroAngle - (3 * Math.PI / 2);
            }
            gyroAngle = -1 * gyroAngle;
            if(y){ //Disables gyro, sets to -Math.PI/2 so front is defined correctly.
                gyroAngle = -Math.PI/2;
            }
            //Linear directions in case you want to do straight lines.
            if(dright){
                stick_x = 0.5;
            }
            else if(dleft){
                stick_x = -0.5;
            }
            if(dup){
                stick_y = -0.5;
            }
            else if(ddwn){
                stick_y = 0.5;
            }
            //MOVEMENT
            theta = Math.atan2(stick_y, stick_x) - gyroAngle - (Math.PI / 2);
            Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
            Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

            myRobot.leftRed.setPower(Py - Protate);
            myRobot.rightGreen.setPower(Px - Protate);
            myRobot.leftBlue.setPower(Py + Protate);
            myRobot.rightYellow.setPower(Px + Protate);
    }
}
