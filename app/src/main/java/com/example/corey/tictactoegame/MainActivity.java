package com.example.corey.tictactoegame;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;



//implementing OnClickListener for Buttons//
class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //I decided to use a matrix instead of just calling all buttons
    // individually and creating a long list of winConditions.
    // I did do a little more in depth research on using 2D arrays
    // and it took a while to figure this out but I'm glad I did//
    private Button[][] buttonMatrix = new Button[3][3];




    private boolean p1Turn = true;

    private int turnAdvance;
    private int player1Score;
    private int player2Score;


    TextView textViewP1;
    TextView textViewP2;
    TextView gameStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //IDs the TextViews containing player scores and the game outcomes//
        textViewP1 = findViewById(R.id.p1Score);
        textViewP2 = findViewById(R.id.p2Score);
        gameStatus = findViewById(R.id.gameOutcome);



        //The loop for the game to be continuous until winConditions are met//
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {


                // I had to look this up, I didn't know how to
                // reference the button IDs for
                // each individual button using a matrix without typing each
                // out separately and I didn't see a section on
                // it in the text unless I overlooked//
                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttonMatrix[i][j] = findViewById(resourceID);
                buttonMatrix[i][j].setOnClickListener(this);

            }
        }


        // creating the click functionality for the New Game Button//
        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                newGameReset();
                gameStatus.setText("");

            }
        });


    }


    @Override
    //Checks to see if button pressed is an empty button or a
    // previously used button and returns a value if
    // empty depending on player turn//
    
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        // Creates text values for the buttons with either "X" or "O" depending on player turn//
        if (p1Turn) {
            ((Button) view).setText("X");
        }
        else {
            ((Button) view).setText("O");
        }
        //advances turns//
        turnAdvance++;

        //ends game in a draw if 9 turns are made//
        if (turnAdvance == 9) {
                gameDraw();
            }
            else {
                p1Turn = !p1Turn;
            }







        if (winConditions()) {
            if (p1Turn) {
                p2Wins();
            } else {
                p1Wins();
            }
        }

    }

    // creating the win method based on conditions within the matrix as stated below//
    private boolean winConditions() {
        String[][] matrix = new String[3][3];




        //creating the win conditions for the game covering all
        // horizontal, vertical, and diagonal possibilities//


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = buttonMatrix[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0].equals(matrix[i][1])
                    && matrix[i][0].equals(matrix[i][2])
                    && !matrix[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i].equals(matrix[1][i])
                    && matrix[0][i].equals(matrix[2][i])
                    && !matrix[0][i].equals("")) {
                return true;
            }
        }
        if (matrix[0][0].equals(matrix[1][1])
                && matrix[0][0].equals(matrix[2][2])
                && !matrix[0][0].equals("")) {
            return true;
        }
        if (matrix[0][2].equals(matrix[1][1])
                && matrix[0][2].equals(matrix[2][0])
                && !matrix[0][2].equals("")) {
            return true;
        }
        //continues the looping of the game until winConditions returns true//
        return false;
    }



    //creating a method to clear the board when a game ends//
    private void boardClear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonMatrix[i][j].setText("");
            }
        }
        //starts game at 0 turns and player 1's turn//
        turnAdvance = 0;
        p1Turn = true;

    }

    //creating a method to set the score text for each player//
    @SuppressLint("SetTextI18n")
    private void updateScoreText() {
        textViewP1.setText("Player One (X): " + player1Score);
        textViewP2.setText("Player Two (O): " + player2Score);
    }

    //creating a method to reset all score values and clear
    // board when the New Game button is pressed//
    private void newGameReset() {
        player1Score = 0;
        player2Score = 0;
        updateScoreText();
        boardClear();
    }

    //creating a method for player one winning//
    @SuppressLint("SetTextI18n")
    private void p1Wins() {
        player1Score++;
        gameStatus.setText("Player One (X) Wins!");
        updateScoreText();
        boardClear();
    }

    //creating a method for player two winning//
    @SuppressLint("SetTextI18n")
    private void p2Wins() {
        player2Score++;
        gameStatus.setText("Player Two (O) Wins!");
        updateScoreText();
        boardClear();
    }

    //creating a method for the game ending in a draw//
    @SuppressLint("SetTextI18n")
    private void gameDraw() {
        gameStatus.setText("Game Draw!");
        boardClear();
    }

    //I had to look this up as well. Wasn't certain with what I read in the text but
    // I do understand this is saving outputs within the app and reassigning
    // the same outputs when placed in a horizontal orientation and back to vertical//
    protected void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putInt("turnAdvance", turnAdvance);
        currentState.putInt("player1Score", player1Score);
        currentState.putInt("player2Score", player2Score);
        currentState.putBoolean("p1Turn", p1Turn);
    }

    protected void onRestoreInstanceState(Bundle newState) {
        super.onRestoreInstanceState(newState);

        turnAdvance = newState.getInt("turnAdvance");
        player1Score = newState.getInt("player1Score");
        player2Score = newState.getInt("player2Score");
        p1Turn = newState.getBoolean("p1Turn");
    }
}