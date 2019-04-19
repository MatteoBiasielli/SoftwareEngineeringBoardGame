package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CostChoiceController extends Controller{
	@FXML
	private Label label;
	@FXML
	private Button one;
	@FXML
	private Button two;

	public CostChoiceController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE LABEL IS SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE LABEL IS SET EVERYTIME THE STAGE IS SHOWED
	}
	public void setMessage(String s){
		this.label.setText(s);
	}
	/**activated when a player chooses which cost he wants to pay to get a card*/
	public void onOneClick(){
		this.getController().putInOutput("TOWERACTIONWITHCHOICE;1");
		this.getStage().hide();
	}
	/**activated when a player chooses which cost he wants to pay to get a card*/
	public void onTwoClick(){
		this.getController().putInOutput("TOWERACTIONWITHCHOICE;2");
		this.getStage().hide();
	}
}
