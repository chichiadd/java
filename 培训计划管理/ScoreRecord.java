import java.sql.Date;

public class ScoreRecord {
    private int planYear; // 年度
    private String name; // 培训计划名称
    private String majorName; // 专业名称
    private Date startDate; // 开始时间
    private Date endDate; // 结束时间
    private Integer id1;
    private Integer id2;

    public ScoreRecord(int planYear, String name, String majorName, Date startDate, Date endDate) {
        this.planYear = planYear;
        this.name = name;
        this.majorName = majorName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ScoreRecord(int id1, int id2, int planYear, String name, String majorName, Date startDate, Date endDate) {
        this.id1 = id1;
        this.id2 = id2;
        this.planYear = planYear;
        this.name = name;
        this.majorName = majorName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter 和 Setter 方法
    public int getPlanYear() {
        return planYear;
    }

    public void setPlanYear(int planYear) {
        this.planYear = planYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getId1() {
        return id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }
}
