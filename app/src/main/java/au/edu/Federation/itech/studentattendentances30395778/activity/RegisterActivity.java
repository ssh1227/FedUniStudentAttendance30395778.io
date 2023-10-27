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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.User;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

import java.text.SimpleDateFormat;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private Activity activity;
    private EditText etAccount;//手机号
    private EditText etPassword;//密码
    private EditText etPasswordSure;//确认密码
    private TextView tvLogin;//登录
    private Button btnRegister;//注册按钮
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        helper = new MySqliteOpenHelper(this);
        setContentView(R.layout.activity_register);//加载页面
        etAccount =(EditText) findViewById(R.id.et_account);//获取手机号
        etPassword=(EditText) findViewById(R.id.et_password);//获取密码
        etPasswordSure=(EditText) findViewById(R.id.et_password_sure);//获取确认密码
        tvLogin=(TextView) findViewById(R.id.tv_login);//登录
        btnRegister=(Button) findViewById(R.id.btn_register);//获取注册按钮
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登录页面
                Intent intent=new Intent(activity, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //设置注册点击按钮
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭虚拟键盘
                InputMethodManager inputMethodManager= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                String account= etAccount.getText().toString();
                String password=etPassword.getText().toString();
                String passwordSure=etPasswordSure.getText().toString();
                if ("".equals(account)){//账号不能为空
                    Toast.makeText(activity,"The account cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(password)){//密码为空
                    Toast.makeText(activity,"Password is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(passwordSure)){//密码不一致
                    Toast.makeText(activity,"Password inconsistency", Toast.LENGTH_LONG).show();
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
                if (mUser == null) {//注册
                    String insertSql = "insert into user(account,password) values(?,?)";
                    db.execSQL(insertSql,new Object[]{account,password});
                    Toast.makeText(activity, "Registration Successful ", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(activity, "The account already exists", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
    }
}
