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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Concept: Scan Servo", group = "Concept")
//@Disabled
public class ServoTests extends LinearOpMode {
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    CRServo servo = null;
    DcMotor collect = null;
    DcMotor lift = null;


    @Override
    public void runOpMode() {
        // Change the text in quotes to match any servo name on your robot.
        servo = hardwareMap.get(CRServo.class, "Servo");
        collect = hardwareMap.get(DcMotor.class,"CollectD");
        lift = hardwareMap.get(DcMotor.class,"LiftD");
        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        servo.setPower(0.0);
        waitForStart();
        // Scan servo till stop pressed.
        while(opModeIsActive()){
            // slew the servo, according to the rampUp (direction) variable.
            if (gamepad1.dpad_left) {
                servo.setPower(-0.3);
            }
            else if (gamepad1.dpad_right) {
                servo.setPower(0.0);
            }else{
                servo.setPower(0.0);
            }
            // Set the servo to the new position and pause;
            if(gamepad1.dpad_up){
               collect.setPower(1);
               lift.setPower(-0.2);
            }else if(gamepad1.dpad_down){
                collect.setPower(-1);
                lift.setPower(0.2);
            }else{
                collect.setPower(0.0);
                lift.setPower(0.0);
            }
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
        }
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
