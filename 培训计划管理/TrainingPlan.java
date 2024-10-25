import java.util.Date;

public class TrainingPlan {
    private Integer id; //编号
    private String name; //培训计划名称
    private Integer planYear; //年度
    private Date startDate; //开始时间
    private Date endDate; //结束时间
    private String majorPlane; //各专业培训计划内容
    // 构造方法
    public TrainingPlan() {
    }

    public TrainingPlan(Integer id, String name, Integer planYear, Date startDate, Date endDate, String majorPlane) {
        this.id = id;
        this.name = name;
        this.planYear = planYear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.majorPlane = majorPlane;
    }
    //getter方法

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPlanYear() {
        return planYear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getMajorPlane() {
        return majorPlane;
    }

    //setter方法

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanYear(Integer planYear) {
        this.planYear = planYear;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMajorPlane(String majorPlane) {
        this.majorPlane = majorPlane;
    }
}
