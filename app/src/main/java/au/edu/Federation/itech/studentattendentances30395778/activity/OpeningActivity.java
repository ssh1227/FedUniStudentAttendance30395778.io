package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

/**
 * 开屏页面
 */
public class OpeningActivity extends AppCompatActivity {
    private Activity myActivity;
    MySqliteOpenHelper helper = null;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        helper = new MySqliteOpenHelper(this);
        //设置页面布局
        setContentView(R.layout.activity_opening);
        try {
            initView();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() throws IOException, JSONException {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                    finish();
                    return;
                }

                //两秒后跳转到主页面
                Intent intent = new Intent();
                intent.setClass(OpeningActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


    @Override
    public void onBackPressed() {

    }
}
