package zzc.recruitment.service;

import zzc.recruitment.bean.Invite;
import java.util.List;
public interface InviteService {
    int addInvite(Invite invite);

    int deleteById(int id);

    List<Invite> getByUid(int uid);
}
