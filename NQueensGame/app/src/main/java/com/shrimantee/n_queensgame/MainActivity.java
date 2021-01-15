package com.shrimantee.n_queensgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the first activity where the user need to give input value of the chessboard size
 * and then start the N-Queens Game
 * The rules of the game is mentioned in the layout of this activity
 */
public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    EditText et_BoardSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        et_BoardSize = (EditText) findViewById(R.id.board_size);

        ConstraintLayout layout_MainActivity = (ConstraintLayout) findViewById(R.id.activity_main_layout);


        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable background = getResources().getDrawable(R.drawable.background);

        layout_MainActivity.setBackground( background);

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // start the game
                String boardSize = !et_BoardSize.getText().toString().isEmpty() ? et_BoardSize.getText().toString() : getString(R.string.default_board_size);
                Intent intent = new Intent(getApplicationContext(), QueenGameActivity.class);
                intent.putExtra("BoardSize",Integer.parseInt(boardSize));
                startActivity(intent);

            }
        });

    }
}