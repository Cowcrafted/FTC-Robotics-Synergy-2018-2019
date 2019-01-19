package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "SamplingAuto-V3", group = "Linear OpMode")
//@Disabled
public class MineralDetectionV3 extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private ElapsedTime runtime = new ElapsedTime();

    TestBedHardWare uwuBot = new TestBedHardWare();

    private static final String VUFORIA_KEY = "AZjoEnH/////AAABmXdFD2Xsrk4krInf+EdRY0NaRrdzvbptLaUoVN2kuF2/FnuWVscRF9ozak4bIpJCr1SLehfzrXHS+H3Z7XMNIgxwg6lttQ4zp7ODEDt1XQ/DLQcjpmYXruF4eBBRsIBey35Ue6g4E51WOebmNW/aDFDhz3zON+NNYbyk/4XOszsw7CwHpcNLBXqT0prM/NYwkCaJFocA8cpWcViM0Mka8kEV+T1X1ZtRnPwMxtQrxO19ksdbRv0bjPmco0iiOAvRwMcyVxg250tckD64iSWJkIhlqakYMLA1r00YPtUY4VSfShG0pWTDn/RF9/TqhM8qICp9ZPCz5QlPn8qt4cfiofTjzE41R+VvjKnIGK1B9g5o";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    //some global variables used by functions
    int goldPos = 0;
    public int spinDirect = 0;

    //Servos
    //private Servo fServo = null;

    //some powers
    double redPower;
    double bluePower;
    double greenPower;
    double yellowPower;

    @Override
    public void runOpMode() {
        //initializes all the hardward for the robot
        uwuBot.initDrive(this);
        //uwuBot.relicDump.setPosition(0.3);
        //initializes all the vuforia tracking systems
        //fServo = hardwareMap.get(Servo.class,"flickServo");
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        telemetry.addData("Time:",runtime.seconds());
        drop();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
            while (goldPos == 0) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = 4;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                                telemetry.addData("Gold X Position", goldMineralX);
                                telemetry.addData("Silver X Position", silverMineral1X);
                                telemetry.addData("Silver X Position", silverMineral2X);
                            }
                            if (goldMineralX != 4){
                                if (goldMineralX < silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    goldPos = 1;
                                }else if(silverMineral1X < goldMineralX){
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    goldPos = 2;
                                }
                            }
                            else{
                                telemetry.addData("Gold Mineral Position", "Right");
                                goldPos = 3;
                            }
                            telemetry.addData("goldPos", goldPos);
                        }
                        telemetry.update();
                    }
                }
            }
            //function that moves to knock off gold and calls a face to wall
            determineRoute(goldPos);
            //function that moves to the wall to align and travels to depot
            //approachWall();
            //approachCrater();
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }
    //compares the position of the golds
    public void determineRoute(int goldPos){
        //change this spintime to change how much extra spin the second path may need
        double spinTime = 1.0;
        if(goldPos == 1){
            moveAny(-0.6,0.05,-0.01,0.35);
            owoLeft();
            spinTime = 1.0;
            spinDirect = -1;
        } else if(goldPos == 3){
            moveAny(-0.6,0.05,-0.01,0.35);
            owoRight();
            spinTime = 1.0;
            spinDirect = 1;
        } else{
            owoCen();
            spinTime = 1.0;
            spinDirect = 1;
        }
        //backs up the robot and spins to face a wall
        //spintoWall(spinTime, spinDirect);
    }
    //the respective functions for each possibility of the gold
    public void owoLeft(){
        moveAny(0.0,-0.33,0.0,0.7);
        moveAny(-0.3,0.0,0.0,1.7);
        moveAny(0.0,0,0.0,0.5);
    }
    public void owoCen(){
        moveAny(0,0.3,0,0.5);
        moveAny(-0.4,0.11,-0.04,1.9);
        moveAny(0.0,0,0.0,0.2);
    }
    public void owoRight(){
        moveAny(-0.3,0.0,0.0,0.5);
        moveAny(0.0,0.4,0.0,1.2);
        moveAny(-0.4,0.2,-0.1,1.7);
        moveAny(0.0,0,0.0,0.5);
    }
    //this backs up the robot and faces the closer wall
    public void spintoWall(double spinTime,int spinDirect){
        moveAny(0,0,0,2);
        moveAny(0.0,0.0,0.0,0.5);
        moveAny(0.3,0.0,0.0,0.5);
        moveAny(0.0,0.0,spinDirect*0.2,spinTime);
        moveAny(0.0,0,0.0,0.5);
    }
    //this drives the robot forward and uses the wall to align itself
    public void approachWall(){
        moveAny(0,0,0,4);
        moveAny(-0.3,0.0,0,.50);
        moveAny(0.0,0.0,spinDirect*-0.2,2.0);
        moveAny(-0.2,0.0,0.0,3.5);
        moveAny(0.0,0,-spinDirect*-0.2,0.5);
        moveAny(-0.2,0.0,0.0,3.5);
        moveAny(0.0,0.0,0.0,0.2);
    }
    public void approachCrater(){
        moveAny(0,0,0,4);
        moveAny(-0.1,spinDirect*0.3,0.0,7.0);
        moveAny(0.0,0,0.0,0.5);
        uwuBot.reachDrive.setPower(0.3);
        moveAny(0,0,0,1.3);
        uwuBot.reachDrive.setPower(0);

    }
    //function that drops the arm to get off the lander.
    public void drop(){
        telemetry.addData("Time:",runtime.seconds());

        uwuBot.liftDrive.setPower(0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5.6)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        telemetry.update();
        uwuBot.liftDrive.setPower(0);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        moveAny(-0.2,0.0,0.0,0.2);
        moveAny(0.0,0.0,-0.4,0.2);
        moveAny(0.0,0.0,0.0,0.2);
        uwuBot.liftDrive.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5.39)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        uwuBot.liftDrive.setPower(0.0);

        moveAny(0.0,0.0,0.4,0.176);
        moveAny(0.0,0.0,0.0,0.2);
        moveAny(0.0,-0.4,0.0,0.13);
        moveAny(0.0,0.0,0.0,0.2);
    }

    //a general function to move using any input for the bot
    public void moveAny(double forw, double side, double spin, double time){

        redPower = forw - side + spin;
        bluePower = forw + side + spin;
        greenPower = -forw - side + spin;
        yellowPower = -forw + side + spin;

        uwuBot.leftRed.setPower(redPower);
        uwuBot.leftBlue.setPower(bluePower);
        uwuBot.rightGreen.setPower(greenPower);
        uwuBot.rightYellow.setPower(yellowPower);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}