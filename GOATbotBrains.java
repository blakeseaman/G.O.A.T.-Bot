package robotcode;
import rxtxrobot.*; 

public class GOATbotBrains {
    
    
    //--------------MOTOR CALIBRATION STUFF--------------//
    static int FWDmotor1power = 350;
    static int FWDmotor2power = 350;
    static int FWDmotor1ticks = 28;
    static int FWDmotor2ticks = 28;
    static int ROTmotor1power = 250;
    static int ROTmotor2power = 250;
    static int ROTmotor1ticks = 90;
    static int ROTmotor2ticks = 90;
    //--------Enter Values as Is without negative--------//
    
    static char n;
    static char s; 
    static char e;
    static char w;
    static char heading = n; //Specify this heading at the beginning of round
    
    static RXTXRobot r = new ArduinoUno(); 
   
    public static void main(String[] args) {
        
        r.setPort("COM3"); 
        r.connect();
        r.attachServo(RXTXRobot.SERVO3, 10);
        
        getConductivityReading();
        //r.attachServo(RXTXRobot.SERVO1, 11);
            
        //r.moveServo(RXTXRobot.SERVO3, 170);
        //System.out.println(r.getConductivity());
        /*for(int count = 0; count < 20; count++){
        r.moveServo(RXTXRobot.SERVO3, 170);
        }*/
        
       r.close();
       
    }
    //----Task Methods----//
    public static void performRamp(){
        moveForward(9);
        performWeatherBoom();
        moveForward(9);
    }
    public static void performSandbox(){
        r.refreshAnalogPins();   
        AnalogPin temp = r.getAnalogPin(1);
        while (temp.getValue() > 100){
            r.runMotor(RXTXRobot.MOTOR1, 370, RXTXRobot.MOTOR2, -400, 0); 
            r.refreshAnalogPins();
            temp = r.getAnalogPin(3);
            System.out.println("Bump Sensor Value" + temp.getValue());          
        }
                r.refreshAnalogPins();
                r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); 
               
       
                    System.out.println("Bump Sensor Value" + temp.getValue());          
                
    }
    public static void performObstacleAvoidance(){
        r.refreshAnalogPins();
        //AnalogPin temp = r.getAnalogPin(1);
        boolean notThereYet = true;
            while (notThereYet){
                moveForward(2);
                    if(r.getPing(7) < 30){
                        turnNinetyClockwise();
                        moveForward(5);
                        turnNinetyCounterclockwise();
                        moveForward(7);
                        turnNinetyCounterclockwise();
                        moveForward(5);
                        turnNinetyClockwise();
                        moveForward(5);
                            notThereYet = false;
                            }

            }
                r.refreshAnalogPins();
                moveForward(2);
                         
                
    }
    //---Sensor Methods--//
    public static void performWeatherBoom(){
        r.moveServo(RXTXRobot.SERVO1, 90); // Move Servo 1 to location 30 
        for (int i = 0; i < 5; i++) {
            r.moveServo(RXTXRobot.SERVO1, 180);
            getAnemometer();
            getWeatherData();
        }
        r.sleep(2000);
    }
    public static void getWeatherData(){ //this also prints anemomeneter, need to move anemometer to its own method. ADD 1 to 
                double temperatureDifference;
                //THERMOMETER IN A0
		double thermistorReading2 = getThermistorReading(2);
                double thermistorReading = getThermistorReading(0);//TEMP
		//System.out.println("The probe 2 reads the value: " + thermistorReading);
                //System.out.println("The probe 0 reads the value: " + thermistorReading2); //TEMP
		//System.out.println("In volts " + (thermistorReading * (5.0/1023.0)));
                double temp;
                double temp2;
                int sum = 0;
                int sum2 = 0;
                for (int i = 0; i < 5; i++){                    
                    temp = (double) ((thermistorReading - 764.874)/-8.2968);
                    temp2 = (double) ((thermistorReading2 - 749.488)/-7.629);
                    sum += temp;
                    sum2 += temp2;        
                }
		System.out.println("The temperature is (2): " + (sum2 / 5));
                temperatureDifference = (sum2/5) - (sum/5);
                System.out.println("The difference :" + temperatureDifference + 1); //corrective 1 
    }
    public static void getAnemometer(){

		int thermistorReading = getThermistorReading(2); //gets reading from first thermister
                int thermistorReading2 = getThermistorReading(0); //gets a reading from the second thermistor
                int anemometerReading = thermistorReading - thermistorReading2; //takes the difference of thermister values
                System.out.println("Aemometer Reading: " + anemometerReading); //prints the difference and calls it the anemometer reading

    }
    public static int getThermistorReading(int pin){
           
		int sum = 0;
		//Read the analog pint values ten times, adding the sum of each time
		for(int i = 0; i < 5; i ++){
			//Refresh the analog pins so we get new readings
			r.refreshAnalogPins();
			int reading = r.getAnalogPin(pin).getValue();
			sum += reading;
		}
		//Return the average reading
		return sum/ 10;
    }
    public static void getConductivityReading(){
        //double waterReading;
        //for(int i = 0; i < 5; i++){
            r.moveServo(RXTXRobot.SERVO3, 170);
            //double ADC = r.getConductivity();
            //waterReading = ((-0.0227962)*(ADC))+(24.3498);
            //System.out.println(waterReading);
        //} 

    }
    //---MOTION METHODS---//
    public static void moveForward(int blocksToMove){
        

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
    public static void moveUp(int blocksToMove){
      if(heading == n){
          moveForward(blocksToMove);
      }
      else if (heading == e){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == s){
          turnNinetyCounterclockwise();
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == w){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = n;
    }
    public static void moveRight(int blocksToMove){
      if(heading == n){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == e){
          moveForward(blocksToMove);
      }
      else if (heading == s){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == w){
          turnNinetyClockwise();
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = e;
    }
    public static void moveDown(int blocksToMove){
      if(heading == n){
          turnNinetyClockwise();
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == e){    
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == s){
          moveForward(blocksToMove);
      }
      else if (heading == w){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = s;
    }
    public static void moveLeft(int blocksToMove){
      if(heading == n){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == e){
          turnNinetyCounterclockwise();
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == s){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == w){
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = w;
      System.out.println(heading);
    }
    //---Scanning Methods---//RUN THESE WHEN IN QUAD STARTING POSITION, THEY HAVE TASKS EMBEDDED
    public static void scanForRamp(){
        //starting at bottom right corner of scanning field
        scanSomeBlocksForRamp(3);
        turnNinetyClockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyClockwise();
        //start scanning second row
        scanSomeBlocksForRamp(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyCounterclockwise();
        //start scanning third row
        scanSomeBlocksForRamp(3);
        turnNinetyClockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyClockwise();
        //start scanning fourth row
        scanSomeBlocksForRamp(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyCounterclockwise();   
        //start scanning fifth row
        scanSomeBlocksForRamp(3);
        turnNinetyClockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyClockwise();
        //start scanning sixth or last row
        scanSomeBlocksForRamp(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForRamp(1);
        turnNinetyCounterclockwise();
        
        
    }
    public static void scanForSandbox(){
        //starting at bottom right corner of scanning field
        scanSomeBlocksForSandbox(3);
        turnNinetyClockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyClockwise();
        //start scanning second row
        scanSomeBlocksForSandbox(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyCounterclockwise();
        //start scanning third row
        scanSomeBlocksForSandbox(3);
        turnNinetyClockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyClockwise();
        //start scanning fourth row
        scanSomeBlocksForSandbox(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyCounterclockwise();   
        //start scanning fifth row
        scanSomeBlocksForSandbox(3);
        turnNinetyClockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyClockwise();
        //start scanning sixth or last row
        scanSomeBlocksForSandbox(3);
        turnNinetyCounterclockwise();
        scanSomeBlocksForSandbox(1);
        turnNinetyCounterclockwise();
        
        
    }
    public static void scanSomeBlocksForRamp(int blocksToScan){
        
        for(int i = 0; i < blocksToScan; i++){
            boolean temp = moveAndScanForward();
                if(temp == true){
                    performRamp();
                }
                else{
                    System.out.println("Block One Scanned");
                }
        }
    }
    public static void scanSomeBlocksForSandbox(int blocksToScan){
        
        for(int i = 0; i < blocksToScan; i++){
            boolean temp = moveAndScanForward();
                if(temp == true){
                    performSandbox();
                }
                else{
                    System.out.println("Block One Scanned");
                }
        }

    }
    public static boolean moveAndScanForward(){
        //where that boolean is an answer to Did you see something?
        if(r.getPing(7) >= 80){
            //80CM is around 219 ticks, so lets move forward 105 ticks twice
            r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, 105, RXTXRobot.MOTOR2, FWDmotor2power, 105);
            r.sleep(1000);
            r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, 105, RXTXRobot.MOTOR2, FWDmotor2power, 105);
            return false;  
        }
        else{
            r.sleep(5000);
            if(r.getPing(7) <= 80){
                return true;
            }
            else{
                r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, 105, RXTXRobot.MOTOR2, FWDmotor2power, 105);
                r.sleep(1000);
                r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, 105, RXTXRobot.MOTOR2, FWDmotor2power, 105);
                return false;
            }
        }
    }
    
}
