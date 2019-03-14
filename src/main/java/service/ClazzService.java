package service;


import mapper.ClazzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Clazz;
import pojo.ClazzExample;

import java.util.Date;

@Service
public class ClazzService {

    @Autowired
    ClazzMapper clazzMapper;
    public Clazz test()
    {
        Clazz clazz= new Clazz();
        clazz.setStartTime(new Date());
        new Date();
        clazzMapper.insertSelective(clazz);
        ClazzExample clazzExample = new ClazzExample();
        clazzMapper.selectByExample(clazzExample);
        return clazzMapper.selectByPrimaryKey(1);
    }
}
