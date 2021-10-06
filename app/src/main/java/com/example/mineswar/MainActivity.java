package com.example.mineswar;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean gameState;
    boolean first;
    Cell[][] board = new Cell[8][8];
    int bomb = 0;
    int reveCell = 0;
    boolean flagMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = true;
        first = true;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GridLayout cellGrid = (GridLayout) findViewById(R.id.cellGrid);
                board[i][j] = new Cell(this);
                board[i][j].i = i;
                board[i][j].j = j;
                board[i][j].setImageResource(R.drawable.cell);
                board[i][j].isShow = false;
                board[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Cell cell = (Cell) v;
                        int i = cell.i;
                        int j = cell.j;
                        if (gameState && !cell.isShow) {
                            if (flagMode) {
                                if (cell.changeFlag())
                                    cell.setImageResource(R.drawable.cell);
                                else
                                    cell.setImageResource(R.drawable.flag);
                            } else {
                                if (first) {
                                    first = false;
                                    for (int k = 0; k < 8; k++)
                                        for (int t = 0; t < 8; t++)
                                            setCell(k, t);

                                    cell.state = 0;   // the first cell must be diff than bomb
                                    setCellState();
                                    System.out.println(cell.state);
                                }

                                switch (cell.state) {
                                    case -1:
                                        gameState = false;
                                        cell.setImageResource(R.drawable.bomb);
                                        break;

                                    default:
                                        floodFill(cell.i, cell.j);

                                }

                                if (reveCell == 64 - bomb) {
                                    gameState = false;
                                    TextView won = (TextView) findViewById(R.id.text_won);
                                    won.setAlpha(1);
                                }
                            }
                        }
                    }
                });

                cellGrid.addView(board[i][j]);


            }

        }


    }

    public void flagButton(View view) {
        flagMode = !flagMode;
        ImageView flagButton=(ImageView) view;
        if(flagButton.getAlpha()==1)
            flagButton.setAlpha((float) 0.5);
        else
            flagButton.setAlpha((float) 1);

    }

    public void setCell(int i, int j) {
        Random objGenerator = new Random();
        int x = objGenerator.nextInt(20);
        if ((x == 2 || x == 10 || x == 18 || x == 13) && bomb < 10) {
            board[i][j].state = -1;
            bomb++;
        } else
            board[i][j].state = 0;


    }


    public void reSetCells(View view) {
        bomb = 0;
        reveCell = 0;
        gameState = true;
        first = true;
        flagMode = false;

        TextView won = (TextView) findViewById(R.id.text_won);
        won.setAlpha(0);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j].setImageResource(R.drawable.cell);
                board[i][j].isShow = false;
                board[i][j].flag = false;
            }


    }

    public void setCellState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].state == -1) {
                    if (i < 7 && board[i + 1][j].state != -1) board[i + 1][j].state++;
                    if (i > 0 && board[i - 1][j].state != -1) board[i - 1][j].state++;
                    if (i < 7 && j < 7 && board[i + 1][j + 1].state != -1)
                        board[i + 1][j + 1].state++;
                    if (i > 0 && j > 0 && board[i - 1][j - 1].state != -1)
                        board[i - 1][j - 1].state++;
                    if (j < 7 && board[i][j + 1].state != -1) board[i][j + 1].state++;
                    if (j > 0 && board[i][j - 1].state != -1) board[i][j - 1].state++;
                    if (i < 7 && j > 0 && board[i + 1][j - 1].state != -1)
                        board[i + 1][j - 1].state++;
                    if (i > 0 && j < 7 && board[i - 1][j + 1].state != -1)
                        board[i - 1][j + 1].state++;
                }


            }
        }

    }

    public void floodFill(int i, int j) {
        if (!board[i][j].isShow) {
            board[i][j].isShow = true;
            reveCell++;

            switch (board[i][j].state) {
                case 0:
                    board[i][j].setImageResource(R.drawable.blanc);
                    if (i < 7) floodFill(i + 1, j);
                    if (i > 0) floodFill(i - 1, j);
                    if (i < 7 && j < 7) floodFill(i + 1, j + 1);
                    if (i > 0 && j > 0) floodFill(i - 1, j - 1);
                    if (j < 7) floodFill(i, j + 1);
                    if (j > 0) floodFill(i, j - 1);
                    if (i < 7 && j > 0) floodFill(i + 1, j - 1);
                    if (i > 0 && j < 7) floodFill(i - 1, j + 1);
                    break;
                case 1:
                    board[i][j].setImageResource(R.drawable.one);
                    break;
                case 2:
                    board[i][j].setImageResource(R.drawable.two);
                    break;
                case 3:
                    board[i][j].setImageResource(R.drawable.three);
                    break;
                case 4:
                    board[i][j].setImageResource(R.drawable.four);
                    break;
                case 5:
                    board[i][j].setImageResource(R.drawable.five);
                    break;
                case 6:
                    board[i][j].setImageResource(R.drawable.six);
                    break;

            }
        }
    }


}



