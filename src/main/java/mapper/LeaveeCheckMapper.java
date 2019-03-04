package mapper;

import java.util.List;
import pojo.LeaveeCheck;
import pojo.LeaveeCheckExample;

public interface LeaveeCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LeaveeCheck record);

    int insertSelective(LeaveeCheck record);

    List<LeaveeCheck> selectByExample(LeaveeCheckExample example);

    LeaveeCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LeaveeCheck record);

    int updateByPrimaryKey(LeaveeCheck record);
}