import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.min;

public class HomeWork4 {
    public static final char PLAYER_FIELD = 'X';
    public static final char COMPUTER_FIELD = 'O';
    public static final char EMPTY_FIELD = '-';

    public static int hSize = 3;
    public static int vSize = 3;
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

    public static char[][] initField()    {
        char [][] field = new char[hSize][vSize];
        for (int i=0;i<hSize;i++){
            for (int j=0;j<vSize;j++){
                field[i][j]=EMPTY_FIELD;
            }
        }
        return field;
    }

    public static boolean isDraw(char[][] field)    {
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
            if (diagLength>=winSize) {
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


    public static int weigthLine(char[][] field, int[][] addr){
        int sum=0;
        int maxSum=0;
        for (int i = 0; i <addr[0].length ; i++) {
            int addrh = addr[0][i];
            int addrv = addr[1][i];
                                    //AI играет против игрока
            if (field[addrh][addrv]==PLAYER_FIELD){
                sum++;
                if (sum>maxSum){
                    maxSum=sum;
                }
            } else {
                sum=0;
            }
        }
        return maxSum;
    }

    public static void sortWeigthArray(int[][] weigthArray){
        boolean moved=true;
        int tempIndex,tempWeigth;
        while(moved){
            moved=false;
            for (int i = 0; i <weigthArray[0].length-1 ; i++) {
                if (weigthArray[1][i]<weigthArray[1][i+1]){
                    tempIndex=weigthArray[0][i];
                    tempWeigth=weigthArray[1][i];
                    weigthArray[0][i]=weigthArray[0][i+1];
                    weigthArray[1][i]=weigthArray[1][i+1];
                    weigthArray[0][i+1]=tempIndex;
                    weigthArray[1][i+1]=tempWeigth;
                    moved=true;
                }
            }
        }
    }

    public static boolean moveToLine(char[][] field,int[][] addr){
        boolean prevEmpty=false;
        boolean prevPlayer=false;
        for (int i = 0; i <addr[0].length ; i++) {
            int addrh = addr[0][i];
            int addrv = addr[1][i];
            if ((field[addrh][addrv]==PLAYER_FIELD)&&(prevEmpty))
            {
                int prevaddrh = addr[0][i-1];
                int prevaddrv = addr[1][i-1];
                field[prevaddrh][prevaddrv]=COMPUTER_FIELD;
                return true;
            }
            if (field[addrh][addrv]==EMPTY_FIELD&&prevPlayer){
                int prevaddrh = addr[0][i];
                int prevaddrv = addr[1][i];
                field[prevaddrh][prevaddrv]=COMPUTER_FIELD;
                return true;
            }
            if (field[addrh][addrv]==PLAYER_FIELD)
            {
                prevPlayer=true;
            } else if (field[addrh][addrv]==EMPTY_FIELD){
                prevEmpty=true;
            } else if (field[addrh][addrv]==COMPUTER_FIELD){
                prevPlayer=false;prevEmpty=false;
            }

        }
        return false;
    }

    public static void doAiMove(char[][] field,HashMap<Integer,int[][]> allLineList){
        int[][] weigthLines = new int[2][allLineList.size()];
        int i=0;
        for (Integer key:allLineList.keySet()) {
            int[][] addr = allLineList.get(key);
            weigthLines[0][i]=key;
            weigthLines[1][i]=weigthLine(field,addr);
            i++;
        }
        sortWeigthArray(weigthLines);
       //ходить будем в строку где победа игрока ближе
        int[][] addr;
        boolean complete=false;
        for (int j = 0; j <weigthLines[0].length ; j++) {
            addr = allLineList.get(weigthLines[0][j]);
            complete = moveToLine(field,addr);
            if (complete) break;
        }
        if (!complete) System.out.println("Error");


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
            doAiMove(field,allLinesList);
            //doCompMove(field,allLinesList);
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
