public class test extends Thread{
    public void run(){
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                this.sleep(100);
            }
        }
        catch (Exception e){
            System.out.println(" ");
        }
    }
    public static void main(String[] args) {
        test test = new test();
        test.start();
    }
}
