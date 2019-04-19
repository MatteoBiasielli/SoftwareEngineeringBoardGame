package it.polimi.ingsw.LM6.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
import it.polimi.ingsw.LM6.client.exceptions.TerminatedException;

public class ClientSender extends Thread{
	IClient user;
	
	public ClientSender(IClient u){
		this.user=u;
	}
	@Override
	public void run(){
		System.out.println("Welcome to Lorenzo il Magnifico!");
		try{
			String mex = this.user.getClientNickAndPass();
			this.user.sendToServer(mex);
			while (true){
				
				try {
					mex = this.user.getClientInput();
					this.user.sendToServer(mex);			
				} catch (OutputNotNecessaryException e){
					continue;
				}
				
			}
		}catch(TerminatedException | RemoteException e){
			System.exit(0);
		}catch (NoSuchElementException | IOException e) {
			System.out.println("Client error occurred.");
			return;
		}
	}
}
