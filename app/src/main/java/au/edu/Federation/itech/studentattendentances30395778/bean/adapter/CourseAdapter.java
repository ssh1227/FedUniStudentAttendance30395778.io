package au.edu.Federation.itech.studentattendentances30395778.bean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;

public class CourseAdapter extends ArrayAdapter<Course> {
    private ItemListener mItemListener;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    private int resourceId;

    public CourseAdapter(Context context, int textViewResourceId, List<Course> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);//获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView tvName =  view.findViewById(R.id.title);
        Button btn_student = view.findViewById(R.id.btn_student);
        Button btn_attendance = view.findViewById(R.id.btn_attendance);
        Button btn_delete = view.findViewById(R.id.btn_delete);
        tvName.setText(String.format("%s",course.getName()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemClick(course);
                }
            }
        });
        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemStudentClick(course);
                }
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemAttendanceClick(course);
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemDeleteClick(course);
                }
            }
        });
        return view;
    }
    public interface ItemListener{
        void ItemClick(Course course);
        void ItemStudentClick(Course course);
        void ItemAttendanceClick(Course course);
        void ItemDeleteClick(Course course);
    }
}

