package it.polimi.ingsw.LM6.client.gui;

import it.polimi.ingsw.LM6.client.gui.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class OfferController extends Controller{
	@FXML
	private TextField servants;
	@FXML
	private Button plusServants;
	@FXML
	private Button minusServants;
	@FXML
	private TextField coins;
	@FXML
	private Button plusCoins;
	@FXML
	private Button minusCoins;
	@FXML
	private TextField stones;
	@FXML
	private Button plusStones;
	@FXML
	private Button minusStones;
	@FXML
	private TextField wood;
	@FXML
	private Button plusWood;
	@FXML
	private Button minusWood;
	
	
	
	@FXML
	private Button send;
	
	
	
	
	public OfferController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.resetOffer();
	}
	public void resetOffer(){
		this.servants.setText("0");
		this.wood.setText("0");
		this.stones.setText("0");
		this.coins.setText("0");
	}
	/** plus one servant*/
	public void onPlusServantsClick(){
		this.servants.setText(Integer.toString(Integer.parseInt(this.servants.getText())+1));
	}
	/**minus one servant*/
	public void onMinusServantsClick(){
		int n=Integer.parseInt(this.servants.getText())-1;
		if(n<0)
			n=0;
		this.servants.setText(Integer.toString(n));
	}
	
	/** plus one coin*/
	public void onPlusCoinsClick(){
		this.coins.setText(Integer.toString(Integer.parseInt(this.coins.getText())+1));
	}
	/**minus one coin*/
	public void onMinusCoinsClick(){
		int n=Integer.parseInt(this.coins.getText())-1;
		if(n<0)
			n=0;
		this.coins.setText(Integer.toString(n));
	}
	
	/** plus one wood*/
	public void onPlusWoodClick(){
		this.wood.setText(Integer.toString(Integer.parseInt(this.wood.getText())+1));
	}
	/**minus one wood*/
	public void onMinusWoodClick(){
		int n=Integer.parseInt(this.wood.getText())-1;
		if(n<0)
			n=0;
		this.wood.setText(Integer.toString(n));
	}
	
	/** plus one stone*/
	public void onPlusStonesClick(){
		this.stones.setText(Integer.toString(Integer.parseInt(this.stones.getText())+1));
	}
	/**minus one stone*/
	public void onMinusStonesClick(){
		int n=Integer.parseInt(this.stones.getText())-1;
		if(n<0)
			n=0;
		this.stones.setText(Integer.toString(n));
	}
	
	/**sends the request
	 * 
	 */
	public void onSendClick(){
		this.getController().putInOutput("FAMILIAROFFER;" + this.wood.getText()+";"+ this.stones.getText()+";"+ this.coins.getText()+";"+ this.servants.getText());
		this.getStage().hide();
	}
}
