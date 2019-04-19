package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ProductionActionController extends Controller{
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
	@FXML
	private RadioButton card00;
	@FXML
	private RadioButton card01;
	@FXML
	private RadioButton card02;
	@FXML
	private RadioButton card10;
	@FXML
	private RadioButton card11;
	@FXML
	private RadioButton card12;
	@FXML
	private RadioButton card20;
	@FXML
	private RadioButton card21;
	@FXML
	private RadioButton card22;
	@FXML
	private RadioButton card30;
	@FXML
	private RadioButton card31;
	@FXML
	private RadioButton card32;
	@FXML
	private RadioButton card40;
	@FXML
	private RadioButton card41;
	@FXML
	private RadioButton card42;
	@FXML
	private RadioButton card50;
	@FXML
	private RadioButton card51;
	@FXML
	private RadioButton card52;
	
	
	public ProductionActionController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.servants.setText("0");
		bonus=false;
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
	/**called when this contoller must be used to handle a bonus production action. Sets the right header and disables familiar radio buttons*/
	public void setHeaderBonus(){
		this.header="BONUSPRODUCTIONACTION;";
		bonus=true;
		this.zero.setDisable(true);
		this.one.setDisable(true);
		this.orange.setDisable(true);
		this.black.setDisable(true);
		this.white.setDisable(true);
		this.uncoloured.setDisable(true);
	}
	/**called when this contoller must be used to handle a normal production action. Sets the right header and enables familiar radio buttons*/
	public void resetHeaderBonus(){
		this.header="PRODUCTIONACTION;";
		bonus=false;
		this.zero.setDisable(false);
		this.one.setDisable(false);
		this.orange.setDisable(false);
		this.black.setDisable(false);
		this.white.setDisable(false);
		this.uncoloured.setDisable(false);
	}
	
	/**sends the message containing the necessary information*/
	public void onSendProductionClick(){
		String out=header;
		String a="";
		String b;
		String c="";
		String d="";
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
		if(this.card00.isSelected())
			d+="0;";
		else if(this.card01.isSelected())
			d+="1;";
		else if(this.card02.isSelected())
			d+="2;";
		
		if(this.card10.isSelected())
			d+="0;";
		else if(this.card11.isSelected())
			d+="1;";
		else if(this.card12.isSelected())
			d+="2;";
		
		if(this.card20.isSelected())
			d+="0;";
		else if(this.card21.isSelected())
			d+="1;";
		else if(this.card22.isSelected())
			d+="2;";
		
		if(this.card30.isSelected())
			d+="0;";
		else if(this.card31.isSelected())
			d+="1;";
		else if(this.card32.isSelected())
			d+="2;";
		
		if(this.card40.isSelected())
			d+="0;";
		else if(this.card41.isSelected())
			d+="1;";
		else if(this.card42.isSelected())
			d+="2;";
		
		if(this.card50.isSelected())
			d+="0";
		else if(this.card51.isSelected())
			d+="1";
		else if(this.card52.isSelected())
			d+="2";
		if(!bonus)
			this.getController().putInOutput(out+a+";"+b+";"+c+";"+d);
		else
			this.getController().putInOutput(out+b+";"+d);
		this.getStage().hide();
		
	}
	/**sets enables the radioBoxes required to allow the user to choose which production he wants to activate on each card
	 * 
	 * @param yc - the number of the user's yellow cards
	 */
	public void setChoices(Integer yc) {
		this.card00.setSelected(true);
		this.card01.setSelected(false);
		this.card02.setSelected(false);
		this.card10.setSelected(true);
		this.card11.setSelected(false);
		this.card12.setSelected(false);
		this.card20.setSelected(true);
		this.card21.setSelected(false);
		this.card22.setSelected(false);
		this.card30.setSelected(true);
		this.card31.setSelected(false);
		this.card32.setSelected(false);
		this.card40.setSelected(true);
		this.card41.setSelected(false);
		this.card42.setSelected(false);
		this.card50.setSelected(true);
		this.card51.setSelected(false);
		this.card52.setSelected(false);
		this.card00.setDisable(false);
		this.card01.setDisable(false);
		this.card02.setDisable(false);
		this.card10.setDisable(false);
		this.card11.setDisable(false);
		this.card12.setDisable(false);
		this.card20.setDisable(false);
		this.card21.setDisable(false);
		this.card22.setDisable(false);
		this.card30.setDisable(false);
		this.card31.setDisable(false);
		this.card32.setDisable(false);
		this.card40.setDisable(false);
		this.card41.setDisable(false);
		this.card42.setDisable(false);
		this.card50.setDisable(false);
		this.card51.setDisable(false);
		this.card52.setDisable(false);
		if(yc<6){
			this.card50.setDisable(true);
			this.card51.setDisable(true);
			this.card52.setDisable(true);
		}
		if(yc<5){
			this.card40.setDisable(true);
			this.card41.setDisable(true);
			this.card42.setDisable(true);
		}
		if(yc<4){
			this.card30.setDisable(true);
			this.card31.setDisable(true);
			this.card32.setDisable(true);
		}
		if(yc<3){
			this.card20.setDisable(true);
			this.card21.setDisable(true);
			this.card22.setDisable(true);
		}
		if(yc<2){
			this.card10.setDisable(true);
			this.card11.setDisable(true);
			this.card12.setDisable(true);
		}
		if(yc<1){
			this.card00.setDisable(true);
			this.card01.setDisable(true);
			this.card02.setDisable(true);
		}
			
	}
}
