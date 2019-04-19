package it.polimi.ingsw.LM6.client.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayDiscardLeaderController extends Controller{
	private String header;
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

	public PlayDiscardLeaderController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.disableButtons();
	}
	/**sets the controller ready to handle a discard leader action*/
	public void setDiscard(){
		this.header="LEADERDISCARD;";
	}
	/**sets the controller ready to handle a play leader action*/
	public void setPlay(){
		this.header="LEADERPLAY;";
	}
	/**updates the leaders list and images when an UPDATEPLAYERS or PLAYERS message is received.
	 * enables the related buttons
	 */
	public void setLeaders(ArrayList<Integer> leaders){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\";
		for(int i=0;i<leaders.size();i++){
			if(i==0){
				this.tile1.setImage(new Image(path+leaders.get(i)+".png"));
				this.one.setDisable(false);
			}
			else if(i==1){
				this.tile2.setImage(new Image(path+leaders.get(i)+".png"));
				this.two.setDisable(false);
			}
			else if(i==2){
				this.tile3.setImage(new Image(path+leaders.get(i)+".png"));
				this.three.setDisable(false);
			}
			else if(i==3){
				this.tile4.setImage(new Image(path+leaders.get(i)+".png"));
				this.four.setDisable(false);
			}
		}
	}
	/**sends the action*/
	public void onSendClick(){
		String out=header;
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
	/**disables all the buttons*/
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
