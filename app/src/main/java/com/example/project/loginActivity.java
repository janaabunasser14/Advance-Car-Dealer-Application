package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class loginActivity extends AppCompatActivity {
    private List<CarType> listOfCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final RelativeLayout layout = findViewById(R.id.rootLayout);
        Button connectButton = findViewById(R.id.connectButton);
        Context context=this;

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Connection done.", Toast.LENGTH_LONG).show();
                animateBackgroundFadeOut(layout);
            }
        });
    }
    private void checkTheRest(final RelativeLayout layout){
        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(loginActivity.this);
        connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
        Toast.makeText(this, "Connection done.", Toast.LENGTH_LONG).show();
        animateBackgroundFadeOut(layout);
    }
    private void animateBackgroundFadeOut(final RelativeLayout layout) {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setDuration(1000); // duration in milliseconds (1 second)

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layout.setAlpha((float)animation.getAnimatedValue());
            }
        });
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Transition to another activity here
                Intent intent = new Intent(loginActivity.this, LoginApp.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });


    }
    public void fillCar(List<CarType> Car) {

        this.listOfCar = Car;
    }

}
