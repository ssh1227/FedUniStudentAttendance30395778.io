package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.bean.Student;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;


/**
 * 添加或者修改
 */
public class AddStudentActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private EditText etNumber;
    private EditText etName;
    private Student student;
    private Course course;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        etNumber = findViewById(R.id.et_number);
        etName = findViewById(R.id.et_name);
        helper = new MySqliteOpenHelper(this);
        student = (Student) getIntent().getSerializableExtra("student");
        course = (Course) getIntent().getSerializableExtra("course");
        if (student != null) {
            etName.setText(student.getName());
            etNumber.setText(student.getNumber());
        }
        getDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<String> getDate(){
        List<String> dates = new ArrayList<>();
        String startDateStr = course.getStartDate();
        String endDateStr = course.getEndDate();
        LocalDate startDate = LocalDate.of(Integer.valueOf(startDateStr.split("-")[0]),
                Integer.valueOf(startDateStr.split("-")[1]),
                Integer.valueOf(startDateStr.split("-")[2]));
        LocalDate endDate = LocalDate.of(Integer.valueOf(endDateStr.split("-")[0]),
                Integer.valueOf(endDateStr.split("-")[1]),
                Integer.valueOf(endDateStr.split("-")[2]));

        // 计算每份的时间长度（以周为单位）
        long weeksPerPart = 12 / 12;

        // 创建一个ArrayList来存储每份的时间
        List<LocalDate> parts = new ArrayList<>();

        // 循环插入每份的时间
        for (int i = 0; i < 12; i++) {
            LocalDate date = startDate.plusWeeks(i * weeksPerPart);
            parts.add(date);
            dates.add(date.toString());
        }
        return dates;
    }





    /**
     * 保存
     *
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String number = etNumber.getText().toString();
        String name = etName.getText().toString();
        if ("".equals(number)) {
            Toast.makeText(AddStudentActivity.this, "The student number cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(name)) {
            Toast.makeText(AddStudentActivity.this, "The student name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (student == null) {//新增
            int studentId = 0;
            String sql = "insert into student(courseId,name,number) values(?,?,?)";
            db.execSQL(sql, new Object[]{course.getId(),name,number});
            //查询id
            Cursor cur = db.rawQuery("select LAST_INSERT_ROWID() ", null);
            cur.moveToFirst();
            studentId = cur.getInt(0);
            List<String> dates = getDate();
            String attendanceSql = "insert into attendance(courseId,studentId,status,date) values(?,?,?,?)";
            for (String date : dates) {
                db.execSQL(attendanceSql, new Object[]{course.getId(),studentId,0,date});
            }

            Toast.makeText(AddStudentActivity.this, "New success", Toast.LENGTH_SHORT).show();
        } else {//修改
            db.execSQL("update course set name = ?, number = ? where id=?",
                    new Object[]{name, number,  student.getId()});
            Toast.makeText(AddStudentActivity.this, "Update successful ", Toast.LENGTH_SHORT).show();
        }
        db.close();
        finish();
    }


}
