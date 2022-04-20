package zzc.recruitment.mapper;
import zzc.recruitment.bean.Invite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InviteMapper {
    int addInvite(Invite invite);

    int deleteById(int id);

    List<Invite> getByUid(int uid);
}
