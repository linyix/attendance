package service;

import mapper.GooutCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.GooutCheck;
import pojo.GooutCheckExample;

import java.util.List;

@Service
public class GooutCheckService {
    @Autowired
    GooutCheckMapper GooutCheckMapper;

    public GooutCheck getByGooutId(int lid)
    {
        GooutCheckExample GooutCheckExample= new GooutCheckExample();
        GooutCheckExample.createCriteria().andGooutIdEqualTo(lid);
        List<GooutCheck> GooutChecks=  GooutCheckMapper.selectByExample(GooutCheckExample);
        return GooutChecks.size()==0?null:GooutChecks.get(0);
    }
}
