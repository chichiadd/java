public interface Study {
//    public abstract void study();{} 接口不能有方法主体
    public abstract void Study();
    default void test(){
        System.out.println("我要开始学习了");
    }
}
