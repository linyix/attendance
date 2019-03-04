package pojo;

public class GooutCheck {
    private Integer id;

    private Integer employeeId;

    private Integer gooutId;

    private String notes;

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

    public Integer getGooutId() {
        return gooutId;
    }

    public void setGooutId(Integer gooutId) {
        this.gooutId = gooutId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }
}