// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.  

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore OktoRead;
  public static Semaphore OktoWrite;
  public static int AR = 0; //This counter counts the number of active readers
  public static int AW = 0; //This counter counts the number of active writers
  public static int WR = 0; //This counter counts the number of waiting readers
  public static int WW = 0; //This counter counts the number of waiting writers

}  // end of class "Synch"
