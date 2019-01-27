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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;

@Autonomous(name="TestofAccelandGyro", group="Gyros")
//@Disabled
public class DriveUsingGyroAccel extends LinearOpMode {

    /* Declare OpMode members. */
    ElapsedTime runtime = new ElapsedTime();
    TestBedHardWare uwuBot = new TestBedHardWare();
    GyroCalc logic = new GyroCalc();
    TestBedFunctions functions = new TestBedFunctions();
    double Derivative = 0.0;


    double forwT;
    double sideT;
    double spinT;

    Acceleration gravity;

    @Override
    public void runOpMode() {
        uwuBot.initDrive(this);
        logic.initDrive(uwuBot);
        waitForStart();
        while(opModeIsActive()){
            gyroCor(0.0,0.6,0.0,0.0);
            sleep(5000);
            gyroCor(90,0.6,0.0,0.0);
            sleep(5000);
            telemetry.addData("1 imu heading:", logic.lastAngles.firstAngle);
            telemetry.addData("2 global heading:", logic.globalAngle);
            telemetry.addData("3 correction:", logic.correction);
            telemetry.addData("4 PD math:", termstoString());
            telemetry.addData("5 Strafe Math",strafetoString());
            telemetry.update();
        }
    }
    public void gyroCor(double target, double forw, double side, double spin){
        double spinPower = 0.0;
        if(forw != 0 || side != 0 && spin == 0){
            target = logic.angles.firstAngle;
            if(forw != 0 && side == 0 && spin == 0){
                if(gravity.xAccel != 0){
                    side = -gravity.xAccel/3;
                }
            }
            if(forw == 0 && side != 0 && spin == 0){
                if(gravity.yAccel != 0){
                    side = -gravity.yAccel/3;
                }
            }
            logic.target = target;
            logic.correction = logic.checkDirection();
            Derivative = Math.abs(5/logic.correction);
            if(Math.abs(logic.correction)>=1.0 && spin == 0){
                spinPower = Range.clip((logic.correction/5)/Derivative, -0.5,0.50);
            }
        }
        forwT =forw;
        sideT = side;
        spinT =spin;
        functions.moveRobot(forw,side, spinPower + spin);
    }
    String termstoString(){
        String s;
        s = Double.toString(logic.pTerm)+"+"+Double.toString(logic.dTerm);
        return s;
    }
    String strafetoString(){
        String f;
        String i;
        String p;
        f = Double.toString(forwT);
        i = Double.toHexString(sideT);
        p = Double.toString(spinT);
        return f + ", " + i + ", " + p;
    }
}
