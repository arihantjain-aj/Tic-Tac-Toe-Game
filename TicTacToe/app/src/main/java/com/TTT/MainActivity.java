package com.TTT;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.TTT.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton[] cells = new ImageButton[9];
    private boolean isPlayerX = true;
    private boolean gameActive = true;
    private MediaPlayer playerXWin;
    private MediaPlayer playerOWin;
    private MediaPlayer drawSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerXWin = MediaPlayer.create(this, R.raw.win_sound);
        playerOWin = MediaPlayer.create(this, R.raw.win_sound);
        drawSound = MediaPlayer.create(this, R.raw.draw_sound);


        for (int i = 0; i < 9; i++) {
            String cellID = "cells" + i;
            int resID = getResources().getIdentifier(cellID, "id", getPackageName());
            cells[i] = findViewById(resID);
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridCellClick(v);
                }
            });
        }

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }



    private void checkForWinner() {
        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (cells[i * 3].getDrawable() != null &&
                    cells[i * 3 + 1].getDrawable() != null &&
                    cells[i * 3 + 2].getDrawable() != null &&
                    cells[i * 3].getDrawable().getConstantState() != null &&
                    cells[i * 3].getDrawable().getConstantState().equals(cells[i * 3 + 1].getDrawable().getConstantState()) &&
                    cells[i * 3].getDrawable().getConstantState().equals(cells[i * 3 + 2].getDrawable().getConstantState())) {
                endGame(cells[i * 3].getDrawable());
                return;
            }
            // Check columns
            if (cells[i].getDrawable() != null &&
                    cells[i + 3].getDrawable() != null &&
                    cells[i + 6].getDrawable() != null &&
                    cells[i].getDrawable().getConstantState() != null &&
                    cells[i].getDrawable().getConstantState().equals(cells[i + 3].getDrawable().getConstantState()) &&
                    cells[i].getDrawable().getConstantState().equals(cells[i + 6].getDrawable().getConstantState())) {
                endGame(cells[i].getDrawable());
                return;
            }
        }

        // Check diagonals
        if (cells[0].getDrawable() != null &&
                cells[4].getDrawable() != null &&
                cells[8].getDrawable() != null &&
                cells[0].getDrawable().getConstantState() != null &&
                cells[0].getDrawable().getConstantState().equals(cells[4].getDrawable().getConstantState()) &&
                cells[0].getDrawable().getConstantState().equals(cells[8].getDrawable().getConstantState())) {
            endGame(cells[0].getDrawable());
            return;
        }
        if (cells[2].getDrawable() != null &&
                cells[4].getDrawable() != null &&
                cells[6].getDrawable() != null &&
                cells[2].getDrawable().getConstantState() != null &&
                cells[2].getDrawable().getConstantState().equals(cells[4].getDrawable().getConstantState()) &&
                cells[2].getDrawable().getConstantState().equals(cells[6].getDrawable().getConstantState())) {
            endGame(cells[2].getDrawable());
            return;
        }

        // Check for a draw
        boolean isBoardFull = true;
        for (ImageButton cell : cells) {
            if (cell.getDrawable() == null) {
                isBoardFull = false;
                break;
            }
        }

        if (isBoardFull) {
            endGame(null); // It's a draw
        }
    }


    private void endGame(Drawable winner) {
        gameActive = false;

        if (winner == null) {
            // It's a draw
            // Play draw sound
            drawSound.start();
            showMessage("It's a draw!");
        } else if (winner.getConstantState().equals(getResources().getDrawable(R.drawable.x_icon).getConstantState())) {
            // Player X wins
            // Play player X win sound
            playerXWin.start();
            showMessage("Player X wins!");
        } else {
            // Player O wins
            // Play player O win sound
            playerOWin.start();
            showMessage("Player O wins!");
        }

        // Disable all cells to prevent further clicks
        for (ImageButton cell : cells) {
            cell.setClickable(false);
        }
    }


    private void showMessage(String message) {
        // Display a message to the user, e.g., in a Toast or an AlertDialog
        // Here, we use a simple Toast to display the message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    private void resetGame() {
        for (ImageButton cell : cells) {
            cell.setImageResource(0); // Clear the image
            cell.setClickable(true);
        }

        playerXWin.release();
        playerOWin.release();
        drawSound.release();

        isPlayerX = true;
        gameActive = true;
    }

    public void onGridCellClick(View view) {
        ImageButton cell = (ImageButton) view;

        if (!gameActive)
            return;

        if (cell.getDrawable() == null) {
            int imageResource = isPlayerX ? R.drawable.x_icon : R.drawable.o_icon;
            cell.setImageResource(imageResource);
            cell.setClickable(false);
            isPlayerX = !isPlayerX;
            checkForWinner();
        }
    }
}
