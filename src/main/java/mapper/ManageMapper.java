package mapper;

import java.util.List;
import pojo.Manage;
import pojo.ManageExample;

public interface ManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Manage record);

    int insertSelective(Manage record);

    List<Manage> selectByExample(ManageExample example);

    Manage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manage record);

    int updateByPrimaryKey(Manage record);
}