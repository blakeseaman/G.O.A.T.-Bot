package goatbotbrains;
import rxtxrobot.*; 

public class NavigationExersize {

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
                boolean notThereYet = true;
                    while (notThereYet){
                        r.runMotor(RXTXRobot.MOTOR1, 370, RXTXRobot.MOTOR2, -400, 0); 
                            if(r.getPing(12) < 30){
                               avoidObstacle();
                               notThereYet = false;
                            }
                           //do we want to make this a while loop for future reliability?

                    }
                r.refreshAnalogPins();
                r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); 
                         
                r.close(); 
                
        }
                    
static void avoidObstacle(){
    		r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 30, RXTXRobot.MOTOR2, 500, 30); //move clockwise
		r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move forward
                r.runEncodedMotor(RXTXRobot.MOTOR1, -500, 30, RXTXRobot.MOTOR2, -500, 30); //move counterclockwise
                r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move forward
                r.runEncodedMotor(RXTXRobot.MOTOR1, -500, 30, RXTXRobot.MOTOR2, -500, 30); //move counterclockwise
                r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 30, RXTXRobot.MOTOR2, 500, 30); //move clockwise
                r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 300, RXTXRobot.MOTOR2, -500, 300); //move forward some after avoidance
                
                /*
                if(r.getPing(12) < 30){ //if there is an obstacle?
                   r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 30, RXTXRobot.MOTOR2, 500, 30); //move clockwise
                   r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move forward
                   r.runEncodedMotor(RXTXRobot.MOTOR1, -500, 30, RXTXRobot.MOTOR2, -500, 30); //move counterclockwise
                }
                else{ //if there isn't an obstacle?
                   r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move forward
                   r.runEncodedMotor(RXTXRobot.MOTOR1, -500, 30, RXTXRobot.MOTOR2, -500, 30); //turn counterclockwise to check again
                        if(r.getPing(12) < 30){ //if there is an obstacle again?
                            r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 30, RXTXRobot.MOTOR2, 500, 30); //move clockwise
                            r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move forward
                            r.runEncodedMotor(RXTXRobot.MOTOR1, 500, 70, RXTXRobot.MOTOR2, -500, 70); //move counterclockwise
                        }
                        else{
                            
                        }
                }
*/
}
                
}

