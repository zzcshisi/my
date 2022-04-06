package zzc.recruitment.mapper;

import zzc.recruitment.bean.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    //增加新的岗位
    int addNotice(Notice noticeBean);

    //删除用户
    int deleteNotice(int id);

    Notice searchNotice(int id);

    //获取所有信息
    List<Notice> getAll();

    int updateNotice(Notice noticeBean);

    List<Notice> getCarousel();

    List<Notice> searchTitle(String title);
}
