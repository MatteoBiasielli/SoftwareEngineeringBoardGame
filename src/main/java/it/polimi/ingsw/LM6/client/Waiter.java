package it.polimi.ingsw.LM6.client;

public class Waiter{
	public void wakeUp(){
		synchronized(this){
			notify();
		}
	}
}
