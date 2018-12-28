package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="TESTBEDauto", group="Linear OpMode")
//@Disabled
public class TESTBEDauto extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRed = null;
    private DcMotor leftBlue = null;
    private DcMotor rightGreen = null;
    private DcMotor rightYellow = null;

    MineralHardWare robot = new MineralHardWare();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.running = true;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftRed = hardwareMap.get(DcMotor.class, "LeftR");
        leftBlue = hardwareMap.get(DcMotor.class, "LeftB");
        rightGreen = hardwareMap.get(DcMotor.class, "RightG");
        rightYellow = hardwareMap.get(DcMotor.class, "RightY");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            robot.runOpMode();

            if (robot.goldMineralX != -1 && robot.silverMineral1X != -1 && robot.silverMineral2X != -1) {
                if (robot.goldMineralX < robot.silverMineral1X && robot.goldMineralX < robot.silverMineral2X) {
                    telemetry.addData("Gold Mineral Position", "Left");
                } else if (robot.goldMineralX > robot.silverMineral1X && robot.goldMineralX > robot.silverMineral2X) {
                    telemetry.addData("Gold Mineral Position", "Right");
                } else {
                    telemetry.addData("Gold Mineral Position", "Center");
                }
                telemetry.update();
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}