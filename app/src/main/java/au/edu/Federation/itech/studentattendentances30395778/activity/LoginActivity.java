package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.User;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private static final String TAG = "LoginActivity";
    private Activity activity;
    private EditText etAccount;//手机号
    private EditText etPassword;//密码
    private TextView tvRegister;//注册
    private Button btnLogin;//登录按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        helper = new MySqliteOpenHelper(this);
        setContentView(R.layout.activity_login);//加载页面
        etAccount =(EditText) findViewById(R.id.et_account);//获取手机号
        etPassword=(EditText)findViewById(R.id.et_password);//获取密码
        tvRegister=(TextView)findViewById(R.id.tv_register);//获取注册
        btnLogin=(Button)findViewById(R.id.btn_login);//获取登录

        //手机号注册
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent=new Intent(activity, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //设置点击按钮
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭虚拟键盘
                InputMethodManager inputMethodManager= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                //获取请求参数
                String account= etAccount.getText().toString();
                String password=etPassword.getText().toString();
                if ("".equals(account)){//账号不能为空
                    Toast.makeText(activity,"The account cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(password)){//密码为空
                    Toast.makeText(activity,"Password is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                User mUser = null;
                String sql = "select * from user where account = ?";
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery(sql, new String[]{account});
                if (cursor != null && cursor.getColumnCount() > 0) {
                    while (cursor.moveToNext()) {
                        Integer dbId = cursor.getInt(0);
                        String dbAccount = cursor.getString(1);
                        String dbPassword = cursor.getString(2);
                        mUser = new User(dbId, dbAccount,dbPassword);
                    }
                }
                db.close();
                if (mUser != null) {
                    if (!password.equals(mUser.getPassword())) {
                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
