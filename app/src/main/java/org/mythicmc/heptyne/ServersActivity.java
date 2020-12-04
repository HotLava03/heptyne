package org.mythicmc.heptyne;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class ServersActivity extends AuthenticatedActivity {
    private TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        // TODO: This is all temporary for testing purposes.
        temp = (TextView) findViewById(R.id.temp);
    }

    @Override
    protected void setServers(Map<String, Boolean> servers) {
        temp.setText(servers.toString());
    }
}
