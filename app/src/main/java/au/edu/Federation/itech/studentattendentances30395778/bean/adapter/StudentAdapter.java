package au.edu.Federation.itech.studentattendentances30395778.bean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import au.edu.Federation.itech.studentattendentances30395778.R;
import au.edu.Federation.itech.studentattendentances30395778.bean.Course;
import au.edu.Federation.itech.studentattendentances30395778.bean.Student;

public class StudentAdapter extends ArrayAdapter<Student> {
    private ItemListener mItemListener;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    private int resourceId;

    public StudentAdapter(Context context, int textViewResourceId, List<Student> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);//获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView tvName =  view.findViewById(R.id.title);
        Button btn_delete = view.findViewById(R.id.btn_delete);
        tvName.setText(String.format("%s",student.getName()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemClick(student);
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener!=null){
                    mItemListener.ItemDeleteClick(student);
                }
            }
        });
        return view;
    }
    public interface ItemListener{
        void ItemClick(Student student);
        void ItemDeleteClick(Student student);
    }
}

