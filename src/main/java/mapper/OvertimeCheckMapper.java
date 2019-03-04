package mapper;

import java.util.List;
import pojo.OvertimeCheck;
import pojo.OvertimeCheckExample;

public interface OvertimeCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OvertimeCheck record);

    int insertSelective(OvertimeCheck record);

    List<OvertimeCheck> selectByExample(OvertimeCheckExample example);

    OvertimeCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OvertimeCheck record);

    int updateByPrimaryKey(OvertimeCheck record);
}