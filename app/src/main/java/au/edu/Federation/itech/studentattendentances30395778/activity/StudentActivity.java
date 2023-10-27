package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.adapter.StudentAdapter;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.bean.Student;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

/**
 * 学生
 */
public class StudentActivity extends AppCompatActivity {
    private Activity myActivity;
    private List<Student> studentList;
    MySqliteOpenHelper helper = null;
    private ListView listView;
    private FloatingActionButton btn_add;
    private Course mCourse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        helper = new MySqliteOpenHelper(this);
        setContentView(R.layout.activity_student);//加载页面
        mCourse =(Course)getIntent().getSerializableExtra("course");
        helper = new MySqliteOpenHelper(myActivity);
        listView = findViewById(R.id.listView);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myActivity, AddStudentActivity.class);
                intent.putExtra("course",mCourse);
                myActivity.startActivity(intent);
            }
        });
        initView();
    }


    /**
     * 初始化页面
     */
    private void initView() {

    }

    private void loadData() {
        studentList = new ArrayList<>();
        Student student = null;
        String sql = "select * from student where courseId ="+mCourse.getId();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                Integer courseId = cursor.getInt(1);
                String name = cursor.getString(2);
                String number = cursor.getString(3);
                student = new Student(id, courseId, name, number);
                studentList.add(student);
            }
        }
        StudentAdapter adapter = new StudentAdapter(myActivity,R.layout.item_rv_student_list, studentList);
        listView.setAdapter(adapter);
        adapter.setItemListener(new StudentAdapter.ItemListener() {
            @Override
            public void ItemClick(Student student) {

            }
            @Override
            public void ItemDeleteClick(Student student) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(myActivity);
                dialog.setMessage("Are you sure you want to delete this data?");
                dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        if (db.isOpen()) {
                            db.execSQL("delete from student where id = " + student.getId());
                            db.close();
                        }
                        Toast.makeText(myActivity, "successfully delete", Toast.LENGTH_LONG).show();
                        loadData();
                    }
                });
                dialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }


        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
