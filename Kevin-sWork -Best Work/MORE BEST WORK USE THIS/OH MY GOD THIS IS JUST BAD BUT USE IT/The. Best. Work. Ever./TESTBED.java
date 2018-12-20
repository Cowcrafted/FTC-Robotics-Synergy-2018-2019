package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TESTBED", group="Linear OpMode")
//@Disabled
public class TESTBED extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRed = null;
    private DcMotor leftBlue = null;
    private DcMotor rightGreen = null;
    private DcMotor rightYellow = null;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftRed = hardwareMap.get(DcMotor.class, "LeftR");
        leftBlue = hardwareMap.get(DcMotor.class, "LeftB");
        rightGreen = hardwareMap.get(DcMotor.class, "RightG");
        rightYellow = hardwareMap.get(DcMotor.class, "RightY");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double redPower = 0:
            double bluePower = 0;
            double greenPower = 0;
            double yellowPower = 0;
            double forw= gamepad1.left_stick_y;
            double side = gamepad1.left_stick_x;
            double spin = gamepad1.right_stick_x;

            if(forw != 0 && Math.abs(forw) > Math.abs(side)){
                redPower = Range.clip(forw, -.50, .50);
                bluePower = -Range.clip(forw, -.50, .50);
                greenPower = -Range.clip(forw, -.50, .50);
                yellowPower = Range.clip(forw, -.50, .50);
            }
            if (side != 0 && Math.abs(side) > Math.abs(forw)){
                redPower = -Range.clip(side, -.50, .50);
                bluePower = -Range.clip(side, -.50, .50);
                greenPower = Range.clip(side, -.50, .50);
                yellowPower = Range.clip(side, -.50, .50);
            }

            if (spin != 0){
                redPower = Range.clip(spin, -.50, .50);
                bluePower = Range.clip(spin, -.50, .50);
                greenPower = Range.clip(spin, -.50, .50);
                yellowPower = Range.clip(spin, -.50, .50);
            }



            leftRed.setPower(redPower);
            leftBlue.setPower(bluePower);
            rightGreen.setPower(greenPower);
            rightYellow.setPower(yellowPower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}