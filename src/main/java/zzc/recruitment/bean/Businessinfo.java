package zzc.recruitment.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//企业信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Businessinfo {
    private int id;
    private String avatar;
    private String city;
    private String address;
    private String businessname;
    private String web;
    private String founding;

    private String industry;

    private String bscale;
    private String nature;
    private String intro;
}
