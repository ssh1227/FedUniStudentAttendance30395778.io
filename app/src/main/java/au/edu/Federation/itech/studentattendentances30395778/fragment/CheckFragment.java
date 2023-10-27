package au.edu.Federation.itech.studentattendentances30395778.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.adapter.AttendanceAdapter2;
import au.edu.Federation.itech.studentattendentances30395778.bean.Attendance;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;


/**
 * 考勤
 */
public class CheckFragment extends Fragment {
    private Activity myActivity;
    private List<Attendance> attendanceList;
    MySqliteOpenHelper helper = null;
    private ListView listView;
    private Button btnCheck;
    private String date="";
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //时间
    protected String year;
    protected String month;
    protected String day;
    private boolean isSelectDate=false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        helper = new MySqliteOpenHelper(myActivity);
        listView = view.findViewById(R.id.listView);
        btnCheck = view.findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        initView();
        return view;
    }


    /**
     * 初始化页面
     */
    private void initView() {
        Date date = new Date();
        year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(date);
        month = new SimpleDateFormat("MM", Locale.getDefault()).format(date);
        day = new SimpleDateFormat("dd", Locale.getDefault()).format(date);
    }

    //弹出时间选择对话框
    protected void showDatePickerDialog() {
        final int yearNum = Integer.parseInt(year);
        final int monthNum = Integer.parseInt(month);
        final int dayNum = Integer.parseInt(day);

        new DatePickerDialog(myActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearSet, int monthSet, int daySet) {

                Calendar c = Calendar.getInstance();
                c.set(yearSet, monthSet, daySet);
                Calendar c1 = Calendar.getInstance();
                c1.set(yearSet, monthSet, daySet+84);
                date=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                        .format(c.getTime());
                loadData();
                isSelectDate =true;
            }//对话框默认选择的日期
        }, yearNum, monthNum - 1, dayNum).show();
    }


    private void loadData() {
        attendanceList = new ArrayList<>();
        Attendance attendance = null;
        String sql = "select a.*,s.name studentName from attendance a ,student s where a.studentId = s.id " +
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
        AttendanceAdapter2 adapter = new AttendanceAdapter2(myActivity,R.layout.item_rv_attendance_list2, attendanceList);
        listView.setAdapter(adapter);
        adapter.setItemListener(new AttendanceAdapter2.ItemListener() {
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
