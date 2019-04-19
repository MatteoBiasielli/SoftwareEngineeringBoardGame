package it.polimi.ingsw.LM6.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BoardController {
	static Logger log;
	
	/** GUI main controller*/
	private GUIMainController controller;
	/**offsets for faith track*/
	private int[] offsets={0,33,67,109,158,211,259,293,324,358,392,426,460,494,528,562};
	private int[] base={790,795,805};
	/**offsets for the military track*/
	private int offsetMilitary=22;
	private int[] baseMilitary={567,572,574};
	/**ArrayList containing the list of players*/
	private ArrayList<String> players;
	/**ArrayList containing the list of players' colors, in the same position of the corresponding player*/
	private ArrayList<String> colors;
	/**ArrayList containing the list of the player's leader cards' numbers*/
	private ArrayList<Integer> leaders;
	/**player's number of yellow cards*/
	private Integer yellowCards=0;
	/** attribute used to handle operations on the tower action screen*/
	private FXMLLoader towerLoader;
	/** attribute used to handle operations on the tower action screen*/
	private Stage towerStage;
	
	/** attribute used to handle operations on the council action screen*/
	private FXMLLoader councilLoader;
	/** attribute used to handle operations on the council action screen*/
	private Stage councilStage;
	
	/** attribute used to handle operations on the single council bonus screen*/
	private FXMLLoader singleCouncilLoader;
	/** attribute used to handle operations on the single council bonus screen*/
	private Stage singleCouncilStage;
	
	/** attribute used to handle operations on the market action screen*/
	private FXMLLoader marketLoader;
	/** attribute used to handle operations on the market action screen*/
	private Stage marketStage;
	
	/** attribute used to handle operations on the harvest action screen*/
	private FXMLLoader harvestLoader;
	/** attribute used to handle operations on the harvest action screen*/
	private Stage harvestStage;
	
	/** attribute used to handle operations on the production action screen*/
	private FXMLLoader productionLoader;
	/** attribute used to handle operations on the production action screen*/
	private Stage productionStage;
	
	/** attribute used to handle operations on the double council bonus screen*/
	private FXMLLoader doubleCouncilLoader;
	/** attribute used to handle operations on the double council bonus screen*/
	private Stage doubleCouncilStage;
	
	/** attribute used to handle operations on the triple council bonus screen*/
	private FXMLLoader tripleCouncilLoader;
	/** attribute used to handle operations on the triple council bonus screen*/
	private Stage tripleCouncilStage;
	
	/** attribute used to handle operations on the excommunication screen*/
	private FXMLLoader excommLoader;
	/** attribute used to handle operations on the excommunication screen*/
	private Stage excommStage;
	
	/** attribute used to handle operations on the cost choice screen*/
	private FXMLLoader choiceLoader;
	/** attribute used to handle operations on the cost choice screen*/
	private Stage choiceStage;
	
	/** attribute used to handle operations on the player screen*/
	private FXMLLoader player1Loader;
	/** attribute used to handle operations on the player screen*/
	private Stage player1Stage;
	/** attribute used to handle operations on the player screen*/
	private FXMLLoader player2Loader;
	/** attribute used to handle operations on the player screen*/
	private Stage player2Stage;
	/** attribute used to handle operations on the player screen*/
	private FXMLLoader player3Loader;
	/** attribute used to handle operations on the player screen*/
	private Stage player3Stage;
	/** attribute used to handle operations on the player screen*/
	private FXMLLoader player4Loader;
	/** attribute used to handle operations on the player screen*/
	private Stage player4Stage;
	/** attribute used to handle operations on the player screen*/
	private FXMLLoader player5Loader;
	/** attribute used to handle operations on the player screen*/
	private Stage player5Stage;
	/** attribute used to handle operations on the tiles choice screen*/
	private FXMLLoader tileLoader;
	/** attribute used to handle operations on the tiles choice screen*/
	private Stage tileStage;
	/** attribute used to handle operations on the leaders choice screen*/
	private FXMLLoader leaderLoader;
	/** attribute used to handle operations on the leaders choice screen*/
	private Stage leaderStage;
	/** attribute used to handle operations on the leaders play/discard screen*/
	private FXMLLoader playDiscardLoader;
	/** attribute used to handle operations on the leaders play/discard screen*/
	private Stage playDiscardStage;
	/** attribute used to handle operations on the leaders sixFamiliar screen*/
	private FXMLLoader leaderSixLoader;
	/** attribute used to handle operations on the leaders sixFamiliar screen*/
	private Stage leaderSixStage;
	/** attribute used to handle operations on the lorenzo il magnifico screen*/
	private FXMLLoader lorenzoLoader;
	/** attribute used to handle operations on the lorenzo il magnificor screen*/
	private Stage lorenzoStage;
	/** attribute used to handle operations on the offer screen*/
	private FXMLLoader offerLoader;
	/** attribute used to handle operations on the offer screen*/
	private Stage offerStage;
	
	@FXML
	private TextArea textUpdates;
	@FXML
	private Button towerButton;
	@FXML
	private Button productionButton;
	@FXML
	private Button harvestButton;
	@FXML
	private Button councilButton;
	@FXML
	private Button marketButton;
	@FXML
	private Button playLeaderButton;
	@FXML
	private Button discardLeaderButton;
	@FXML
	private Button leaderHarvestButton;
	@FXML
	private Button leaderProductionButton;
	@FXML
	private Button leaderSixButton;
	@FXML
	private Button leaderResourcesButton;
	@FXML
	private Button passButton;
	@FXML
	private Button updateBoardButton;
	@FXML
	private Button updatePlayersButton;
	@FXML
	private ImageView gc3;
	@FXML
	private ImageView gc2;
	@FXML
	private ImageView gc1;
	@FXML
	private ImageView gc0;
	@FXML
	private ImageView yc3;
	@FXML
	private ImageView yc2;
	@FXML
	private ImageView yc1;
	@FXML
	private ImageView yc0;
	@FXML
	private ImageView pc3;
	@FXML
	private ImageView pc2;
	@FXML
	private ImageView pc1;
	@FXML
	private ImageView pc0;
	@FXML
	private ImageView bc3;
	@FXML
	private ImageView bc2;
	@FXML
	private ImageView bc1;
	@FXML
	private ImageView bc0;
	@FXML
	private ImageView fgc3;
	@FXML
	private ImageView fgc2;
	@FXML
	private ImageView fgc1;
	@FXML
	private ImageView fgc0;
	@FXML
	private ImageView fyc3;
	@FXML
	private ImageView fyc2;
	@FXML
	private ImageView fyc1;
	@FXML
	private ImageView fyc0;
	@FXML
	private ImageView fpc3;
	@FXML
	private ImageView fpc2;
	@FXML
	private ImageView fpc1;
	@FXML
	private ImageView fpc0;
	@FXML
	private ImageView fbc3;
	@FXML
	private ImageView fbc2;
	@FXML
	private ImageView fbc1;
	@FXML
	private ImageView fbc0;
	@FXML
	private ImageView council0;
	@FXML
	private ImageView council1;
	@FXML
	private ImageView council2;
	@FXML
	private ImageView council3;
	@FXML
	private ImageView council4;
	@FXML
	private ImageView market0;
	@FXML
	private ImageView market1;
	@FXML
	private ImageView market2;
	@FXML
	private ImageView market3;
	@FXML
	private ImageView excomm1;
	@FXML
	private ImageView excomm2;
	@FXML
	private ImageView excomm3;
	@FXML
	private ImageView turn0;
	@FXML
	private ImageView turn1;
	@FXML
	private ImageView turn2;
	@FXML
	private ImageView turn3;
	@FXML
	private ImageView turn4;
	@FXML
	private ImageView hm0;
	@FXML
	private ImageView hm1;
	@FXML
	private ImageView hm2;
	@FXML
	private ImageView pm0;
	@FXML
	private ImageView pm1;
	@FXML
	private ImageView pm2;
	@FXML
	private ImageView hs0;
	@FXML
	private ImageView hs1;
	@FXML
	private ImageView hs2;
	@FXML
	private ImageView hs3;
	@FXML
	private ImageView hs4;
	@FXML
	private ImageView hs5;
	@FXML
	private ImageView hs6;
	@FXML
	private ImageView hs7;
	@FXML
	private ImageView hs8;
	@FXML
	private ImageView hs9;
	@FXML
	private ImageView ps0;
	@FXML
	private ImageView ps1;
	@FXML
	private ImageView ps2;
	@FXML
	private ImageView ps3;
	@FXML
	private ImageView ps4;
	@FXML
	private ImageView ps5;
	@FXML
	private ImageView ps6;
	@FXML
	private ImageView ps7;
	@FXML
	private ImageView ps8;
	@FXML
	private ImageView ps9;
	@FXML
	private Button player1;
	@FXML
	private Button player2;
	@FXML
	private Button player3;
	@FXML
	private Button player4;
	@FXML
	private Button player5;
	@FXML
	private Label player1nick;
	@FXML
	private Label player2nick;
	@FXML
	private Label player3nick;
	@FXML
	private Label player4nick;
	@FXML
	private Label player5nick;
	@FXML
	private ImageView diceO;
	@FXML
	private ImageView diceB;
	@FXML
	private ImageView diceW;
	@FXML
	private ImageView pf1;
	@FXML
	private ImageView pf2;
	@FXML
	private ImageView pf3;
	@FXML
	private ImageView pf4;
	@FXML
	private ImageView pf5;
	@FXML
	private ImageView ctileH;
	@FXML
	private ImageView ctileP;
	@FXML
	private ImageView ctileM1;
	@FXML
	private ImageView ctileM2;
	@FXML
	private ImageView ex11;
	@FXML
	private ImageView ex12;
	@FXML
	private ImageView ex13;
	@FXML
	private ImageView ex14;
	@FXML
	private ImageView ex15;
	@FXML
	private ImageView ex21;
	@FXML
	private ImageView ex22;
	@FXML
	private ImageView ex23;
	@FXML
	private ImageView ex24;
	@FXML
	private ImageView ex25;
	@FXML
	private ImageView ex31;
	@FXML
	private ImageView ex32;
	@FXML
	private ImageView ex33;
	@FXML
	private ImageView ex34;
	@FXML
	private ImageView ex35;
	@FXML
	private ImageView mil1;
	@FXML
	private ImageView mil2;
	@FXML
	private ImageView mil3;
	@FXML
	private ImageView mil4;
	@FXML
	private ImageView mil5;
	public BoardController(){
		
	}
	public void setController(GUIMainController gmc){
		this.controller=gmc;
	}
	
	@FXML
	private void initialize(){
		log.getLogger("it.polimi.ingsw.LM6.client.gui.BoardController");
		textUpdates.setDisable(true);
		textUpdates.setOpacity(1);
		this.disableAllButtons();
		this.initializeTowerStage();
		this.initializeCouncilStage();
		this.initializeSingleCouncilStage();
		this.initializeMarketStage();
		this.initializeDoubleCouncilStage();
		this.initializeTripleCouncilStage();
		this.initializeHarvestStage();
		this.initializeProductionStage();
		this.initializeExcommStage();
		this.initializeChoiceStage();
		this.initializePlayDiscardLeaderStage();
		this.initializePlayer2Stage();
		this.initializePlayer3Stage();
		this.initializePlayer4Stage();
		this.initializePlayer5Stage();
		this.initializePlayer1Stage();
		this.initializeTileStage();
		this.initializeLeaderStage();
		this.initializeLeaderSixStage();
		this.initializeLorenzoStage();
		this.initializeOfferStage();
	}
	
	/**initializes the FXMLloader and the stage of the player1
	 * 
	 */
	private void initializePlayer1Stage() {
		player1Stage=new Stage();
		player1Loader=new FXMLLoader();
		player1Loader.setLocation(getClass().getResource("Player.fxml"));
		try {
			player1Stage.setScene(new Scene(player1Loader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		player1Stage.setResizable(false);
		
		
	}
	/**initializes the FXMLloader and the stage of the player2
	 * 
	 */
	private void initializePlayer2Stage() {
		player2Stage=new Stage();
		player2Loader=new FXMLLoader();
		player2Loader.setLocation(getClass().getResource("Player.fxml"));
		try {
			player2Stage.setScene(new Scene(player2Loader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		player2Stage.setResizable(false);
		
		
	}
	/**initializes the FXMLloader and the stage of the player3
	 * 
	 */
	private void initializePlayer3Stage() {
		player3Stage=new Stage();
		player3Loader=new FXMLLoader();
		player3Loader.setLocation(getClass().getResource("Player.fxml"));
		try {
			player3Stage.setScene(new Scene(player3Loader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		player3Stage.setResizable(false);
		
		
	}
	/**initializes the FXMLloader and the stage of the player4
	 * 
	 */
	private void initializePlayer4Stage() {
		player4Stage=new Stage();
		player4Loader=new FXMLLoader();
		player4Loader.setLocation(getClass().getResource("Player.fxml"));
		try {
			player4Stage.setScene(new Scene(player4Loader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		player4Stage.setResizable(false);
		
		
	}
	/**initializes the FXMLloader and the stage of the player5
	 * 
	 */
	private void initializePlayer5Stage() {
		player5Stage=new Stage();
		player5Loader=new FXMLLoader();
		player5Loader.setLocation(getClass().getResource("Player.fxml"));
		try {
			player5Stage.setScene(new Scene(player5Loader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		player5Stage.setResizable(false);
		
		
	}
	/**initializes the towerStage and the towerLoader
	 * 
	 */
	private void initializeTowerStage() {
		this.towerStage=new Stage();
		this.towerLoader=new FXMLLoader();
		this.towerLoader.setLocation(getClass().getResource("TowerAction.fxml"));
		try {
			this.towerStage.setScene(new Scene(towerLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((TowerActionController)this.towerLoader.getController()).setStage(this.towerStage);
		this.towerStage.setOnCloseRequest(e-> e.consume());
		this.towerStage.setResizable(false);
		
		
	}
	
	/**initializes the leaderStage and the leaderLoader
	 * 
	 */
	private void initializeLeaderStage() {
		this.leaderStage=new Stage();
		this.leaderLoader=new FXMLLoader();
		this.leaderLoader.setLocation(getClass().getResource("LeaderChoice.fxml"));
		try {
			this.leaderStage.setScene(new Scene(leaderLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((LeaderChoiceController)this.leaderLoader.getController()).setStage(this.leaderStage);
		this.leaderStage.setOnCloseRequest(e-> e.consume());
		this.leaderStage.setResizable(false);
		
		
	}
	/**initializes the tileStage and the tileLoader
	 * 
	 */
	private void initializeTileStage() {
		this.tileStage=new Stage();
		this.tileLoader=new FXMLLoader();
		this.tileLoader.setLocation(getClass().getResource("TilesChoice.fxml"));
		try {
			this.tileStage.setScene(new Scene(tileLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((TileChoiceController)this.tileLoader.getController()).setStage(this.tileStage);
		this.tileStage.setOnCloseRequest(e-> e.consume());
		this.tileStage.setResizable(false);
		
		
	}
	
	/**initializes the councilStage and the councilLoader
	 * 
	 */
	private void initializeCouncilStage() {
		this.councilStage=new Stage();
		this.councilLoader=new FXMLLoader();
		this.councilLoader.setLocation(getClass().getResource("CouncilAction.fxml"));
		try {
			this.councilStage.setScene(new Scene(councilLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((CouncilActionController)this.councilLoader.getController()).setStage(this.councilStage);
		this.councilStage.setOnCloseRequest(e-> e.consume());
		this.councilStage.setResizable(false);
		
		
	}
	
	/**initializes the singleCouncilStage and the singleCouncilLoader
	 * 
	 */
	private void initializeSingleCouncilStage() {
		this.singleCouncilStage=new Stage();
		this.singleCouncilLoader=new FXMLLoader();
		this.singleCouncilLoader.setLocation(getClass().getResource("SingleCouncilBonus.fxml"));
		try {
			this.singleCouncilStage.setScene(new Scene(singleCouncilLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((SingleCouncilBonusController)this.singleCouncilLoader.getController()).setStage(this.singleCouncilStage);
		this.singleCouncilStage.setOnCloseRequest(e-> e.consume());
		this.singleCouncilStage.setResizable(false);
		
		
	}
	
	/**initializes the doubleCouncilStage and the doubleCouncilLoader
	 * 
	 */
	private void initializeDoubleCouncilStage() {
		this.doubleCouncilStage=new Stage();
		this.doubleCouncilLoader=new FXMLLoader();
		this.doubleCouncilLoader.setLocation(getClass().getResource("DoubleCouncilBonus.fxml"));
		try {
			this.doubleCouncilStage.setScene(new Scene(doubleCouncilLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((DoubleCouncilBonusController)this.doubleCouncilLoader.getController()).setStage(this.doubleCouncilStage);
		this.doubleCouncilStage.setOnCloseRequest(e-> e.consume());
		this.doubleCouncilStage.setResizable(false);
		
		
	}
	
	/**initializes the tripleCouncilStage and the tripleCouncilLoader
	 * 
	 */
	private void initializeTripleCouncilStage() {
		this.tripleCouncilStage=new Stage();
		this.tripleCouncilLoader=new FXMLLoader();
		this.tripleCouncilLoader.setLocation(getClass().getResource("TripleCouncilBonus.fxml"));
		try {
			this.tripleCouncilStage.setScene(new Scene(tripleCouncilLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((TripleCouncilBonusController)this.tripleCouncilLoader.getController()).setStage(this.tripleCouncilStage);
		this.tripleCouncilStage.setOnCloseRequest(e-> e.consume());
		this.tripleCouncilStage.setResizable(false);
		
		
	}
	/**initializes the marketStage and the marketLoader
	 * 
	 */
	private void initializeMarketStage() {
		this.marketStage=new Stage();
		this.marketLoader=new FXMLLoader();
		this.marketLoader.setLocation(getClass().getResource("MarketAction.fxml"));
		try {
			this.marketStage.setScene(new Scene(marketLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((MarketActionController)this.marketLoader.getController()).setStage(this.marketStage);
		this.marketStage.setOnCloseRequest(e-> e.consume());
		this.marketStage.setResizable(false);
		
		
	}
	
	/**initializes the harvestStage and the harvestLoader
	 * 
	 */
	private void initializeHarvestStage() {
		this.harvestStage=new Stage();
		this.harvestLoader=new FXMLLoader();
		this.harvestLoader.setLocation(getClass().getResource("HarvestAction.fxml"));
		try {
			this.harvestStage.setScene(new Scene(harvestLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((HarvestActionController)this.harvestLoader.getController()).setStage(this.harvestStage);
		this.harvestStage.setOnCloseRequest(e-> e.consume());
		this.harvestStage.setResizable(false);
		
		
	}
	
	/**initializes the productionStage and the productionLoader
	 * 
	 */
	private void initializeProductionStage() {
		this.productionStage=new Stage();
		this.productionLoader=new FXMLLoader();
		this.productionLoader.setLocation(getClass().getResource("ProductionAction.fxml"));
		try {
			this.productionStage.setScene(new Scene(productionLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((ProductionActionController)this.productionLoader.getController()).setStage(this.productionStage);
		this.productionStage.setOnCloseRequest(e-> e.consume());
		this.productionStage.setResizable(false);
		
		
	}
	
	/**initializes the excommStage and the excommLoader
	 * 
	 */
	private void initializeExcommStage() {
		this.excommStage=new Stage();
		this.excommLoader=new FXMLLoader();
		this.excommLoader.setLocation(getClass().getResource("ExcommunicationAction.fxml"));
		try {
			this.excommStage.setScene(new Scene(excommLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((ExcommunicationController)this.excommLoader.getController()).setStage(this.excommStage);
		this.excommStage.setOnCloseRequest(e-> e.consume());
		this.excommStage.setResizable(false);
		
		
	}
	/**initializes the offerStage and the offerLoader
	 * 
	 */
	private void initializeOfferStage() {
		this.offerStage=new Stage();
		this.offerLoader=new FXMLLoader();
		this.offerLoader.setLocation(getClass().getResource("OfferAction.fxml"));
		try {
			this.offerStage.setScene(new Scene(offerLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((OfferController)this.offerLoader.getController()).setStage(this.offerStage);
		this.offerStage.setOnCloseRequest(e-> e.consume());
		this.offerStage.setResizable(false);
		
		
	}
	
	/**initializes the choiceStage and the choiceLoader
	 * 
	 */
	private void initializeChoiceStage() {
		this.choiceStage=new Stage();
		this.choiceLoader=new FXMLLoader();
		this.choiceLoader.setLocation(getClass().getResource("CostChoice.fxml"));
		try {
			this.choiceStage.setScene(new Scene(choiceLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((CostChoiceController)this.choiceLoader.getController()).setStage(this.choiceStage);
		this.choiceStage.setOnCloseRequest(e-> e.consume());
		this.choiceStage.setResizable(false);
		
		
	}
	
	/**initializes the playDiscardStage and the playDiscardLoader
	 * 
	 */
	private void initializePlayDiscardLeaderStage() {
		this.playDiscardStage=new Stage();
		this.playDiscardLoader=new FXMLLoader();
		this.playDiscardLoader.setLocation(getClass().getResource("PlayDiscardLeader.fxml"));
		try {
			this.playDiscardStage.setScene(new Scene(playDiscardLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setStage(this.playDiscardStage);
		this.playDiscardStage.setOnCloseRequest(e-> e.consume());
		this.playDiscardStage.setResizable(false);
		
		
	}
	
	/**initializes the leaderSixStage and the leaderSixLoader
	 * 
	 */
	private void initializeLeaderSixStage() {
		this.leaderSixStage=new Stage();
		this.leaderSixLoader=new FXMLLoader();
		this.leaderSixLoader.setLocation(getClass().getResource("LeaderSixFamiliar.fxml"));
		try {
			this.leaderSixStage.setScene(new Scene(leaderSixLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((LeaderSixController)this.leaderSixLoader.getController()).setStage(this.leaderSixStage);
		this.leaderSixStage.setOnCloseRequest(e-> e.consume());
		this.leaderSixStage.setResizable(false);
		
		
	}
	
	/**initializes the lorenzoStage and the lorenzoLoader
	 * 
	 */
	private void initializeLorenzoStage() {
		this.lorenzoStage=new Stage();
		this.lorenzoLoader=new FXMLLoader();
		this.lorenzoLoader.setLocation(getClass().getResource("LorenzoIlMagnifico.fxml"));
		try {
			this.lorenzoStage.setScene(new Scene(lorenzoLoader.load()));
		} catch (IOException e) {
			log.log(Level.SEVERE, "CannotInitializePlayersStagesGUI");
		}
		
		((LorenzoIlMagnificoController)this.lorenzoLoader.getController()).setStage(this.lorenzoStage);
		this.lorenzoStage.setOnCloseRequest(e-> e.consume());
		this.lorenzoStage.setResizable(false);
		
		
	}
	
	
	/** adds the text to the update window
	 * 
	 * @param s - the test that has to be added
	 */
	public void addUpdate(String s){
		if(("").equals(s) || (" ").equals(s))
			return;
		String[] split=s.split(" ");
		int count=0;
		textUpdates.appendText(">>");
		for(int i=0; i<split.length;i++){
			textUpdates.appendText(split[i]+" ");
			count+=split[i].length()+1;
			if(count>=26){
				count=0;
				textUpdates.appendText("\n");
			}
		}
		textUpdates.appendText("\n");
		
	}
	
	/** disables all the menu's buttons on the board
	 *  
	 */
	public void disableAllButtons(){
		towerButton.setDisable(true);		
		productionButton.setDisable(true);		
		harvestButton.setDisable(true);		
		councilButton.setDisable(true);		
		marketButton.setDisable(true);		
		playLeaderButton.setDisable(true);		
		discardLeaderButton.setDisable(true);		
		leaderHarvestButton.setDisable(true);		
		leaderProductionButton.setDisable(true);		
		leaderSixButton.setDisable(true);		
		leaderResourcesButton.setDisable(true);		
		passButton.setDisable(true);		
		updateBoardButton.setDisable(true);
		updatePlayersButton.setDisable(true);
	}
	
	/** when a menu is received, set the disabled property of the buttons to false is the player can do a specified action
	 * 
	 * @param menu - the menu
	 */
	public void menuHandler(String menu){
		String[] s=menu.split(";");
		if(("1").equals(s[2]))
			this.towerButton.setDisable(false);
		if(("1").equals(s[3]))
			this.productionButton.setDisable(false);
		if(("1").equals(s[4]))
			this.harvestButton.setDisable(false);
		if(("1").equals(s[5]))
			this.councilButton.setDisable(false);
		if(("1").equals(s[6]))
			this.marketButton.setDisable(false);
		if(("1").equals(s[7]))
			this.playLeaderButton.setDisable(false);
		if(("1").equals(s[8]))
			this.discardLeaderButton.setDisable(false);
		if(("1").equals(s[9]))
			this.updateBoardButton.setDisable(false);
		if(("1").equals(s[10]))
			this.updatePlayersButton.setDisable(false);
		if(("1").equals(s[11]))
			this.passButton.setDisable(false);
		if(("1").equals(s[12]))
			this.leaderHarvestButton.setDisable(false);
		if(("1").equals(s[13]))
			this.leaderProductionButton.setDisable(false);
		if(("1").equals(s[14]))
			this.leaderResourcesButton.setDisable(false);
		if(("1").equals(s[15]))
			this.leaderSixButton.setDisable(false);
		this.yellowCards=Integer.parseInt(s[16]);
	}
	
	/**allows the player to pass the turn
	 * 
	 */
	public void passButtonClick(){
		this.disableAllButtons();
		this.controller.putInOutput("PASS");
	}
	

	/** sends a BOARD update request
	 * 
	 */
	public void updateBoardButtonClick(){
		this.disableAllButtons();
		this.controller.putInOutput("SHOWTABLEREQUEST");
	}
	
	/** sends a PLAYERS update request
	 * 
	 */
	public void updatePlayersButtonClick(){
		this.disableAllButtons();
		this.controller.putInOutput("SHOWPLAYERSREQUEST");
	}
	
	/** shows the towerACtion stage, to allow the player t do an action on towers
	 * 
	 */
	public void towerButtonClick(){
		this.disableAllButtons();
		((TowerActionController)this.towerLoader.getController()).setController(this.controller);
		((TowerActionController)this.towerLoader.getController()).resetBonus();
		this.towerStage.show();
	}
	/** shows the councilACtion stage, to allow the player t do an action on the council
	 * 
	 */
	public void councilButtonClick(){
		this.disableAllButtons();
		((CouncilActionController)this.councilLoader.getController()).setController(this.controller);
		this.councilStage.show();
	}
	/** shows the marketACtion stage, to allow the player t do an action on the market
	 * 
	 */
	
	public void marketButtonClick(){
		this.disableAllButtons();
		((MarketActionController)this.marketLoader.getController()).setController(this.controller);
		this.marketStage.show();
	}
	/** shows the towerACtion stage, to allow the player t do an action on towers
	 * 
	 */
	public void harvestButtonClick(){
		this.disableAllButtons();
		((HarvestActionController)this.harvestLoader.getController()).setController(this.controller);
		((HarvestActionController)this.harvestLoader.getController()).resetHeaderBonus();
		this.harvestStage.show();
	}
	/** shows the towerACtion stage, to allow the player t do an action on towers
	 * 
	 */
	public void productionButtonClick(){
		this.disableAllButtons();
		((ProductionActionController)this.productionLoader.getController()).setController(this.controller);
		((ProductionActionController)this.productionLoader.getController()).setChoices(this.yellowCards);
		((ProductionActionController)this.productionLoader.getController()).resetHeaderBonus();
		this.productionStage.show();
	}
	
	/**activated when the player clicks on the button related to player1's personal board.
	 * Shows the board
	 * 
	 */
	public void onPlayer1CLick(){
		this.player1Stage.show();
	}
	/**activated when the player clicks on the button related to player2's personal board.
	 * Shows the board
	 * 
	 */
	public void onPlayer2CLick(){
		this.player2Stage.show();
	}
	/**activated when the player clicks on the button related to player3's personal board.
	 * Shows the board
	 * 
	 */
	public void onPlayer3CLick(){
		this.player3Stage.show();
	}
	/**activated when the player clicks on the button related to player4's personal board.
	 * Shows the board
	 * 
	 */
	public void onPlayer4CLick(){
		this.player4Stage.show();
	}
	/**activated when the player clicks on the button related to player5's personal board.
	 * Shows the board
	 * 
	 */
	public void onPlayer5CLick(){
		this.player5Stage.show();
	}
	
	/**activated when the button playLeader is clicked. it sets the necessary parameters in the PlayDiscardLeaderController and shows the playDiscardStage
	 * 
	 */
	public void onPlayLeaderClick(){
		this.disableAllButtons();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setController(this.controller);
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setPlay();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).disableButtons();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setLeaders(this.leaders);
		this.playDiscardStage.show();
	}
	/**activated when the button discardLeader is clicked. it sets the necessary parameters in the PlayDiscardLeaderController and shows the playDiscardStage
	 * 
	 */
	public void onDiscardLeaderCLick(){
		this.disableAllButtons();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setController(this.controller);
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setDiscard();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).disableButtons();
		((PlayDiscardLeaderController)this.playDiscardLoader.getController()).setLeaders(this.leaders);
		this.playDiscardStage.show();
	}
	
	/**activated when the button LeaderHarvest is clicked. Puts the command in the output buffer
	 */
	public void onLeaderHarvestClick(){
		this.disableAllButtons();
		this.controller.putInOutput("LEADERHARVEST");
	}
	/**activated when the button LeaderProduction is clicked. Puts the command in the output buffer
	 */
	public void onLeaderProductionClick(){
		this.disableAllButtons();
		this.controller.putInOutput("LEADERPRODUCTION");
	}
	/**activated when the button LeaderSixButton is clicked. Puts the command in the output buffer
	 */
	public void onLeaderSixClick(){
		this.disableAllButtons();
		((LeaderSixController)this.leaderSixLoader.getController()).setController(this.controller);
		this.leaderSixStage.show();
	}
	/**activated when the button LeaderResources is clicked. Puts the command in the output buffer
	 */
	public void onLeaderResourcesClick(){
		this.disableAllButtons();
		this.controller.putInOutput("LEADERRESOURCES");
	}
	
	/** called when the player obtains a single council bonus, shows the right stage
	 * 
	 */
	public void singleCouncilBonus() {
		((SingleCouncilBonusController)this.singleCouncilLoader.getController()).setController(this.controller);
		this.singleCouncilStage.show();
		
	}
	/** called when the player obtains a double council bonus, shows the right stage
	 * 
	 */
	public void doubleCouncilBonus() {
		((DoubleCouncilBonusController)this.doubleCouncilLoader.getController()).setController(this.controller);
		this.doubleCouncilStage.show();
		
	}
	/** called when the player obtains a triple council bonus, shows the right stage
	 * 
	 */
	public void tripleCouncilBonus() {
		((TripleCouncilBonusController)this.tripleCouncilLoader.getController()).setController(this.controller);
		this.tripleCouncilStage.show();
		
	}
	/** called when a message with FAMOFFERREQUEST header is received 
	 * 
	 */
	public void familiarOfferPhase() {
		((OfferController)this.offerLoader.getController()).setController(this.controller);
		((OfferController)this.offerLoader.getController()).resetOffer();
		this.offerStage.show();
		
	}
	/** called when a message with CHOICEREQUEST header is received 
	 * 
	 */
	public void costChoice(String s) {
		((CostChoiceController)this.choiceLoader.getController()).setController(this.controller);
		((CostChoiceController)this.choiceLoader.getController()).setMessage(s);
		this.choiceStage.show();
		
	}
	
	/** called when a message with BONUSTOWERACTION header is received 
	 * 
	 */
	public void bonusActionRequest(String[] messSplit) {
		((TowerActionController)this.towerLoader.getController()).setController(this.controller);
		((TowerActionController)this.towerLoader.getController()).setAsBonus(messSplit);
		this.towerStage.show();
	}
	/** called when a message with EXCOMMCHOICEREQUEST header is received
	 * 
	 */
	public void excommunicationRequest() {
		((ExcommunicationController)this.excommLoader.getController()).setController(this.controller);
		this.excommStage.show();
	}
	/** called when a message with TILESDRAFT header is received 
	 * 
	 */
	public void tilesDraft(String[] messSplit) {
		((TileChoiceController)this.tileLoader.getController()).setController(this.controller);
		((TileChoiceController)this.tileLoader.getController()).setTiles(messSplit);
		this.tileStage.show();
	}
	/** called when a message with LEADERDRAFT header is received 
	 * 
	 */
	public void leaderDraft(String[] messSplit) {
		((LeaderChoiceController)this.leaderLoader.getController()).setController(this.controller);
		((LeaderChoiceController)this.leaderLoader.getController()).disableButtons();
		((LeaderChoiceController)this.leaderLoader.getController()).setLeaders(messSplit);
		this.leaderStage.show();
	}
	
	/** called when a message with BONUSHARVESTREQUEST header is received
	 * 
	 */
	public void bonusHarvestRequest() {
		((HarvestActionController)this.harvestLoader.getController()).setController(this.controller);
		((HarvestActionController)this.harvestLoader.getController()).setHeaderBonus();
		this.harvestStage.show();
	}
	/** called when a message with BONUSPRODUCTIONREQUEST header is received
	 * 
	 */
	public void bonusProductionRequest() {
		((ProductionActionController)this.productionLoader.getController()).setController(this.controller);
		((ProductionActionController)this.productionLoader.getController()).setHeaderBonus();
		((ProductionActionController)this.productionLoader.getController()).setChoices(this.yellowCards);
		this.productionStage.show();
		
	}
	/** called when a message with LORENZOILMAGNIFICO header is received
	 * 
	 */
	public void lorenzoIlMagnifico(String[] messSplit) {
		((LorenzoIlMagnificoController)this.lorenzoLoader.getController()).setController(this.controller);
		((LorenzoIlMagnificoController)this.lorenzoLoader.getController()).setLeaders(messSplit);
		this.lorenzoStage.show();
		
	}
	
	/**called when a BOARD message is received, it updates the board images
	 * 
	 */
	public void updateBoard(String board){
		this.clearBoard();
		String[] split=board.split(";");
		this.createPlayerColorCorrespondance(split[12]);
		this.checkCoveringTiles();
		this.updateGreenCards(split[2].split("!"));
		this.updateBlueCards(split[3].split("!"));
		this.updateYellowCards(split[4].split("!"));
		this.updatePurpleCards(split[5].split("!"));
		this.updateCouncil(split[6].split("!"));
		this.updateMarket(split[7].split("!"));
		this.updateWorkArea(split[8].split("!"));
		this.updateExcommunications(split[9].split("!"), split[10].split("!"), split[11].split("!"));
		this.updateTurnOrder(split[12]);
		
	}
	
	/**supports the updateBoard method. updates the green tower ImageViews
	 * 
	 * @param cards - a string representing the cards on each floor
	 */
	private void updateGreenCards(String cards[]){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		
		String[] floor=cards[0].split("ì");
		String[] card=floor[0].split("ù");
		String[] space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.gc0.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fgc0.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[1].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.gc1.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fgc1.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[2].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.gc2.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fgc2.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[3].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.gc3.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fgc3.setImage(this.getFamiliarImageFor(space[2],space[3]));
		
	}
	/**supports the updateBoard method. updates the yellow tower ImageViews
	 * 
	 * @param cards - a string representing the cards on each floor
	 */
	private void updateYellowCards(String cards[]){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		String[] floor=cards[0].split("ì");
		String[] card=floor[0].split("ù");
		String[] space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.yc0.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fyc0.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[1].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.yc1.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fyc1.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[2].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.yc2.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fyc2.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[3].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.yc3.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fyc3.setImage(this.getFamiliarImageFor(space[2],space[3]));
	}
	/**supports the updateBoard method. updates the purple tower ImageViews
	 * 
	 * @param cards - a string representing the cards on each floor
	 */
	private void updatePurpleCards(String cards[]){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		String[] floor=cards[0].split("ì");
		String[] card=floor[0].split("ù");
		String[] space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.pc0.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fpc0.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[1].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.pc1.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fpc1.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[2].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.pc2.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fpc2.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[3].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.pc3.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fpc3.setImage(this.getFamiliarImageFor(space[2],space[3]));
	}
	/**supports the updateBoard method. updates the blue tower ImageViews
	 * 
	 * @param cards - a string representing the cards on each floor
	 */
	private void updateBlueCards(String cards[]){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		String[] floor=cards[0].split("ì");
		String[] card=floor[0].split("ù");
		String[] space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.bc0.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fbc0.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[1].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.bc1.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fbc1.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[2].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.bc2.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fbc2.setImage(this.getFamiliarImageFor(space[2],space[3]));
		floor=cards[3].split("ì");
		card=floor[0].split("ù");
		space=floor[1].split("ù");
		if(!(" ").equals(floor[0]))
			this.bc3.setImage(new Image(path+card[0]+".png"));
		if(!(" ").equals(space[2]))
			this.fbc3.setImage(this.getFamiliarImageFor(space[2],space[3]));
	}
	/**supports the updateBoard method. updates the market ImageViews
	 * 
	 * @param market - a string array representing the market spaces
	 */
	private void updateMarket(String market[]){
		String[] app;
		String[] app2;
		for(int i=1;i<market.length;i++){
			app=market[i].split("ù");
			if(!(" ").equals(app[1])){
				app2=app[1].split("-");
				app=app2[0].split(":");
				if(i==1)
					this.market0.setImage(this.getFamiliarImageFor(app[0], app[1]));
				else if(i==2)
					this.market1.setImage(this.getFamiliarImageFor(app[0], app[1]));
				else if(i==3)
					this.market2.setImage(this.getFamiliarImageFor(app[0], app[1]));
				else if(i==4)
					this.market3.setImage(this.getFamiliarImageFor(app[0], app[1]));
			}
		}
	}
	
	/**supports the updateBoard method. updates the excommunications ImageViews
	 * 
	 * @param e1 - a string array representing the excommuication 1 and excmmunicated players
	 * @param e2 - a string array representing the excommuication 2 and excmmunicated players
	 * @param e3 - a string array representing the excommuication 3 and excmmunicated players
	 */
	private void updateExcommunications(String e1[], String e2[], String e3[]){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\excommunications\\";
		String path2="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\familiars\\";
		this.excomm1.setImage(new Image(path+e1[1]+".png"));
		for(int i=2;i<e1.length;i++){
			if(i==2)
				this.ex11.setImage(new Image(path2+this.getColorFor(e1[i])+"c.png"));
			else if(i==3)
				this.ex12.setImage(new Image(path2+this.getColorFor(e1[i])+"c.png"));
			else if(i==4)
				this.ex13.setImage(new Image(path2+this.getColorFor(e1[i])+"c.png"));
			else if(i==5)
				this.ex14.setImage(new Image(path2+this.getColorFor(e1[i])+"c.png"));
			else if(i==6)
				this.ex15.setImage(new Image(path2+this.getColorFor(e1[i])+"c.png"));	
		}
		this.excomm2.setImage(new Image(path+e2[1]+".png"));
		for(int i=2;i<e2.length;i++){
			if(i==2)
				this.ex21.setImage(new Image(path2+this.getColorFor(e2[i])+"c.png"));
			else if(i==3)
				this.ex22.setImage(new Image(path2+this.getColorFor(e2[i])+"c.png"));
			else if(i==4)
				this.ex23.setImage(new Image(path2+this.getColorFor(e2[i])+"c.png"));
			else if(i==5)
				this.ex24.setImage(new Image(path2+this.getColorFor(e2[i])+"c.png"));
			else if(i==6)
				this.ex25.setImage(new Image(path2+this.getColorFor(e2[i])+"c.png"));	
		}
		this.excomm3.setImage(new Image(path+e3[1]+".png"));
		for(int i=2;i<e3.length;i++){
			if(i==2)
				this.ex31.setImage(new Image(path2+this.getColorFor(e3[i])+"c.png"));
			else if(i==3)
				this.ex32.setImage(new Image(path2+this.getColorFor(e3[i])+"c.png"));
			else if(i==4)
				this.ex33.setImage(new Image(path2+this.getColorFor(e3[i])+"c.png"));
			else if(i==5)
				this.ex34.setImage(new Image(path2+this.getColorFor(e3[i])+"c.png"));
			else if(i==6)
				this.ex35.setImage(new Image(path2+this.getColorFor(e3[i])+"c.png"));	
		}
	}
	
	/** returns the color related to the given nickname
	 * 
	 * @param nick - the player's nickname
	 * @return the color related to the given nickname
	 */
	private String getColorFor(String nick){
		for(int i=0;i<this.players.size();i++)
			if(this.players.get(i).equals(nick))
				return this.colors.get(i);
		return "";
	}
	
	/**supports the updateBoard Method, updates the turn order ImageViews
	 * 
	 * @param corr - the string containing players and their related colors
	 */
	private void updateTurnOrder(String corr){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\familiars\\";
		String[] app=corr.split("!");
		for(int i=1;i<app.length;i=i+2){
			String finalPath=path+app[i]+".png";
			if(i==1)
				this.turn0.setImage(new Image(finalPath));
			else if(i==3)
				this.turn1.setImage(new Image(finalPath));
			else if(i==5)
				this.turn2.setImage(new Image(finalPath));
			else if(i==7)
				this.turn3.setImage(new Image(finalPath));
			else if(i==9)
				this.turn4.setImage(new Image(finalPath));
		}
	}	
	/** creates a correspondance betweeb players and colors
	 * 
	 * @param corr - the string containing players and their related colors
	 */
	private void createPlayerColorCorrespondance(String corr){
		String[] app=corr.split("!");
		this.players=new ArrayList<String>();
		this.colors=new ArrayList<String>();
		for(int i=0;i<app.length;i=i+2){
			players.add(app[i]);
			colors.add(app[i+1]);
		}
	}
	/** given a nickname and a familiar's color, returns the corresponding image
	 * 
	 * @param player - the player
	 * @param fam - the familiar's color
	 * @return the image corresponding to that familiar and player
	 */
	private Image getFamiliarImageFor(String player, String fam) {
		String path2="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\familiars\\";
		String f=Character.toString(fam.charAt(1));
		if(!("f").equals(f)){
			for(int i=0;i<this.players.size();i++){
				if(this.players.get(i).equals(player)){
					return new Image(path2+this.colors.get(i)+f+".png");
				}
			}
		}
		return null;
	}
	
	/**clears the board
	 * 
	 */
	private void clearBoard(){
		this.bc0.setImage(null);
		this.bc1.setImage(null);
		this.bc2.setImage(null);
		this.bc3.setImage(null);
		this.gc0.setImage(null);
		this.gc1.setImage(null);
		this.gc2.setImage(null);
		this.gc3.setImage(null);
		this.yc0.setImage(null);
		this.yc1.setImage(null);
		this.yc2.setImage(null);
		this.yc3.setImage(null);
		this.pc0.setImage(null);
		this.pc1.setImage(null);
		this.pc2.setImage(null);
		this.pc3.setImage(null);
		this.fpc0.setImage(null);
		this.fpc1.setImage(null);
		this.fpc2.setImage(null);
		this.fpc3.setImage(null);
		this.fbc0.setImage(null);
		this.fbc1.setImage(null);
		this.fbc2.setImage(null);
		this.fbc3.setImage(null);
		this.fgc0.setImage(null);
		this.fgc1.setImage(null);
		this.fgc2.setImage(null);
		this.fgc3.setImage(null);
		this.fyc0.setImage(null);
		this.fyc1.setImage(null);
		this.fyc2.setImage(null);
		this.fyc3.setImage(null);
		this.fpc0.setImage(null);
		this.fpc1.setImage(null);
		this.fpc2.setImage(null);
		this.fpc3.setImage(null);
		this.council0.setImage(null);
		this.council1.setImage(null);
		this.council2.setImage(null);
		this.council3.setImage(null);
		this.council4.setImage(null);
		this.market0.setImage(null);
		this.market1.setImage(null);
		this.market2.setImage(null);
		this.market3.setImage(null);
		this.hm0.setImage(null);
		this.hm1.setImage(null);
		this.hm2.setImage(null);
		this.pm0.setImage(null);
		this.pm1.setImage(null);
		this.pm2.setImage(null);
		this.hs0.setImage(null);
		this.hs1.setImage(null);
		this.hs2.setImage(null);
		this.hs3.setImage(null);
		this.hs4.setImage(null);
		this.hs5.setImage(null);
		this.hs6.setImage(null);
		this.hs7.setImage(null);
		this.hs8.setImage(null);
		this.hs9.setImage(null);
		this.ps0.setImage(null);
		this.ps1.setImage(null);
		this.ps2.setImage(null);
		this.ps3.setImage(null);
		this.ps4.setImage(null);
		this.ps5.setImage(null);
		this.ps6.setImage(null);
		this.ps7.setImage(null);
		this.ps8.setImage(null);
		this.ps9.setImage(null);
	}
	
	/**supports updateBoard, updates the council
	 * 
	 * @param council - a string array representing the council 
	 */
	private void updateCouncil(String council[]){
		if(council.length<=2)
			return;
		String[] csplit=council[2].split("-");
		ArrayList<String> playersHere=new ArrayList<String>();
		for(int i=0;i<csplit.length;i++){
			String[] space=csplit[i].split(":");
			int size=playersHere.size();
			boolean doAction=true;
			for(String s:playersHere){
				if(s.equals(space[0]))
					doAction=false;
			}
			if(doAction){
				playersHere.add(space[0]);
				if(size==0)
					this.council0.setImage(this.getFamiliarImageFor(space[0], space[1]));
				else if(size==1)
					this.council1.setImage(this.getFamiliarImageFor(space[0], space[1]));
				else if(size==2)
					this.council2.setImage(this.getFamiliarImageFor(space[0], space[1]));
				else if(size==3)
					this.council3.setImage(this.getFamiliarImageFor(space[0], space[1]));
				else if(size==4)
					this.council4.setImage(this.getFamiliarImageFor(space[0], space[1]));
			}
		}
		
	}
	private void updateWorkArea(String[] work){
		this.updateProductionArea(work[1], work[2]);
		this.updateHarvestArea(work[3], work[4]);
	}
	
	/**supports updateBoard, updates the harvest area
	 * 
	 * @param h1- a string representing the harvest small area
	 * @param h2- a string representing the harvest big area
	 */
	private void updateHarvestArea(String h1, String h2){
		String[] app;
		String[] app2;
		if(!(" ").equals(h1)){
			app=h1.split("-");
			for(int i=0;i<app.length;i++){
				app2=app[i].split(":");
				if(i==0)
					this.hm0.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==1)
					this.hm1.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==2)
					this.hm2.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
			}
			
		}
		if(!(" ").equals(h2)){
			app=h2.split("-");
			for(int i=0;i<app.length;i++){
				app2=app[i].split(":");
				if(i==0)
					this.hs0.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==1)
					this.hs1.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==2)
					this.hs2.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==3)
					this.hs3.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==4)
					this.hs4.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==5)
					this.hs5.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==6)
					this.hs6.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==7)
					this.hs7.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==8)
					this.hs8.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==9)
					this.hs9.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
			}
		}
	}
	/**supports updateBoard, updates the harvest area
	 * 
	 * @param h1- a string representing the production small area
	 * @param h2- a string representing the production big area
	 */
	private void updateProductionArea(String h1, String h2){
		String[] app;
		String[] app2;
		if(!(" ").equals(h1)){
			app=h1.split("-");
			for(int i=0;i<app.length;i++){
				app2=app[i].split(":");
				if(i==0)
					this.pm0.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==1)
					this.pm1.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==2)
					this.pm2.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
			}
			
		}
		if(!(" ").equals(h2)){
			app=h2.split("-");
			for(int i=0;i<app.length;i++){
				app2=app[i].split(":");
				if(i==0)
					this.ps0.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==1)
					this.ps1.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==2)
					this.ps2.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==3)
					this.ps3.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==4)
					this.ps4.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==5)
					this.ps5.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==6)
					this.ps6.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==7)
					this.ps7.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==8)
					this.ps8.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
				else if(i==9)
					this.ps9.setImage(this.getFamiliarImageFor(app2[0], app2[1]));
			}
		}
	}
	/** called when a message with PLAYERS header is received 
	 * 
	 */
	public void updatePlayers(String app) {
		this.relocateFaithTrackers();
		this.relocateMilitaryTrackers();
		this.resetPlayerButtons();
		this.leaders=new ArrayList<Integer>();
		String[] split=app.split(";");
		int s=split.length;
		for(int i=2;i<s;i++){
			String[] player=split[i].split("!");
			if(i==2){
				this.player1nick.setText(player[0]);
				this.player1.setDisable(false);
				this.setFaithTracker(this.pf1, player[0], ResourceSet.parseResourceSet(player[1]));
				this.setMilitaryTracker(this.mil1, player[0], ResourceSet.parseResourceSet(player[1]));
				((PlayerController)this.player1Loader.getController()).updatePlayer(player,this);
				this.setColor(this.player1Loader, player[0]);
				
			}
			else if(i==3){
				this.player2nick.setText(player[0]);
				this.player2.setDisable(false);
				this.setFaithTracker(this.pf2, player[0], ResourceSet.parseResourceSet(player[1]));
				this.setMilitaryTracker(this.mil2, player[0], ResourceSet.parseResourceSet(player[1]));
				((PlayerController)this.player2Loader.getController()).updatePlayer(player,this);
				this.setColor(this.player2Loader, player[0]);
			}
			else if(i==4){
				this.player3nick.setText(player[0]);
				this.player3.setDisable(false);
				this.setFaithTracker(this.pf3, player[0], ResourceSet.parseResourceSet(player[1]));
				this.setMilitaryTracker(this.mil3, player[0], ResourceSet.parseResourceSet(player[1]));
				((PlayerController)this.player3Loader.getController()).updatePlayer(player,this);
				this.setColor(this.player1Loader, player[0]);
			}
			else if(i==5){
				this.player4nick.setText(player[0]);
				this.player4.setDisable(false);
				this.setFaithTracker(this.pf4, player[0], ResourceSet.parseResourceSet(player[1]));
				this.setMilitaryTracker(this.mil4, player[0], ResourceSet.parseResourceSet(player[1]));
				((PlayerController)this.player4Loader.getController()).updatePlayer(player,this);
				this.setColor(this.player1Loader, player[0]);
			}
			else if(i==6){
				this.player5nick.setText(player[0]);
				this.player5.setDisable(false);
				this.setFaithTracker(this.pf5, player[0], ResourceSet.parseResourceSet(player[1]));
				this.setMilitaryTracker(this.mil5, player[0], ResourceSet.parseResourceSet(player[1]));
				((PlayerController)this.player5Loader.getController()).updatePlayer(player,this);
				this.setColor(this.player1Loader, player[0]);
			}
		}
		
	}
	private void resetPlayerButtons(){
		this.player1.setDisable(true);
		this.player2.setDisable(true);
		this.player3.setDisable(true);
		this.player4.setDisable(true);
		this.player5.setDisable(true);
		this.player1nick.setText("");
		this.player2nick.setText("");
		this.player3nick.setText("");
		this.player4nick.setText("");
		this.player5nick.setText("");
	}
	private void setColor(FXMLLoader l, String nick){
		for(int i=0;i<this.players.size();i++)
			if(nick.equals(this.players.get(i)))
				((PlayerController)l.getController()).setColor(this.colors.get(i));
	}
	public void updateDice(String[] messSplit) {
		String path2="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\dice\\";
		this.diceB.setImage(new Image(path2+"b"+messSplit[3]+".png"));
		this.diceO.setImage(new Image(path2+"o"+messSplit[4]+".png"));
		this.diceW.setImage(new Image(path2+"w"+messSplit[5]+".png"));
	}
	
	public void addLeader(Integer i){
		this.leaders.add(i);
	}
	private void relocateFaithTrackers(){
		this.pf1.relocate(this.base[0], this.pf1.getLayoutY());
		this.pf5.relocate(this.base[0], this.pf5.getLayoutY());
		this.pf2.relocate(this.base[1], this.pf2.getLayoutY());
		this.pf3.relocate(this.base[2], this.pf3.getLayoutY());
		this.pf4.relocate(this.base[2], this.pf4.getLayoutY());
		this.pf1.setImage(null);
		this.pf2.setImage(null);
		this.pf3.setImage(null);
		this.pf4.setImage(null);
		this.pf5.setImage(null);
	}
	private void relocateMilitaryTrackers(){
		this.mil1.relocate(this.mil1.getLayoutX(), this.baseMilitary[0]);
		this.mil2.relocate(this.mil2.getLayoutX(), this.baseMilitary[0]);
		this.mil3.relocate(this.mil3.getLayoutX(), this.baseMilitary[1]);
		this.mil4.relocate(this.mil4.getLayoutX(), this.baseMilitary[2]);
		this.mil5.relocate(this.mil5.getLayoutX(), this.baseMilitary[2]);
		this.mil1.setImage(null);
		this.mil2.setImage(null);
		this.mil3.setImage(null);
		this.mil4.setImage(null);
		this.mil5.setImage(null);
	}
	private void setFaithTracker(ImageView imm,String nick, ResourceSet res){
		String path2="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\familiars\\";
		for(int i=0;i<this.players.size();i++)
			if(nick.equals(this.players.get(i))){
				int faith=res.getFaithPoints();
				if(faith>15)
					faith=15;
				imm.setImage(new Image(path2+this.colors.get(i)+".png"));
				imm.relocate(imm.getLayoutX()+this.offsets[faith], imm.getLayoutY());
				return;
			}
	}
	private void setMilitaryTracker(ImageView imm, String nick, ResourceSet res){
		String path2="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\familiars\\";
		for(int i=0;i<this.players.size();i++)
			if(nick.equals(this.players.get(i))){
				int mil=res.getMilitaryPoints();
				if(mil>25)
					mil=25;
				imm.setImage(new Image(path2+this.colors.get(i)+".png"));
				imm.relocate(imm.getLayoutX(), imm.getLayoutY()-this.offsetMilitary*mil);
				return;
			}
	}
	private void checkCoveringTiles(){
		if(this.players.size()>2){
			this.ctileH.setImage(null);
			this.ctileP.setImage(null);
			if(this.players.size()>=4){
				this.ctileM1.setImage(null);
				this.ctileM2.setImage(null);
			}
		}
	}
	

}
