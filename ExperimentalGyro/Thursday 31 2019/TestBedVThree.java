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
@TeleOp(name="TestBedVFOUR", group="Linear OpMode")
//@Disabled
public class TestBedVThree extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    //creates a new object for the hardwareclass
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();
    GyroCalc logic = new GyroCalc();

    private boolean collide;
    @Override
    public void runOpMode() throws InterruptedException {
        //initializes all the hardware in the other class
        uwuBot.initDrive(this);
        functions.initDrive(uwuBot);
        logic.initDrive(uwuBot);
        waitForStart();

        uwuBot.leftRed.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        uwuBot.leftBlue.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        uwuBot.rightGreen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        uwuBot.rightYellow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        runtime.reset();
        uwuBot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        while (opModeIsActive()) {
            logic.correction = logic.checkDirection();
            //inputs which one is pressed to move the lifitng arm
            //functions.lifting(gamepad1.dpad_up, gamepad1.dpad_down);
            //all movement is inputed to call the robot to move
            functions.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
            //pure data for the gyro in the robot
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.update();

            if (uwuBot.Touch.isPressed()) {
                telemetry.addData("Digital Touch", "Is Not Pressed");
                collide = true;
            } else {
                telemetry.addData("Digital Touch", "Is Pressed");
                collide = false;
            }
            //left side of the controller for the lift
            if(gamepad1.left_bumper && !collide){
                uwuBot.Lift.setPower(0.7);
            }
            else if(gamepad1.left_trigger != 0) {
                uwuBot.Lift.setPower(-0.7);
            }
            else{
                uwuBot.Lift.setPower(0.0);
            }
            //right side of the controller for the pulley
            if(gamepad1.right_bumper){
                uwuBot.Pull.setPower(0.9);
            }
            else if(gamepad1.right_trigger != 0){
                uwuBot.Pull.setPower(-0.9);
            }
            else{
                uwuBot.Pull.setPower(0.0);
            }
            //toggles
            if(gamepad1.b && uwuBot.off){
                uwuBot.Intake.setPower(0.3);
                uwuBot.off = false;
            }else if(gamepad1.b && !uwuBot.off){
                uwuBot.Intake.setPower(0.0);
                uwuBot.off = true;
            }
        }
    }
}