public class Score {
    private int id1; // training_plan 的 ID
    private int id2; // major_plan 的 ID
    private int id3; // employee 的 ID
    private double attendanceScore; // 出勤成绩
    private double examScore; // 考试成绩
    private double totalScore; // 总成绩
    private String name;

    // 构造方法
    public Score(int id1, int id2, int id3, double attendanceScore, double examScore, double totalScore) {
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
        this.attendanceScore = attendanceScore;
        this.examScore = examScore;
        this.totalScore = totalScore;
    }

    public Score() {
    }



    // Getter 和 Setter 方法
    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getId3() {
        return id3;
    }

    public void setId3(int id3) {
        this.id3 = id3;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(double attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public double getExamScore() {
        return examScore;
    }

    public void setExamScore(double examScore) {
        this.examScore = examScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", id3=" + id3 +
                ", attendanceScore=" + attendanceScore +
                ", examScore=" + examScore +
                ", totalScore=" + totalScore +
                '}';
    }
}
