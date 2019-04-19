package it.polimi.ingsw.LM6.client.gui;

import javafx.stage.Stage;

public abstract class Controller {
	/**The GUI's main controller, that is used by the various controllers to put messages in output*/
	private GUIMainController controllerMain;
	/**The stage related to the current controller*/
	private Stage myStage;
	
	/**allows to set set the main controller in the current controller*/
	public void setController(GUIMainController gmc){
		this.controllerMain=gmc;
	}
	public void setStage(Stage s){
		this.myStage=s;
	}
	public Stage getStage(){
		return myStage;
	}
	public GUIMainController getController(){
		return controllerMain;
	}
}
