package zzc.recruitment.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invite {
    private int id;
    private int uid;
    private int pid;
    private String pname;
    private String bname;
}
