package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TESTBEDTWO", group="Linear OpMode")
//@Disabled
public class TESTBEDVTWO extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRed = null;
    private DcMotor leftBlue = null;
    private DcMotor rightGreen = null;
    private DcMotor rightYellow = null;

    private DcMotor liftDrive = null;
    private DcMotor armDrive = null;
    private DcMotor pulleyDrive = null;

    private Servo ScpDrive = null;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftRed = hardwareMap.get(DcMotor.class, "LeftR");
        leftBlue = hardwareMap.get(DcMotor.class, "LeftB");
        rightGreen = hardwareMap.get(DcMotor.class, "RightG");
        rightYellow = hardwareMap.get(DcMotor.class, "RightY");

        leftRed.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBlue.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightGreen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightYellow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        liftDrive = hardwareMap.get(DcMotor.class, "LiftD");

        armDrive = hardwareMap.get(DcMotor.class,"ArmD");
        pulleyDrive = hardwareMap.get(DcMotor.class,"PullD");

        ScpDrive = hardwareMap.get(Servo.class,"ScoopD");

        armDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            boolean lftUp = gamepad1.dpad_up;
            boolean lftDwn = gamepad1.dpad_down;

            boolean scpUp = gamepad1.dpad_left;
            boolean scpDwn = gamepad1.dpad_right;

            double prevPos = 0.5;
            double currPos = 0.5;
            double plowCnt = 0;

            plowCnt = currPos;

            prevPos = ScpDrive.getPosition();
            currPos = prevPos;


            boolean pullUp = gamepad1.left_bumper;
            boolean pullDwn = gamepad1.right_bumper;

            float armUp = gamepad1.left_trigger;
            float armDwn = gamepad1.right_trigger;

            double redPower = 0;
            double bluePower = 0;
            double greenPower = 0;
            double yellowPower = 0;

            double forw = gamepad1.left_stick_y;
            double side = gamepad1.left_stick_x;
            double spin = gamepad1.right_stick_x;

            redPower = forw - side + spin;
            bluePower = forw + side + spin;
            greenPower= -forw - side + spin;
            yellowPower = -forw + side + spin;

            redPower = Range.clip(redPower, -0.5,0.5);
            bluePower = Range.clip(bluePower, -0.5,0.5);
            greenPower = Range.clip(greenPower, -0.5,0.5);
            yellowPower = Range.clip(yellowPower, -0.5,0.5);

            leftRed.setPower(redPower);
            leftBlue.setPower(bluePower);
            rightGreen.setPower(greenPower);
            rightYellow.setPower(yellowPower);



            if(lftUp){
                liftDrive.setPower(0.2);
            }
            else if(lftDwn){
                liftDrive.setPower(-0.2);
            }
            else{
                liftDrive.setPower(0);
            }
            //pulley working
            if(pullUp){
                pulleyDrive.setPower(0.2);
            }
            else if(pullDwn){
                pulleyDrive.setPower(-0.2);
            }
            else{
                pulleyDrive.setPower(0);
            }



            //Coded for the arm
            armDrive.setPower(armUp);
            armDrive.setPower(-armDwn);

            if(armDwn == 0 && armUp == 0){
                armDrive.setPower(0);
            }

            //the bucket

            //tests for button presses plow up and down
            //increments the position by 0.005 out of 0 to 1 range
            if(scpUp){
                plowCnt += 0.005;
                currPos = plowCnt;
            }
            else if(scpDwn){
                plowCnt -= 0.005;
                currPos = plowCnt;
            }

            ScpDrive.setPosition(currPos);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}