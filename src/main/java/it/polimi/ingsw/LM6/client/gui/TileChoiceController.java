package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileChoiceController extends Controller{
	@FXML
	private ImageView tile1;
	@FXML
	private ImageView tile2;
	@FXML
	private ImageView tile3;
	@FXML
	private ImageView tile4;
	@FXML
	private ImageView tile5;
	@FXML
	private RadioButton one;
	@FXML
	private RadioButton two;
	@FXML
	private RadioButton three;
	@FXML
	private RadioButton four;
	@FXML
	private RadioButton five;
	
	@FXML
	private Button send;

	public TileChoiceController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.one.setDisable(true);
		this.two.setDisable(true);
		this.three.setDisable(true);
		this.four.setDisable(true);
		this.five.setDisable(true);
	}
	/** sets the tiles' images in the screen and enables the right buttons*/
	public void setTiles(String[] s){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\tiles\\";
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
			else if(i==6){
				this.tile5.setImage(new Image(path+app[1]+".png"));
				this.five.setDisable(false);
			}
		}
	}
	/**sends the choice*/
	public void onSendClick(){
		String out="TILESDRAFTCHOICE;";
		String choice="";
		if(this.one.isSelected())
			choice="0";
		else if(this.two.isSelected())
			choice="1";
		else if(this.three.isSelected())
			choice="2";
		else if(this.four.isSelected())
			choice="3";
		else if(this.five.isSelected())
			choice="4";
		this.getController().putInOutput(out+choice);
		this.getStage().hide();
	}
}
