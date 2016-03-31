package goatbotbrains;

// This example shows how to get the Analog pin sensor data from the Arduino.  It shows the value of every Analog pin once after connecting to the Arduino. 
import rxtxrobot.*; 
 
public class MotionWithBumpSensor 
{    
    static RXTXRobot r;
	 
    public static void main(String[] args) 
    {     
        // All sensor data will be read from the analog pins 
      RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		r.setPort("COM3"); // Set port to COM2 
		r.connect();
                r.attachMotor(RXTXRobot.MOTOR1, 5);
                r.attachMotor(RXTXRobot.MOTOR2, 6);
                r.refreshAnalogPins();   
                AnalogPin temp = r.getAnalogPin(1);
                while (temp.getValue() > 100){
                r.runMotor(RXTXRobot.MOTOR1, 370, RXTXRobot.MOTOR2, -400, 0); 
                   r.refreshAnalogPins();
                   temp = r.getAnalogPin(1);
                    System.out.println("Bump Sensor Value" + temp.getValue());          
                }
                r.refreshAnalogPins();
                r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); 
               
       
                    System.out.println("Bump Sensor Value" + temp.getValue());          
                r.close(); 
                
    }
    
} 


