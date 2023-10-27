package au.edu.Federation.itech.studentattendentances30395778.bean;

import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {
    private Integer id;
    private String account;//账号
    private String password;//密码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User(Integer id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }
}
