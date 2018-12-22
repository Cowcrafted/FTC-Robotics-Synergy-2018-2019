package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TESTBED", group="Linear OpMode")
//@Disabled
public class TESTBEDVTWO extends LinearOpMode {

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
            double redPower = 0;
            double bluePower = 0;
            double greenPower = 0;
            double yellowPower = 0;
            double forw= gamepad1.left_stick_y;
            double side = gamepad1.left_stick_x;
            double spin = gamepad1.right_stick_x;

            redPower = forw + side + spin;
            bluePower = forw + side + spin;
            greenPower= forw + side + spin;
            yellowPower = forw + side + spin;

            redPower = Range.clip(redPower, -1.0,1.0);
            bluePower = Range.clip(bluePower, -1.0,1.0);
            greenPower = Range.clip(greenPower, -1.0,1.0);
            yellowPower = Range.clip(yellowPower, -1.0,1.0);

            leftRed.setPower(redPower);
            leftBlue.setPower(bluePower);
            rightGreen.setPower(greenPower);
            rightYellow.setPower(yellowPower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}