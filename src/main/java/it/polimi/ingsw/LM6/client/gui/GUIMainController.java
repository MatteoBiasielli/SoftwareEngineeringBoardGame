package it.polimi.ingsw.LM6.client.gui;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.LM6.client.exceptions.OutputNotNecessaryException;
import it.polimi.ingsw.LM6.client.exceptions.TerminatedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMainController extends Application {
	/**output buffer*/
	private Vector<String> output;
	/**input buffer*/
	private Vector<String> input;
	private boolean isTerminated;
	
	/** attribute used to handle operations on the login screen*/
	private FXMLLoader loginLoader;
	/** attribute used to handle operations on the login screen*/
	private Stage loginStage;
	/** attribute used to handle operations on the starting screen*/
	private FXMLLoader startingLoader;
	/** attribute used to handle operations on the starting screen*/
	private Stage startingStage;
	/**  attribute used to handle operations on the board*/
	private FXMLLoader boardLoader;
	/**  attribute used to handle operations on the board*/
	private Stage boardStage;
	/**  attribute used to handle operations on the scoreboard screen*/
	private FXMLLoader scoreboardLoader;
	/**  attribute used to handle operations on the scoreboard screen*/
	private Stage scoreboardStage;
	/**  attribute used to handle operations on the scoreboard screen*/
	private FXMLLoader scoreboardLoader2;
	/**  attribute used to handle operations on the scoreboard screen*/
	private Stage scoreboardStage2;
	
	@Override
	public void start(Stage primaryStage) {
		this.output=new Vector<>();
		this.input=new Vector<>();
		this.isTerminated=false;
		this.loginStage=primaryStage;
		this.initializeLoginStage();
		this.initializeStartingStage();
		
		this.initializeScoreboardStage();
		this.initializeBoardStage();
		this.loginStage.show();
		this.initializeScoreboardStage2();
		GUIThread.setMainController(this);
	}
	@Override
	public void stop(){
		this.isTerminated=true;
	}
	/**initializes the startingStage and the startingLoader
	 * 
	 */
	private void initializeStartingStage() {
		this.startingStage=new Stage();
		this.startingLoader=new FXMLLoader();
		this.startingLoader.setLocation(getClass().getResource("StartingStage.fxml"));
		try {
			this.startingStage.setScene(new Scene(startingLoader.load()));
		} catch (IOException e) {
			Logger.getLogger("it.polimi.ingsw.LM6.client.gui.GUIMainController").log(Level.SEVERE, "CannotInitializeStartingStage");
		}
		this.startingStage.setResizable(false);
		((StartingStageController)this.startingLoader.getController()).setController(this);
		
	}
	/**initializes the loginStage and the loginLoader
	 * 
	 */
	private void initializeLoginStage(){
		this.loginLoader= new FXMLLoader();
		this.loginLoader.setLocation(getClass().getResource("Login.fxml"));
		try {
			this.loginStage.setScene(new Scene(loginLoader.load()));
		} catch (IOException e) {
			Logger.getLogger("it.polimi.ingsw.LM6.client.gui.GUIMainController").log(Level.SEVERE, "CannotInitializeLoginStage");
		}
		this.loginStage.setResizable(false);
		((LoginController)this.loginLoader.getController()).setController(this);
	}
	
	/**initializes the boardStage and the boardLoader
	 * 
	 */
	private void initializeBoardStage(){
		this.boardStage=new Stage();
		this.boardLoader= new FXMLLoader();
		this.boardLoader.setLocation(getClass().getResource("Board.fxml"));
		try {
			this.boardStage.setScene(new Scene(boardLoader.load()));
		} catch (IOException e) {
			Logger.getLogger("it.polimi.ingsw.LM6.client.gui.GUIMainController").log(Level.SEVERE, "CannotInitializeBoardStage");
		}
		((BoardController)this.boardLoader.getController()).setController(this);
	}
	
	/**initializes the scoreboardStage and the scoreboardLoader
	 * 
	 */
	private void initializeScoreboardStage(){
		this.scoreboardStage=new Stage();
		this.scoreboardLoader= new FXMLLoader();
		this.scoreboardLoader.setLocation(getClass().getResource("Scoreboard.fxml"));
		try {
			this.scoreboardStage.setScene(new Scene(scoreboardLoader.load()));
		} catch (IOException e) {
			Logger.getLogger("it.polimi.ingsw.LM6.client.gui.GUIMainController").log(Level.SEVERE, "CannotInitializeScoreboardStage");
		}
		this.scoreboardStage.setResizable(false);
		((ScoreboardController)this.scoreboardLoader.getController()).setController(this);
	}
	private void initializeScoreboardStage2(){
		this.scoreboardStage2=new Stage();
		this.scoreboardLoader2= new FXMLLoader();
		this.scoreboardLoader2.setLocation(getClass().getResource("Scoreboard.fxml"));
		try {
			this.scoreboardStage2.setScene(new Scene(scoreboardLoader2.load()));
		} catch (IOException e) {
			Logger.getLogger("it.polimi.ingsw.LM6.client.gui.GUIMainController").log(Level.SEVERE, "CannotInitializeScoreboardStage2");
		}
		this.scoreboardStage2.setResizable(false);
		((ScoreboardController)this.scoreboardLoader2.getController()).setController(this);
	}
	
	
	public static void startGUI() {
		launch();
	}
	
	
	
	public Vector<String> getInput(){
		return this.input;
	}
	
	
	/** reads a String from the output buffer of the GUI
	 * 
	 * @return the String that has to be sent to the server as answer to what was received
	 * @throws OutputNotNecessaryException is nothing has to be sent to the server
	 */
	public String readFromOutput() throws OutputNotNecessaryException{
		while(output.isEmpty())
			if(isTerminated)
				throw new TerminatedException("The GUI has been closed.");
		String s=output.get(0);
		output.remove(0);
		if(("FAKE").equals(s))
			throw new OutputNotNecessaryException(" ");
		return s;
	}
	
	/**puts the string given as parameter in the output buffer
	 * 
	 * @param s - the message
	 */
	public void putInOutput(String s){
		output.add(s);
	}
	
	/**puts the string given as parameter in the input buffer
	 * 
	 * @param s - the message
	 */
	public void putInInput(String s){
		input.add(s);
	}

	
	/** handles the message given as parameter, calling the right method in the controller
	 * 
	 * @param app - the message
	 */
	public void solveMessage(String app) {
		String[] messSplit=app.split(";");
		if(("INVALIDLOGIN").equals(messSplit[0]))
			this.invalidLogin();
		else if(("SETUPMENU").equals(messSplit[0])){
			this.setupMenu();
			try{
				if(!(" ").equals(messSplit[2]))
					this.showScoreboard(messSplit[2]);
			}catch(ArrayIndexOutOfBoundsException e){
				//DO NOTHING
			}
		}
		else if(("INFO").equals(messSplit[0])){
			this.putInOutput("FAKE");
			this.info(app);
		}
		else if(("MENU").equals(messSplit[0]))
			this.printMenu(app);
		else if(("BOARD").equals(messSplit[0])||("UPDATEBOARD").equals(messSplit[0])){
			this.putInOutput("FAKE");
			this.updateBoard(app);
		}
		else if(("PLAYERS").equals(messSplit[0])||("UPDATEPLAYERS").equals(messSplit[0])){
			this.putInOutput("FAKE");
			this.updatePlayers(app);
		}
		else if(("BONUSCOUNCILREQUEST").equals(messSplit[0])){
			if(("1").equals(messSplit[2]))
				this.singleCouncilBonus();
			else if(("2").equals(messSplit[2]))
				this.doubleCouncilBonus();
			else if(("3").equals(messSplit[2]))
				this.tripleCouncilBonus();
		}
		else if(("EXCOMMCHOICEREQUEST").equals(messSplit[0])){
			this.excommunicationRequest();
		}
		else if(("BONUSHARVESTREQUEST").equals(messSplit[0])){
			this.bonusHarvestRequest();
		}
		else if(("BONUSPRODUCTIONREQUEST").equals(messSplit[0])){
			this.bonusProductionRequest();
		}
		else if(("BONUSACTIONREQUEST").equals(messSplit[0])){
			this.bonusActionRequest(messSplit);
		}
		else if(("COSTCHOICEREQUEST").equals(messSplit[0])){
			this.costChoiceRequest(messSplit[1]);
		}
		else if(("TILESDRAFT").equals(messSplit[0])){
			this.tilesDraft(messSplit);
		}
		else if(("LEADERDRAFT").equals(messSplit[0])){
			this.leaderDraft(messSplit);
		}
		else if(("LORENZOILMAGNIFICO").equals(messSplit[0])){
			this.lorenzoIlMagnifico(messSplit);
		}
		else if(("FAMOFFERREQUEST").equals(messSplit[0])){
			this.familiarOfferRequest();
		}
		else if(("ENDGAME").equals(messSplit[0])){
			this.showScoreboard(messSplit[5]);
			this.showScoreboard2(messSplit[4]);
			this.boardStage.hide();
		}
	}
	

	
	//*************************************************************************************+
	// MESSAGES
	
	
	
	/** called when a message with SETUPMENU header is received
	 * 
	 */
	private void setupMenu() {
		this.startingStage.show();
		this.resetTriesLogin();
		this.loginStage.hide();
		((StartingStageController)this.startingLoader.getController()).setupMenu();
	}
	/** called when a message with EXCOMMCHOICEREQUEST header is received
	 * 
	 */
	private void excommunicationRequest() {
		((BoardController)this.boardLoader.getController()).excommunicationRequest();
		
	}
	/** called when a message with INVALIDLOGIN header is received
	 * 
	 */
	private void invalidLogin(){
		this.loginStage.show();
		this.startingStage.hide();
		((LoginController)this.loginLoader.getController()).invalidLogin();
	}
	
	/** called when a message with INFO header is received
	 * 
	 */
	private void info(String app){
		String[] messSplit=app.split(";");
		((BoardController)this.boardLoader.getController()).addUpdate(messSplit[1]);
		if(messSplit.length>2){
			if(("GAMESTART").equals(messSplit[2])){
				this.boardStage.show();
				this.startingStage.hide();
			}
			else if (("DICE").equals(messSplit[2]))
				((BoardController)this.boardLoader.getController()).updateDice(messSplit);
		}
	}
	
	/** called when a message with MENU header is received
	 * 
	 */
	private void printMenu(String a){
		String[] messSplit=a.split(";");
		((BoardController)this.boardLoader.getController()).addUpdate(messSplit[1]);
		((BoardController)this.boardLoader.getController()).menuHandler(a);
	}
	
	/** called when a message with BOARD or UPDATEBOARD header is received
	 * 
	 */
	private void updateBoard(String a){
		((BoardController)this.boardLoader.getController()).updateBoard(a);
	}
	/** called when a message with BONUSCOUNCILREQUEST header is received and the 3rd field indicates that it's a single bonus
	 * 
	 */
	private void singleCouncilBonus(){
		((BoardController)this.boardLoader.getController()).singleCouncilBonus();
	}
	/** called when a message with BONUSCOUNCILREQUEST header is received and the 3rd field indicates that it's a double bonus
	 * 
	 */
	private void doubleCouncilBonus() {
		((BoardController)this.boardLoader.getController()).doubleCouncilBonus();
	}
	/** called when a message with BONUSCOUNCILREQUEST header is received and the 3rd field indicates that it's a triple bonus
	 * 
	 */
	private void tripleCouncilBonus() {
		((BoardController)this.boardLoader.getController()).tripleCouncilBonus();
		
	}
	/** called when a message with BONUSHARVESTREQUEST header is received 
	 * 
	 */
	private void bonusHarvestRequest() {
		((BoardController)this.boardLoader.getController()).bonusHarvestRequest();
	}
	/** called when a message with BONUSPRODUCTIONREQUEST header is received 
	 * 
	 */
	private void bonusProductionRequest() {
		((BoardController)this.boardLoader.getController()).bonusProductionRequest();
		
	}
	
	/** called when a message with BONUSTOWERACTION header is received 
	 * 
	 */
	private void bonusActionRequest(String[] messSplit) {
		((BoardController)this.boardLoader.getController()).addUpdate(messSplit[1]);
		((BoardController)this.boardLoader.getController()).bonusActionRequest(messSplit);
	}
	/** called when a message with COSTCHOICEREQUEST header is received 
	 * 
	 */
	private void costChoiceRequest(String s) {
		((BoardController)this.boardLoader.getController()).costChoice(s);
	}
	
	/** called when a message with PLAYERS header is received 
	 * 
	 */
	private void updatePlayers(String app) {
		((BoardController)this.boardLoader.getController()).updatePlayers(app);
		
	}
	/** called when a message with TILESDRAFT header is received 
	 * 
	 */
	private void tilesDraft(String[] messSplit) {
		((BoardController)this.boardLoader.getController()).tilesDraft(messSplit);	
	}
	/** called when a message with LEADERDRAFT header is received 
	 * 
	 */
	private void leaderDraft(String[] messSplit) {
		((BoardController)this.boardLoader.getController()).addUpdate(messSplit[1]);
		((BoardController)this.boardLoader.getController()).leaderDraft(messSplit);	
	}
	
	/** called when a message with LORENZOILMAGNIFICO header is received 
	 * 
	 */
	private void lorenzoIlMagnifico(String[] messSplit) {
		((BoardController)this.boardLoader.getController()).addUpdate(messSplit[1]);
		((BoardController)this.boardLoader.getController()).lorenzoIlMagnifico(messSplit);	
	}
	/** called when a message with SETUPMENU header that contains a scoreboard or a record is received 
	 * 
	 */
	private void showScoreboard(String mess){
		((ScoreboardController)this.scoreboardLoader.getController()).reset();
		((ScoreboardController)this.scoreboardLoader.getController()).setTitle("Global Scoreboard");
		((ScoreboardController)this.scoreboardLoader.getController()).setRecords(mess);
		this.scoreboardStage.show();
	}
	/** called when a message with ENDGAME header that contains a scoreboard or a record is received 
	 * 
	 */
	private void showScoreboard2(String mess){
		((ScoreboardController)this.scoreboardLoader2.getController()).reset();
		((ScoreboardController)this.scoreboardLoader2.getController()).setTitle("Local Scoreboard");
		((ScoreboardController)this.scoreboardLoader2.getController()).setRecords(mess);
		this.scoreboardStage2.show();
	}
	
	/** called when a message with LORENZOILMAGNIFICO header is received 
	 * 
	 */
	private void familiarOfferRequest() {
		((BoardController)this.boardLoader.getController()).familiarOfferPhase();	
	}
	public void resetTriesLogin() {
		((LoginController)this.loginLoader.getController()).resetTries();	
	}
}
