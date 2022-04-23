package zzc.recruitment.service;

import zzc.recruitment.bean.Position;
import zzc.recruitment.bean.Store;

import java.util.List;

public interface StoreService {
    int addStore(int uid,int pid);

    int deleteStore(int uid,int pid);

    int get(int uid, int pid);

    List<Integer> getStores(int uid);

    List<Position> getPost(int uid);
}
