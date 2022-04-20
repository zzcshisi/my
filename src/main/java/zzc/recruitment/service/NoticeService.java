package zzc.recruitment.service;

import zzc.recruitment.bean.Notice;

import java.util.List;
public interface NoticeService {
    //增加新的公告
    int addNotice(Notice noticeBean);

    //删除公告
    int deleteNotice(int id);

    //根据领域查找岗位
    Notice searchNotice(int id);

    //获取所有信息
    List<Notice> getAll();

    int updateNotice(Notice noticeBean);

    List<Notice> getShow();

    List<Notice> searchTitle(String title);
}
