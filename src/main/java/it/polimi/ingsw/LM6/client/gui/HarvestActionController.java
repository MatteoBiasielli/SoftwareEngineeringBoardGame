package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class HarvestActionController extends Controller{
	private String header;
	private boolean bonus;
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
	private RadioButton orange;
	@FXML
	private RadioButton black;
	@FXML
	private RadioButton white;
	@FXML
	private RadioButton uncoloured;
	
	public HarvestActionController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.servants.setText("0");
		this.header="HARVESTACTION;";
		bonus=false;
	}
	/**called when this contoller must be used to handle a bonus harvest action. Sets the right header and disables familiar radio buttons*/
	public void setHeaderBonus(){
		this.header="BONUSHARVESTACTION;";
		bonus=true;
		this.zero.setDisable(true);
		this.one.setDisable(true);
		this.orange.setDisable(true);
		this.black.setDisable(true);
		this.white.setDisable(true);
		this.uncoloured.setDisable(true);
	}
	/**called when this contoller must be used to handle a normal harvest action. Sets the right header and enables familiar radio buttons*/
	public void resetHeaderBonus(){
		this.header="HARVESTACTION;";
		bonus=false;
		this.zero.setDisable(false);
		this.one.setDisable(false);
		this.orange.setDisable(false);
		this.black.setDisable(false);
		this.white.setDisable(false);
		this.uncoloured.setDisable(false);
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
	/**sends the message containing the necessary information*/
	public void onSendHarvestClick(){
		String out=header;
		String a="";
		String b;
		String c="";
		if(this.zero.isSelected())
			c="1";
		else if(this.one.isSelected())
			c="2";
		if(this.black.isSelected())
			a="BLACK";
		else if(this.orange.isSelected())
			a="ORANGE";
		else if(this.white.isSelected())
			a="WHITE";
		else if(this.uncoloured.isSelected())
			a="UNCOLOUR";
		b=this.servants.getText();
		if(!bonus)
			this.getController().putInOutput(out+a+";"+b+";"+c);
		else
			this.getController().putInOutput(out+b);
		this.getStage().hide();
		
	}
}
