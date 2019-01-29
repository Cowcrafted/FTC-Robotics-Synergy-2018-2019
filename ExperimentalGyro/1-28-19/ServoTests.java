package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Demonstrates empty OpMode
 */
@TeleOp(name = "TestingTheArm", group = "Concept")
//@Disabled
public class ServoTests extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    public DcMotor Lift;
    public DcMotor Pull;

    @Override
    public void runOpMode(){
    Lift = hardwareMap.dcMotor.get("LiftD");
    Pull = hardwareMap.dcMotor.get("PullD");

    Lift.setPower(0);
    Pull.setPower(0);

    waitForStart();
    while(opModeIsActive()){
        //left side of the controller for the lift
        if(gamepad1.left_bumper){
            Lift.setPower(0.7);
        }
        else if(gamepad1.left_trigger != 0) {
            Lift.setPower(-0.7);
        }
        else{
            Lift.setPower(0.0);
        }
        //right side of the controller for the pulley
        if(gamepad1.right_bumper){
            Pull.setPower(0.9);
        }
        else if(gamepad1.right_trigger != 0){
            Pull.setPower(-0.9);
        }
        else{
            Pull.setPower(0.0);
        }
        //xyb for forw, stop and back respectively
    }

    }
}
