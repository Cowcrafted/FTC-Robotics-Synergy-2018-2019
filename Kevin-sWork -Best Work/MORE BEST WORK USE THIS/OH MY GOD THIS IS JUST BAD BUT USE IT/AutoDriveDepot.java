
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="AutoDriveDepot", group="Linear OpMode")
//@Disabled
public class AutoDriveDepot extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor liftDrive = null;

    //Servos Yall
    private Servo relicServo = null;
    private Servo Plow1 = null;
    private Servo Plow2 = null;


    static final double FORWARD_SPEED = 0.6;
    static final double TURN_SPEED = 0.35;


    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        liftDrive = hardwareMap.get(DcMotor.class, "lift_drive");

        Plow1 = hardwareMap.get(Servo.class, "plow1");
        Plow2 = hardwareMap.get(Servo.class, "plow2");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        liftDrive.setDirection(DcMotor.Direction.FORWARD);

        relicServo = hardwareMap.get(Servo.class, "relicServo");
        relicServo.setPosition(0.35);
        Plow1.setPosition(0.48);
        Plow2.setPosition(0.48);

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        // Send telemetry message to signify waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1 Get the robot off of the lander. (Make sure it lifts to the point where it the lip lifts off the hatch.
        liftDrive.setPower(0.3);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 7.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        liftDrive.setPower(0);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Move backwards to prevent hitting lip from lander.
        rightDrive.setPower(FORWARD_SPEED);
        leftDrive.setPower(FORWARD_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.08)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Turn the Robot right to detach lip from hook
        rightDrive.setPower(TURN_SPEED);
        leftDrive.setPower(-TURN_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.75)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        //Stops the motors
        rightDrive.setPower(0);
        leftDrive.setPower(0);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Lower the lift arm
        liftDrive.setPower(-0.3);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 7.8)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Turns right to correct lowered movement.
        liftDrive.setPower(0);
        leftDrive.setPower(TURN_SPEED);
        rightDrive.setPower(-TURN_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.75)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Drive forward to align
        leftDrive.setPower(.50);
        rightDrive.setPower(.50);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
        }

        // Step 4: Stop for 1 Second
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
        }

        // Step 5: Back up for a bit
        leftDrive.setPower(-0.5);
        rightDrive.setPower(-0.5);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .70)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Turns right for a bit
        leftDrive.setPower(TURN_SPEED);
        rightDrive.setPower(-TURN_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .70)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Drive forward for a bit
        leftDrive.setPower(0.5);
        rightDrive.setPower(0.5);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 5: Drop relic
        relicServo.setPosition(0.80);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
        }

        // Step 4: Stop for 1 Second
        relicServo.setPosition(0.35);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Turn for a bit
        leftDrive.setPower(-TURN_SPEED);
        rightDrive.setPower(TURN_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .50)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
        }

        // Run forward till the crater
        leftDrive.setPower(1);
        rightDrive.setPower(1);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 7:  Stop
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        }
}