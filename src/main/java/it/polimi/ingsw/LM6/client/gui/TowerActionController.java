package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class TowerActionController extends Controller{
	private String header;
	private boolean bonus;
	private String bonusOn;
	@FXML
	private TextField servants;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	@FXML
	private Button send;
	@FXML
	private Button renounce;
	@FXML
	private RadioButton green;
	@FXML
	private RadioButton yellow;
	@FXML
	private RadioButton blue;
	@FXML
	private RadioButton purple;
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
	public TowerActionController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.servants.setText("0");
		this.bonus=false;
		this.header="TOWERACTION;";
		this.renounce.setVisible(false);
		this.renounce.setDisable(true);
		this.bonusOn="NaB";
	}
	/**called when this controller must be used to handle a bonus tower action.
	 * basing on the type of the action it disables familiars' and tower colors' radio buttons
	 * @param a - a string array representing the bonus action
	 */
	public void setAsBonus(String[] a){
		this.bonus=true;
		this.header="BONUSTOWERACTION;";
		this.renounce.setVisible(true);
		this.renounce.setDisable(false);
		this.bonusOn=a[2];
		if(!("EVERY").equals(a[2]))
			this.setDisable(true, true);
		else
			this.setDisable(false,true);
	}	
	public void resetBonus(){
		this.bonus=false;
		this.header="TOWERACTION;";
		this.bonusOn="NaB";
		this.renounce.setVisible(false);
		this.renounce.setDisable(true);
		this.setDisable(false,false);
	}
	private void setDisable(boolean b1, boolean b2){
		this.green.setDisable(b1);
		this.yellow.setDisable(b1);
		this.blue.setDisable(b1);
		this.purple.setDisable(b1);
		this.white.setDisable(b2);
		this.black.setDisable(b2);
		this.orange.setDisable(b2);
		this.uncoloured.setDisable(b2);
	}
	public void onPlusClick(){
		this.servants.setText(Integer.toString(Integer.parseInt(this.servants.getText())+1));
	}
	public void onMinusClick(){
		int n=Integer.parseInt(this.servants.getText())-1;
		if(n<0)
			n=0;
		this.servants.setText(Integer.toString(n));
	}
	/**called when this controller must be used to handle a normal tower action.*/
	public void onSendClick(){
		String out=header;
		String col="";
		String floor="";
		String fam="";
		String serv="";
		if(this.green.isSelected())
			col+="GREEN";
		else if(this.yellow.isSelected())
			col+="YELLOW";
		else if(this.blue.isSelected())
			col+="BLUE";
		else if(this.purple.isSelected())
			col+="PURPLE";
		if(this.zero.isSelected())
			floor+="0";
		else if(this.one.isSelected())
			floor+="1";
		else if(this.two.isSelected())
			floor+="2";
		else if(this.three.isSelected())
			floor+="3";
		if(this.black.isSelected())
			fam+="BLACK";
		else if(this.orange.isSelected())
			fam+="ORANGE";
		else if(this.white.isSelected())
			fam+="WHITE";
		else if(this.uncoloured.isSelected())
			fam+="UNCOLOUR";
		serv+=this.servants.getText();
		if(!bonus)
			this.getController().putInOutput(out+col+";"+floor+";"+fam+";"+serv);
		else if(("EVERY").equals(this.bonusOn))
			this.getController().putInOutput(out+serv+";"+floor+";"+col);
		else
			this.getController().putInOutput(out+serv+";"+floor+";"+this.bonusOn);
		this.getStage().hide();
		
	}
	/**sends the renounce message*/
	public void onRenounceClick(){
		this.getController().putInOutput("BONUSRENOUNCE;BONUSTOWERACTION");
		this.getStage().hide();
	}
}
