package au.edu.Federation.itech.studentattendentances30395778.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.activity.AddCourseActivity;
import au.edu.Federation.itech.studentattendentances30395778.activity.AttendanceActivity;
import au.edu.Federation.itech.studentattendentances30395778.activity.StudentActivity;
import au.edu.Federation.itech.studentattendentances30395778.bean.adapter.CourseAdapter;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.util.MySqliteOpenHelper;

/**
 * 首页
 */
public class HomeFragment extends Fragment {
    private Activity myActivity;
    private List<Course> courseList;
    MySqliteOpenHelper helper = null;
    private ListView listView;

    private FloatingActionButton btn_add;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        helper = new MySqliteOpenHelper(myActivity);
        listView = view.findViewById(R.id.listView);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myActivity, AddCourseActivity.class);
                myActivity.startActivity(intent);
            }
        });
        initView();
        return view;
    }


    /**
     * 初始化页面
     */
    private void initView() {

    }

    private void loadData() {
        courseList = new ArrayList<>();
        Course course = null;
        String sql = "select * from course";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                String startDate = cursor.getString(2);
                String endDate = cursor.getString(2);
                course = new Course(id, name, startDate, endDate);
                courseList.add(course);
            }
        }
        CourseAdapter adapter = new CourseAdapter(myActivity,R.layout.item_rv_course_list, courseList);
        listView.setAdapter(adapter);
        adapter.setItemListener(new CourseAdapter.ItemListener() {
            @Override
            public void ItemClick(Course course) {
            }

            @Override
            public void ItemStudentClick(Course course) {
                Intent intent = new Intent(myActivity, StudentActivity.class);
                intent.putExtra("course",course);
                startActivity(intent);
            }

            @Override
            public void ItemAttendanceClick(Course course) {
                Intent intent = new Intent(myActivity, AttendanceActivity.class);
                intent.putExtra("course",course);
                startActivity(intent);
            }

            @Override
            public void ItemDeleteClick(Course course) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(myActivity);
                dialog.setMessage("Are you sure you want to delete this data?");
                dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        if (db.isOpen()) {
                            db.execSQL("delete from course where id = " + course.getId());
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
