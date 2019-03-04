package mapper;

import java.util.List;
import pojo.Leavee;
import pojo.LeaveeExample;

public interface LeaveeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Leavee record);

    int insertSelective(Leavee record);

    List<Leavee> selectByExample(LeaveeExample example);

    Leavee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Leavee record);

    int updateByPrimaryKey(Leavee record);
}