
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "SamplingAuto-V3", group = "Linear OpMode")
@Disabled
public class MineralDetectionV3 extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private ElapsedTime runtime = new ElapsedTime();
    TestBedHardWare uwuBot = new TestBedHardWare();
    GyroCalc logic = new GyroCalc();
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
    @Override
    public void runOpMode() {
        //initializes all the hardward for the robot
        uwuBot.initDrive(this);
        //initializes all the vuforia tracking systems
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
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
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
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    goldPos = 1;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    goldPos = 3;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    goldPos = 2;
                                }
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        //function that moves to knock off gold and calls a face to wall
        determineRoute(goldPos);
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }
    //compares the position of the golds
    public void determineRoute(int goldPos){
        if(goldPos == 1){
            owoLeft();
        } else if(goldPos == 3){
            owoRight();
        } else{
            owoCen();
        }
    }
    //the respective functions for each possibility of the gold
    public void owoLeft(){
        moveAny(0.4,0.0,0.0,1.0);
        moveAny(0.0,0.3,0.0,0.6);
        moveAny(0.3,0.0,0.0,0.9);
        moveAny(0.0,0,0.0,0.5);
    }
    public void owoCen(){
        moveAny(0.35,-0.035,0.03,2.2);
        moveAny(0.0,0.00,0.00,0.5);
        moveAny(0.0,0,0.0,0.5);
    }
    public void owoRight(){
        moveAny(0.45,-0.06,0.02,0.9);
        moveAny(0.0,-0.34,0.0,1);
        moveAny(0.3,0.0,0.01,1);
        moveAny(0.0,0,0.0,0.5);
    }
    //a general function to move using any input for the bot
    public void moveAny(double forw, double side, double spin, double time){

        uwuBot.redPower = forw - side + spin;
        uwuBot.bluePower = forw + side + spin;
        uwuBot.greenPower = -forw - side + spin;
        uwuBot.yellowPower = -forw + side + spin;

        uwuBot.leftRed.setPower(uwuBot.redPower);
        uwuBot.leftBlue.setPower(uwuBot.bluePower);
        uwuBot.rightGreen.setPower(uwuBot.greenPower);
        uwuBot.rightYellow.setPower(uwuBot.yellowPower);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
        }
    }
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
