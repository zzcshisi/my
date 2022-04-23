package zzc.recruitment.mapper;

import zzc.recruitment.bean.Store;
import zzc.recruitment.bean.Position;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzc.recruitment.bean.Userinfo;

import java.util.List;

@Mapper
@Repository
public interface StoreMapper {
    int addStore(int uid,int pid);

    int deleteStore(int uid,int pid);

    int get(int uid,int pid);

    List<Integer> getStores(int uid);

    List<Position> getPost(int uid);
}
