package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LeaderChoiceController extends Controller{
	@FXML
	private ImageView tile1;
	@FXML
	private ImageView tile2;
	@FXML
	private ImageView tile3;
	@FXML
	private ImageView tile4;

	@FXML
	private RadioButton one;
	@FXML
	private RadioButton two;
	@FXML
	private RadioButton three;
	@FXML
	private RadioButton four;
	
	@FXML
	private Button send;

	public LeaderChoiceController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.disableButtons();
	}
	/** sets the leaders images in the screen and enables the right buttons*/
	public void setLeaders(String[] s){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\";
		for(int i=2;i<s.length;i++){
			String[] app=s[i].split("ù");
			if(i==2){
				this.tile1.setImage(new Image(path+app[1]+".png"));
				this.one.setDisable(false);
			}
			else if(i==3){
				this.tile2.setImage(new Image(path+app[1]+".png"));
				this.two.setDisable(false);
			}
			else if(i==4){
				this.tile3.setImage(new Image(path+app[1]+".png"));
				this.three.setDisable(false);
			}
			else if(i==5){
				this.tile4.setImage(new Image(path+app[1]+".png"));
				this.four.setDisable(false);
			}
		}
	}
	/**sends the choice*/
	public void onSendClick(){
		String out="LEADERDRAFTCHOICE;";
		String choice="";
		if(this.one.isSelected())
			choice="0";
		else if(this.two.isSelected())
			choice="1";
		else if(this.three.isSelected())
			choice="2";
		else if(this.four.isSelected())
			choice="3";
		this.getController().putInOutput(out+choice);
		this.getStage().hide();
	}
	/**disables all buttons ont he screen*/
	public void disableButtons(){
		this.one.setDisable(true);
		this.tile1.setImage(null);
		this.tile2.setImage(null);
		this.tile3.setImage(null);
		this.tile4.setImage(null);
		this.two.setDisable(true);
		this.three.setDisable(true);
		this.four.setDisable(true);
		this.one.setSelected(true);
	}
}
