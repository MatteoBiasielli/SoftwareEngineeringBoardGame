package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CouncilActionController extends Controller{
	@FXML
	private TextField servants;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
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
	public CouncilActionController(){
		//EMPTY BECAUSE NO INITIALIZATION REQUIRED
	}
	@FXML
	public void initialize(){
		this.servants.setText("0");
	}
	/** plus one servant*/
	public void onPlusClick(){
		this.servants.setText(Integer.toString(Integer.parseInt(this.servants.getText())+1));
	}
	/**minus one servant*/
	public void onMinusClick(){
		int n=Integer.parseInt(this.servants.getText())-1;
		if(n<0)
			n=0;
		this.servants.setText(Integer.toString(n));
	}
	/**sends the council action*/
	public void onSendClick(){
		String out="COUNCILACTION;";
		if(this.black.isSelected())
			out+="BLACK";
		else if(this.orange.isSelected())
			out+="ORANGE";
		else if(this.white.isSelected())
			out+="WHITE";
		else if(this.uncoloured.isSelected())
			out+="UNCOLOUR";
		out+=";";
		out+=this.servants.getText();
		this.getController().putInOutput(out);
		this.getStage().hide();
		
	}
}
