package au.edu.Federation.itech.studentattendentances30395778.bean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.Attendance;

public class AttendanceAdapter2 extends ArrayAdapter<Attendance> {
    private ItemListener mItemListener;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    private int resourceId;

    public AttendanceAdapter2(Context context, int textViewResourceId, List<Attendance> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attendance attendance = getItem(position);//获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView tvName =  view.findViewById(R.id.title);
        TextView tvStatus =  view.findViewById(R.id.tv_status);
        tvName.setText(String.format("%s",attendance.getStudentName()));
        tvStatus.setText(attendance.getStatus().intValue()==1?"Already on duty":"Absence");
        return view;
    }
    public interface ItemListener{
        void ItemClick(Attendance attendance,boolean isChecked);
    }
}

