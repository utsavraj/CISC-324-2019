package CISC;

public class MainThread
{
  public static void main(String [] args)
  {
     Buffer b = new Buffer(4);
     Producer p = new Producer(b);
     Consumer c = new Consumer(b);
    
     p.start();
     c.start();
     
  }
}
 
