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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
@Autonomous(name="StraightAsMe", group="Pushbot")
//@Disabled
public class DriveInAStraightLIne extends LinearOpMode {

    /* Declare OpMode members. */
    TestBedHardWare uwuBot = new TestBedHardWare();
    GyroCalc logic = new GyroCalc();
    double Derivative = 0.0;

    @Override
    public void runOpMode() {
        uwuBot.initDrive(this);
        logic.initDrive(uwuBot);

        waitForStart();
        moveAny(0.0, 0.0, 0.0);
        while(opModeIsActive()) {
            logic.correction = logic.checkDirection();
            Derivative = Math.abs(2/logic.correction);
            if(Math.abs(logic.correction)>=1.0){
                double spinPower = Range.clip((logic.correction/5)/Derivative, -0.5,0.50);
                moveAny(0.0, 0.0, spinPower);
            }else{
                moveAny(0.0,0.0,0.0);
            }
            telemetry.addData("1 imu heading", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading", logic.globalAngle);
            telemetry.addData("3 correction", logic.correction);
            telemetry.update();
        }
    }
    public void moveAny(double forw, double side, double spin){
        uwuBot.redPower = forw - side + spin;
        uwuBot.bluePower = forw + side + spin;
        uwuBot.greenPower = -forw - side + spin;
        uwuBot.yellowPower = -forw + side + spin;

        uwuBot.leftRed.setPower(uwuBot.redPower);
        uwuBot.leftBlue.setPower(uwuBot.bluePower);
        uwuBot.rightGreen.setPower(uwuBot.greenPower);
        uwuBot.rightYellow.setPower(uwuBot.yellowPower);
    }
}
