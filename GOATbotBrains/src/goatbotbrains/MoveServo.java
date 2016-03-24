package goatbotbrains;

import rxtxrobot.*; 

public class MoveServo {
    
	public static void main(String[] args) 
	{ 
		RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		r.setPort("COM3"); // Set the port to COM3 
		//r.setVerbose(true); // Turn on debugging messages 
		r.connect(); 
		r.attachServo(RXTXRobot.SERVO1, 10); //Connect the servos to the Arduino 
		//r.attachServo(RXTXRobot.SERVO2, 10); 
		r.moveServo(RXTXRobot.SERVO1, 160); // Move Servo 1 to location 30 
		//r.moveServo(RXTXRobot.SERVO2, 170); // Move Servo 2 to location 170 
		r.close(); 
	} 
} 
    
