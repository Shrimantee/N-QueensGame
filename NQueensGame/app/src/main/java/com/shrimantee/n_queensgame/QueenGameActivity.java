package com.shrimantee.n_queensgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *  This activity is dedicated to create the game board
 */
public class QueenGameActivity extends AppCompatActivity {

    int noOfQueens;
    LinearLayout layout_QueenGameActivity, topLayout;
    TextView tv_moves;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        noOfQueens = intent.getIntExtra("BoardSize", 4);

        layout_QueenGameActivity = new LinearLayout(getApplicationContext());
        layout_QueenGameActivity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_QueenGameActivity.setOrientation(LinearLayout.VERTICAL);

        topLayout = new LinearLayout(getApplicationContext());
        topLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0.1f));
        topLayout.setOrientation(LinearLayout.HORIZONTAL);

        tv_moves = new TextView(getApplicationContext());
        tv_moves.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        tv_moves.setTextAppearance(this, android.R.style.TextAppearance_Large);
        tv_moves.setTextColor(R.color.colorAccent);
        tv_moves.setText("Moves : 0");
        tv_moves.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv_moves.setTextColor(Color.BLACK);
        topLayout.addView(tv_moves);

        layout_QueenGameActivity.addView(topLayout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GameBoardView view = new GameBoardView(this,noOfQueens,metrics.widthPixels,metrics.heightPixels, tv_moves);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0.8f));

        layout_QueenGameActivity.addView(view);
        setContentView(layout_QueenGameActivity);

    }
}