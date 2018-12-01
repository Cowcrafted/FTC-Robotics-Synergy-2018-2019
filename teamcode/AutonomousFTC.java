package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * FTC 2018-2019 code for autonomous controlling (synergy)
 */
@Autonomous(name="Autonomous Synergy")
@Disabled
public class AutonomousFTC extends LinearOpMode
{
    //  Declare motors
    DcMotor motorLeft = null;
    DcMotor motorRight = null;
    DcMotor motorArm = null;

    // Declare arm servo
    Servo relicServo = null;

    public double DRIVE_POWER = 0.5;
    public void main() throws InterruptedException{}

    public void runOpMode()throws InterruptedException {
        //Initialize the motors
        motorLeft = hardwareMap.get(DcMotor.class,"motorLeft");
        motorRight = hardwareMap.get(DcMotor.class,"motorRight");
        motorArm = hardwareMap.get(DcMotor.class,"motorArm");


        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        //Initialize the servos
        relicServo = hardwareMap.servo.get("armServo");

        relicServo.setPosition(1);

        // Wait for the game to start
        waitForStart();

        runOpMode();

        // Starts the auto robot code
        // Decend the robot (raise the arm a bit (it goes a little further up))
       /// motorArm.setPower(DRIVE_POWER);
       /// Thread.sleep(4500);
        TurnLeft(DRIVE_POWER);
        Thread.sleep(500);
        DriveBackwards(DRIVE_POWER);
        Thread.sleep(3000);
        TurnLeft(DRIVE_POWER);
        Thread.sleep(500);
        DriveForward(DRIVE_POWER);
        Thread.sleep(5000);
        //Drop relic into the site
        relicServo.setPosition(0.4);
    }

    public void DriveForward(double power)
    {
        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    public void DriveBackwards(double power)
    {
        motorLeft.setPower(-power);
        motorRight.setPower(-power);
    }

    public void TurnLeft(double power)
    {
        motorLeft.setPower(-power);
        motorRight.setPower(power);
    }

    public void TurnRight(double power)
    {
        motorLeft.setPower(power);
        motorRight.setPower(-power);
    }

    public void RaiseArm(double power)
    {

        motorArm.setPower(power);
    }

    public void LowerArm(double power)
    {

        motorArm.setPower(-power);
    }
}