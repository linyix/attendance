package mapper;

import java.util.List;
import pojo.Overtime;
import pojo.OvertimeExample;

public interface OvertimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Overtime record);

    int insertSelective(Overtime record);

    List<Overtime> selectByExample(OvertimeExample example);

    Overtime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Overtime record);

    int updateByPrimaryKey(Overtime record);
}