package mapper;

import java.util.List;
import pojo.GooutCheck;
import pojo.GooutCheckExample;

public interface GooutCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GooutCheck record);

    int insertSelective(GooutCheck record);

    List<GooutCheck> selectByExample(GooutCheckExample example);

    GooutCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GooutCheck record);

    int updateByPrimaryKey(GooutCheck record);
}