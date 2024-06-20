import java.util.Scanner;

public class test1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String num = scanner.nextLine();
        String nnum = "";
        for(int i = 0;i < num.length();i++){
            int j = num.length() - i - 1;
            nnum += num.charAt(j);
        }
        System.out.println(nnum.toString());
    }

}
