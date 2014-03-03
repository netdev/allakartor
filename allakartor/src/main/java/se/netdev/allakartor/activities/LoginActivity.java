package se.netdev.allakartor.activities;

import se.netdev.allakartor.R;
import android.os.Bundle;

public class LoginActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login);

        if (savedInstanceState != null) {
            
        } else {
            
        }
    }
}