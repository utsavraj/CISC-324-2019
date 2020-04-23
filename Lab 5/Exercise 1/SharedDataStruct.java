// This file defines class "SharedDataStruct", a shared data structure which is
// accessed by reader and writer threads.
//
// In this small example, the "shared data" consists of a single integer.
// This integer is declared to be private to class SharedData.  The only
// way for readers and writers to access this integer is by calling the
// data access methods "dataRead" and "dataWrite". The reader/writer 
// synchronization is built into the data access methods.  (The implementation
// of dataRead and dataWrite uses calls to the rw monitor, to provide
// synchronization.)
//
// From the outside, the reader and writer threads using the SharedData
// class do not need to be aware that there is any synchronization going on.
// This is a "safe" way to organize the software, with no chance that a
// reader or writer could access the data illegally.  For example, it is
// impossible for a reader to "forget to call startRead". The only way a 
// reader can access the shared data is via method "dataRead".  If the reader
// tries to access dataValue directly, a compile-time error results, since
// dataValue is declared to be private.
//
// In a more realistic example, a complex data structure would be declared
// instead of the integer dataValue.  This complex data structure (still 
// declared "private") would be accessed via set of data access methods, with
// one data access method for each type of update or value-retireval that 
// can be done.
//
// The data access methods (dataRead and dataWrite) use sleep statements 
// to simulate the time taken to access a complex database.  This allows
// us to observe the synchronization between readers and writers.

// This code uses class rwMonitor.

//package CISC;


public class SharedDataStruct {
    // For this small example, the "shared data" consists of a single integer
    private int dataValue;  


    // Declare an instance of the reader-writer monitor, to synchronize access
    // to dataValue.  If several instances of SharedDataStruct are created
    // (e.g. in mainMethod), then each of these data structures has its own 
    // instance of the reader-writer monitor.  That way, the synchronization
    // for "access to data structure number one" is not affected by 
    // synchornization for access to "data structure number two".
    private rwMonitor rw;


    // The constructor for class SharedDataStruct.
    public SharedDataStruct() {
        dataValue = 0;     // Initialize the "shared data"
        rw = new rwMonitor();
    }  // end of the constructor for class SharedDataStruct


    // ---------------------  Data Access Methods ----------------------------
    // The dataWrite method changes the value of some part of the shared
    // data.  In this small example, the integer value is updated.
    // A sleep statement simulates the time taken to update shared data that
    // has a more complicated structure.
    public void dataWrite (int value) {
      rw.startWrite();
      dataValue = -30;  // This value represents that "the database is being
                        // updated".  No reader should ever see this value.
      try { Thread.sleep((int)(200*Math.random()+1)); } catch(Exception e) { }
      dataValue = value;
      rw.endWrite();
  }  // end of the dataWrite method



    // The dataRead method reads some part of the shared data.  In this small
    // example, the integer value is read.
    // A sleep statement simulates the time taken to access shared data that
    // has a more complicated structure.  Reading takes half as long as writing.
    public int dataRead () {
        int value;  // the value read from the shared data.

        rw.startRead();
        try { Thread.sleep((int)(100*Math.random()+1)); } catch(Exception e) { }
        value = dataValue;
        rw.endRead();

        return value;
    }  // end of the dataRead method

}  // end of class "SharedDataStruct"

