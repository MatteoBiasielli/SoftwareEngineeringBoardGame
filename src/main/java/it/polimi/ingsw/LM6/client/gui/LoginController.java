package it.polimi.ingsw.LM6.client.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private GUIMainController controller;
	@FXML
	private Button loginButton;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	/** attribute used to handle operations on the login screen*/
	private FXMLLoader invalidLoginLoader;
	/** attribute used to handle operations on the login screen*/
	private Stage invalidLoginStage;
	private int tries;
	
	public LoginController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	
	@FXML
	public void initialize(){
		this.loginButton.setDisable(true);
		tries=0;
		this.initializeInvalidLoginStage();
	}
	public void setController(GUIMainController gmc){
		this.controller=gmc;
	}
	public void resetTries(){
		tries=0;
	}
	/**activated when the login button is pressed. If all data are correct and sendable, it puts the 
	 * LOGINREQUEST message in the output buffer
	 */
	public void onLoginButtonClick(){
		this.invalidLoginStage.hide();
		tries++;
		String u=username.getText();
		String p=password.getText();
		if(u!=null && !("").equals(u) && p!=null && !("").equals(p)){
			loginButton.setDisable(true);
			this.controller.putInOutput("LOGINREQUEST;"+u+";"+p);
		}
	}
	/**initializes the startingStage and the invalidLoginLoader
	 * 
	 */
	private void initializeInvalidLoginStage() {
		this.invalidLoginStage=new Stage();
		this.invalidLoginLoader=new FXMLLoader();
		this.invalidLoginLoader.setLocation(getClass().getResource("InvalidLogin.fxml"));
		try {
			this.invalidLoginStage.setScene(new Scene(invalidLoginLoader.load()));
		} catch (IOException e) {
			Logger.getLogger("MyLogger").log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		this.invalidLoginStage.setResizable(false);
		
	}
	/**activated when the INVALIDLOGIN message is received
	 */
	public void invalidLogin() {
		this.loginButton.setDisable(false);
		if(tries>0){
			this.invalidLoginStage.show();
			resetTries();
		}
		
	}
	
	
}
