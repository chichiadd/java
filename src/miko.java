public class miko extends person implements Study{
    public void Study(){
        test();
        System.out.println("我会努力学习巫术");
    }
    void Heal(person player){
        System.out.println("治疗伤口吧");
        player.HP += 50;
    }
    void Ward(person player){
        System.out.println("守护我们不受伤害吧");
        player.Defense += 20;
    }

    public  void main(String[] args) {
        this.Defense = 10;
        this.Magic_Resist = 20;
    }
}
