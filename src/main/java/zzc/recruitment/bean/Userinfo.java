package zzc.recruitment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用户表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinfo {
    private int id;
    private String userName;
}
