# Software Engineering Board Game
Software Engineering - Matteo Biasielli, Luca Dondoni and Emilio Capo's Final project - Politecnico di Milano

# The game
This is a Java implementation of the board game "Lorenzo Il Magnifico", from Cranio Creations.

# The project
The project includes:
- initial and final UML diagrams
- Implementation
- ReadMe & use instructions

# What does the implementation include?
Server:
- communication with clients using both RMI and Sockets
- cards' effects, game phases, excommunications and actions management
- leader cards effects and playing conditions
- possibility to save a resume games
- users registration and scoreboard
- multithreading used to run several games at the same time
- automatic game rooms creation and players matching when a user logs in
- usage tests covering more than 90% of the code

Client:
- CLI and GUI implementations both available
- communication with server using both RMI and Sockets. Software engineering practices applied to make communication invisible to the user interface
