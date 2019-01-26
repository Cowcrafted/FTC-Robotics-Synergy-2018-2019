package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class TestBedHardWare {
    //imports the class member
    private LinearOpMode myOpMode;
    BNO055IMU imu;
    //driving motors
    public DcMotor leftRed = null;
    public DcMotor leftBlue = null;
    public DcMotor rightGreen = null;
    public DcMotor rightYellow = null;
    //exterior motors not for driving
    public DcMotor liftDrive = null;
    public DcMotor reachDrive = null;

    public double bluePower;
    public double redPower;
    public double greenPower;
    public double yellowPower;

    public TestBedHardWare(){

    }

    public void initDrive(LinearOpMode opMode) {
        myOpMode = opMode;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = myOpMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        // Define and Initialize Motors
        leftRed = myOpMode.hardwareMap.get(DcMotor.class, "LeftR");
        leftBlue = myOpMode.hardwareMap.get(DcMotor.class, "LeftB");
        rightGreen = myOpMode.hardwareMap.get(DcMotor.class, "RightG");
        rightYellow = myOpMode.hardwareMap.get(DcMotor.class, "RightY");
        //
        liftDrive = myOpMode.hardwareMap.get(DcMotor.class, "LiftD");
        reachDrive = myOpMode.hardwareMap.get(DcMotor.class, "ReachD");
        //Brakes the Motors
        leftRed.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBlue.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightGreen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightYellow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //input for the reac
    }
}
