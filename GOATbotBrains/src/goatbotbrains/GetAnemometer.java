package goatbotbrains;
//testing addition
import rxtxrobot.*; 

public class GetAnemometer {
    
    static RXTXRobot r = new ArduinoUno();
    
    public static void main(String[] args){
               
                r.setPort("COM3");
                r.connect();
    
                //RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		int thermistorReading = getThermistorReading(3);
                int thermistorReading2 = getThermistorReading(0);
                int diff = thermistorReading - thermistorReading2; 
                System.out.println("Aemometer Reading:" + diff);
		System.out.println("The probe read the value: " + thermistorReading);
		System.out.println("In volts " + (thermistorReading * (5.0/1023.0)));
                   int temp =0;
		temp = (int) ((thermistorReading - 816.517881)/-11.66755);

		System.out.println("The temperature is: " + temp);
	
                r.close();
	
    }
    
    public static int getThermistorReading(int pin)
	{
		int sum = 0;
		int readingCount = 10;

		//Read the analog pint values ten times, adding the sum of each time
		for(int i = 0; i < readingCount; i ++)
		{
			//Refresh the analog pins so we get new readings
			r.refreshAnalogPins();
			int reading = r.getAnalogPin(pin).getValue();
			sum += reading;
		}

		//Return the average reading
		return sum/ readingCount;
                    
	} 
}

        