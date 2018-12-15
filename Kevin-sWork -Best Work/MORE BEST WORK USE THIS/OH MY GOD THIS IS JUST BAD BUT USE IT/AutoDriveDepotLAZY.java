package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoDriveDepotLAZY", group="Linear OpMode")
//@Disabled
public class AutoDriveDepotLAZY extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor liftDrive = null;

    //Servos Y'all
    private Servo relicServo = null;
    private Servo Plow1 = null;
    private Servo Plow2 = null;


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.35;


    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        liftDrive = hardwareMap.get(DcMotor.class, "lift_drive");

        Plow1 = hardwareMap.get(Servo.class, "plow1");
        Plow2 = hardwareMap.get(Servo.class, "plow2");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        relicServo = hardwareMap.get(Servo.class, "relicServo");
        relicServo.setPosition(0.35);
        Plow1.setPosition(1);
        Plow2.setPosition(1);
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
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Turn the Robot right to detach lip from hook
        rightDrive.setPower(0.35);
        leftDrive.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.4)) {
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
        while (opModeIsActive() && (runtime.seconds() < 7.6)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Turns right to correct lowered movement.
        liftDrive.setPower(0);
        leftDrive.setPower(0.35);
        rightDrive.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.75)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        // Go forward for 2 seconds
        leftDrive.setPower(FORWARD_SPEED);
        rightDrive.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftDrive.setPower(FORWARD_SPEED);
        rightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        relicServo.setPosition(0.8);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        relicServo.setPosition(0.35);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

            // Step 5:  Stop
        leftDrive.setPower(0);
        rightDrive.setPower(0);


        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}