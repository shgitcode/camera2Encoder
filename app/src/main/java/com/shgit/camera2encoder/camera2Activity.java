package com.shgit.camera2encoder;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class camera2Activity extends AppCompatActivity {
    private static final String TAG = "camera2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate!");

        setContentView(R.layout.activity_camera2);

        if (null == savedInstanceState) {// 为啥为空
            Log.d(TAG, "commit fragment Transaction!");
            // 载入片段frament
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }
}
