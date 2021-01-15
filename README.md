# N-QueensGame
It is a game based on N-Queens problem solved using Backtracking where you have to place N queens on a N×N chessboard so that no two queens attack each other i.e. no two queens share the same row, column, or diagonal.
          
# Workflow

Provide a value for the size of the chessboard and click on the button "Start Game"

Place N queens on the NxN chessboard such that no two queens clash each other by being in the same row, column or diagonal

When we place a queen in a cell, check for clashes with already placed queens is performed.
First, cells below and above the cell with queen placed, are checked to identify clash in the same column.
Then, cells right and left to the cell with queen placed, are checked to identify clash in the same row.
Lastly, left and right diagonals are checked.
If either of the checks returns true, i.e., there's a clash, the threatened queens' cells will be marked with red color.

The game gets over when all N queens are placed according to the rule in the board.

# How to view code

Java files are located at: 
\NQueensGame\app\src\main\java\com\shrimantee\n_queensgame\

### MainActivity.java
  
This is the first activity where the user need to give input value of the chessboard size and then start the N-Queens Game.
The rules of the game is mentioned in the layout of this activity

### QueenGameActivity.java

This activity is dedicated to create the game board
 
### GameBoardView.java

The GameBoardView is the game board view populated at the QueenGameActivity. 



# How to run a sample
Clone or download the project open it with Android Studio compile and run it will work.

# Credits
https://www.geeksforgeeks.org/java-program-for-n-queen-problem-backtracking-3/

https://en.wikipedia.org/wiki/Eight_queens_puzzle

