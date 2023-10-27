package au.edu.Federation.itech.studentattendentances30395778.bean;

import java.io.Serializable;

/**
 * 学生
 */
public class Student implements Serializable {
    private Integer id;
    private Integer courseId;//课程ID
    private String name;//名称
    private String number;//学号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Student(Integer id, Integer courseId, String name, String number) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.number = number;
    }
}
