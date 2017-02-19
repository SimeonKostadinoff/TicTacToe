README

Software Workshop - Tic-Tac-Toe client-server assessed exercise

Simeon Kostadinov - svk578 - 1549278

Commands: 
javac *.java
java Server <port>
java Client <user nickname> <port number> <machine name>

After starting the Clients: 

For each client, a lobby is opened - with three buttons 
 	- Refresh - must be clicked in order to see the online users
	- Scoreboard - show the points of every user (1 point for a win, 0 - draw and lose)
	- Connect - once a user is chosen, this button sends the request for game

2) Request is made 
	- the second user receives a pop-up window, where he can choose to accept or reject the request 
	- if he rejects the request, a pop-up message is shown to the first user, which tells that his opponent rejected the request, both users can choose new opponents again
	- if he accepts the offer for playing - two frames with board 3x3 are opened and the users start playing

3) The game ends 
	- if it is a draw, both windows are closed and only the users lobbies remain - the users can choose their next opponent
	- if one of the players wins, then all the field on the 3x3 grid become unable and the windows are closed again, but the lobbies remain: 
		- a pop-up messages are shown to see who won and who lost
		- the user who won adds 1 point to his score - his score can be seen in the Scoreboard

4) Additional features
	- if during the game one of the player decides to end the game , he simply click the red “X” button on the top of the frame and both his and his opponents’s grid are closed (not the lobbies)
	- if a new user sign in , but his nickname is the same as to someone who exists , the new user receives new name and is represented in the scoreboard and the list of users with it. (the new name i  simply the same with just added “1” in the back)
	- the red “X” button form the frame is transformed to exit button - when users end game they remain in the system and can continue making request
	- during a game of two users if a another users want to play with the ones who are still playing, their request are rejected automatically and a pop-up message appear
 