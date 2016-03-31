package goatbotbrains;
import rxtxrobot.*; 

public class GOATbotBrains {
    
   // RXTXRobot r = new ArduinoUno(); 
   
    public static void main(String[] args) {
        
       
        MoveServo();
        GetPing();
        
        
        
        
    }
    
    public static void MoveServo(){
                
                RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		r.setPort("COM3"); // Set the port to COM3, which is correct on Blake's Machine
		//r.setVerbose(true); 
		r.connect(); 
		r.attachServo(RXTXRobot.SERVO1, 10); //Connect the servos to the Arduino 
                r.moveServo(RXTXRobot.SERVO1, 40); // Move Servo 1 to location 30 
		r.close(); 
    }
    
    public static void GetPing(){
                RXTXRobot r = new ArduinoUno();
        	r.setPort("COM3"); // Set the port to COM3 
		r.connect();
		System.out.println("Response: " + r.getPing(12) + " cm"); 
		r.sleep(300); 
		r.close();
    }
    public static void GetTemperature(){
                RXTXRobot r = new ArduinoUno();
                r.setPort("COM3");
                r.connect();
		int thermistorReading = getThermistorReading(0);
		System.out.println("The probe read the value: " + thermistorReading);
		System.out.println("In volts " + (thermistorReading * (5.0/1023.0)));
                int temp = 0;
		temp = (int) ((thermistorReading - 816.517881)/-11.66755);
		System.out.println("The temperature is: " + temp);
                r.close();
    }
    public static void GetAnemometer(){
                RXTXRobot r = new ArduinoUno();
                r.setPort("COM3"); //Sets port to COM3, which is on Blake's Machine
                r.connect(); //opens connection to robot
		int thermistorReading = getThermistorReading(3); //gets reading from first thermister
                int thermistorReading2 = getThermistorReading(0); //gets a reading from the second thermistor
                int anemometerReading = thermistorReading - thermistorReading2; //takes the difference of thermister values
                System.out.println("Aemometer Reading: " + anemometerReading); //prints the difference and calls it the anemometer reading
                r.close();
	
    }
    public static int getThermistorReading(int pin){
                RXTXRobot r = new ArduinoUno();
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
}
