package robotcode;
import rxtxrobot.*; 
import java.util.Scanner;

public class GOATbotBrains {
    
    //--------------MOTOR CALIBRATION STUFF--------------//
    static int FWDmotor1power = 175;
    static int FWDmotor2power = 200;
    static int FWDmotor1ticks = 28;
    static int FWDmotor2ticks = 28;
    static int ROTmotor1power = 175;
    static int ROTmotor2power = 200;
    static int ROTmotor1ticks = 18;
    static int ROTmotor2ticks = 18;
    //--------Enter Values as Is without negative--------//
    
   static RXTXRobot r = new ArduinoUno(); 
   
    public static void main(String[] args) {
        
       r.setPort("COM3"); 
       r.connect();
       
       
       //testConductivity();
       // MoveServo();
       //GetPing();
       //GetTemperature();
       //GetAnemometer();
       //TemperatureArm();
       moveForward(5);
       
 
       r.close();
       
    }

    public static void MoveServo(){
                
        r.setVerbose(true); 
        r.attachServo(RXTXRobot.SERVO1, 10); //Connect the servos to the Arduino
        r.moveServo(RXTXRobot.SERVO1, 90); // Move Servo 1 to location 30 
        for (int i = 0; i < 20; i++) {
            System.out.println("MEMES");
            r.moveServo(RXTXRobot.SERVO1, 90);
        }
        r.sleep(8000);

    }
    public static void GetPing(){

		System.out.println("Response: " + r.getPing(12) + " cm"); 
		r.sleep(300); 
    }
    //---Weather Methods--//
    public static void weatherRoutine(){
        
    }
    public static void TemperatureArm(){
       
        r.setVerbose(true); 
        r.attachServo(RXTXRobot.SERVO1, 10); //Connect the servos to the Arduino
        r.moveServo(RXTXRobot.SERVO1, 90); // Move Servo 1 to location 30 
        for (int i = 0; i < 20; i++) {
            System.out.println("MEMES");
            r.moveServo(RXTXRobot.SERVO1, 90);
        }
        r.sleep(8000);

    }
    public static void GetTemperature(){

		int thermistorReading = getThermistorReading(0);
		System.out.println("The probe read the value: " + thermistorReading);
		System.out.println("In volts " + (thermistorReading * (5.0/1023.0)));
                int temp = 0;
		temp = (int) ((thermistorReading - 816.517881)/-11.66755);
		System.out.println("The temperature is: " + temp);

    }
    public static void GetAnemometer(){

		int thermistorReading = getThermistorReading(3); //gets reading from first thermister
                int thermistorReading2 = getThermistorReading(0); //gets a reading from the second thermistor
                int anemometerReading = thermistorReading - thermistorReading2; //takes the difference of thermister values
                System.out.println("Aemometer Reading: " + anemometerReading); //prints the difference and calls it the anemometer reading

    }
    public static int getThermistorReading(int pin){
           
		int sum = 0;
		int readingCount = 10;
		//Read the analog pint values ten times, adding the sum of each time
		for(int i = 0; i < readingCount; i ++){
			//Refresh the analog pins so we get new readings
			r.refreshAnalogPins();
			int reading = r.getAnalogPin(0).getValue();
			sum += reading;
		}
		//Return the average reading
		return sum/ readingCount;
    }
    public static double getConductivity(){
        r.refreshAnalogPins();
        double conductivityReading1 = r.getAnalogPin(5).getValue(); //gets ADC code from one panel
        double conductivityReading2 = r.getAnalogPin(4).getValue(); //gets ADC code from another panel
        double conductivityDifference = conductivityReading1 - conductivityReading2; //takes difference of ADC readings
        double resistance = (2000*conductivityDifference)/(conductivityDifference-1); //uses equation to calculate resistance
        double conductivityReading = .016/resistance*0.001075; //uses calibrated code
        return conductivityReading;
    }
    public static void testConductivity(){
        double conductivityReading1 = r.getAnalogPin(3).getValue();
        double conductivityReading2 = r.getAnalogPin(1).getValue();
        System.out.println(conductivityReading1);
        System.out.println(conductivityReading2);
        double ConductivityOutput = getConductivity();
        System.out.println(ConductivityOutput);
    }
    //--------------------//
    //---MOTION METHODS---//
    public static void moveForward(int blocksToMove){
        
        r.attachMotor(RXTXRobot.MOTOR1, 5);
        r.attachMotor(RXTXRobot.MOTOR2, 6);
            for(int i = 0 ; i < blocksToMove ; i++){ //loop will run blocksToMove times
                // Run both motors forward a block
                r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, FWDmotor1ticks, RXTXRobot.MOTOR2, -FWDmotor2power, FWDmotor2ticks);
            }
    }
    public static void turnNinetyClockwise(){
        // Run both motors inverse to turn 
        r.runEncodedMotor(RXTXRobot.MOTOR1, ROTmotor1power, ROTmotor1ticks, RXTXRobot.MOTOR2, ROTmotor2power, ROTmotor2ticks); 

    }
    public static void turnNinetyCounterclockwise(){
        // Run both motors inverse to turn 
        r.runEncodedMotor(RXTXRobot.MOTOR1, -ROTmotor1power, ROTmotor1ticks, RXTXRobot.MOTOR2, -ROTmotor2power, ROTmotor2ticks); 

    }
    //--------------------//
}
