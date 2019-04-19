package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class LeaderSixController extends Controller{
	@FXML
	private Button send;
	@FXML
	private RadioButton orange;
	@FXML
	private RadioButton black;
	@FXML
	private RadioButton white;
	@FXML
	private RadioButton uncoloured;
	public LeaderSixController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	/**sends the message containing the header and the familiar of which the value must be raised to 6*/
	public void onSendClick(){
		String out="LEADERFAMILYBONUS;";
		if(this.black.isSelected())
			out+="BLACK";
		else if(this.orange.isSelected())
			out+="ORANGE";
		else if(this.white.isSelected())
			out+="WHITE";
		else if(this.uncoloured.isSelected())
			out+="UNCOLOUR";
		this.getController().putInOutput(out);
		this.getStage().hide();
		
	}
}

