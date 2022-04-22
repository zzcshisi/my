package zzc.recruitment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


//用户表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinfo {
    private int id;
    private String avatar;
    private String sex;
    private String intro;
    private String phone;
    private String email;
    private String home;

    private String gradeyear;

    private int xueli;
    private String school;
    private String zhuanye;
    private String status;
    private String hposition;
    private String hplace;
    private int hleft;
    private int hright;
}
