
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TestTeleOp", group="Linear Opmode")
public class TestTeleOp extends LinearOpMode {


    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    //Servos Yall
    private Servo relicServo = null;
    private Servo Plow1 = null;
    private Servo Plow2 = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        Plow1 = hardwareMap.get(Servo.class, "plow1");
        Plow2 = hardwareMap.get(Servo.class, "plow2");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        Plow1.setDirection(Servo.Direction.REVERSE);
        Plow2.setDirection(Servo.Direction.FORWARD);
        Plow1.setPosition(0.45);
        Plow2.setPosition(0.45);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double leftPower;
            double rightPower;
            double plowPos;
            double plowCnt;
            double prevPos;

            double drive = gamepad1.left_stick_y;
            double turn  = -gamepad1.right_stick_x;
            boolean Plowup = gamepad1.dpad_up;
            boolean Plowdown = gamepad1.dpad_down;
            boolean turbo = gamepad1.a;
            boolean relUp = gamepad1.x;
            boolean relDwn = gamepad1.y;

            prevPos = Plow1.getPosition();
            plowPos = prevPos;

            if(turbo) {
                leftPower = Range.clip(drive + turn, -1.0, 1.0);
                rightPower = Range.clip(drive - turn, -1.0, 1.0);
            }
            else{
                leftPower = Range.clip(drive + turn, -.50, .50);
                rightPower = Range.clip(drive - turn, -.50, .50);
            }

            plowCnt = plowPos;

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            if(prevPos!= (Plow2.getPosition())){
                Plow2.setPosition(prevPos);
            }

            if(Plowup){
                plowCnt += 0.005;
                plowPos = plowCnt;
            }
            else if(Plowdown){
                plowCnt -= 0.005;
                plowPos = plowCnt;
            }

            Plow1.setPosition(plowPos);
            Plow2.setPosition(plowPos);

            if(relUp){
                relicServo.setPosition(0.3);
            }
            if(relDwn){
                relicServo.setPosition(0.7);
            }


            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
