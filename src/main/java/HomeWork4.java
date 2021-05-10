import java.util.Scanner;

public class HomeWork4 {
    public static Scanner in = new Scanner(System.in);

    public static int inputInt(String message){
        System.out.println(message);
        return in.nextInt();
    }

    public static void main(String[] args) {
        System.out.println("Home work 4");
        int i =inputInt("test");
    }
}
