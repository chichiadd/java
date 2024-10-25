import java.util.Date;

public class TrainingPlanDTO {
    private Integer id; //编号
    private String name; //培训计划名称
    private Integer planYear; //年度
    private Date startDate; //开始时间
    private Date endDate; //结束时间
    private String majorPlane; //各专业培训计划内容
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
