package com.moringaschool.mymovie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.mymovie.R;

public class AnimationActivity extends AppCompatActivity {

    private static int ANIMATION_SCREEN = 5000;

    //    variables
    Animation topAnim, bottomAnim;
    ImageView cona;
    TextView heading, designer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

//        Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        hooks
        cona = findViewById(R.id.LUCY);
        heading = findViewById(R.id.heading);
        designer = findViewById(R.id.designer);

        cona.setAnimation(topAnim);
        heading.setAnimation(bottomAnim);
        designer.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AnimationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },ANIMATION_SCREEN);

    }
}