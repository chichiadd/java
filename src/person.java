public class person {
    String name = "未知";
    int age = 0;
    String sex = "未知";
    int HP = 100;
    int MP = 100;
    int level = 1;
    int Defense;//防御力
    int Magic_Resist;//魔抗

    void hello(){
        System.out.println("我是" + name + "，性别" + sex + "，今年" + age + "岁");
    }
    void person(String name,int age,String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

}
