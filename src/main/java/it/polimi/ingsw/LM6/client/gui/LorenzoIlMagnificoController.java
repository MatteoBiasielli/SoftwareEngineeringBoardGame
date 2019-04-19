package it.polimi.ingsw.LM6.client.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LorenzoIlMagnificoController extends Controller{
	private String choice="0";
	/**the list of leaders that the player can copy*/
	private ArrayList<Integer> leaders;
	@FXML
	private ImageView tile1;
	@FXML
	private Button send;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	
	public LorenzoIlMagnificoController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	/** sets a leader image on the LorenzoIlMagnifico screen
	 * @param n - the position of the leader in the list "leaders"
	 */
	private void setLeader(int n){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\";
		this.tile1.setImage(new Image(path+n+".png"));
	}
	/**puts in the list "leaders" all the required integers, representing allt he leaders the player can copy
	 * 
	 * @param s - a string containing all the required data about the leaders
	 */
	public void setLeaders(String[] s){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\";
		this.leaders=new ArrayList<>();
		for(int i=2;i<s.length;i++){
			String[] app=s[i].split("ù");
			this.leaders.add(Integer.parseInt(app[1]));
		}
		this.tile1.setImage(new Image(path+leaders.get(0)+".png"));
	}
	/**previous leader (if the currently showed oen is not the first one)*/
	public void onMinusClick(){
		if(!("0").equals(this.choice)){
			this.choice=Integer.toString(Integer.parseInt(this.choice)-1);
			this.setLeader(this.leaders.get(Integer.parseInt(this.choice)));
		}
	}
	/**next leader (if the currently showed oen is not the last one)*/
	public void onPlusClick(){
		if(!this.choice.equals(Integer.toString(this.leaders.size()-1))){
			this.choice=Integer.toString(Integer.parseInt(this.choice)+1);
			this.setLeader(this.leaders.get(Integer.parseInt(this.choice)));
		}
	}
	/**sends the choice*/
	public void onSendClick(){
		String out="LORENZOILMAGNIFICOCHOICE;";
		this.getController().putInOutput(out+choice);
		this.getStage().hide();
	}
}
