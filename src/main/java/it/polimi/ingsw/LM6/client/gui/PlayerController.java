package it.polimi.ingsw.LM6.client.gui;

import it.polimi.ingsw.LM6.server.game.bonuses.ResourceSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerController {
	@FXML
	private Label nick;
	@FXML
	private Label col;
	@FXML
	private Label wood;
	@FXML
	private Label stone;
	@FXML
	private Label coin;
	@FXML
	private Label servant;
	@FXML
	private Label mp;
	@FXML
	private Label vp;
	@FXML
	private Label fp;
	@FXML
	private ImageView gc1;
	@FXML
	private ImageView gc2;
	@FXML
	private ImageView gc3;
	@FXML
	private ImageView gc4;
	@FXML
	private ImageView gc5;
	@FXML
	private ImageView gc6;
	@FXML
	private ImageView yc1;
	@FXML
	private ImageView yc2;
	@FXML
	private ImageView yc3;
	@FXML
	private ImageView yc4;
	@FXML
	private ImageView yc5;
	@FXML
	private ImageView yc6;
	@FXML
	private ImageView pc1;
	@FXML
	private ImageView pc2;
	@FXML
	private ImageView pc3;
	@FXML
	private ImageView pc4;
	@FXML
	private ImageView pc5;
	@FXML
	private ImageView pc6;
	@FXML
	private ImageView bc1;
	@FXML
	private ImageView bc2;
	@FXML
	private ImageView bc3;
	@FXML
	private ImageView bc4;
	@FXML
	private ImageView bc5;
	@FXML
	private ImageView bc6;
	@FXML
	private ImageView lc1;
	@FXML
	private ImageView lc2;
	@FXML
	private ImageView lc3;
	@FXML
	private ImageView lc4;
	@FXML
	private ImageView lpnp1;
	@FXML
	private ImageView lpnp2;
	@FXML
	private ImageView lpnp3;
	@FXML
	private ImageView lpnp4;
	@FXML
	private ImageView tile;
	
	public PlayerController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	@FXML
	public void initialize(){
		this.resetStage();
	}
	
	public void updatePlayer(String[] p, BoardController board){
		
		this.resetStage();
		this.nick.setText(p[0]);
		ResourceSet app=ResourceSet.parseResourceSet(p[1]);
		this.wood.setText(Integer.toString(app.getWood()));
		this.stone.setText(Integer.toString(app.getStone()));
		this.coin.setText(Integer.toString(app.getCoin()));
		this.servant.setText(Integer.toString(app.getServants()));
		this.mp.setText(Integer.toString(app.getMilitaryPoints()));
		this.vp.setText(Integer.toString(app.getVictoryPoints()));
		this.fp.setText(Integer.toString(app.getFaithPoints()));
		if(!(" ").equals(p[2]))
			this.updateGreenCards(p[2].split("-"));
		if(!(" ").equals(p[3]))
			this.updateYellowCards(p[3].split("-"));
		if(!(" ").equals(p[4]))
			this.updateBlueCards(p[4].split("-"));
		if(!(" ").equals(p[5]))
			this.updatePurpleCards(p[5].split("-"));
		this.updateLeaders(p[7],p[6],board);
		this.updateTile(p[8].split("ù"));
		
	}
	private void updateLeaders(String played, String hand, BoardController board) {
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\";
		if(!(" ").equals(played)){
			String[] app=played.split("-");
			for(int i=0;i<app.length;i++)
			{
				String[] leader=app[i].split("ù");
				if(i==0){
					this.lc4.setImage(new Image(path+leader[1]+".png"));
					this.lpnp4.setImage(new Image(path+"played.png"));
				}
				else if(i==1){
					this.lc3.setImage(new Image(path+leader[1]+".png"));
					this.lpnp3.setImage(new Image(path+"played.png"));
				}
				else if(i==2){
					this.lc2.setImage(new Image(path+leader[1]+".png"));
					this.lpnp2.setImage(new Image(path+"played.png"));
				}
				else if(i==3){
					this.lc1.setImage(new Image(path+leader[1]+".png"));
					this.lpnp1.setImage(new Image(path+"played.png"));
				}
			}
		}
		if(!(" ").equals(hand)){
			String[] app=hand.split("-");
			for(int i=0;i<app.length;i++)
			{
				String[] leader=app[i].split("ù");
				board.addLeader(Integer.parseInt(leader[1]));
				if(i==0){
					this.lc1.setImage(new Image(path+leader[1]+".png"));
					this.lpnp1.setImage(new Image(path+"notplayed.png"));
				}
				else if(i==1){
					this.lc2.setImage(new Image(path+leader[1]+".png"));
					this.lpnp2.setImage(new Image(path+"notplayed.png"));
				}
				else if(i==2){
					this.lc3.setImage(new Image(path+leader[1]+".png"));
					this.lpnp3.setImage(new Image(path+"notplayed.png"));
				}
				else if(i==3){
					this.lc4.setImage(new Image(path+leader[1]+".png"));
					this.lpnp4.setImage(new Image(path+"notplayed.png"));
				}
			}
		}
			
		
	}
	private void updateTile(String[] split) {
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\tiles\\";
		this.tile.setImage(new Image(path+split[1]+".png"));
		
	}
	private void updateGreenCards(String[] cards){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		for(int i=0;i<cards.length;i++){
			String[] split=cards[i].split("ù");
			if(i==0)
				this.gc1.setImage(new Image(path+split[0]+".png"));
			else if(i==1)
				this.gc2.setImage(new Image(path+split[0]+".png"));
			else if(i==2)
				this.gc3.setImage(new Image(path+split[0]+".png"));
			else if(i==3)
				this.gc4.setImage(new Image(path+split[0]+".png"));
			else if(i==4)
				this.gc5.setImage(new Image(path+split[0]+".png"));
			else if(i==5)
				this.gc6.setImage(new Image(path+split[0]+".png"));
		}
	}
	private void updateYellowCards(String[] cards){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		for(int i=0;i<cards.length;i++){
			String[] split=cards[i].split("ù");
			if(i==0)
				this.yc1.setImage(new Image(path+split[0]+".png"));
			else if(i==1)
				this.yc2.setImage(new Image(path+split[0]+".png"));
			else if(i==2)
				this.yc3.setImage(new Image(path+split[0]+".png"));
			else if(i==3)
				this.yc4.setImage(new Image(path+split[0]+".png"));
			else if(i==4)
				this.yc5.setImage(new Image(path+split[0]+".png"));
			else if(i==5)
				this.yc6.setImage(new Image(path+split[0]+".png"));
		}
	}
	private void updateBlueCards(String[] cards){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		for(int i=0;i<cards.length;i++){
			String[] split=cards[i].split("ù");
			if(i==0)
				this.bc1.setImage(new Image(path+split[0]+".png"));
			else if(i==1)
				this.bc2.setImage(new Image(path+split[0]+".png"));
			else if(i==2)
				this.bc3.setImage(new Image(path+split[0]+".png"));
			else if(i==3)
				this.bc4.setImage(new Image(path+split[0]+".png"));
			else if(i==4)
				this.bc5.setImage(new Image(path+split[0]+".png"));
			else if(i==5)
				this.bc6.setImage(new Image(path+split[0]+".png"));
		}
	}
	private void updatePurpleCards(String[] cards){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\cards\\";
		for(int i=0;i<cards.length;i++){
			String[] split=cards[i].split("ù");
			if(i==0)
				this.pc1.setImage(new Image(path+split[0]+".png"));
			else if(i==1)
				this.pc2.setImage(new Image(path+split[0]+".png"));
			else if(i==2)
				this.pc3.setImage(new Image(path+split[0]+".png"));
			else if(i==3)
				this.pc4.setImage(new Image(path+split[0]+".png"));
			else if(i==4)
				this.pc5.setImage(new Image(path+split[0]+".png"));
			else if(i==5)
				this.pc6.setImage(new Image(path+split[0]+".png"));
		}
	}

	
	
	private void resetStage(){
		String path="it\\polimi\\ingsw\\LM6\\client\\gui\\resources\\leaders\\backleaders.png";
		this.nick.setText("");
		this.col.setText("");
		this.wood.setText("");
		this.stone.setText("");
		this.coin.setText("");
		this.servant.setText("");
		this.mp.setText("");
		this.vp.setText("");
		this.fp.setText("");
		this.gc1.setImage(null);
		this.gc2.setImage(null);
		this.gc3.setImage(null);
		this.gc4.setImage(null);
		this.gc5.setImage(null);
		this.gc6.setImage(null);
		this.pc1.setImage(null);
		this.pc2.setImage(null);
		this.pc3.setImage(null);
		this.pc4.setImage(null);
		this.pc5.setImage(null);
		this.pc6.setImage(null);
		this.yc1.setImage(null);
		this.yc2.setImage(null);
		this.yc3.setImage(null);
		this.yc4.setImage(null);
		this.yc5.setImage(null);
		this.yc6.setImage(null);
		this.bc1.setImage(null);
		this.bc2.setImage(null);
		this.bc3.setImage(null);
		this.bc4.setImage(null);
		this.bc5.setImage(null);
		this.bc6.setImage(null);
		this.lc1.setImage(new Image(path));
		this.lc2.setImage(new Image(path));
		this.lc3.setImage(new Image(path));
		this.lc4.setImage(new Image(path));
		this.lpnp1.setImage(new Image(path));
		this.lpnp2.setImage(new Image(path));
		this.lpnp3.setImage(new Image(path));
		this.lpnp4.setImage(new Image(path));
	}
	public void setColor(String string) {
		if(("r").equals(string))
			this.col.setText("RED");
		else if(("p").equals(string))
			this.col.setText("PURPLE");
		else if(("y").equals(string))
			this.col.setText("YELLOW");
		else if(("g").equals(string))
			this.col.setText("GREEN");
		else if(("b").equals(string))
			this.col.setText("BLUE");
		else
			this.col.setText("UNDEFINED");
		
	}
}
