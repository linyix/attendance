package pojo;

import java.util.Date;

public class Clazz {
    private Integer id;

    private Integer departmentId;

    private Date startTime;

    private Date endTime;

    private Integer bfStart;

    private Integer afEnd;

    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBfStart() {
        return bfStart;
    }

    public void setBfStart(Integer bfStart) {
        this.bfStart = bfStart;
    }

    public Integer getAfEnd() {
        return afEnd;
    }

    public void setAfEnd(Integer afEnd) {
        this.afEnd = afEnd;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}