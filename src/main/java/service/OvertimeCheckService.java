package service;

import mapper.OvertimeCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.OvertimeCheck;
import pojo.OvertimeCheckExample;

import java.util.List;

@Service
public class OvertimeCheckService {
    @Autowired
    OvertimeCheckMapper overtimeCheckMapper;

    public OvertimeCheck getByOvertimeId(int lid)
    {
        OvertimeCheckExample overtimeCheckExample= new OvertimeCheckExample();
        overtimeCheckExample.createCriteria().andOvertimeIdEqualTo(lid);
        List<OvertimeCheck> overtimeChecks=  overtimeCheckMapper.selectByExample(overtimeCheckExample);
        return overtimeChecks.size()==0?null:overtimeChecks.get(0);
    }
}
