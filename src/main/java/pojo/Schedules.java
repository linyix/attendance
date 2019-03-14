package pojo;

import java.util.Date;

public class Schedules {
    private Integer id;

    private Integer employeeId;

    private Integer clazzId;

    private Date clazzDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getClazzId() {
        return clazzId;
    }

    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
    }

    public Date getClazzDate() {
        return clazzDate;
    }

    public void setClazzDate(Date clazzDate) {
        this.clazzDate = clazzDate;
    }
}