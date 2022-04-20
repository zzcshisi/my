package zzc.recruitment.serviceImpl;

import zzc.recruitment.bean.Invite;
import zzc.recruitment.service.InviteService;

import java.util.List;
import zzc.recruitment.mapper.InviteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    InviteMapper inviteMapper;

    @Override
    public int addInvite(Invite invite){
        return inviteMapper.addInvite(invite);
    }

    @Override
    public int deleteById(int id){
        return inviteMapper.deleteById(id);
    }

    @Override
    public List<Invite> getByUid(int uid){
        return inviteMapper.getByUid(uid);
    }
}
