
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Sampling AutoV2OWO", group = "Linear OpMode")
//@Disabled
public class MineralDetectionV3 extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private ElapsedTime runtime = new ElapsedTime();

    TestBedHardWare uwuBot = new TestBedHardWare();

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
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

    int goldPos = 0;

    @Override
    public void runOpMode() {
        uwuBot.initDrive(this);

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
        }
        else if(goldPos == 3){
            owoRight();
        }
        else{
            owoCen();
        }
        moveAny(0.45,-0.06,0.02,0.9);
    }

    public void owoLeft(){
        moveAny(0.45,-0.06,0.02,0.9);
    }
    public void owoRight(){
        moveAny(0.45,-0.06,0.02,0.9);
    }
    public void owoCen(){
        moveAny(0.45,-0.06,0.02,0.9);
    }

    //a general function to move any
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
