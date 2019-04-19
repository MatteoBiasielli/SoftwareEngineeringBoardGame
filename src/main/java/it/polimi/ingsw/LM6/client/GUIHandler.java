package it.polimi.ingsw.LM6.client;

import java.util.Vector;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
import it.polimi.ingsw.LM6.client.gui.GUIMainController;
import it.polimi.ingsw.LM6.client.gui.GUIThread;
import javafx.application.Platform;

public class GUIHandler implements MessageHandler {
	
	/** The Thread that starts the GUI*/
	private GUIThread threadRunningGUI;
	
	
	/**GUI handler constructor.
	 * Receives no parameters.
	 * Starts the Thread that starts the GUI.
	 */
	public GUIHandler(){
		this.threadRunningGUI= new GUIThread();
		this.threadRunningGUI.start();
	}
	
	/**handles a message received from the server. The message is put in an input list inside the GUI and the
	 * a Platform.runLater is called with the update routine as parameter, so that the gui will auto-update, consuming the message.
	 * @param message - the message as it was received
	 * @return the String that has to be sent to the server as answer to what was received
	 * @throws OutputNotNecessaryException is nothing has to be sent to the server
	 */
	@Override
	public String handleMessage( String message) throws OutputNotNecessaryException {
		this.threadRunningGUI.getController().putInInput(message);
		Platform.runLater(new UpdateRunnable(threadRunningGUI.getController()));
		return this.threadRunningGUI.read();
	}
}


/**GUI update routine. It is invoked with a Platform.runLater instruction, because it must be excecuted by the
 * JAVAFX Application Thread.
 * 
 */
class UpdateRunnable implements Runnable{
	private GUIMainController controller;
	private Vector<String> input;
	
	/**  Update routine's constructor method
	 * 
	 * @param gmc - the GUI main controller
	 */
	UpdateRunnable(GUIMainController gmc){
		this.controller=gmc;
		this.input=controller.getInput();
	}
    @Override
    public void run() {
    	String app=input.get(0);
    	input.remove(0);
    	controller.solveMessage(app);
    }
	
}
