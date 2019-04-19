package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ExcommunicationController extends Controller{
	@FXML
	private Button no;
	@FXML
	private Button yes;

	public ExcommunicationController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY
	}
	@FXML
	public void initialize(){
		
	}
	/** activated if the player wants to be excommunicated*/
	public void onYesClick(){
		this.getController().putInOutput("EXCOMMUNICATIONCHOICE;2");
		this.getStage().hide();
	}
	/** activated if the player doesn't want to be excommunicated*/
	public void onNoClick(){
		this.getController().putInOutput("EXCOMMUNICATIONCHOICE;1");
		this.getStage().hide();
	}
}
