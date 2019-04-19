package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class TripleCouncilBonusController extends Controller{
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
	@FXML
	private RadioButton woodStone1;
	@FXML
	private RadioButton coins1;
	@FXML
	private RadioButton servants1;
	@FXML
	private RadioButton military1;
	@FXML
	private RadioButton faith1;
	@FXML
	private RadioButton woodStone2;
	@FXML
	private RadioButton coins2;
	@FXML
	private RadioButton servants2;
	@FXML
	private RadioButton military2;
	@FXML
	private RadioButton faith2;
	
	public TripleCouncilBonusController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	/**sends the triple council bonus choices, if they're different*/
	public void onSendClick(){
		String out="TRIPLECOUNCILREQUIREMENT;";
		String b1="";
		String b2="";
		String b3="";
		if(this.woodStone.isSelected())
			b1="1";
		else if(this.servants.isSelected())
			b1="2";
		else if(this.coins.isSelected())
			b1="3";
		else if(this.military.isSelected())
			b1="4";
		else if(this.faith.isSelected())
			b1="5";
		if(this.woodStone1.isSelected())
			b2="1";
		else if(this.servants1.isSelected())
			b2="2";
		else if(this.coins1.isSelected())
			b2="3";
		else if(this.military1.isSelected())
			b2="4";
		else if(this.faith1.isSelected())
			b2="5";
		if(this.woodStone2.isSelected())
			b3="1";
		else if(this.servants2.isSelected())
			b3="2";
		else if(this.coins2.isSelected())
			b3="3";
		else if(this.military2.isSelected())
			b3="4";
		else if(this.faith2.isSelected())
			b3="5";
		if(!b1.equals(b2) && !b1.equals(b3) && !b2.equals(b3)){
			this.getController().putInOutput(out+b1+";"+b2+";"+b3);
			this.getStage().hide();
		}
		
	}
	/**sends the triple council bonus renounciation*/
	public void onRenounceClick(){
		String out="BONUSRENOUNCE;TRIPLECOUNCILREQUIREMENT";
		this.getController().putInOutput(out);
		this.getStage().hide();
		
	}
}
