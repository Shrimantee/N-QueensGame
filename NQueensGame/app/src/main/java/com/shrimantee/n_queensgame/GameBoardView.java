package com.shrimantee.n_queensgame;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * The GameBoardView is the game board view populated at the {@link QueenGameActivity}.
 */
@SuppressLint("ViewConstructor")
public class GameBoardView extends View {


    int screenHeight;
    int screenWidth;

    static int startYPos;
    static int startXPos;

    int noOfQueens;
    int movesCount;

    int rows, columns;
    int sizeOfEachCell;

    boolean[][] placeQueen;
    boolean[][] queenNotAllowed;

    Context context;
    @SuppressLint("UseCompatLoadingForDrawables")
    Drawable queenIcon = getResources().getDrawable(R.drawable.queen);
    Paint paint;
    TextView movesTV;

    public GameBoardView(Context context, int noOfQueens, int screenHeight, int screenWidth, TextView tv_Moves) {
        super(context);

        this.context = context;
        this.movesTV = tv_Moves;
        this.noOfQueens = rows = columns = noOfQueens;

        //get window screen height and width
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        queenNotAllowed = new boolean[rows][columns];
        placeQueen = new boolean[rows][columns];

        // reset
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                placeQueen[i][j] = false;
                queenNotAllowed[i][j] = false;
            }
        }


        movesCount = 0;

        sizeOfEachCell = Math.min(screenHeight / rows, screenWidth / columns);
        startXPos = 0;
        startYPos = 0;


        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {


        int xPos = startXPos;
        int yPos = startYPos;

        int k = 0;

        // drawing the chess board
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                paint.setStyle(Paint.Style.FILL);

                if (k % 2 == 0) {

                    paint.setColor(Color.LTGRAY);
                } else {

                    paint.setColor(Color.WHITE);
                }
                canvas.drawRect(xPos, yPos, xPos + sizeOfEachCell, yPos + sizeOfEachCell, paint);

                if (queenNotAllowed[i][j]){ // if a queen is being threatened those cells will be bordered red
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                }
                else{
                    paint.setColor(Color.LTGRAY);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);
                }

                canvas.drawRect(xPos, yPos, xPos + sizeOfEachCell, yPos + sizeOfEachCell, paint);

                if (placeQueen[i][j]) {
                    queenIcon.setBounds(xPos, yPos, xPos + sizeOfEachCell, yPos + sizeOfEachCell);
                    queenIcon.draw(canvas);
                }


                k++;
                xPos += sizeOfEachCell;
            }
            xPos = startXPos;
            yPos += sizeOfEachCell;
            if (rows % 2 == 0)
                k++;
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            markQueen(event.getX(), event.getY());
        }

        return true;
    }

    @SuppressLint("SetTextI18n")
    public void markQueen(float xTouchPos, float yTouchPos) {

        int xPos = startXPos;
        int yPos = startYPos;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {


                if (xTouchPos > xPos && xTouchPos < xPos + sizeOfEachCell && yTouchPos > yPos && yTouchPos < yPos + sizeOfEachCell) {


                    if (placeQueen[i][j]) // if queen is already placed, it will be removed
                    {
                        placeQueen[i][j] = false;
                        noOfQueens++;
                        movesCount++;
                        movesTV.setText("Moves Made : " + movesCount);
                        checkQueenPlacementAllowed();
                        invalidate();
                    } else if (noOfQueens > 0)
                    {
                        placeQueen[i][j] = true;
                        noOfQueens--;
                        movesCount++;
                        movesTV.setText("Moves Made : " + movesCount);
                        if (noOfQueens == 0 && checkQueens())
                            finishGame();
                        checkQueenPlacementAllowed();
                        invalidate();
                    }
                }


                xPos += sizeOfEachCell;
            }
            xPos = startXPos;
            yPos += sizeOfEachCell;
        }
    }

    public void checkQueenPlacementAllowed() {
        /*
          N-Queen Game is solved using Backtracking.
          When we place a queen in a cell, check for clashes with already placed queens is performed.
          First, cells below and above the cell with queen placed, are checked to identify clash in the same column.
          Then, cells right and left to the cell with queen placed, are checked to identify clash in the same row.
          Lastly, left and right diagonals are checked.
          If either of the checks returns true, the clashed cells will be colored red at onDraw() as we set queenNotAllowed[r][c] = true
          with those particular cell's row and column index as r and c respectively.
         */
        int flag;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                flag = 0;
                if (placeQueen[i][j])  { // identify the cell with queen

                    for (int r = i + 1; r < rows; r++) { //checking if any queen is placed in the same column's downward cells
                        if (placeQueen[r][j]) {
                            // queen is already placed in same column
                            flag = 1;
                            queenNotAllowed[r][j] = true;
                        }
                    }

                    for (int r = i - 1; r >= 0; r--) { //checking if any queen is placed in the same column's upward cells
                        if (placeQueen[r][j]) {
                            // queen is already placed in same column
                            flag = 1;
                            queenNotAllowed[r][j] = true;
                        }
                    }

                    for (int c = j + 1; c < columns; c++) { //checking if any queen is placed in the same row's cells in the right
                        if (placeQueen[i][c]) {
                            // queen is already placed in same row
                            flag = 1;
                            queenNotAllowed[i][c] = true;
                        }
                    }

                    for (int c = j - 1; c >= 0; c--) { //checking if any queen is placed in the same row's cells in the left
                        if (placeQueen[i][c]) {
                            // queen is already placed in same row
                            flag = 1;
                            queenNotAllowed[i][c] = true;
                        }
                    }

                    for (int r = i + 1, c = j + 1; r < rows && c < columns; r++, c++) { //checking if any queen is placed in the downward right diagonal
                        if (placeQueen[r][c]) {
                            // queen is already placed in right diagonal
                            flag = 1;
                            queenNotAllowed[r][c] = true;
                        }
                    }

                    for (int r = i - 1, c = j + 1; r >= 0 && c < columns; r--, c++) { //checking if any queen is placed in the upward right diagonal
                        if (placeQueen[r][c]) {
                            // queen is already placed in right diagonal
                            flag = 1;
                            queenNotAllowed[r][c] = true;
                        }
                    }

                    for (int r = i - 1, c = j - 1; r >= 0 && c >= 0; r--, c--) { //checking if any queen is placed in the upward left diagonal
                        if (placeQueen[r][c]) {
                            // queen is already placed in left diagonal
                            flag = 1;
                            queenNotAllowed[r][c] = true;
                        }
                    }

                    for (int r = i + 1, c = j - 1; r < rows && c >= 0; r++, c--) { //checking if any queen is placed in the downward left diagonal
                        if (placeQueen[r][c]) {
                            // queen is already placed in left diagonal
                            flag = 1;
                            queenNotAllowed[r][c] = true;
                        }
                    }

                    queenNotAllowed[i][j] = flag == 1;

                } else
                    queenNotAllowed[i][j] = false;


            }
        }

    }

    public boolean checkQueens() {

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                //checking for a clash of queens

                if (placeQueen[i][j]) { // identify the cell with queen

                    for (int r = i + 1; r < rows; r++) ////checking if any queen is placed in the same column's downward cells
                        if (placeQueen[r][j])
                            return false;

                    for (int r = i - 1; r >= 0; r--) //checking if any queen is placed in the same column's upward cells
                        if (placeQueen[r][j])
                            return false;

                    for (int c = j + 1; c < columns; c++) //checking if any queen is placed in the same row's cells in the right
                        if (placeQueen[i][c])
                            return false;

                    for (int c = j - 1; c >= 0; c--) //checking if any queen is placed in the same row's cells in the left
                        if (placeQueen[i][c])
                            return false;

                    for (int r = i + 1, c = j + 1; r < rows && c < columns; r++, c++) //checking if any queen is placed in the downward right diagonal
                        if (placeQueen[r][c])
                            return false;

                    for (int r = i - 1, c = j + 1; r >= 0 && c < columns; r--, c++) //checking if any queen is placed in the upward right diagonal
                        if (placeQueen[r][c])
                            return false;

                    for (int r = i - 1, c = j - 1; r >= 0 && c >= 0; r--, c--) //checking if any queen is placed in the upward left diagonal
                        if (placeQueen[r][c])
                            return false;

                    for (int r = i + 1, c = j - 1; r < rows && c >= 0; r++, c--) //checking if any queen is placed in the downward left diagonal
                        if (placeQueen[r][c])
                            return false;
                }

            }
        }
        return true;
    }

    public void finishGame() {


        AlertDialog.Builder builder = new Builder(context);

        builder.setTitle("Congratulations").setMessage("Game Complete!");


        builder.setNegativeButton("Start Again", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

    }
}
