/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="AutoDriveCrater", group="Linear OpMode")
public class AutoDriveCrater extends LinearOpMode {
    //Motors Yall
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    //Servos Yall

    private Servo relicServo = null;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Use (motor maybe) arm to drop for about 4 seconds

        // Set up the motor for the arm and put the code in HERE

        // May need to put in a lot more steps to dehook from the holder once on the ground
        // Step 2:  Go forward until reach minerals
        leftDrive.setPower(1); //the motor values will range from 1 to -1
        rightDrive.setPower(1);
        sleep(2000); //code stays for however many milliseconds given in "sleep"

        // Step 3:  Turn left about 100°
        leftDrive.setPower(-0.6);
        rightDrive.setPower(0.6);
        sleep(750);

        // Step 4:  Go forward about 4 feet
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(2000);

        // Step 5: Turn left about 20-35°
        leftDrive.setPower(-0.4);
        rightDrive.setPower(0.4);
        sleep(500);

        // Step 6: Go forward for final stretch to depot (about  4 feet maybe)
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(3000);

        // Step 7: Drop the relic in depot using the relic servo
        relicServo.setPosition(1);
        sleep(1000);

        // Step 8: Turn about 160-175° to the left
        leftDrive.setPower(-0.6);
        rightDrive.setPower(0.6);
        sleep(450);

        // Step 8: Ram into crater at full speed
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(6000);

    }
}
