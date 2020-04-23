// This file defines class "writer".

// This code uses class SharedDataStruct, which provides access methods 
// for the shared data.

//package CISC;

public class Writer extends Thread {
  int myName;  // The name of this thread, passed as a parameter by
               // the process creating this thread.
  SharedDataStruct sharedData;  // The shared data, passed as a parameter


  // This is the constructor for class Writer.
  public Writer(int name, SharedDataStruct SD) {
    myName = name;    // copy the parameter value to local variable "MyName"
    sharedData = SD;  // sharedData refers to the SharedDataStruct object
                      // that the readers and writers are using.
  }  // end of the constructor for class "Writer"


  public void run () {
    int val;  // this value is written to the database.

    for (int I = 0;  I < 5; I++) {
      val = (int)(30.0 * Math.random());

      System.out.println("Writer " + myName + 
         " is now requesting to write the value " + val + " to the database.");
      sharedData.dataWrite(val);

      System.out.println("Writer " + myName + " has written the value " + val +
         " and is doing something else for a while");

      // Now simulate "doing something else" by delaying for a while.
      try { sleep((int)(500.0*Math.random()+1)); } catch(Exception e) {break;}
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"

