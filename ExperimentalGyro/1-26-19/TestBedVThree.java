package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


@TeleOp(name="TestBedVThree", group="Linear OpMode")
//@Disabled
public class TestBedVThree extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    //creates a new object for the hardwareclass
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();
    GyroCalc logic = new GyroCalc();
    @Override
    public void runOpMode() throws InterruptedException {
        //initializes all the hardware in the other class
        uwuBot.initDrive(this);
        functions.initDrive(uwuBot);
        logic.initDrive(uwuBot);
        waitForStart();
        runtime.reset();
        uwuBot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        while (opModeIsActive()) {
            logic.correction = logic.checkDirection();
            //inputs whether or not a is pressed for turbo mode
            functions.turbo(gamepad1.a);
            //inputs which one is pressed to move the lifitng arm
            functions.lifting(gamepad1.dpad_up, gamepad1.dpad_down);
            //all movement is inputed to call the robot to move
            functions.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
            //pure data for the gyro in the robot
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.update();
        }
    }
}