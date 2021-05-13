import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.min;

public class HomeWork4 {
    public static final char PLAYER_FIELD = 'X';
    public static final char COMPUTER_FIELD = 'O';
    public static final char EMPTY_FIELD = '-';

    public static int hSize = 5;
    public static int vSize = 6;
    public static int winSize = 4; //Длина линии для победы

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


    //формирует массив адресов диагоналей и линий матрицы (игрового поля), в первой ячейке номер диагонали, 2 и 3 - адрес элемента диагонали
    public static HashMap<Integer,int[][]> allLinesList(char[][] field){
        int countDiagonal = (hSize+vSize-3)*2;//-2 угла.
        HashMap<Integer,int[][]> allLinesList =new HashMap<>();
        int index = 0;

        //вправо от левого верхнего угла, и вправо от нижнего левого угла
        for (int i = 0; i <countDiagonal/2 ; i++) {
            int diagLength=(min(vSize,hSize)-i);
            if (diagLength>winSize) {
                int[][] diag = new int[2][diagLength];
                int [][] ldiag = new int[2][diagLength];//обратные диагонали от нижнего ряда
                for (int j = 0; j < diagLength; j++) {
                    diag[0][j] = j;
                    diag[1][j] = i + j;

                    ldiag[0][j]=(hSize-1)-j;
                    ldiag[1][j]=(i+j);
                }
                index++;
                allLinesList.put(index,diag);
                allLinesList.put(-index,ldiag);//отрицательный индекс для упрощения отладки, что бы отличать в отладчике разные виды диагоналей
            }
        }

        //вниз от левого верхнего угла и вниз от правого верхнего угла
        //0,0 и 0,max пропускаем, поскольку эти диагонали получены в прощлый раз
        for (int i = 1; i <countDiagonal/2 ; i++) {
            int diagLength=(min(vSize,hSize)-i);
            if (diagLength>=winSize) {
                int[][] diag = new int[2][diagLength];
                int [][] ldiag = new int[2][diagLength];//обратные диагонали от нижнего ряда
                for (int j = 0; j < diagLength; j++) {
                    diag[0][j] = i+j;
                    diag[1][j] = j;

                    ldiag[0][j]=i+j;
                    ldiag[1][j]=(hSize-1)-j;
                }
                index++;
                allLinesList.put(index,diag);
                allLinesList.put(-index,ldiag);//отрицательный индекс для упрощения отладки, что бы отличать в отладчике разные виды диагоналей
            }
        }

        //теперь задача попроще, соберем все линии в этот же словарь
        for (int i = 0; i <field.length ; i++) {
            int[][] line = new int[2][field[i].length];
            for (int j = 0; j <field[i].length ; j++) {
                line[0][j]=i;
                line[1][j]=j;
            }
            index++;
            allLinesList.put(index,line);
        }
        for (int j = 0; j <field[0].length ; j++) {
            int [][] line = new int[2][field.length];
            for (int i = 0; i < field.length ; i++) {
                line[0][i]=i;
                line[1][i]=j;
            }
            index++;
            allLinesList.put(index,line);
        }

        return allLinesList;
    }


    public static boolean isWin(char[][] field,char w,HashMap<Integer,int[][]> allLineList){

        for (Integer key:allLineList.keySet()) {
            int[][] addr = allLineList.get(key);
            int sum=0;
            for (int i = 0; i < addr[0].length ; i++) {
                int addrh = addr[0][i];
                int addrv = addr[1][i];
                if (field[addrh][addrv]==w){
                    sum++;
                } else {
                    sum=0;
                }
                if(sum>=winSize) return true;
            }
        }
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

    public static void doCompMove(char[][] field,HashMap<Integer,int[][]> allLinesList){
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
        HashMap<Integer,int[][]> allLinesList = allLinesList(field);
        do {
            doPlayerMove(field);
            if (isWin(field,PLAYER_FIELD,allLinesList)){
                System.out.println("Player WIN!!!");
                break;
            }
            if (isDraw(field)){
                System.out.println("Is Draw");
                break;
            }
            doCompMove(field,allLinesList);
            if (isWin(field,COMPUTER_FIELD,allLinesList)){
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
