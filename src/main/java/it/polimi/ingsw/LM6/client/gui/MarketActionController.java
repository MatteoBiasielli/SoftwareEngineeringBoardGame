package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class MarketActionController extends Controller{
	@FXML
	private TextField servants;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	@FXML
	private Button send;
	@FXML
	private RadioButton zero;
	@FXML
	private RadioButton one;
	@FXML
	private RadioButton two;
	@FXML
	private RadioButton three;
	@FXML
	private RadioButton orange;
	@FXML
	private RadioButton black;
	@FXML
	private RadioButton white;
	@FXML
	private RadioButton uncoloured;
	public MarketActionController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.servants.setText("0");
	}
	/**plus one servant*/
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
	/**sends the action and closes the screen*/
	public void onSendClick(){
		String out="MARKETACTION;";
		if(this.zero.isSelected())
			out+="0";
		else if(this.one.isSelected())
			out+="1";
		else if(this.two.isSelected())
			out+="2";
		else if(this.three.isSelected())
			out+="3";
		out+=";";
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
