package zzc.recruitment.mapper;

import zzc.recruitment.bean.Businessinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusinessinfoMapper {
    //插入新的用户
    int addBusinessinfo(Businessinfo businessinfoBean);

    //删除用户
    int deleteBusiness(int id);

    //修改用户
    int updateBusiness(Businessinfo businessinfoBean);
    int updateAvatar(String avatar,int id);

    //查询所有用户
    List<Businessinfo> getAllBusiness();

    Businessinfo getById(int id);

    //根据id模糊查找
    List<Businessinfo> searchId(String id);
}
