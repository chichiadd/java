import java.time.LocalDate; // 导入用于处理日期的类

public class Employee {
    // 姓名，必填项
    private String name;

    // 身份证号，必填项
    private String idCardNumber;

    // 年龄，选填项
    private int age;

    // 电子邮件，选填项
    private String email;

    // 专业名称
    private String majorName;

    // 专业编号
    private Integer majorId;

    // 入厂时间，必填项
    private LocalDate hireDate;

    // 性别，必填项
    private String gender; // 可以考虑使用枚举类型来表示性别

    private Integer id;
    // 构造函数
    public Employee(String name, String idCardNumber, LocalDate hireDate, String gender) {
        this.name = name;
        this.idCardNumber = idCardNumber;
        this.hireDate = hireDate;
        this.gender = gender;
    }

    public Employee(Integer id,String name,String majorName) {
        this.id = id;
        this.name = name;
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return name; // 返回员工姓名
    }


    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Integer getId() {
        return id;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // 输出员工信息
    public void displayInfo() {
        System.out.println("姓名: " + name);
        System.out.println("身份证号: " + idCardNumber);
        System.out.println("年龄: " + age);
        System.out.println("电子邮件: " + email);
        System.out.println("专业名称: " + majorName);
        System.out.println("专业编号: " + majorId);
        System.out.println("入厂时间: " + hireDate);
        System.out.println("性别: " + gender);
    }
}
