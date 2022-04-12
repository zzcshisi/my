package zzc.recruitment.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzc.recruitment.bean.Businessinfo;
import zzc.recruitment.mapper.BusinessinfoMapper;
import zzc.recruitment.service.BusinessinfoService;

import java.util.List;

@Service
public class BusinessinfoServiceImpl implements BusinessinfoService {
    @Autowired
    BusinessinfoMapper businessinfoMapper;

    //插入新的用户
    @Override
    public int addBusinessinfo(Businessinfo businessinfoBean){
        return businessinfoMapper.addBusinessinfo(businessinfoBean);
    }

    //删除用户
    @Override
    public int deleteBusiness(int id){
        return businessinfoMapper.deleteBusiness(id);
    }

    //修改用户
    @Override
    public int updateBusiness(Businessinfo businessinfoBean){
        return businessinfoMapper.updateBusiness(businessinfoBean);
    }
    @Override
    public int updateAvatar(String avatar,int id){
        return businessinfoMapper.updateAvatar(avatar,id);
    }

    //查询所有用户
    @Override
    public List<Businessinfo> getAllBusiness(){
        return businessinfoMapper.getAllBusiness();
    }

    @Override
    public Businessinfo getById(int id){
        return businessinfoMapper.getById(id);
    }

    //根据id模糊查找
    @Override
    public List<Businessinfo> searchId(String id){
        return businessinfoMapper.searchId(id);
    }
}
