package org.mythicmc.heptyne;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/*
 * There's not much to this activity when it comes to
 * code. We just let Android handle everything for us
 * in layouts and the manifest for the back button.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
