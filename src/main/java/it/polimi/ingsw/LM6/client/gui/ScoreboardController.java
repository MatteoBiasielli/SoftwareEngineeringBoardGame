package it.polimi.ingsw.LM6.client.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScoreboardController extends Controller{
	@FXML
	private Label title;
	@FXML
	private Label record1;
	@FXML
	private Label record2;
	@FXML
	private Label record3;
	@FXML
	private Label record4;
	@FXML
	private Label record5;
	@FXML
	private Label record6;
	@FXML
	private Label record7;
	@FXML
	private Label record8;
	@FXML
	private Label record9;
	@FXML
	private Label record10;
	@FXML
	private Label record11;
	@FXML
	private Label record12;
	@FXML
	private Label record13;
	@FXML
	private Label record14;
	@FXML
	private Label record15;
	@FXML
	private Label record16;
	@FXML
	private Label record17;
	@FXML
	private Label record18;
	@FXML
	private Label record19;
	@FXML
	private Label record20;
	@FXML
	private Label record21;
	@FXML
	private Label record22;
	@FXML
	private Label record23;
	private ArrayList<String> records;
	private int page;
	public ScoreboardController(){
		//EMPTY BECAUSE NO INITIALIZATION IS NECESSARY. THE PARAMETERS ARE SET EVERYTIME THE STAGE IS SHOWED
	}
	
	@FXML
	public void initialize(){
		this.reset();
		page=0;
	}
	public void onNextClick(){
		int max;
		if(records.size()%23==0)
			max=records.size()/23-1;
		else
			max=(int)(records.size()/23);
		if(page<max)
		{
			page++;
			showRecords();
		}
	}
	public void onBackClick(){
		if(page>0){
			page--;
			showRecords();
		}
	}
	private void showRecords(){
		this.resetLabels();
		for(int i=23*page, j=1;i<this.records.size() && i<23*(page+1);i++,j++)
		{
			if(j==1)
				this.record1.setText(this.records.get(i));
			else if(j==2)
				this.record2.setText(this.records.get(i));
			else if(j==3)
				this.record3.setText(this.records.get(i));
			else if(j==4)
				this.record4.setText(this.records.get(i));
			else if(j==5)
				this.record5.setText(this.records.get(i));
			else if(j==6)
				this.record6.setText(this.records.get(i));
			else if(j==7)
				this.record7.setText(this.records.get(i));
			else if(j==8)
				this.record8.setText(this.records.get(i));
			else if(j==9)
				this.record9.setText(this.records.get(i));
			else if(j==10)
				this.record10.setText(this.records.get(i));
			else if(j==11)
				this.record11.setText(this.records.get(i));
			else if(j==12)
				this.record12.setText(this.records.get(i));
			else if(j==13)
				this.record13.setText(this.records.get(i));
			else if(j==14)
				this.record14.setText(this.records.get(i));
			else if(j==15)
				this.record15.setText(this.records.get(i));
			else if(j==16)
				this.record16.setText(this.records.get(i));
			else if(j==17)
				this.record17.setText(this.records.get(i));
			else if(j==18)
				this.record18.setText(this.records.get(i));
			else if(j==19)
				this.record19.setText(this.records.get(i));
			else if(j==20)
				this.record20.setText(this.records.get(i));
			else if(j==21)
				this.record21.setText(this.records.get(i));
			else if(j==22)
				this.record22.setText(this.records.get(i));
			else if(j==23)
				this.record23.setText(this.records.get(i));
			
		}
	}
	public void setRecords(String mess){
		this.records=new ArrayList<>();
		String[] app=mess.split("!");
		for(int i=0;i<app.length;i++){
			String[] rec=app[i].split("/");
			this.records.add(rec[0]+ " - Nick: "+ rec[1]+ "   Wins: "+ rec[2] +"   Draws: " + rec[3]+"   Losses: "+ rec[4]+ "   TotalVP:"+ rec[5]+"   Time: "+ rec[6]);
		}
		showRecords();
			
	}
	public void setTitle(String text){
		this.title.setText(text);
	}
	public void reset(){
		page=0;
		this.resetLabels();
	}
	private void resetLabels(){
		this.record1.setText("");
		this.record2.setText("");
		this.record3.setText("");
		this.record4.setText("");
		this.record5.setText("");
		this.record6.setText("");
		this.record7.setText("");
		this.record8.setText("");
		this.record9.setText("");
		this.record10.setText("");
		this.record11.setText("");
		this.record12.setText("");
		this.record13.setText("");
		this.record14.setText("");
		this.record15.setText("");
		this.record16.setText("");
		this.record17.setText("");
		this.record18.setText("");
		this.record19.setText("");
		this.record20.setText("");
		this.record21.setText("");
		this.record22.setText("");
		this.record23.setText("");
	}
}
