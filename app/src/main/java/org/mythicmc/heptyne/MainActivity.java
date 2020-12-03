package org.mythicmc.heptyne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the login button in order to register the click listener.
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Listen to the login button.
        AppCompatEditText username = (AppCompatEditText) findViewById(R.id.username);
        AppCompatEditText password = (AppCompatEditText) findViewById(R.id.password);
        TextView alerts = (TextView) findViewById(R.id.login_alerts);
        // Reset old alerts and colors.
        setRed(username, false);
        setRed(password, false);
        alerts.setText("");
        // Check if the EditTexts meet the requirements.
        if (username.getText().toString().isEmpty()) { // #getText isn't null, as the EditText is always set.
            setRed(username, true);
            alerts.setText(R.string.username_required);
        } else if (password.getText().toString().isEmpty()) { // #getText isn't null, as the EditText is always set.
            setRed(password, true);
            alerts.setText(R.string.password_required);
        } else {
            // They meet the requirements.
        }
    }

    // The warnings aren't to worry about.
    @SuppressLint("RestrictedApi")
    private void setRed(AppCompatEditText editText, boolean red) {
        // Shorter way to quickly set the tint of the EditTexts.
        if (red) editText.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        else editText.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
    }
}