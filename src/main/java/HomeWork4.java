import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.min;

public class HomeWork4 {
    public static final char PLAYER_FIELD = 'X';
    public static final char COMPUTER_FIELD = 'O';
    public static final char EMPTY_FIELD = '-';

    public static int hSize = 5;
    public static int vSize = 5;
    public static int winSize = 3; //Длина линии для победы

    public static int inputValue(String str,int maxValue){
        Scanner scanner = new Scanner(System.in);
        int i;
        do {
            System.out.format("Введите значение %s-",str);
            i = scanner.nextInt()-1;
        }while (i<0||i>maxValue-1);
        return i;
    }

    public static char[][] initField()
    {
        char [][] field = new char[hSize][vSize];
        for (int i=0;i<hSize;i++){
            for (int j=0;j<vSize;j++){
                field[i][j]=EMPTY_FIELD;
            }
        }
        return field;
    }

    public static boolean isDraw(char[][] field)
    {
        for (int i = 0; i <hSize ; i++) {
            for (int j = 0; j <vSize ; j++) {
                if (isEmpty(field,i,j)) return false;
            }
        }
        return true;
    }

    //ищем в одномерном массиве winSize w символов подряд
    public static boolean isWinLine(char[] line,char w)
    {
        int size=0;
        for (int i = 0; i <line.length ; i++) {
            if (line[i]==w){
                size++;
                if (size==winSize) return true;
            } else {
                size=0;
            }
        }
        return false;
    }

    public static boolean checkHorizontal(char[][] field,char w)
    {
        for (char[] line:field)
        {
            if (isWinLine(line,w)) return true;
        }
        return false;
    }

    public static boolean checkVertical(char[][] field,char w){
        char[] line = new char[hSize];
        //поле у нас прямоугольное, т.е. все линии в нем одинаковой длины :)
        for (int j = 0; j <field[0].length ; j++)
        {
            for (int i = 0; i < field.length ; i++)
            {
                line[i]=field[i][j];
            }
            if (isWinLine(line,w)) return true;
        }
        return false;
    }

    //формирует массив адресов диагоналей и линий матрицы (игрового поля), в первой ячейке номер диагонали, 2 и 3 - адрес элемента диагонали
    public static HashMap<Integer,int[][]> allLinesMatrix(char[][] field){
        int countDiagonal = (hSize+vSize-3)*2;//-2 угла.
        HashMap<Integer,int[][]> allLinesMatrix =new HashMap<>();
        int index = 0;

        //вправо от левого верхнего угла, и вправо от нижнего левого угла
        for (int i = 0; i <countDiagonal/2 ; i++) {
            int diagLength=(min(vSize,hSize)-i);
            if (diagLength>1) {
                int[][] diag = new int[2][diagLength];
                int [][] ldiag = new int[2][diagLength];//обратные диагонали от нижнего ряда
                for (int j = 0; j < diagLength; j++) {
                    diag[0][j] = j;
                    diag[1][j] = i + j;

                    ldiag[0][j]=(hSize-1)-j;
                    ldiag[1][j]=(i+j);
                }
                index++;
                allLinesMatrix.put(index,diag);
                allLinesMatrix.put(-index,ldiag);//отрицательный индекс для упрощения отладки, что бы отличать в отладчике разные виды диагоналей
            }
        }

        //вниз от левого верхнего угла и вниз от правого верхнего угла
        //0,0 и 0,max пропускаем, поскольку эти диагонали получены в прощлый раз
        for (int i = 1; i <countDiagonal/2 ; i++) {
            int diagLength=(min(vSize,hSize)-i);
            if (diagLength>1) {
                int[][] diag = new int[2][diagLength];
                int [][] ldiag = new int[2][diagLength];//обратные диагонали от нижнего ряда
                for (int j = 0; j < diagLength; j++) {
                    diag[0][j] = i+j;
                    diag[1][j] = j;

                    ldiag[0][j]=i+j;
                    ldiag[1][j]=(hSize-1)-j;
                }
                index++;
                allLinesMatrix.put(index,diag);
                allLinesMatrix.put(-index,ldiag);//отрицательный индекс для упрощения отладки, что бы отличать в отладчике разные виды диагоналей
            }
        }

        return allLinesMatrix;
    }

    public static boolean checkLeftToRightDiagonal(char[][] field, char w){
        /*
          2 2 2 2 2
        1 1 0 0 0 0
        1 2 1 0 0 0
        1 3 2 1 0 0
        1 4 3 2 1 0
        1 5 4 3 2 1

          2 2 2 2
        1 1 2 3 4
        1 2 3 4 0
        1 3 4 0 0
        1 4 0 0 0
         */


        for (int i = 0; i < field.length ; i++) { /* 1 */
            int size = min(hSize,vSize)-i;//длина диагонали слева сверху -> вправо вниз , берем минимальную разницу, поскольку игровое поле в теории может быть прямоугольным
            if (size>=winSize){
                char[] line = new char[size];
                for (int j = 0; j <size ; j++) {
                    line[j]=field[i+j][j];
                }
                if (isWinLine(line,w)) return true;
            }
        }

        return false;
    }

    public static boolean checkRightToLeftDiagonal(char[][] field, char w){

        return false;
    }

    public static boolean isWin(char[][] field,char w){
        boolean win = checkHorizontal(field,w);
        if (win) return win;

        win = checkVertical(field,w);
        if (win) return win;

        win = checkLeftToRightDiagonal(field,w);
        if (win) return win;

        win = checkRightToLeftDiagonal(field,w);
        if (win) return win;

        return false;
    }

    public static void drawField(char[][] field){

        for (int i = 0; i <vSize; i++) {
            System.out.format("__%d_",i+1);
        }
        System.out.println("(v)");
        for (char[] line:field) {
            System.out.print("| ");
            for (char c:line) {
                System.out.print(c+" | ");
            }
            System.out.println();
            for (int i = 0; i <vSize*4+1 ; i++) {
                System.out.print('\u00AF');
            }
            System.out.println();
        }
    }

    public static boolean isEmpty(char[][] field,int h,int v){
        return field[h][v]==EMPTY_FIELD;
    }

    public static boolean isNotEmpty(char[][] field,int h,int v){
        return !isEmpty(field,h,v);
    }

    public static void doPlayerMove(char[][] field){
        int h,v;
        do{
            System.out.println("Enteer coord...");
            h= inputValue("h",hSize);
            v = inputValue("v",vSize);
        } while (isNotEmpty(field,h,v));
        field[h][v]=PLAYER_FIELD;
    }

    public static void doCompMove(char[][] field){
        int h,v;
        do{
            Random random=new Random();
            h = random.nextInt(hSize);
            v = random.nextInt(vSize);
        }while (isNotEmpty(field,h,v));
        field[h][v]=COMPUTER_FIELD;
    }

    public static void main(String[] args) {
        start();
    }

    static void start(){
        char[][] field=initField();
        HashMap<Integer,int[][]> diagonals = allLinesMatrix(field);
        do {
            doPlayerMove(field);
            if (isWin(field,PLAYER_FIELD)){
                System.out.println("Player WIN!!!");
                break;
            }
            if (isDraw(field)){
                System.out.println("Is Draw");
                break;
            }
            doCompMove(field);
            if (isWin(field,COMPUTER_FIELD)){
                System.out.println("Comp WIN!!!");
                break;
            }
            if (isDraw(field)){
                System.out.println("Is Draw");
                break;
            }
            drawField(field);
        } while(true);
        drawField(field);

    }
}
