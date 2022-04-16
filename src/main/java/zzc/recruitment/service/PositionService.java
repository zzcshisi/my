package zzc.recruitment.service;

import zzc.recruitment.bean.Position;
import java.util.List;

public interface PositionService {
    int addPosition(Position position);

    int deleteByPid(int pid);

    int deleteByBid(int bid);

    int updatePosition(Position position);

    List<Position> getAll();

    Position getByPid(int pid);

    List<Position> getByBid(int bid);

    List<Position> getByPname(String pname);

    List<Position> searchPosition(String searchword,Position position);
}

