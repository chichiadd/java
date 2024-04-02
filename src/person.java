public class person {
    String name = "未知";
    int age = 0;
    String sex = "未知";

    void hello(){
        System.out.println("我是" + name + "，性别" + sex + "，今年" + age + "岁");
    }
    void person(String name,int age,String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

}
