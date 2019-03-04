package mapper;

import java.util.List;
import pojo.Goout;
import pojo.GooutExample;

public interface GooutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goout record);

    int insertSelective(Goout record);

    List<Goout> selectByExample(GooutExample example);

    Goout selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goout record);

    int updateByPrimaryKey(Goout record);
}