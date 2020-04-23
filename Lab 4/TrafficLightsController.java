
public class TrafficLightsController extends Thread
{
	public static boolean EastBound_Light_is_red = true;
	public static boolean WestBound_Light_is_red = true;
	public static int Green_time = 500; //time unit used for green light period
	public static int car_time = 2;
	
	public void run () 
	  {
		int I;
		int U = 0;
		int Work_Count;
		Synch.timeSim.threadStart();
		int time_left;
		while(U<100) 
		{
			
			//get Mutex on traffic light and Eastcounter
		    Synch.Mutex.acquire();
		   
		    //Turn the Eastbound traffic light Green 
			EastBound_Light_is_red = false;
			System.out.println("At time " + Synch.timeSim.curTime() +" Westbound light is red\n");
			System.out.println("At time " + Synch.timeSim.curTime() +" Eastbound light is green\n");
			
			//If there are cars waiting, release one car at a time
	    	//Release cars here (we use 5 time units to separate a car front the car behind) 
			
			time_left = Green_time;//I use this variable to keept track of how much time is left
			
			while((Synch.Eastbound_Car_Counter>0) && (time_left>0)) 
			{ 
				
				Synch.EastEntrance.release(); 
				//release the mutex on traffic light boolean var (EastBound_Light_is_red) and the counter (Eastbound_Car_Counter)
				Synch.Mutex.release();
				//Simulate the delay between two cars
				Synch.timeSim.doSleep(car_time);
							
				//Get mutex on Eastbound_Car_Counter
				Synch.Mutex.acquire();
				Synch.Eastbound_Car_Counter--;
				time_left = time_left - car_time;
				  
			}
			//Release mutex
			Synch.Mutex.release();
			
			//Traffic light period (time_left)
			Synch.timeSim.doSleep(time_left);

			
			
			//get Mutex on traffic light (MUTEX)
		    Synch.Mutex.acquire();
			//Both traffic light becomes red for 100 time units
			WestBound_Light_is_red = true;
			EastBound_Light_is_red = true;
			System.out.println("At time " + Synch.timeSim.curTime() +" Westbound light is red\n");
			System.out.println("At time " + Synch.timeSim.curTime() +" Eastbound light is red\n");
			//Releae traffic light mutex (MUTEX)
			Synch.Mutex.release();
			Synch.timeSim.doSleep(100);
			

			
			//get Mutex on traffic light and Westcounter
		    Synch.Mutex.acquire();
		    //Turn the Westbound traffic light Green 
			WestBound_Light_is_red = false;
			System.out.println("At time " + Synch.timeSim.curTime() +" Westbound light is green\n");
			System.out.println("At time " + Synch.timeSim.curTime() +" Eastbound light is red\n");
			
			//If there are cars waiting, release one car at a time
	    	//Release cars here (we use 5 time units to separate a car front the car behind) 
			
			time_left = Green_time;//I use this variable to keept track of how much time is left
			
			while((Synch.Westbound_Car_Counter>0)&& (time_left>0)) 
			{ 
				
				Synch.WestEntrance.release(); 
				//release the mutex on traffic light boolean var (EastBound_Light_is_red) and the counter (Eastbound_Car_Counter)
				Synch.Mutex.release();
				
				//Simulate the delay between two cars
				Synch.timeSim.doSleep(car_time);
				
				//Get mutex on Westbound_Car_Counter
				Synch.Mutex.acquire();
				Synch.Westbound_Car_Counter--;
				time_left = time_left - car_time;
				  
			}
			//Release mutex
			Synch.Mutex.release();
			//Traffic light period (time_left)
			Synch.timeSim.doSleep(time_left);

			
			
			
			//get Mutex on traffic light (MUTEX)
		    Synch.Mutex.acquire();
			//Both traffic light becomes red for 100 time units
			WestBound_Light_is_red = true;
			EastBound_Light_is_red = true;
			System.out.println("At time " + Synch.timeSim.curTime() +" Westbound light is red\n");
			System.out.println("At time " + Synch.timeSim.curTime() +" Eastbound light is red\n");
			//Releae traffic light mutex (MUTEX)
			Synch.Mutex.release();
			Synch.timeSim.doSleep(100);
			
			U++;
		}
		
		
	  }
}


