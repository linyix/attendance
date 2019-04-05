package service;

import mapper.LeaveeCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.LeaveeCheck;
import pojo.LeaveeCheckExample;

import java.util.List;

@Service
public class LeaveeCheckService {
    @Autowired
    LeaveeCheckMapper leaveeCheckMapper;

    public LeaveeCheck getByLeaveeId(int lid)
    {
        LeaveeCheckExample leaveeCheckExample= new LeaveeCheckExample();
        leaveeCheckExample.createCriteria().andLeaveeIdEqualTo(lid);
        List<LeaveeCheck> leaveeChecks=  leaveeCheckMapper.selectByExample(leaveeCheckExample);
        return leaveeChecks.size()==0?null:leaveeChecks.get(0);
    }
}
