package com.sacramentum.apk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.sacramentum.apk.view.loginPanel.LoadingActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        val intent = Intent(this, LoadingActivity::class.java);
        startActivity(intent);
        finish();

        enableEdgeToEdge();
        setContent {
            Text(text = "TESTE");
        }
    }
}
