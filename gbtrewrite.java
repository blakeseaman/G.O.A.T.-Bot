package robotcode;
import rxtxrobot.*; 

//"C:\Users\blake\OneDrive\Documents\Southern Methodist University\Spring2016\Engineering Design\RobotCodeBACKUPORIGINAL\src\robotcode\GOATbotBrains.java"

public class GOATbotBrains {
    
    
    //--------------MOTOR CALIBRATION STUFF--------------//84 ticks is 1 foot
    static int FWDmotor1power = 210 ;//CHANGE THESE
    static int FWDmotor2power = 225;//CHANGE THESE
    static int FWDmotor1ticks = 84;//Default to 84
    static int FWDmotor2ticks = 84;//Default to 84
    static int ROTmotor1power = 190;//Don't fucking touch
    static int ROTmotor2power = 190;//DOn't fucking touch
    static int ROTmotor1ticks = 80;//CHANGE THESE TO CALLIBRATE ROTATION
    static int ROTmotor2ticks = 80;//CHANGE THESE TO CALLINRATE ROTATION
    //--------Enter Values as Is without negative--------//
    

    static char heading = 'e'; //Specify this heading at the beginning of round
    
    static RXTXRobot r = new ArduinoUno(); 
   
    public static void main(String[] args) {
         
       r.setPort("COM3"); 
       r.connect();
        
       r.attachMotor(RXTXRobot.MOTOR1, 5);
       r.attachMotor(RXTXRobot.MOTOR2, 6);
       r.attachServo(RXTXRobot.SERVO3, 10);
       r.attachServo(RXTXRobot.SERVO1, 11);
         
       r.attachGPS();
       
       
        //Quad 1
        
        /*
        moveLeft(13);
        moveDown(9);
        moveLeft(5);
        performRamp();
        moveLeft(1);
        moveDown(3);
        moveRight(5);
        moveDown(10);
        performSandbox();
        moveRight(18);
        moveDown(7);
        */
        
        //Quad 2
        
        /*
        moveRight(7);
        moveDown(4);
        performRamp();
        moveDown(1);
        moveLeft(3);
        moveDown(9);
        performSandbox();
        moveLeft(3);
        moveUp(10);
        moveRight(10);
        moveUp(6);
        moveRight(12);
        */
        
        //Quad 3
        
        /*
        moveUp(5);
        moveRight(3);
        performSandbox();
        moveLeft(3);
        moveUp(10);
        moveRight(4);
        moveUp(5);
        performRamp();
        moveUp(5);
        moveLeft(5);
        */
        
        //Quad 4
        
        /*
        moveUp(16);
        moveLeft(10);
        moveUp(6);
        moveLeft(6);
        performRamp();
        moveLeft(1);
        moveDown(3);
        moveRight(5);
        moveDown(10);
        performSandbox();
        moveLeft(7);
        moveDown(7);
        */
      
        
     
       r.close();
       
    }
    //----Task Methods----//
    public static void performRamp(){ 
        moveForward(5);
        performWeatherBoom();
        moveForward(5);
    }
    public static void performSandbox(){
        r.refreshAnalogPins();   
        AnalogPin temp = r.getAnalogPin(3); //Actually GETS the value of the pin
        while (temp.getValue() > 100){
            r.runMotor(RXTXRobot.MOTOR1, FWDmotor1power, RXTXRobot.MOTOR2, -FWDmotor1power, 0); 
            r.refreshAnalogPins();
            //temp = r.getAnalogPin(3);
            temp = r.getAnalogPin(3);
            System.out.println("Bump Sensor Value" + temp.getValue());   
        }
            r.refreshAnalogPins();
            r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); 
                //System.out.println("Should be running Sandbox");
                getConductivityReading();
                r.runEncodedMotor(RXTXRobot.MOTOR1, -FWDmotor1power, 30, RXTXRobot.MOTOR2, FWDmotor2power, 30);                
    }
    public static void performObstacleAvoidance(){ //WE NEED TO REWRITE THIS BULLSHIT
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
       // r.moveServo(RXTXRobot.SERVO1, 90); // Move Servo 1 to location 30 
        for (int i = 0; i < 1; i++) {
            r.moveServo(RXTXRobot.SERVO1, 180);
            
            getWeatherData();
            
        }
        r.sleep(2000);
    }
    public static void getWeatherData(){ //this also prints anemomeneter, need to move anemometer to its own method. ADD 1 to 
                double temperatureDifference;
                //THERMOMETER IN A0
		double thermistorReading2 = getThermistorReading(2);
                double thermistorReading = getThermistorReading(0);//TEMP
		System.out.println("The probe 0 reads the value: " + thermistorReading);
                System.out.println("The probe 2 reads the value: " + thermistorReading2); //TEMP
                double temp;
                double temp2;
                 
                    temp = ((thermistorReading - 379.08)/(-3.88));
                    temp2 = ((thermistorReading2 - 390.2)/(-4.35));


                    
		System.out.println("The temperature is (2): " + temp2);
                System.out.println("The Temperature is (0): " + temp);
                temperatureDifference = temp2 - temp;
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
        double waterReading;
        double ADC;
        for(int i = 0; i < 1; i++){
           
           
           r.moveServo(RXTXRobot.SERVO3, 170);

           ADC = r.getConductivity();
                       //System.out.println(ADC);


           //System.out.println(ADC);
       waterReading = ((-0.0227962)*(ADC))+(24.3498);
            System.out.println(waterReading - 1);
            //System.out.println(((-.0227962)*(r.getConductivity()))+(24.3498));
        } 
        r.sleep(2000);
        r.moveServo(RXTXRobot.SERVO3, 90);
        r.sleep(1000);

    }
    //---MOTION METHODS---//
    public static void moveForward(int blocksToMove){
        
        int totalMovementDistance1 = blocksToMove * FWDmotor1ticks;
        int totalMovementDistance2 = blocksToMove * FWDmotor2ticks;
        
        r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor1power, totalMovementDistance1, RXTXRobot.MOTOR2, -FWDmotor2power, totalMovementDistance2);
        
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
      if(heading == 'n'){
          moveForward(blocksToMove);
      }
      else if (heading == 'e'){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 's'){
          turnNinetyCounterclockwise();
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'w'){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = 'n';
      System.out.println("Heading should be N " + heading);
    }
    public static void moveRight(int blocksToMove){
      if(heading == 'n'){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'e'){
          moveForward(blocksToMove);
      }
      else if (heading == 's'){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'w'){
          turnNinetyClockwise();
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = 'e';
      System.out.println("Heading should be e " + heading);

    }
    public static void moveDown(int blocksToMove){
      if(heading == 'n'){
          turnNinetyClockwise();
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'e'){    
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 's'){
          moveForward(blocksToMove);
      }
      else if (heading == 'w'){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = 's';
      System.out.println("Heading should be s " + heading);

    }
    public static void moveLeft(int blocksToMove){
      if(heading == 'n'){
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'e'){
          turnNinetyCounterclockwise();
          turnNinetyCounterclockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 's'){
          turnNinetyClockwise();
          moveForward(blocksToMove);
      }
      else if (heading == 'w'){
          moveForward(blocksToMove);
      }
      else{
          System.out.println("Error: Something to do with variable char heading");
      }
      heading = 'w';
      System.out.println("Heading should be W " + heading);
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
            r.runEncodedMotor(RXTXRobot.MOTOR1, FWDmotor2power, 105, RXTXRobot.MOTOR2, FWDmotor1power, 105);
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
    
    public static void loopForMovement(int oneBlockSprints){
	for(int i = 0; i < oneBlockSprints; i++){
	moveForward(1);
            if(r.getPing(7) < 30){
		r.sleep(5000);
                    if(r.getPing(7) < 75){
			turnNinetyClockwise();
                        moveForward(3);
                        turnNinetyCounterclockwise();
                        moveForward(5);
                        turnNinetyCounterclockwise();
                        moveForward(3);
                        turnNinetyClockwise();
                        moveForward(3);
                    }
                    else{
			System.out.println("Temporary Obstacle/Robot Sighted");
                    }
            }
            else{
                System.out.println("No Obstacles Sighted");
            }
	}
    }
    
    
}

