import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    // 添加员工信息
    public void add(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (name, id_card_number, age, email, major_name, major_id, hire_date, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false); // 关闭自动提交

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, employee.getName());
                pstmt.setString(2, employee.getIdCardNumber());
                pstmt.setInt(3, employee.getAge());
                pstmt.setString(4, employee.getEmail());
                pstmt.setString(5, employee.getMajorName());
                pstmt.setInt(6, employee.getMajorId());
                pstmt.setDate(7, Date.valueOf(employee.getHireDate()));
                pstmt.setString(8, employee.getGender());
                pstmt.executeUpdate();
            }

            connection.commit(); // 提交事务
        } catch (SQLException e) {
            connection.rollback(); // 回滚事务
            throw e; // 重新抛出异常
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交
        }
    }

    // 获取所有员工信息的方法
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT name, id_card_number, age, hire_date, gender, email, major_name, major_id FROM employee"; // 更新查询字段

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String idCardNumber = resultSet.getString("id_card_number");
                int age = resultSet.getInt("age"); // 获取年龄
                LocalDate hireDate = resultSet.getDate("hire_date").toLocalDate(); // 获取入职日期
                String gender = resultSet.getString("gender"); // 获取性别
                String email = resultSet.getString("email"); // 获取电子邮件
                String majorName = resultSet.getString("major_name"); // 获取专业名称
                int majorId = resultSet.getInt("major_id"); // 获取专业编号

                // 创建 Employee 对象
                Employee employee = new Employee(name, idCardNumber, hireDate, gender);
                employee.setAge(age);
                employee.setEmail(email);
                employee.setMajorName(majorName);
                employee.setMajorId(majorId);
                employees.add(employee);
            }
        }

        return employees;
    }

    // 删除员工的方法
    public void delete(String idCardNumber) throws SQLException {
        String query = "DELETE FROM employee WHERE id_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, idCardNumber);
            statement.executeUpdate();
        }
    }

    // 根据身份证号获取员工信息
    public Employee getEmployeeById(String idCardNumber) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id_card_number = ?"; // SQL 查询语句
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, idCardNumber); // 设置参数
            ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询

            if (resultSet.next()) { // 如果有结果
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                LocalDate hireDate = resultSet.getDate("hire_date").toLocalDate();
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String majorName = resultSet.getString("major_name");
                int majorId = resultSet.getInt("major_id");

                // 创建 Employee 对象并返回
                Employee employee = new Employee(name, idCardNumber, hireDate, gender);
                employee.setAge(age);
                employee.setEmail(email);
                employee.setMajorName(majorName);
                employee.setMajorId(majorId);
                return employee;
            }
        }
        return null; // 如果没有找到员工，返回 null
    }

    // 修改员工信息
    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, age = ?, email = ?, major_name = ?, major_id = ?, hire_date = ?, gender = ? WHERE id_card_number = ?";

        try {
            connection.setAutoCommit(false); // 关闭自动提交

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, employee.getName());
                pstmt.setInt(2, employee.getAge());
                pstmt.setString(3, employee.getEmail());
                pstmt.setString(4, employee.getMajorName());
                pstmt.setInt(5, employee.getMajorId());
                pstmt.setDate(6, Date.valueOf(employee.getHireDate()));
                pstmt.setString(7, employee.getGender());
                pstmt.setString(8, employee.getIdCardNumber());
                pstmt.executeUpdate();
            }

            connection.commit(); // 提交事务
        } catch (SQLException e) {
            connection.rollback(); // 回滚事务
            throw e; // 重新抛出异常
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交
        }
    }

    // 查询员工信息
    public List<Employee> select(String name, String gender) throws SQLException {
        String sql = "SELECT * FROM employee WHERE (name LIKE ? OR ? IS NULL) AND (gender = ? OR ? IS NULL)";
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name != null ? "%" + name + "%" : null);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setString(4, gender);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getString("name"),
                            rs.getString("id_card_number"),
                            rs.getDate("hire_date").toLocalDate(),
                            rs.getString("gender")
                    );
                    employee.setAge(rs.getInt("age"));
                    employee.setEmail(rs.getString("email"));
                    employee.setMajorName(rs.getString("major_name"));
                    employee.setMajorId(rs.getInt("major_id"));
                    employees.add(employee);
                }
            }
        }

        return employees;
    }
}
