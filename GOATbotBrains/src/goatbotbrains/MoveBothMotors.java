/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goatbotbrains;

import rxtxrobot.*; 

public class MoveBothMotors {
    // This example shows how to run both DC encoded motors at the same time but different distances. 

public static void main(String[] args){
	
		RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		r.setPort("COM3"); // Set port to COM2 
		r.connect(); 
		//We don't have to attach anything because these motors are 
		//attached by default 
                //runEncodedMotor(MOTOR1, 250, 820);
                //runEncodedMotor(MOTOR2, )
                r.attachMotor(RXTXRobot.MOTOR1, 5);
                r.attachMotor(RXTXRobot.MOTOR2, 6);
		r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 820, RXTXRobot.MOTOR2, -500, 820); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
		r.close(); 
	} 
public int getEncodedMotorPosition(int MOTOR){
    return 0; 
}
public void runEncodedMotor(int motor, int speed, int ticks){
		//We don't have to attach anything because these motors are 
		//attached by default 
                //r.attachMotor(RXTXRobot.MOTOR1, 5);
                //r.attachMotor(RXTXRobot.MOTOR2, 6);
		//r.runEncodedMotor(RXTXRobot.MOTOR1, 255, 1, RXTXRobot.MOTOR2, -255, 1); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks. 
}
}
