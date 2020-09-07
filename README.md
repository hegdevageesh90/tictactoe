# Tic Tac Toe 
A REST API based n*n Tic-Tac-Toe game designed using SpringBoot framework for a person to play against a computer. To win, a player must place n of his/her symbols on n squares that line up vertically, horizontally or diagonally.

## APIs Exposed
1. Start a new game
2. Get current state
3. Ask for board size 3x3, 5x5 etc.
4. Play next move
5. If this step / move wins the game

## Details

1. Board sizes only of odd numbers are supported.
2. The implementation of the game is Java.
3. Although code has descriptive variables and is self-explanatory ,inline comments are there for explanation wherever necessary.

## Assumptions

1. Mark for the player is : X; Mark for the computer is : O.
2. Right after player's turn, computer makes it's mark and the API response has the updated board.