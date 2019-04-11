package mapper;

import java.util.List;
import pojo.StaticSchedule;
import pojo.StaticScheduleExample;

public interface StaticScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaticSchedule record);

    int insertSelective(StaticSchedule record);

    List<StaticSchedule> selectByExample(StaticScheduleExample example);

    StaticSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaticSchedule record);

    int updateByPrimaryKey(StaticSchedule record);
}