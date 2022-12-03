package com.example.xmixdrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // The state of every box in the game by id
    // 0 => Empty, 1 => X, 2 => O
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Player 1 => X, 2 => O
    int currentPlayer = 1;

    // All the win positions in array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}}; // oblique lines

    public int turnsCounter = 0;
    boolean isGameActive = true;
    ImageView box0;
    ImageView box1;
    ImageView box2;
    ImageView box3;
    ImageView box4;
    ImageView box5;
    ImageView box6;
    ImageView box7;
    ImageView box8;

    // when player tapped on box of the grid
    public void playerTap(View view) {
        ImageView status = findViewById(R.id.turnStatus);
        ImageView tappedImg = (ImageView) view;
        int tappedImgId = Integer.parseInt(tappedImg.getTag().toString());
        Log.d("TAG", "The current player is (1=X 2=O):" + currentPlayer );
        Log.d("TAG","tapped on box number " + tappedImgId);

        // new turn - if the tapped image is empty
        if (gameState[tappedImgId] == 0) {
            Log.d("TAG", "turn's number " + turnsCounter);
            turnsCounter++;

            // mark this position
            gameState[tappedImgId] = currentPlayer;
            tappedImg.setTranslationY(-1000f);

            // change the active player
            // from 0 to 1 or 1 to 0
            if (currentPlayer == 1) {
                // set the image to x
                tappedImg.setImageResource(R.drawable.x);
                currentPlayer = 2;
                status.setImageResource((R.drawable.oplay));
            } else {
                // set the image to o
                tappedImg.setImageResource(R.drawable.o);
                currentPlayer = 1;
                status.setImageResource((R.drawable.xplay));
            }
            checkWon();
            tappedImg.animate().translationYBy(1000f).setDuration(10);
        }
    }

    public void checkWon() {
        int countForWinnerLine = 0;
        ImageView status = findViewById(R.id.turnStatus);
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] != 0 &&
                    gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]]) {
                showWinnerLine(winPosition, countForWinnerLine);
                isGameActive = false;
                if (gameState[winPosition[0]] == 1) {
                    status.setImageResource(R.drawable.xwin);
                    Log.d("TAG", "X wins");
                } else if (gameState[winPosition[0]] == 2) {
                    status.setImageResource(R.drawable.owin);
                    Log.d("TAG", "O wins");
                }
                return;
            }
            countForWinnerLine++;
        }

        // check if the last turn
        if (turnsCounter == 9) {
            status.setImageResource(R.drawable.nowin);
            isGameActive = false;
            Log.d("TAG", "No win");
        }
    }

    public void showWinnerLine(int[] winPosition, int countForWinnerLine) {
        int winningLine;
        ImageView[] boxes = { box0, box1, box2, box3, box4, box5, box6, box7, box8 };

        // Check if the line is row, column or diagonal
        if(countForWinnerLine <= 2) {
            winningLine = R.drawable.horizontal_line;
        } else if (countForWinnerLine <= 5) {
            winningLine = R.drawable.vertical_line;
        } else if (countForWinnerLine == 6 ){
            winningLine = R.drawable.diagonal_line_left;
        } else {
            winningLine = R.drawable.diagonal_line_right;
        }

        // Set background for the winning boxes
        boxes[winPosition[0]].setBackgroundResource(winningLine);
        boxes[winPosition[1]].setBackgroundResource(winningLine);
        boxes[winPosition[2]].setBackgroundResource(winningLine);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        box0 = findViewById(R.id.square1);
        box1 = findViewById(R.id.square2);
        box2 = findViewById(R.id.square3);
        box3 = findViewById(R.id.square4);
        box4 = findViewById(R.id.square5);
        box5 = findViewById(R.id.square6);
        box6 = findViewById(R.id.square7);
        box7 = findViewById(R.id.square8);
        box8 = findViewById(R.id.square9);
    }
}