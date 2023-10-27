package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;


/**
 * 添加或者修改
 */
public class AddCourseActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private EditText etName;
    private Course course;
    private LinearLayout llStartTime;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //时间
    protected String year;
    protected String month;
    protected String day;
    private boolean isSelectDate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        etName = findViewById(R.id.et_name);
        llStartTime = findViewById(R.id.ll_startTime);
        tvStartDate = findViewById(R.id.tv_startDate);
        tvEndDate = findViewById(R.id.tv_endTime);
        helper = new MySqliteOpenHelper(this);
        course = (Course) getIntent().getSerializableExtra("course");
        if (course != null) {
            etName.setText(course.getName());
        }
        Date date = new Date();
        year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(date);
        month = new SimpleDateFormat("MM", Locale.getDefault()).format(date);
        day = new SimpleDateFormat("dd", Locale.getDefault()).format(date);
        llStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }


    //弹出时间选择对话框
    protected void showDatePickerDialog() {
        final int yearNum = Integer.parseInt(year);
        final int monthNum = Integer.parseInt(month);
        final int dayNum = Integer.parseInt(day);

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearSet, int monthSet, int daySet) {

                Calendar c = Calendar.getInstance();
                c.set(yearSet, monthSet, daySet);
                Calendar c1 = Calendar.getInstance();
                c1.set(yearSet, monthSet, daySet+84);

                tvStartDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                        .format(c.getTime()));
                tvEndDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                        .format(c1.getTime()));
                isSelectDate =true;
            }//对话框默认选择的日期
        }, yearNum, monthNum - 1, dayNum).show();
    }


    /**
     * 保存
     *
     * @param view
     */
    public void save(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String name = etName.getText().toString();
        String startDate = tvStartDate.getText().toString();
        String endDate = tvEndDate.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(AddCourseActivity.this, "The course name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isSelectDate) {
            Toast.makeText(AddCourseActivity.this, "The start date cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (course == null) {//新增
            String sql = "insert into course(name,startDate,endDate) values(?,?,?)";
            db.execSQL(sql, new Object[]{name,startDate,endDate});
            Toast.makeText(AddCourseActivity.this, "New success", Toast.LENGTH_SHORT).show();
        } else {//修改
            db.execSQL("update course set name = ?, startDate = ?, endDate = ? where id=?",
                    new Object[]{name, name, name,endDate, course.getId()});
            Toast.makeText(AddCourseActivity.this, "Update successful ", Toast.LENGTH_SHORT).show();
        }
        db.close();
        finish();
    }


}
