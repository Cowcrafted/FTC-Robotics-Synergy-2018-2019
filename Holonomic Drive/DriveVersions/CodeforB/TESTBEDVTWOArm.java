package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TESTBEDTWOArm", group="Linear OpMode")
//@Disabled
public class TESTBEDVTWOArm extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    //private DcMotor leftRed = null;
    //private DcMotor leftBlue = null;
    //private DcMotor rightGreen = null;
    //private DcMotor rightYellow = null;
    private DcMotor liftDrive = null;
    private DcMotor armDrive = null;
    private DcMotor pulley = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //leftRed = hardwareMap.get(DcMotor.class, "LeftR");
        //leftBlue = hardwareMap.get(DcMotor.class, "LeftB");
        //rightGreen = hardwareMap.get(DcMotor.class, "RightG");
        //rightYellow = hardwareMap.get(DcMotor.class, "RightY");

        liftDrive = hardwareMap.get(DcMotor.class, "LiftD");
        armDrive = hardwareMap.get(DcMotor.class,"ArmD");
        pulley = hardwareMap.get(DcMotor.class,"PulleyD");


        pulley.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            boolean lftUp = gamepad1.dpad_up;
            boolean lftDwn = gamepad1.dpad_down;

            boolean armUp = gamepad1.left_bumper;
            boolean armDwn = gamepad1.right_bumper;

            boolean pulleyUp = (gamepad1.left_trigger != 0);
            boolean pulleyDown = (gamepad1.right_trigger != 0);

            double redPower = 0;
            double bluePower = 0;
            double greenPower = 0;
            double yellowPower = 0;

            double forw= gamepad1.left_stick_y;
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

            //leftRed.setPower(redPower);
            //leftBlue.setPower(bluePower);
            //rightGreen.setPower(greenPower);
            //rightYellow.setPower(yellowPower);



            if(lftUp){
                liftDrive.setPower(0.5);
            }
            else if(lftDwn){
                liftDrive.setPower(-0.5);
            }
            else{
                liftDrive.setPower(0);
            }
            //Coded for the arm
            if(armUp){
                armDrive.setPower(0.5);
            }
            else if(armDwn){
                armDrive.setPower(-0.5);
            }
            else{
                armDrive.setPower(0);
            }
            //Code for the arm pulley system with left trigger (UP)
            if(pulleyUp){
                pulley.setPower(0.3);
            }
            else if(pulleyDown){
                pulley.setPower(-0.3);
            }
            else{
                pulley.setPower(0);
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}