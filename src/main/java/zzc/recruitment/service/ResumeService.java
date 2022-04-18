package zzc.recruitment.service;

import zzc.recruitment.bean.Resume;


public interface ResumeService {
    //插入
    int add(int id);

    //删除
    int delete(int id);

    //修改
    int update(Resume resume);
    int updateAvatar(String avatar,int id);

    //查询
    Resume getById(int id);
}
