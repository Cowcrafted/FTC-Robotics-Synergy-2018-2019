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
@TeleOp(name="TestBedVFIVE?", group="Linear OpMode")
//@Disabled
public class TestBedVFOUR extends LinearOpMode {
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
            functions.moveComplex(gamepad1.right_stick_x,gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.y,gamepad1.dpad_right,gamepad1.dpad_left,gamepad1.dpad_up,gamepad1.dpad_down);
            functions.resetAngle(gamepad1.a);
            //all movement is inputed to call the robot to move
            //functions.moveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
            //left side of the controller for the lift
            functions.lifting(gamepad1.left_bumper, gamepad1.left_trigger);
            //right side of the controller for the pulley
            functions.wenching(gamepad1.left_bumper, gamepad1.left_trigger);
            //toggles the intake
            if(gamepad1.x && uwuBot.off){
                uwuBot.Intake.setPower(0.3);
                uwuBot.off = false;
            }else if(gamepad1.x && !uwuBot.off){
                uwuBot.Intake.setPower(0.0);
                uwuBot.off = true;
            }
            //all data to be reported
            if (uwuBot.Touch.isPressed()) {
                telemetry.addData("Digital Touch", "Is Not Pressed");
                collide = true;
            } else {
                telemetry.addData("Digital Touch", "Is Pressed");
                collide = false;
            }
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.update();
        }
    }
}