package mapper;

import java.util.List;
import pojo.Attendances;
import pojo.AttendancesExample;

public interface AttendancesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attendances record);

    int insertSelective(Attendances record);

    List<Attendances> selectByExample(AttendancesExample example);

    Attendances selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attendances record);

    int updateByPrimaryKey(Attendances record);
}