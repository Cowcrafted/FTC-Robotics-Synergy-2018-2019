package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


@TeleOp(name="TestingATurn", group="Linear OpMode")
@Disabled
public class TestTele extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    //creates a new object for the hardwareclass
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();
    GyroCalc logic = new GyroCalc();
    Double Derivative = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        //initializes all the hardware in the other class
        uwuBot.initDrive(this);
        functions.initDrive(uwuBot);
        logic.initDrive(uwuBot);
        waitForStart();
        runtime.reset();
        uwuBot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        while (opModeIsActive()) {
            GyroTurn(gamepad1.a ,gamepad1.b ,gamepad1.x,gamepad1.y);
            logic.correction = logic.checkDirection();
            Derivative = Math.abs(2/logic.correction);
            if(Math.abs(logic.correction)>=1.0 && gamepad1.right_stick_x == 0 && funcTrue()) {
                double spinPower = Range.clip((logic.correction / 5) / Derivative, -0.5, 0.50);
                functions.moveRobot(0.0, 0.0, spinPower);
            }
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.update();
            //inputs whether or not a is pressed for turbo mode
            functions.turbo(gamepad1.a);
            //inputs which one is pressed to move the lifitng arm
            //functions.lifting(gamepad1.dpad_up, gamepad1.dpad_down);
            //all movement is inputed to call the robot to move
            functions.moveRobot(gamepad1.left_stick_y, sideMov(gamepad1.left_stick_x), gamepad1.right_stick_x);
            //pure data for the gyro in the robot
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.addData("Target",logic.target);
            telemetry.update();
        }
    }
    public double sideMov(double inputPower){
        if(inputPower != 0){
            gyroCor(logic.angles.firstAngle,gamepad1.left_stick_y,gamepad1.left_stick_x);
        }
        return inputPower;
    }
    public void GyroTurn(boolean up, boolean right, boolean left, boolean down){
        if(down){
            gyroCor(180,0,0);
        }else if(right){
            gyroCor(90,0,0);
        }else if(left){
            gyroCor(-90,0,0);
        }else if(up){
            gyroCor(0,0,0);
        }
    }
    boolean funcTrue(){
        boolean truth = true;
        if(gamepad1.a == true ||gamepad1.b == true ||gamepad1.x == true|| gamepad1.y == true){
            truth = false;
        }
        return truth;
    }
    public void gyroCor(double target, double forw, double side){
        logic.target = target;
        logic.correction = logic.checkDirection();
        Derivative = Math.abs(2/logic.correction);
        if(Math.abs(logic.correction)>=1.0){
            double spinPower = Range.clip((logic.correction/5)/Derivative, -0.5,0.50);
            functions.moveRobot(forw,side, spinPower);
        }
    }
}