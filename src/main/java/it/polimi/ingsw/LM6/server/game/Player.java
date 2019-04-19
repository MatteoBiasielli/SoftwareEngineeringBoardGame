package it.polimi.ingsw.LM6.server.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import it.polimi.ingsw.LM6.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.LM6.server.game.card.*;
import it.polimi.ingsw.LM6.server.game.exceptions.BadInputException;
import it.polimi.ingsw.LM6.server.game.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.LM6.server.game.familiar.Familiar;
import it.polimi.ingsw.LM6.server.game.familiar.FamiliarColor;
import it.polimi.ingsw.LM6.server.users.IUser;
import it.polimi.ingsw.LM6.server.game.bonuses.*;
public class Player {
	
	private IUser user;
	private ResourceSet resources;
	private Boolean isActive;
	private Boolean isConnected;
	private Boolean isOfferPhaseWinner;
	private PersonalBonusTile personalBonusTile;
	private PermanentBonusMalus permBonusMalus;
	private ArrayList<GreenCard> greenCards;
	private ArrayList<BlueCard> blueCards;
	private ArrayList<YellowCard> yellowCards;
	private ArrayList<PurpleCard> purpleCards;
	private ArrayList<LeaderCard> leaderCardsInHand;
	private ArrayList<LeaderCard> leaderCardsPlayed;
	private Familiar[] familiar;
	private PlayerColour color;
	private Boolean excommunicationResult;
	private ResourceSet familiarOffer;
	private int draftChoice;
	private final String separator="!";
	private final String cardsSeparator="-";

	
	//TODO correct constructor
	/**used to create a new game
	 * 
	 * @param u
	 * @param g
	 * @param nFamiliars
	 */
	public Player(IUser u, LorenzoIlMagnifico g, int nFamiliars){
		this.user=u;
		this.resources=new ResourceSet(0,0,0,0,0,0,0);
		this.isActive=true;
		this.isConnected=true;
		this.isOfferPhaseWinner = false;
		this.personalBonusTile=new PersonalBonusTile(new ResourceSet(1,1,0,1,0,0,0), new ResourceSet(0,0,2,0,1,0,0),117);
		this.permBonusMalus=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"none");
		this.greenCards=new ArrayList<GreenCard>();
		this.blueCards=new ArrayList<BlueCard>();
		this.yellowCards=new ArrayList<YellowCard>();
		this.purpleCards=new ArrayList<PurpleCard>();
		this.leaderCardsInHand=new ArrayList<LeaderCard>();
		this.leaderCardsPlayed=new ArrayList<LeaderCard>();
		this.isOfferPhaseWinner=false;
		this.familiar=new Familiar[nFamiliars];
			
		familiar[0]=new Familiar(this,FamiliarColor.BLACK);
		familiar[1]=new Familiar(this,FamiliarColor.ORANGE);
		familiar[2]=new Familiar(this,FamiliarColor.WHITE);
		familiar[3]=new Familiar(this,FamiliarColor.UNCOLOURED);
			
		this.excommunicationResult = true;
		this.familiarOffer = new ResourceSet();
	}
	
	/** used to create players for a game that is going to be reloaded from file
	 * 
	 * @param u
	 * @param nFamiliars
	 * @param res
	 * @param tile
	 * @param green
	 * @param blue
	 * @param yellow
	 * @param purple
	 * @param hand
	 * @param played
	 * @throws IOException if any problem occour dorung the reloading from files
	 */
	public Player(IUser u, int nFamiliars, String res, String tile, String green,String blue, String yellow, String purple, String hand, String played) throws IOException{
		this.user=u;
		this.isActive=false;
		this.isConnected=false;
		this.isOfferPhaseWinner = false;
		this.permBonusMalus=new PermanentBonusMalus(0,0,0,0,0,0,0,0,0,0,"none");
		this.personalBonusTile=new PersonalBonusTile(new ResourceSet(1,1,0,1,0,0,0), new ResourceSet(0,0,2,0,1,0,0),117);
		this.greenCards=new ArrayList<GreenCard>();
		this.blueCards=new ArrayList<BlueCard>();
		this.yellowCards=new ArrayList<YellowCard>();
		this.purpleCards=new ArrayList<PurpleCard>();
		this.leaderCardsInHand=new ArrayList<LeaderCard>();
		this.leaderCardsPlayed=new ArrayList<LeaderCard>();
		this.isOfferPhaseWinner=false;
		this.familiar=new Familiar[nFamiliars];
			
		familiar[0]=new Familiar(this,FamiliarColor.BLACK);
		familiar[1]=new Familiar(this,FamiliarColor.ORANGE);
		familiar[2]=new Familiar(this,FamiliarColor.WHITE);
		familiar[3]=new Familiar(this,FamiliarColor.UNCOLOURED);
			
		this.excommunicationResult = true;
		this.familiarOffer = new ResourceSet();
		this.resources=ResourceSet.parseResourceSet(res);
		this.reloadGreenCards(green);
		this.reloadBlueCards(blue);
		this.reloadPurpleCards(purple);
		this.reloadYellowCards(yellow);
		this.setTile(tile);
		this.reloadLeaders(hand, true);
		this.reloadLeaders(played, false);
	}
	public ResourceSet getResourceSet() {
		return resources;
	
	}
	
	/*sends a message to the player if he/she is active*/
	public void send( String m) {
		if(this.isActive())
			this.user.send(m);
	}
	
	/**true if the player is active (not disconnected or afk for too long)
	 * false otherwise
	 */
	public Boolean isActive() {
		return this.isActive;
	
	}
	
	/**true if the player is connected (not disconnected or afk for too long)
	 * false otherwise
	 */
	public Boolean isConnected() {
		return this.isConnected;
	}
	
	public void setOffer(ResourceSet r){
		if(r!=null)
			this.familiarOffer=r;
	}
	public IUser getUser() {
		return user;
	
	}
	
	public ArrayList<GreenCard> getGreenCardList() {
		return greenCards;
	
	}
	
	public ArrayList<BlueCard> getBlueCardList() {
		return blueCards;
	
	}
	
	public ArrayList<YellowCard> getYellowCardList() {
		return yellowCards;
	
	}
	
	public ArrayList<PurpleCard> getPurpleCardList() {
		return purpleCards;
	
	}
	
	/*acquires the resource set contained in a bonus object and adds it to the player's resource set
	 * 
	 */
	public void acquireResourcesFromBonus( Bonus b) {
		this.resources=this.resources.sum(b.getResourceSetFor(this));
		/*Sends the update to all active players*/
		if(!b.isEmpty() && this.getUser().getGame()!=null)
			this.getUser().getGame().sendAll(this.getNickname()+ " obtained " +(b.getResourceSetFor(this).toString()));
	}
	public void acquireResources(ResourceSet rs){
		this.resources=this.resources.sum(rs);
		/*Sends the update to all active players*/
		if(!rs.isEmpty() && this.getUser().getGame()!=null)
			this.getUser().getGame().sendAll(this.getNickname()+ " obtained " +(rs.toString()));
	}
	
	/*merges a permanent bonus (from a card or excommunication with the player's bonuses */
	public void acquirePermBonus( PermanentBonusMalus pmb) {
		this.permBonusMalus.merge(pmb);
	}
	

	/* subtracts rs to the player's recource set 
	 * @throws NotEnoughResourcesException if the player doesn't have enough resources 
	 */
	public void pay( ResourceSet rs) throws NotEnoughResourcesException{
		if(!this.resources.contains(rs))
			throw new NotEnoughResourcesException("You don't have enough resources to perform this action.");
		this.resources=this.resources.sub(rs);
	}
	
	public Familiar getFamiliar( int n) {
		return familiar[n];
	
	}
	
	public Familiar getFamiliar(String color) throws BadInputException{
		if(color!=null){
			if(color.equals("BLACK"))
				return familiar[0];
			else if(color.equals("ORANGE"))
				return familiar[1];
			else if(color.equals("WHITE"))
				return familiar[2];
			else if(color.equals("UNCOLOURED"))
				return familiar[3];
		}
		throw new BadInputException("Not Existing familiar");
	
	}
	
	public String toString(Player pl) {
		String ris="";
		ris=ris+this.getNickname()+separator;
		ris=ris+this.resources.toString()+separator;
		if(this.greenCards.size()>0){
			for(GreenCard card: this.greenCards)
				ris=ris+ card.toString()+ cardsSeparator;
		}
		else
			ris=ris+" ";
		ris=ris+separator;
		if(this.yellowCards.size()>0){
			for(YellowCard card: this.yellowCards)
				ris=ris+ card.toString()+ cardsSeparator;
		}
		else
			ris=ris+" ";
		ris=ris+separator;
		if(this.blueCards.size()>0){
			for(BlueCard card: this.blueCards)
				ris=ris+ card.toString()+ cardsSeparator;
		}
		else
			ris=ris+" ";
		ris=ris+separator;
		if(this.purpleCards.size()>0){
			for(PurpleCard card: this.purpleCards)
				ris=ris+ card.toString()+ cardsSeparator;
		}
		else
			ris=ris+" ";
		ris=ris+separator;
		if(this.leaderCardsInHand.size()>0){
			if(this==pl){
				for(int i=0;i<this.leaderCardsInHand.size();i++)
					ris=ris+i+". "+ this.leaderCardsInHand.get(i).toString()+ cardsSeparator;
			}
			else
				ris=ris+" ";
		}
		else
			ris=ris+" ";
		ris=ris+separator;
		if(this.leaderCardsPlayed.size()>0){
			for(LeaderCard card: this.leaderCardsPlayed)
				ris=ris+ card.toString()+ cardsSeparator;
		}
		else
			ris=ris+" ";
		ris=ris+separator+ this.personalBonusTile.toString()+ separator;
		
		return ris;
	}
	
	public String getNickname(){
		return this.user.getNickname();
	}

	public void setFamiliarsValues(int b, int o, int w, int u) {
		familiar[0].setStrength(b);
		familiar[1].setStrength(o);
		familiar[2].setStrength(w);
		familiar[3].setStrength(u);

	}

	public void activatePlayer(){
		this.isActive=true;
	}
	
	public void deactivatePlayer(){
		this.isActive=false;
	}


	public PermanentBonusMalus getPermanentBonusMalus() {
		return this.permBonusMalus;
	}


	public PersonalBonusTile getPersonalTile() {
		return this.personalBonusTile;
	}
	
	public ArrayList<LeaderCard> getLeadersInHand(){
		return this.leaderCardsInHand;
	}
	
	public ArrayList<LeaderCard> getLeadersPlayed(){
		return this.leaderCardsPlayed;	
	}
	
	public void resetFamiliars(int n) {
		for(Familiar f:familiar){
			if(f!=null && (f.getColour()!=FamiliarColor.BLACK || f.getColour()==FamiliarColor.BLACK && this.isOfferPhaseWinner|| n<5))
				f.resetPlaced();
		}
	}
	
	public void resetExcommunicationResult(){
		this.excommunicationResult = true;
	}
	
	public boolean hasBeenExcommunicated(){
		return this.excommunicationResult;
	}
	
	public ResourceSet getFamiliarOffer(){
		return this.familiarOffer;
	}

	public void setExcommunicationResult(){
		this.excommunicationResult = false;
    }
	public void resetFamiliarOffer(){
		this.familiarOffer.setZero();
	}
	public void setIsActive(boolean active){
		this.isActive=active;
	}
	public void setIsConnected(boolean connected){
		this.isConnected=connected;
	}
	public void setIUser(IUser user){
		this.user = user;
	}
	public int getDraftChoice(){
		return this.draftChoice;
	}
	public void resetDraftChoice(){
		this.draftChoice = 0;
	}
	public void acquireLeader(LeaderCard leader){
		this.leaderCardsInHand.add(leader);
	}
	public void setPersonalTile(PersonalBonusTile tile) {
		this.personalBonusTile = tile;
	}
	public void setDraftChoice(int draftChoice){
		this.draftChoice = draftChoice;
	}
	public void setColour(PlayerColour col){
		this.color=col;
	}
	public String getColourString(){
		return this.color.getColourString();
	}
	public void setIsOfferPhaseWinner(Boolean b){
		this.isOfferPhaseWinner=b;
	}
	public Boolean isOfferPhaseWinner(){
		return this.isOfferPhaseWinner;
	}
	public String getGreenCardsNumbers(){
		String app="";
		for(Card c: this.greenCards)
			app+=c.getNumber()+";";
		if(("").equals(app))
			app=" ";
		return app;
	}
	public String getBlueCardsNumbers(){
		String app="";
		for(Card c: this.blueCards)
			app+=c.getNumber()+";";
		if(("").equals(app))
			app=" ";
		return app;
	}
	public String getYellowCardsNumbers(){
		String app="";
		for(Card c: this.yellowCards)
			app+=c.getNumber()+";";
		if(("").equals(app))
			app=" ";
		return app;
	}
	public String getPurpleCardsNumbers(){
		String app="";
		for(Card c: this.purpleCards)
			app+=c.getNumber()+";";
		if(("").equals(app))
			app=" ";
		return app;
	}
	public String getLeaderCardsNumbers(ArrayList<LeaderCard> list){
		String app="";
		for(LeaderCard c: list)
			app+=c.getNumber()+";";
		if(("").equals(app))
			app=" ";
		return app;
	}
	/** when reloading a game, reloads the green cards of the player
	 * 
	 * @param s - the green cards numbers in a string, separated by a ;
	 * @throws IOException if a problem occours during the reloading process
	 */
	private void reloadGreenCards(String s) throws IOException{
		if((" ").equals(s))
			return;
		String[] cards=s.split(";");
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		for(int i=0;i<cards.length;i++){
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/greencards.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			for(int j=0;j<24;j++){
				app=inputFromFile.readLine();
				GreenCard newCard=new GreenCard(app.split(";"));
				if(newCard.getNumber()==Integer.parseInt(cards[i])){
					this.greenCards.add(newCard);
					break;
				}
			}
			inputFromFile.close();
			gameConfig.close();
		}
		
		
	}
	/** when reloading a game, reloads the yellow cards of the player
	 * 
	 * @param s - the yellow cards numbers in a string, separated by a ;
	 * @throws IOException if a problem occours during the reloading process
	 */
	private void reloadYellowCards(String s) throws IOException{
		if((" ").equals(s))
			return;
		String[] cards=s.split(";");
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		for(int i=0;i<cards.length;i++){
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/yellowcards.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			for(int j=0;j<24;j++){
				app=inputFromFile.readLine();
				YellowCard newCard=new YellowCard(app.split(";"));
				if(newCard.getNumber()==Integer.parseInt(cards[i])){
					this.yellowCards.add(newCard);
					break;
				}
			}
			inputFromFile.close();
			gameConfig.close();
		}

	}
	/** when reloading a game, reloads the purple cards of the player
	 * 
	 * @param s - the purple cards numbers in a string, separated by a ;
	 * @throws IOException if a problem occours during the reloading process
	 */
	private void reloadPurpleCards(String s) throws IOException{
		if((" ").equals(s))
			return;
		String[] cards=s.split(";");
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		for(int i=0;i<cards.length;i++){
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/purplecards.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			for(int j=0;j<24;j++){
				app=inputFromFile.readLine();
				PurpleCard newCard=new PurpleCard(app.split(";"));
				if(newCard.getNumber()==Integer.parseInt(cards[i])){
					this.purpleCards.add(newCard);
					break;
				}
			}
			inputFromFile.close();
			gameConfig.close();
		}

	}
	
	/** when reloading a game, reloads the blue cards of the player
	 * 
	 * @param s - the blue cards numbers in a string, separated by a ;
	 * @throws IOException if a problem occours during the reloading process
	 */
	private void reloadBlueCards(String s) throws IOException{
		if((" ").equals(s))
			return;
		String[] cards=s.split(";");
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		for(int i=0;i<cards.length;i++){
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/bluecards.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			for(int j=0;j<24;j++){
				app=inputFromFile.readLine();
				BlueCard newCard=new BlueCard(app.split(";"));
				if(newCard.getNumber()==Integer.parseInt(cards[i])){
					newCard.giveCardTo(this);
					break;
				}
			}
			inputFromFile.close();
			gameConfig.close();
		}
	}
	
	/** when reloading a game, reloads the personal bonus tile of the player
	 * 
	 * @param s - the string version of the tile
	 * @throws IOException if a problem occurs during the reloading process
	 */
	private void setTile(String s) throws IOException{
		int number=Integer.parseInt(s);
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/personalbonustilesadvanced.txt").getPath());
		inputFromFile=new BufferedReader(gameConfig);
		app=inputFromFile.readLine();
		for(int j=0;j<5;j++){
			app=inputFromFile.readLine();
			PersonalBonusTile tile= new PersonalBonusTile(app);
			if(tile.getNumber()==number){
				this.personalBonusTile=tile;
			}
		}
		inputFromFile.close();
		gameConfig.close();
	}
	
	/** when reloading a game, reloads the leader cards of the player
	 * 
	 * @param s - the leader cards' numbers in a string, separated by a ;
	 * @throws IOException if a problem occurs during the reloading process
	 */
	private void reloadLeaders(String s, boolean hand) throws IOException{
		if((" ").equals(s))
			return;
		String[] cards=s.split(";");
		FileReader gameConfig;
		BufferedReader inputFromFile;
		String app;
		for(int i=0;i<cards.length;i++){
			gameConfig=new FileReader(this.getClass().getResource("/it/polimi/ingsw/LM6/configuration/leaders.txt").getPath());
			inputFromFile=new BufferedReader(gameConfig);
			app=inputFromFile.readLine();
			for(int j=0;j<20;j++){
				app=inputFromFile.readLine();
				LeaderCard newCard=new LeaderCard(app);
				if(newCard.getNumber()==Integer.parseInt(cards[i])){
					if(hand)
						this.leaderCardsInHand.add(newCard);
					else{
						this.leaderCardsPlayed.add(newCard);
						newCard.activate(this);
					}			
					break;
				}
			}
			inputFromFile.close();
			gameConfig.close();
		}
	}
}
