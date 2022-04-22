package zzc.recruitment.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//企业信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    private int id;
    private String sex;
    private int age;
    private String avatar;
    private String myname;
    private String place;
    private String money;
    private String post;
    private String phone;
    private String email;
    private String web;
    private String skills;
    private String pro1;
    private String pro2;
    private String work1;
    private String work2;
    private String edu;
    private String pt1;
    private String pt2;
    private String wt1;
    private String wt2;
    private String selfeval;
}
