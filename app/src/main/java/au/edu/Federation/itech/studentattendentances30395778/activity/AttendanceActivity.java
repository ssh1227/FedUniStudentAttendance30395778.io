package au.edu.Federation.itech.studentattendentances30395778.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.adapter.AttendanceAdapter;
import au.edu.Federation.itech.studentattendentances30395778.bean.Attendance;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

/**
 * 考勤
 */
public class AttendanceActivity extends AppCompatActivity {
    private Activity myActivity;
    private List<Attendance> attendanceList;
    MySqliteOpenHelper helper = null;
    private ListView listView;
    private Spinner spinner;
    private Course mCourse;
    private String date ="";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        helper = new MySqliteOpenHelper(this);
        setContentView(R.layout.activity_attendance);//加载页面
        mCourse =(Course)getIntent().getSerializableExtra("course");
        date = mCourse.getStartDate();
        helper = new MySqliteOpenHelper(myActivity);
        listView = findViewById(R.id.listView);
        spinner = findViewById(R.id.spinner);
        initView();
    }

    public void save(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        for (Attendance attendance : attendanceList) {
            db.execSQL("update attendance set status = ?where id=?",
                    new Object[]{attendance.getStatus(), attendance.getId()});
        }
        finish();
        Toast.makeText(AttendanceActivity.this, "Update successful ", Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<String> getDate(){
        List<String> dates = new ArrayList<>();
        String startDateStr = mCourse.getStartDate();
        String endDateStr = mCourse.getEndDate();
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
     * 初始化页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        List<String> dates = getDate();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1,dates);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date = dates.get(position);
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadData() {
        attendanceList = new ArrayList<>();
        Attendance attendance = null;
        String sql = "select a.*,s.name studentName from attendance a ,student s where a.studentId = s.id " +
                "and a.courseId ="+mCourse.getId() +
                " and date ="+"'"+date+"'";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                Integer courseId = cursor.getInt(1);
                Integer studentId = cursor.getInt(2);
                Integer status = cursor.getInt(3);
                String date = cursor.getString(4);
                String studentName = cursor.getString(5);
                attendance = new Attendance(id, courseId, studentId, status,date,studentName);
                attendanceList.add(attendance);
            }
        }
        AttendanceAdapter adapter = new AttendanceAdapter(myActivity,R.layout.item_rv_attendance_list, attendanceList);
        listView.setAdapter(adapter);
        adapter.setItemListener(new AttendanceAdapter.ItemListener() {
            @Override
            public void ItemClick(Attendance attendance, boolean isChecked) {
                attendance.setStatus(isChecked?1:0);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
