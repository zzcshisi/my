package zzc.recruitment.serviceImpl;

import zzc.recruitment.bean.Position;
import zzc.recruitment.bean.Store;
import zzc.recruitment.mapper.UserinfoMapper;
import zzc.recruitment.service.StoreService;
import zzc.recruitment.mapper.StoreMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;

    @Override
    public int addStore(int uid,int pid){
        return storeMapper.addStore(uid,pid);
    }

    @Override
    public int deleteStore(int uid,int pid){
        return storeMapper.deleteStore(uid,pid);
    }

    @Override
    public int get(int uid, int pid){
        return storeMapper.get(uid,pid);
    }

    @Override
    public List<Integer> getStores(int uid){
        return storeMapper.getStores(uid);
    }

    @Override
    public List<Position> getPost(int uid){
        return storeMapper.getPost(uid);
    }
}
