package it.polimi.ingsw.LM6.client.gui;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;

public class GUIThread extends Thread{
	/** The GUI main controller. This parameter is set when the GUI has finished the init and start methods */
	private static GUIMainController mainController;
	/*This Boolean says if the GUI has already finished the init and start methods or not*/
	private static Boolean isStarted=false;
	
	@Override
	public void run(){
		GUIMainController.startGUI();
	}
	
	/** reads a String from the output buffer of the GUI
	 * 
	 * @return the String that has to be sent to the server as answer to what was received
	 * @throws OutputNotNecessaryException is nothing has to be sent to the server
	 */
	public String read() throws OutputNotNecessaryException{
		while(!isStarted){
			try {
				sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return mainController.readFromOutput();
	}
	
	
	public static void setMainController(GUIMainController gmc){
		mainController=gmc;
		isStarted=true;
	}
	
	public GUIMainController getController(){
		while(!isStarted){
			try {
				sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return mainController;
	}
}
