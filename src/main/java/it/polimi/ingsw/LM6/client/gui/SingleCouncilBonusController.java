package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class SingleCouncilBonusController extends Controller{

	@FXML
	private Button send;
	@FXML
	private Button renounce;
	@FXML
	private RadioButton woodStone;
	@FXML
	private RadioButton coins;
	@FXML
	private RadioButton servants;
	@FXML
	private RadioButton military;
	@FXML
	private RadioButton faith;
	
	public SingleCouncilBonusController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	
	/**sends the council bonus choice*/
	public void onSendClick(){
		String out="COUNCILREQUIREMENT;";
		if(this.woodStone.isSelected())
			out+="1";
		else if(this.servants.isSelected())
			out+="2";
		else if(this.coins.isSelected())
			out+="3";
		else if(this.military.isSelected())
			out+="4";
		else if(this.faith.isSelected())
			out+="5";
		this.getController().putInOutput(out);
		this.getStage().hide();
		
	}
	/**sends the council bonus renounciation*/
	public void onRenounceClick(){
		String out="BONUSRENOUNCE;COUNCILREQUIREMENT";
		this.getController().putInOutput(out);
		this.getStage().hide();
		
	}
}
