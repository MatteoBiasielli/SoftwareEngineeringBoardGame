package it.polimi.ingsw.LM6.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class StartingStageController {
	private GUIMainController controller;
	@FXML
	private Button startGame;
	@FXML
	private Button logout;
	@FXML
	private RadioButton simpleRules;
	@FXML
	private RadioButton advancedRules;
	@FXML
	private TextField numberOfPlayers;
	@FXML
	private Label message;
	@FXML
	private TextField from;
	@FXML
	private TextField to;
	@FXML
	private TextField nickname;
	@FXML
	private Button showScoreboard;
	
	
	public StartingStageController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY.
	}
	
	@FXML
	public void initialize(){
		this.startGame.setDisable(true);
		this.logout.setDisable(true);
	}
	
	public void setController(GUIMainController gmc){
		this.controller=gmc;
	}
	
	/**activated when the start new game button is pressed. If all data are correct and sendable, it puts the 
	 * STARTNEWGAME message in the output buffer
	 */
	public void onStartGameButtonCLick(){
		this.startGame.setDisable(true);
		this.logout.setDisable(true);
		String mess=new String();
		if(this.simpleRules.isSelected())
			mess="STARTNEWGAME"+";0";
		else if(this.advancedRules.isSelected())
			mess="STARTNEWGAME"+";1";
		String app=this.numberOfPlayers.getText();
		try{
			int number=Integer.parseInt(app);
			if(number>=2 && number<=5){
				mess+=";"+number;
			}
			else
				mess+=";5";
		}catch(NumberFormatException e){
			mess+=";5";
		}
		this.controller.putInOutput(mess);
		message.setText("Waiting for other players");
		
	}
	
	/**activated when the SEPUTMENU message is received
	 */
	public void setupMenu(){
		this.startGame.setDisable(false);
		this.logout.setDisable(false);
	}
	
	public void onShowScoreboardClick(){
		String f=this.from.getText();
		String t=this.to.getText();
		String nick=this.nickname.getText();
		if(!("").equals(nick) && !(" ").equals(nick)){
			this.controller.putInOutput("SHOWRECORD;"+nick+"; ");
			return;
		}
		if(!("").equals(f) && !("").equals(t)){
			int da;
			int a;
			try{
				da=Integer.parseInt(f);
				a=Integer.parseInt(t);
				this.controller.putInOutput("SHOWSCOREBOARD;"+da+";"+a);
			}catch(NumberFormatException e){
				//NOTHING
			}
		}
	}
	public void onLogoutClick(){
		this.controller.resetTriesLogin();
		this.controller.putInOutput("LOGOUTREQUEST; ; ");
		
	}
}
