package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TestBedVThree", group="Linear OpMode")
//@Disabled
public class TestBedVThree extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    //creates a new object for the hardwareclass
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();


    @Override
    public void runOpMode() {

        //initializes all the hardware in the other class
        uwuBot.initDrive(this);
        functions.initDrive(uwuBot);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //inputs whether or not a is pressed for turbo mode
            functions.turbo(gamepad1.a);
            //inputs which one is pressed to move the lifitng arm
            functions.lifting(gamepad1.dpad_up, gamepad1.dpad_down);
            //inputs which one is pressed to move the pulley
            functions.pulling(gamepad1.left_bumper, gamepad1.right_bumper);
            //inputs how fast the arm is moving with trigger control
            functions.flexing(gamepad1.left_trigger, -gamepad1.right_trigger);
            //inputs which direction to move the servo
            functions.mining(gamepad1.dpad_left, gamepad1.dpad_right);

            //all movement is inputed to call the robot to move
            functions.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        }
    }
}