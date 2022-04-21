package zzc.recruitment.mapper;

import org.springframework.web.bind.annotation.RequestParam;
import zzc.recruitment.bean.Position;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PositionMapper {
    int addPosition(Position position);

    int deleteByPid(int Pid);

    int deleteByBid(int bid);

    int updatePosition(Position position);

    List<Position> getAll();

    Position getByPid(int pid);

    List<Position> getByBid(int bid);

    List<Position> getByPname(String pname,int bid);

    List<Position> searchPosition(String searchword,String city,String industry,String nature,String bscale,String kind,String cate, int xueli, int exp, int pleft,int pright);
}
