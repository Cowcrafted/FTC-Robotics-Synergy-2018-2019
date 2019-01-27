package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.lang.annotation.Target;

@TeleOp(name="TestingATurn", group="Linear OpMode")
@Disabled
public class TestTele extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    //creates a new object for the hardwareclass
    TestBedHardWare uwuBot = new TestBedHardWare();
    TestBedFunctions functions = new TestBedFunctions();
    GyroCalc logic = new GyroCalc();
    Double Derivative = 0.0;
    Double TARGETHERE = 0.0;
    Acceleration gravity;

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
            angleTurn(gamepad1.dpad_up, gamepad1.dpad_left, gamepad1.dpad_down, gamepad1.dpad_left);
            gyroCor(TARGETHERE,gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            //pure data for the gyro in the robot
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.addData("Target",logic.target);
            telemetry.update();
        }
    }
    public void gyroCor(double target, double forw, double side, double spin){
        double spinPower = 0.0;
        if(forw != 0 || side != 0 && spin == 0){
            target = logic.angles.firstAngle;
            if(forw != 0 && side == 0 && spin == 0){
                if(gravity.xAccel != 0){
                    side = -gravity.xAccel/3;
                }
            }
            if(forw == 0 && side != 0 && spin == 0){
                if(gravity.yAccel != 0){
                    side = -gravity.yAccel/3;
                }
            }
            logic.target = target;
            logic.correction = logic.checkDirection();
            Derivative = Math.abs(5/logic.correction);
            if(Math.abs(logic.correction)>=1.0 && spin == 0){
                spinPower = Range.clip((logic.correction/5)/Derivative, -0.5,0.50);
            }
        }
        functions.moveRobot(forw,side, spinPower + spin);
    }
    public void angleTurn(boolean forw,boolean right, boolean down, boolean left){
        if(forw){
            TARGETHERE = 0.0;
        }else if(right){
            TARGETHERE = -90.0;
        }else if(down){
            TARGETHERE = 179.0;
        }else if(left){
            TARGETHERE = 90.0;
        }
    }
}