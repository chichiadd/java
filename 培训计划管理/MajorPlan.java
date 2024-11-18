import java.util.List;

public class MajorPlan {
    // 属性
    private String majorName;          // 专业名称
    private Integer majorId;              // 专业编号
    private String trainingPurpose;    // 培训目的
    private String trainingContent;    // 培训内容
    private Integer classHours;        // 课时
    private String teacher;            // 培训教师
    private Integer trainingPlanId; //编号,在数据库中做为外键

    // 构造方法
    public MajorPlan() {
    }

    public MajorPlan(String majorName, Integer majorId, String trainingPurpose, String trainingContent, Integer classHours,
                     String teacher) {
        this.majorName = majorName;
        this.majorId = majorId;
        this.trainingPurpose = trainingPurpose;
        this.trainingContent = trainingContent;
        this.classHours = classHours;
        this.teacher = teacher;
    }

    // getter和setter方法
    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getTrainingPurpose() {
        return trainingPurpose;
    }

    public void setTrainingPurpose(String trainingPurpose) {
        this.trainingPurpose = trainingPurpose;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public Integer getClassHours() {
        return classHours;
    }

    public void setClassHours(Integer classHours) {
        this.classHours = classHours;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void settrainingPlanId(Integer trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public Integer gettrainingPlanId() {
        return trainingPlanId;
    }

}
