package it.polimi.ingsw.LM6.server.game.scores;

public class Record{
	private int ranking;
	private String nickname;
	private int wins;
	private int draws;
	private int losses;
	private int totalVPs;
	private String time;
	
	private static final String SPLITCHAR = "/";
	
	/******CONSTRUCTORS******/
	
	/**	@author Emilio
	 */
	public Record(int ranking, String nickname, int wins, int draws, int losses, int totalVPs, String time){
		this.ranking = ranking;
		this.nickname = nickname;
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
		this.totalVPs = totalVPs;
		this.time = time;
	}
	
	/******PRODUCTORS******/
	
	/**	It creates a Record object from a String given as a parameter.
	 * 
	 * 	@param - The String to convert.
	 * 	
	 * 	@return The Record which corresponds to the given String.
	 * 
	 * 	@author Emilio
	 */
	public static Record parseRecord(String record){
		String[] stringRecord = record.split(SPLITCHAR);
		int ranking = Integer.parseInt(stringRecord[0]);
		String nickname = stringRecord[1];
		int wins = Integer.parseInt(stringRecord[2]);
		int draws = Integer.parseInt(stringRecord[3]);
		int losses = Integer.parseInt(stringRecord[4]);
		int totalVPs = Integer.parseInt(stringRecord[5]);
		String time = stringRecord[6];
		
		return new Record(ranking, nickname, wins, draws, losses, totalVPs, time);
	}
	
	/******OBSERVERS******/
	
	/**	@author Emilio
	 */
	public int getRanking(){
		return this.ranking;
	}
	
	/**	@author Emilio
	 */
	public String getNickname(){
		return this.nickname;
	}
	
	/**	@author Emilio
	 */
	public int getWins(){
		return this.wins;
	}
	
	/**	@author Emilio
	 */
	public int getDraws(){
		return this.draws;
	}
	
	/**	@author Emilio
	 */
	public int getLosses(){
		return this.losses;
	}
	
	/**	@author Emilio
	 */
	public int getTotalVPs(){
		return this.totalVPs;
	}
	
	/**	@author Emilio
	 */
	public String getTime(){
		return this.time;
	}
	
	/**	Compares the two Records, returning true if they are equal, false if they are not.
	 * 
	 * 	@param - The Record to compare.
	 * 
	 * 	@return - true, if each attribute of the first record is equal to the corresponding attribute of the second record | false, otherwise
	 * 
	 * 	@author Emilio
	 */
	public boolean equalTo(Record record){
		boolean result = true;
		
		if(record == null)
			result = false;
		else if(this.ranking != record.getRanking())
			result = false;
		else if(!this.nickname.equals(record.getNickname()))
			result = false;
		else if(this.wins != record.getWins())
			result = false;
		else if(this.draws != record.getDraws())
			result = false;
		else if(this.losses != record.getLosses())
			result = false;
		else if(this.totalVPs != record.getTotalVPs())
			result = false;
		else if(!this.time.equals(record.getTime()))
			result = false;

		return result;
	}
	
	/**	Compares the two Records, progressively considering different attributes, in the following order:
	 * 	1.Wins -> 2.Draws -> 3.Losses -> 4.TotalVPs.
	 * 
	 * 	@param record - The Record to compare.
	 * 
	 * 	@return 1, if the Record on which the method is called is in a higher position | 0, if both record are in the same position | -1, otherwise
	 * 
	 * 	@author Emilio
	 */
	public int compareTo(Record record){
		int result = 0;
		
		if(this.wins > record.getWins())
			result = 1;
		else if(this.wins < record.getWins())
			result = -1;
		
		else if(this.draws > record.getDraws())
			result = 1;
		else if(this.draws < record.getDraws())
			result = -1;
		
		else if(this.losses < record.getLosses())
			result = 1;
		else if(this.losses > record.getLosses())
			result = -1;
		
		else if(this.totalVPs > record.getTotalVPs())
			result = 1;
		else if(this.totalVPs < record.getTotalVPs())
			result = -1;
		
		return result;
	}
	
	/******MODIFIERS******/
	
	/**	Updates the Record according to the given data.
	 * 
	 * @param wins - The number of victories to add.
	 * @param draws - The number of draws to add.
	 * @param losses - The number of defeats to add.
	 * @param totalVPS - The total number of VPs to add.
	 * @param time - The time to add.
	 * 
	 * @author Emilio
	 */
	public void update(int wins, int draws, int losses, int totalVPs, String time){
		//Updating wins, losses, draws and totalVPs.
		this.wins += wins;
		this.draws += draws;
		this.losses += losses;
		this.totalVPs += totalVPs;
		
		//Updating time.
		String[] recordTime = this.time.split(":");
		int recordHours = Integer.parseInt(recordTime[0]);
		int recordMinutes = Integer.parseInt(recordTime[1]);
		
		String[] updatesTime = time.split(":");
		int updatesHours = Integer.parseInt(updatesTime[0]);
		int updatesMinutes = Integer.parseInt(updatesTime[1]);
		
		int newMinutes = recordMinutes + updatesMinutes;
		if(newMinutes >= 60){
			newMinutes -= 60;
			recordHours++;
		}
		int newHours = recordHours + updatesHours;
		
		String newTime = Integer.toString(newHours) + ":";
		
		if(newMinutes < 10)
			newTime += Integer.toString(0);
		newTime += Integer.toString(newMinutes);
		
		this.time = newTime;
	}
	
	/**	Sets the value of ranking.
	 * 
	 * 	@param ranking - The value to set.
	 * 
	 * 	@author Emilio
	 */
	public void setRanking(int ranking){
		if(ranking < 1)
			this.ranking = 1;
		else
			this.ranking = ranking;
	}
	
	/**	Increases by one the number of wins.
	 * 
	 * 	@author Emilio
	 */
	public void incrementWins(){
		this.wins++;
	}
	
	/**	Increases by one the number of draws.
	 * 
	 * 	@author Emilio
	 */
	public void incrementDraws(){
		this.draws++;
	}
	
	/**	Increases by one the number of losses.
	 * 
	 * 	@author Emilio
	 */
	public void incrementLosses(){
		this.losses++;
	}
	
	/******PARSERS******/
	
	/**	Turns the given Record into a String.
	 * 
	 * 	@author Emilio
	 */
	@Override
	public String toString(){
		String stringRanking = Integer.toString(this.ranking) + SPLITCHAR;
		String stringWins = Integer.toString(this.wins) + SPLITCHAR;
		String stringDraws = Integer.toString(this.draws) + SPLITCHAR;
		String stringLosses = Integer.toString(this.losses) + SPLITCHAR;
		String stringTotalVPs = Integer.toString(this.totalVPs) + SPLITCHAR;
		
		return stringRanking + nickname + SPLITCHAR + stringWins + stringDraws + stringLosses + stringTotalVPs + time;
	}
	
	/**	Turns the given long into the time format used by the Record class.
	 * 
	 * @param timemillis - The long to convert.
	 * 
	 * @return A string that represents the given time expressed in the Record class format.
	 * 
	 * @author Emilio
	 */
	public static String parseRecordTimeFormat(long timemillis){
		final int millisecPerMinute = 60000;
		final int minutesPerHour = 60;
		
		long timeInMinutes = timemillis/millisecPerMinute;
		long timeHours = timeInMinutes/minutesPerHour;
		long timeMinutes = timeInMinutes - timeHours*minutesPerHour;
		
		String stringTimeHours = Long.toString(timeHours);
		String stringTimeMinutes = "";
		
		if(timeMinutes < 10)
			stringTimeMinutes += "0";
		stringTimeMinutes += Long.toString(timeMinutes);
		
		return stringTimeHours + ":" + stringTimeMinutes;
	}
}