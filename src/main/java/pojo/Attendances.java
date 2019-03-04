package pojo;

import java.util.Date;

public class Attendances {
    private Integer id;

    private Integer employeeId;

    private Date checkIn;

    private Date checkOut;

    private Integer schedulesId;

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

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getSchedulesId() {
        return schedulesId;
    }

    public void setSchedulesId(Integer schedulesId) {
        this.schedulesId = schedulesId;
    }
}