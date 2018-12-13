
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TestTeleOp", group="Linear Opmode")
public class TestTeleOp extends LinearOpMode {


    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor liftDrive = null;

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
        liftDrive = hardwareMap.get(DcMotor.class,"lift_drive");

        relicServo = hardwareMap.get(Servo.class,"relicServo");
        Plow1 = hardwareMap.get(Servo.class, "plow1");
        Plow2 = hardwareMap.get(Servo.class, "plow2");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        liftDrive.setDirection(DcMotor.Direction.FORWARD);

        Plow1.setDirection(Servo.Direction.REVERSE);
        Plow2.setDirection(Servo.Direction.FORWARD);

        Plow1.setPosition(0.45);
        Plow2.setPosition(0.45);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double leftPower;
            double rightPower;
            double liftPower;

            //Initializes all the variables for the plow
            double plowPos;
            double plowCnt;
            double prevPos;

            //retrieves all the hardware that's mapped to the controller
            double drive = gamepad1.left_stick_y;
            double turn  = -gamepad1.right_stick_x;
            //button presses for the plow arms
            boolean Plowup = gamepad1.dpad_up;
            boolean Plowdown = gamepad1.dpad_down;
            //button presses for turbo and relic Servo
            boolean turbo = gamepad1.a;
            boolean relUp = gamepad1.x;
            boolean relDwn = gamepad1.y;
            //button presses for lift
            boolean lftup = gamepad1.left_bumper;
            boolean lftdwn = gamepad1.right_bumper;
            //gets and sets the plow position
            prevPos = Plow1.getPosition();
            plowPos = prevPos;

            //turbo and movements. Tests to see if the turbo button is pressed and sets ranges
            if(turbo) {
                leftPower = Range.clip(drive + turn, -1.0, 1.0);
                rightPower = Range.clip(drive - turn, -1.0, 1.0);
            }
            else{
                leftPower = Range.clip(drive + turn, -.50, .50);
                rightPower = Range.clip(drive - turn, -.50, .50);
            }

            //the power for the lift drive
            liftPower = 0.;
            //starts the counter for the plow
            plowCnt = plowPos;

            //sets the power that we found from the controller
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            //The Plow arms are counted and controlled
            //sets the plow arms to the same position if out of line
            if(prevPos!= (Plow2.getPosition())){
                Plow2.setPosition(prevPos);
            }

            //tests for button presses plow up and down
            //increments the position by 0.005 out of 0 to 1 range
            if(Plowup){
                plowCnt += 0.005;
                plowPos = plowCnt;
            }
            else if(Plowdown){
                plowCnt -= 0.005;
                plowPos = plowCnt;
            }

            //sets the position of the servos repeatedly to plow pos
            Plow1.setPosition(plowPos);
            Plow2.setPosition(plowPos);

            //Relic Controls: tests for button press and sets that servo to a set position
            if(relUp){
                relicServo.setPosition(0.3);
            }
            if(relDwn){
                relicServo.setPosition(0.7);
            }
            else

            //moves the rack and pinon system up and down
            if (lftup){
                liftDrive.setPower(liftPower);
            }
            else if(lftdwn){
                liftDrive.setPower(-liftPower);
            }
            else{
                liftDrive.setPower(0);
            }
            //sets the telemetry data that appears on the bottom of the drive phone.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
