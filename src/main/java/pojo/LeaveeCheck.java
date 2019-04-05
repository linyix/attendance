package pojo;

public class LeaveeCheck {
    private Integer id;

    private Integer employeeId;

    private Integer leaveeId;

    private String notes;

    private Boolean pass;

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

    public Integer getLeaveeId() {
        return leaveeId;
    }

    public void setLeaveeId(Integer leaveeId) {
        this.leaveeId = leaveeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }
}