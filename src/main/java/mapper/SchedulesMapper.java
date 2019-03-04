package mapper;

import java.util.List;
import pojo.Schedules;
import pojo.SchedulesExample;

public interface SchedulesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Schedules record);

    int insertSelective(Schedules record);

    List<Schedules> selectByExample(SchedulesExample example);

    Schedules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Schedules record);

    int updateByPrimaryKey(Schedules record);
}