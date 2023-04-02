package com.example.synergyplanets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText loadValue;
    RelativeLayout startButton;
    LottieAnimationView animationView;
    TextView result;
    ImageButton refreshButton;

    private static final Double EARTH_GRAVITY = 9.8;
    private static final Double MARS_GRAVITY = 3.721;
    private static final Double EARTH_KG_FUEL = 100.0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadValue = findViewById(R.id.load_value);
        startButton = findViewById(R.id.start_button);
        result = findViewById(R.id.result);
        refreshButton = findViewById(R.id.refresh_button);

        animationView = findViewById(R.id.animation_view);
        animationView.setAnimation(R.raw.rocket2);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {}
            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                result.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);
                refreshButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {}
            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {}
        });

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadValue.getText().length() == 0) {
                    loadValue.setError("Введите число");
                } else {
                    try {
                        double loadWeight = Double.parseDouble(loadValue.getText().toString());
                        double fuelWeight = loadWeight * (MARS_GRAVITY / EARTH_GRAVITY) * EARTH_KG_FUEL;
                        result.setText(String.format(Locale.getDefault(), "%.2f кг\nтоплива", fuelWeight));
                        animationView.playAnimation();
                    } catch (NumberFormatException e) {
                        loadValue.setError("Неверный формат числа");
                    }
                }
            }
        });
        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadValue.setText("");
                result.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });
    }
}