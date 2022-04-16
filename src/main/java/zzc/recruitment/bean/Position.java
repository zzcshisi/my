package zzc.recruitment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private int pid;
    private int bid;
    private String pname;
    private String bname;
    private String kind;
    private String cate;
    private String city;
    private int xueli;
    private int exp;
    private int pleft;
    private int pright;
    private String releasetime;
    private String endtime;
    private String intro;
    private String token1;
    private String token2;
    private String token3;
}
