package au.edu.Federation.itech.studentattendentances30395778.bean;

public class Attendance {
    private Integer id;
    private Integer courseId;//课程ID
    private Integer studentId;//学生ID
    private Integer status;//状态
    private String date;//日期
    private String studentName;

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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Attendance(Integer id, Integer courseId, Integer studentId, Integer status, String date,String studentName) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.status = status;
        this.date = date;
        this.studentName = studentName;
    }
}
