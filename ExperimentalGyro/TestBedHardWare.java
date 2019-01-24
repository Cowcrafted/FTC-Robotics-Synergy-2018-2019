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
    //creates a new object to use the zeroing move function
    TestBedFuctions functions = new TestBedFuctions();
    BNO055IMU imu;
    //driving motors
    public DcMotor leftRed = null;
    public DcMotor leftBlue = null;
    public DcMotor rightGreen = null;
    public DcMotor rightYellow = null;
    //exterior motors not for driving
    public DcMotor liftDrive = null;
    public DcMotor armDrive = null;
    public DcMotor pulleyDrive = null;
    //the servo for thescoop
    public Servo ScpDrive = null;
    //this value for how fast the robot will go
    public double MAX_POWER = 0.5;
    //values to make a stepper for the servo to work
    double prevPos = 0.0;
    double currPos = 0.5;
    double plowCnt = 0;

    double redPower;
    double bluePower;
    double greenPower;
    double yellowPower;

    public TestBedHardWare(){

    }

    public void initDrive(LinearOpMode opMode) {
        myOpMode = opMode;
        //stuff for the gyro
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

        liftDrive = myOpMode.hardwareMap.get(DcMotor.class, "LiftD");

        armDrive = myOpMode.hardwareMap.get(DcMotor.class,"ArmD");
        pulleyDrive = myOpMode.hardwareMap.get(DcMotor.class,"PullD");

        ScpDrive = myOpMode.hardwareMap.get(Servo.class,"ScoopD");

        //Brakes the Motors
        leftRed.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBlue.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightGreen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightYellow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Brakes the Arm
        armDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //sets encoder mode for the arm
        //armDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //armDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Stop all robot motion by setting each axis value to zero
        functions.moveRobot(0,0,0) ;
    }
}
