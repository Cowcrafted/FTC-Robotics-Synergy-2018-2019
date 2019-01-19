package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "DontUse", group = "Linear OpMode")
//@Disabled
public class DontUse extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();

    //Servos
    //private Servo fServo = null;

    //some powers
    double redPower;
    double bluePower;
    double greenPower;
    double yellowPower;

    @Override
    public void runOpMode() {
        //initializes all the hardward for the robot
        uwuBot.initDrive(this);
        functions.initDrive(uwuBot);
        //uwuBot.relicDump.setPosition(0.3);
        //initializes all the vuforia tracking systems
        //fServo = hardwareMap.get(Servo.class,"flickServo");


        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        telemetry.addData("Time:", runtime.seconds());

        functions.moveRobot(0.0,0.0,1);

        while (opModeIsActive() && (runtime.seconds() < 20)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}