package test;

import java.io.*;
import java.util.Scanner;

/*
完成有关文件的创建、读写、复制、删除
 */
public class Fileprogram {
    //创建文件
    public static void createFile(String filename) {
        //创建文件
        try (FileOutputStream inputStream = new FileOutputStream("D:\\" + filename)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readFile(String filename) {
        //读取文件
        try (FileInputStream inputStream = new FileInputStream("D:\\" + filename)) {
            int tmp;
            while ((tmp = inputStream.read()) != -1) {   //通过while循环来一次性读完内容
                System.out.println((char) tmp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String filename) {
        //写入文件
        try (FileOutputStream outputStream = new FileOutputStream("D:\\" + filename, true)) {
            boolean flag = true;
            while (flag) {
                System.out.println("1.写入内容");
                System.out.println("2.停止写入");
                Scanner content = new Scanner(System.in);
                int choose = content.nextInt();
                switch (choose) {
                    case 1:
                        System.out.println("请输入要写入的内容");
                        String str = content.nextLine();
                        outputStream.write(str.getBytes());
                        break;
                    case 2:
                        flag = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(String filename1, String filename2) {
        //复制文件
        try (FileInputStream inputStream = new FileInputStream("D:\\" + filename1);
             FileOutputStream outputStream = new FileOutputStream("D:\\" + filename2)) {
            int tmp;
            while ((tmp = inputStream.read()) != -1) {   //通过while循环来一次性读完内容
                outputStream.write((char) tmp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFile(String filename) {
        //删除文件
        File file = new File("D:\\" + filename);
        file.delete();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===========文件管理系统===========");
            System.out.println("该系统对D盘进行操作");
            System.out.println("1. 创建文件");
            System.out.println("2. 读取文件");
            System.out.println("3. 写入文件");
            System.out.println("4. 复制文件");
            System.out.println("5. 删除文件");
            System.out.println("6. 退出系统");
            System.out.println("===================================");
            int n = scanner.nextInt();
            Scanner name = new Scanner(System.in);
            switch (n) {
                case 1:
                    System.out.println("请输入要创建的文件名");
                    String name1 = name.nextLine();
                    createFile(name1);
                    break;
                case 2:
                    System.out.println("请输入要读取的文件名");
                    String name2 = name.nextLine();
                    readFile(name2);
                    break;
                case 3:
                    System.out.println("请输入要写入内容的文件名");
                    String name3 = name.nextLine();
                    writeFile(name3);
                    break;
                case 4:
                    System.out.println("请输入要复制内容的文件名");
                    String name4 = name.nextLine();
                    System.out.println("请输入要复制内容去的文件名");
                    String name5 = name.nextLine();
                    copyFile(name4, name5);
                    break;
                case 5:
                    System.out.println("请输入要删除的文件名");
                    String name6 = name.nextLine();
                    deleteFile(name6);
                    break;
                case 6:
                    System.out.println("感谢您的使用，再见");
                    System.exit(0);
            }
        }
    }
}
