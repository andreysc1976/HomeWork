import java.util.Random;
import java.util.Scanner;

public class HomeWork3 {
    public static  Scanner in = new Scanner(System.in);

    public static int getIntFromConsole(String question, int min, int max){

        int rez;
        do {
            System.out.println(question);
            rez=in.nextInt();
        } while (rez<min||rez>max);
        return rez;
    }

    public static void randomNumber(){
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        int repeat;
        do {
            int step = 0;
            int question=random.nextInt(10);
            boolean ugadal = false;
            System.out.println("Я загадал число от 0 до 9");
            do {
                //System.out.println(question);
                step++;

                int answer = getIntFromConsole("Угадайте число от 0 до 9",0,9);
                if (answer>question){
                    System.out.println("Загаднное число меньше");
                } else if(answer<question){
                    System.out.println("Загаданное число больше");
                } else  {
                    ugadal=true;

                }
            } while (step < 3 && !ugadal);
            if (ugadal) {
                System.out.println("Вы угадали");
            } else  {
                System.out.println("Вы проиграли");
            }
            System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
            repeat=in.nextInt();
        } while(repeat==1);
    }

    public static void randomFruit(){
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        Random random = new Random();
        int questWord = random.nextInt(words.length);
        boolean ugadal=false;
        char[] answerMask={'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'};
        //System.out.println(words[questWord]);
        System.out.println("Я загадал слово, попробуй угадай");
        while (!ugadal){
            String answer = in.next();
            if (answer.equalsIgnoreCase(words[questWord])){
                ugadal=true;
            } else {
                int size = Math.min(answer.length(),words[questWord].length());
                for (int i=0;i<size;i++){
                    if (answer.charAt(i)==words[questWord].charAt(i)){
                        answerMask[i]=answer.charAt(i);
                    }
                }
            }
            System.out.println(answerMask);

        }
        System.out.println("Поздравляю, вы угадали загаданное слово - "+words[questWord]);
    }

    public static void main(String[] args) {
        int answer;
        boolean exit=false;
        do{
            System.out.println("В какую игру по играем?");
            System.out.println("1. Угадай число");
            System.out.println("2. Угадай фрукт");
            System.out.println("3. Выход");
            answer = in.nextInt();
            switch (answer){
                case 1-> randomNumber();
                case 2-> randomFruit();
                default->exit=true;
            }

        }while (!exit);
    }
}
