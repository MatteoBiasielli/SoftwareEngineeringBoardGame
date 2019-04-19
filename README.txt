0- CLI COMMANDS
1- HOW TO RUN THE GAME
2- DISCONNECTION & INACTIVITY
3- SAVED GAMES
4- BONUS RENOUNCE
5- 5th PLAYER RULES
6- SCOREBOARD

-----------------------------------------------------

0- CLI COMMANDS
To perform an action using the CLI, commands and choices, whether they're not numbers,
must be written in uppercase. 
Here's a list of accepted words:
Towers: GREEN, YELLOW, BLUE, PURPLE
Familiars: BLACK, ORANGE, WHITE, UNCOLOURED
Market spaces: 0,1,2,3 (2 and 3 are available only if there are 4 or 5 players)
Work Area: 1= Small Space     2= Big Space (2 is available only if there are more than 2 players)
 

-----------------------------------------------------

1- HOW TO RUN THE GAME

We exported the compiled game and put it in the "executable" folder. No installation/set-up
procedures are required to run our game.
We noticed that the client requires 1024Mb of maximum heap size to correctly run the GUI. For this reason, we
launch the server and the client with the -Xmx1024m command.

After a game reaches the minimum number of two players, it starts when then proper timeout expires (currently set to 30s).

To make it easier for you to test the features we implemented, we put two saved games in the folder,
so that when you run the server it will load them automatically.
One of them has been created with simplified rules (no leaders) with the players a (password : y), b( password: z) 
and c(password: c).
The other one has been created with advanced rules (with leaders) and with the players d( password: d) and 
e (password: e).

If you wish to run one of those games, just login with the mentioned accounts. Both games have been saved after the first
excommunication phase, so they will restart from turn 3. Some players already have some cards.
If you wish to start a new game, please create new accounts (see the LOGIN section for more information).

-----------------------------------------------------

2- LOGIN
The login procedure requires the users to enter a username and a password.
If there's no user with that username, the request will be interpreted as a registration request.
If the username is already present, the request will be interpreted as a login request.
Passwords are never saved directly. Instead, they are encrypted using a Vigenère code book and
saved only after. 
The login procedure only has a local effect. This means that, after login, users are still not added
to the connected users list. This allows for multiple connections from the same account. The official
connection is registered only when the user decides to start a game. We might as well say that, in
our game, only playing users are considered connected.

-----------------------------------------------------

3- DISCONNECTION & INACTIVITY
We made no assumptions on the disconnection procedure.
Players can disconnect during the game how many times they want and
they can reconnect with a different connection method (SOCKET/RMI) if they wish so.
When a player disconnects he is automatically considered "disconnected" and "inactive",
which means that all his turn are automatically skipped and all other players are updated on its status.

When a player's turn timer expires, the former is automatically considered "inactive" (but not "disconnected"),
and all his turns are automatically skipped as well, until the player sends any message, which will cause the 
server to reset its status to "active".

-----------------------------------------------------

4- SAVED GAMES
We assumed the saving procedure as a guarantee for the players, which allows them to restart a game from 
a checkpoint if a problem occurs to the server while they are playing.
Each game is saved after every excommunication phase and the save file is deleted when a game ends.
All the saved games, if present, are reloaded when the server is started and, when a player decides to start a new game,
if he was playing a game that has been reloaded, he is automatically put in that game. When at least 2 players
reconnect into a reloaded game a timer is started and when it expires the reloaded game is enabled to start again
from the last excommunication phase.

-----------------------------------------------------

5- BONUS RENOUNCE
Even if this is not possible in the board game, we decided to give to the players the possibility to renounce to
the council bonuses and bonus actions.
This was necessary to avoid deadlocks that could happen when a player receives a bonus action and he can't
perform it successfully because he has 0 slaves.
E.G. : the leader "Leonardo da Vinci" gives a bonus production with base value 0. If the player had 0 slaves he
would continuously receive the message "Your value is too low to perform the action".

-----------------------------------------------------

6- 5th PLAYER RULES
To implement the 5th player, we decided to add a new game phase, called the "Familiar Offer Phase".
In a game with 5 players, each player only has 3 familiars: the white one, the uncolored one and 
the orange one.
At the first turn and after every excommunication, all the players are called to offer a number of resources
from their resource sets. The offers are secret and the black familiar is assigned to the player that
offered the highest number of resources (in total: woods + stones + coins+ slaves).
Obviously, the winner looses the offered resources. In case there's a tie, the winner is chosen randomly.
The player that won the Familair Offer Phase, in the end, gets one extra turn (the back familiar's value
is decided from the dice, as always) after all the other turns at the cost of the offered resources.
The rest of the game proceeds normally.

E.G. : in a game with the players A, B, C, D and E, where A won the Familiar Offer Phase and the turn order
is B A C D E in a certain turn, players will perform their actions in this order: B A C D E B A C D E B A C D E A.
This allows all the players to place all their familiars.

-----------------------------------------------------

7- SCOREBOARD

The scoreboard allows to save data about players, such as number of victories, losses, draws, etc, keeping track
of the global ranking.
The scoreboard is loaded only once, when the server is launched, and saved each time it is modified. The loading
procedure does not only load the scoreboard, but it also sorts it. This is to prevent that file edits may lead to
an unsorted scoreboard to be displayed.
The scoreboard can be displayed from the pregame menu, by asking either for a specific ranking gap ((0,0) couple allows
for the whole scoreboard to be displayed), or for a record belonging to a specific player.
At the end of each game, the global record of each player belonging to that game is displayed.
Though the specification explicitly asked for the whole scoreboard to be displayed after the end of the game,
we thought that displaying potentially thousands of record on a CLI interface would not be that pleasant.