package robotcode;
import rxtxrobot.*; 

public class GOATbotBrains {
    
   static RXTXRobot r = new ArduinoUno(); 
   
    public static void main(String[] args) {
        
       r.setPort("COM3"); 
       r.connect();
       MoveServo();
       GetPing();
       GetTemperature();
       GetAnemometer();
       r.close();
       
    }
    
    public static void MoveServo(){
                
		//r.setVerbose(true); 
		r.attachServo(RXTXRobot.SERVO1, 10); //Connect the servos to the Arduino
                r.moveServo(RXTXRobot.SERVO1, 75); // Move Servo 1 to location 30 

    }
    
    public static void GetPing(){

		System.out.println("Response: " + r.getPing(12) + " cm"); 
		r.sleep(300); 
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
}
