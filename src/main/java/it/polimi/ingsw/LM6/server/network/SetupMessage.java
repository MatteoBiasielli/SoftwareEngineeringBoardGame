package it.polimi.ingsw.LM6.server.network;

import it.polimi.ingsw.LM6.server.network.exception.InvalidFormatException;
import it.polimi.ingsw.LM6.server.users.IUser;

/** Represents the messages sent during the connection setup phase.
 * 	The raw messages take the form identifier;info;optional_info, with ";" usually being the used
 * 	splitchar. The defined messages (identifier, info, optional_info) are:
 * 	(LOGINREQUEST, username, password)
 * 	(LOGOUTREQUEST, logoutMessage, empty)
 * 	(SETUPMENU, reasonForSending, guiScoreboardInfo) guiScoreboardInfo is only sent when the reasonForSending contains a scoreboard.
 * 	(SHOWSCOREBOARD, from, to) according to the ranking
 * 	(SHOWRECORD, nicknameOfPlayerToFind, empty)
 * 	(STARTNEWGAME, usingAdvRules, maxNumPlayers)
 * 	(INVALIDLOGIN, reasonForSending, empty)
 * 
 * 	empty is represented as a space (" ")
 */
public class SetupMessage {
	private String header;
	private String info;
	private String optionalInfo;
	private String splitchar;
	private static final Integer[] ENCRYPTION_KEY = {12, 21, 3, 1, 4, 15, 14, 4, 15, 14, 9};
	private static final String VALID_CHAR_LIST = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	/******CONSTRUCTORS******/
	
	private SetupMessage(String header, String info, String optionalInfo, String splitchar) {
		SetupMessage.checkMessage(header, info, optionalInfo);
		
		this.header = header;
		this.info = info;
		
		if(SetupHeader.LOGINREQUEST.getDescription().equals(header))
			this.optionalInfo = SetupMessage.encrypt(optionalInfo);
		else
			this.optionalInfo = optionalInfo;
		
		this.splitchar = splitchar;
	}
	
	/******BUILDERS******/
	
	/**	It interpret the given String rawMessage with the given String splitchar to see if it can be turned into a valid SocketSetupMessage.
	 * 
	 * 	@param rawMessage - The String to interpret.
	 * 	@param splitchar - The String splitchar to use to interpret the String rawMessage.
	 * 
	 * 	@return The resulting SocketSetupMessage.
	 * 
	 * 	@throws InvalidFormatException if the String cannot be interpreted as a SocketSetupMessage.
	 * 
	 * 	@author Emilio
	 */
	public static SetupMessage interpret(String rawMessage, String splitchar) {
		String[] splitRawMessage = rawMessage.split(splitchar);
		
		if(splitRawMessage.length == 3)
			return new SetupMessage(splitRawMessage[0], splitRawMessage[1], splitRawMessage[2], splitchar);
		
		throw new InvalidFormatException("Too many or too less fields for a SocketSetupMessage.");
	}
	
	/**	It builds a SetupMessage with a LOGINREQUEST header.
	 * 
	 * 	@param nickname - The IUser's nickname.
	 * 	@param password - The IUser's password.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildLoginRequest(String nickname, String password){
		return new SetupMessage(SetupHeader.LOGINREQUEST.getDescription(), nickname, password, ";");
	}
	
	/**	It builds a SetupMessage with a LOGOUTREQUEST header.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildLogoutRequest(String logoutMessage){
		return new SetupMessage(SetupHeader.LOGOUTREQUEST.getDescription(), logoutMessage, " ", ";");
	}
	
	/**	It builds a SetupMessage with a SETUPMENU header.
	 * 
	 * 	@param info - The reason for sending the SocketSetupMessage.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildSetupMenu(String info, String optionalInfo){
		return new SetupMessage(SetupHeader.SETUPMENU.getDescription(), info, optionalInfo, ";");
	}
	
	/**	It builds a SetupMessage with a SHOWSCOREBOARD header.
	 * 
	 * 	@param from - The starting ranking position.
	 * 	@param to - The ending ranking position.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildShowScoreboard(Integer from, Integer to){
		return new SetupMessage(SetupHeader.SHOWSCOREBOARD.getDescription(), from.toString(), to.toString(), ";");
	}
	
	/**	It builds a SetupMessage with a SHOWRECORD header.
	 * 
	 * 	@param nickname - The nickname of the player to find.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildShowRecord(String nickname){
		return new SetupMessage(SetupHeader.SHOWRECORD.getDescription(), nickname, " ", ";");
	}
	
	/**	It builds a SetupMessage with a STARTNEWGAME header.
	 * 
	 * 	@param usingAdvRules - The Boolean that specifies if the players want to use the advanced rules or not.
	 * 	@param password - The Integer that specifies the maximum number of players to play with.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildStartNewGame(Boolean usingAdvRules, Integer maxNumOfPlayers){
		String usingAdvRulesToString = "0";
		if(usingAdvRules)
			usingAdvRulesToString = "1";
		return new SetupMessage(SetupHeader.STARTNEWGAME.getDescription(), usingAdvRulesToString, maxNumOfPlayers.toString(), ";");
	}
	
	/**	It builds a SetupMessage with an INVALIDLOGIN header.
	 * 
	 * 	@param info - The reason for sending the SocketSetupMessage.
	 * 
	 * 	@return The SetupMessage with the defined header and given information fields.
	 * 
	 *	@author Emilio
	 */
	public static SetupMessage buildInvalidLogin(String info){
		return new SetupMessage(SetupHeader.INVALIDLOGIN.getDescription(), info, " ", ";");
	}
	
	/******GETTERS******/
	
	/**	@author Emilio
	 */
	public String getInfo(){
		return this.info;
	}
	
	/**	@author Emilio
	 */
	public String getOptionalInfo(){
		return this.optionalInfo;
	}
	
	/**	@author Emilio
	 */
	public String getSplitchar(){
		return this.splitchar;
	}
	
	/******HEADER_CHECKERS******/
	
	/**	@author Emilio
	 */
	public boolean isLoginRequest(){
		return SetupHeader.LOGINREQUEST.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isLogoutRequest(){
		return SetupHeader.LOGOUTREQUEST.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isSetupMenu(){
		return SetupHeader.SETUPMENU.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isShowScoreboard(){
		return SetupHeader.SHOWSCOREBOARD.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isShowRecord(){
		return SetupHeader.SHOWRECORD.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isStartNewGame(){
		return SetupHeader.STARTNEWGAME.getDescription().equals(header);
	}
	
	/**	@author Emilio
	 */
	public boolean isInvalidLogin(){
		return SetupHeader.INVALIDLOGIN.getDescription().equals(header);
	}
	
	/******MESSAGE_CHECKERS******/
	
	/** Checks that the message's format is coherent with the SocketSetupMessage defined format.
	 * 
	 * 	@param header - The identifier to check.
	 * 	@param info - The info to check.
	 * 	@param optionalInfo - The optional info to check.
	 * 
	 * 	@throws InvalidFormatException when the the message format is invalid.
	 * 
	 * 	@author Emilio
	 */
	public static void checkMessage(String header, String info, String optionalInfo) {
		switch(header){
		
		case "LOGINREQUEST":
			SetupMessage.checkLoginRequest(info, optionalInfo);
			break;
			
		case "LOGOUTREQUEST":
			SetupMessage.checkLogoutRequest(optionalInfo);
			break;
				
		case "SHOWSCOREBOARD":
			SetupMessage.checkShowScoreboard(info, optionalInfo);
			break;
			
		case "SHOWRECORD":
			SetupMessage.checkShowRecord(info, optionalInfo);
			break;
				
		case "STARTNEWGAME":
			SetupMessage.checkStartNewGame(info, optionalInfo);
			break;
			
		case "INVALIDLOGIN":
			SetupMessage.checkInvalidLogin(optionalInfo);
			break;
			
		default:
			break;
		}
	}
	
	/**	Checks that the given String both has a length of at maximum 20 characters and only contains the characters defined in the validCharList.
	 * 
	 * 	@param info - The String to check.
	 * 
	 * 	@throws InvalidFormatException - If the length and character limits are not satisfied.
	 * 
	 * 	@author Emilio
	 */
	private static void checkCharValidity(String info){
		char[] infoChars = info.toCharArray();
		Character tmp;
		int i;
		
		if(info.length() > 20)
			throw new InvalidFormatException("The number of characters used exceeded the max number of characters for username and password, which is 20.");			
		
		for(i = 0; i < info.length(); i++){
			tmp = infoChars[i];
			if(!VALID_CHAR_LIST.contains(tmp.toString()) && !VALID_CHAR_LIST.toUpperCase().contains(tmp.toString()))
				throw new InvalidFormatException("Invalid characters used. Only letters and numbers are allowed.");
		}
	}
	
	/**	Sends the SocketSetupMessage through the PrintWriter, after turning it into a String.
	 * 
	 * 	@param out - The PrintWriter to send the SocketSetupMessage through.
	 * 
	 * 	@author Emilio
	 */
	public void send(IUser user){
		String rawMessage = this.toString();
		user.send(rawMessage);
	}
	
	/**	Turns the SocketSetupMessage into a String.
	 * 
	 * 	@return The raw form (String) of the given SocketSetupMessage.
	 * 
	 * 	@author Emilio
	 */
	@Override
	public String toString(){
		return header + splitchar + info + splitchar + optionalInfo;
	}
	
	/**	Checks that the validity of the LoginRequest fields.
	 * 
	 * 	@param info 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkLoginRequest(String info, String optionalInfo) throws InvalidFormatException{
		SetupMessage.checkCharValidity(info);
		SetupMessage.checkCharValidity(optionalInfo);
	}
	
	/**	Checks that the validity of the LogoutRequest fields.
	 * 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkLogoutRequest(String optionalInfo) throws InvalidFormatException{
		if(!" ".equals(optionalInfo))
			throw new InvalidFormatException("Not a valid LOGOUTREQUEST request.");
	}
	
	/**	Checks that the validity of the ShowScoreboard fields.
	 * 
	 * 	@param info 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkShowScoreboard(String info, String optionalInfo) throws InvalidFormatException{
		try{
			int from = Integer.parseInt(info);
			int to = Integer.parseInt(optionalInfo);
			
			if(!(from == 0 && to == 0) && !(from > 0 && from < to))
				throw new InvalidFormatException("Illegal numbers for scoreboard ranking.");
			
		}catch(NumberFormatException e){
			throw new InvalidFormatException("Insert two NUMBERS.");
		}
	}
	
	/**	Checks that the validity of the ShowRecord fields.
	 * 
	 * 	@param info 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkShowRecord(String info, String optionalInfo) throws InvalidFormatException{
		SetupMessage.checkCharValidity(info);
		
		if(!" ".equals(optionalInfo))
			throw new InvalidFormatException("Not a valid SHOWRECORD request.");
	}
	
	/**	Checks that the validity of the StartNewGame fields.
	 * 
	 * 	@param info 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkStartNewGame(String info, String optionalInfo) throws InvalidFormatException{
		if(!"1".equals(info) && !"0".equals(info))
			throw new InvalidFormatException("Invalid values for advanced rules. Only 0 and 1 are allowed.");	
		
		try{
			Integer.parseInt(optionalInfo);
		}catch(NumberFormatException e){
			throw new InvalidFormatException("Insert the max NUMBER of players.");
		}
		return;
	}
	
	/**	Checks that the validity of the InvalidLogin fields.
	 * 
	 * 	@param optionalInfo 
	 * 
	 * 	@author Emilio
	 */
	public static void checkInvalidLogin(String optionalInfo) throws InvalidFormatException{
		if(!(" ").equals(optionalInfo))
			throw new InvalidFormatException("Not a valid INVALIDLOGIN request.");
		return;
	}
	
	/**	Encrypts the given string using a Vigenère code book.
	 * 
	 * 	@param info - The string to encrypt.
	 * 
	 * 	@return The encrypted string.
	 * 
	 * 	@author Emilio
	 */
	public static String encrypt(String info){
		char[] toEncrypt = info.toCharArray();
		String encrypted = "";
		int index;
		char toAdd;
		int encryptionIndex;
		int i;
		
		for(i = 0; i < toEncrypt.length; i++){
			index = VALID_CHAR_LIST.indexOf(Character.toLowerCase(toEncrypt[i]));
			encryptionIndex = index + ENCRYPTION_KEY[i%(ENCRYPTION_KEY.length)];
			toAdd = VALID_CHAR_LIST.charAt(encryptionIndex%(VALID_CHAR_LIST.length()));
			
			if(Character.isUpperCase(toEncrypt[i]))
				encrypted += Character.toString(Character.toUpperCase(toAdd));
			else
				encrypted += Character.toString(toAdd);
		}
		
		return encrypted;
	}
}