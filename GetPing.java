package goatbotbrains;
import rxtxrobot.*; 


public class GetPing 
{

  
        static RXTXRobot r = new ArduinoUno();
        
       // final private static int PING_PIN = 13; 

 
	public static void main(String[] args) 
	{ 
		//RXTXRobot r = new ArduinoUno(); // Create RXTXRobot object 
		r.setPort("COM3"); // Set the port to COM3 
		r.connect();
              
		System.out.println("Response: " + r.getPing(12) + " cm"); 
		r.sleep(300); 
		r.close(); 
	} 
} 
 
    
