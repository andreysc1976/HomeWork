import java.util.Random;

import static java.lang.Math.random;

public class HomeWork2 {

    public static void printArray(int a[]){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println("");
    }

    public static void printArray(int a[][]){
        for(int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length;j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void job1(){
        System.out.println("Job 1");
        int a[]={0,1,1,0,1,0,1,1,0,0,0,1,0,1};
        printArray(a);
        for (int i=0;i<a.length;i++){
            //a[i]=-1*a[i]+1; //вариант без условий)
            if (a[i]==0){
                a[i]=1;
            } else {
                a[i]=0;
            }
        }
        printArray(a);
    }

    public static void job2(){
        System.out.println("Job 2");
        int[] a=new int[8];
        for (int i=0;i<8;i++){
            a[i]=i*3;
        }
        printArray(a);
    }

    public static void job3(){
        System.out.println("Job 3");
        int a[]={ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
        int arraySize = a.length;
        int i=0;
        while(i<arraySize){
            if (a[i]<6) a[i]=a[i]*2;
            i++;
        }
        printArray(a);

    }

    public static void job4(){
        System.out.println("Job 4");
        int a[][]=new int[10][10];
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                if (i==j) a[i][j]=1;
                if (i+j==9) a[i][j]=1;
            }
        }
        printArray(a);
    }

    public static void job5(){
        int[] a = new int[20];//создадим и заполним массив случайными значениями
        Random random = new Random();
        for (int i=0;i<a.length;i++){
            a[i]=random.nextInt(50);
        }
        printArray(a);
        int min = a[0]; int minIndex = 0;
        int max = a[0]; int maxIndex = 0;

        for (int i=0;i<a.length;i++){
            if (a[i]>max){
                max=a[i];
                maxIndex=i;
            }
            if (a[i]<min){
                min = a[i];
                minIndex = i;
            }
        }
        System.out.println("Максимальное значение находится в позиции "+maxIndex+" и равняется "+max);
        System.out.println("Минимальное значение находится в позиции "+minIndex+" и равняется "+min);
    }



    public static boolean job6(int a[]){
        System.out.println("Job 6");
        printArray(a);
        int iter=0;
        do {
            int left = 0;
            int rigth = 0;
            for (int i=0;i<=iter;i++){
                //System.out.print(a[i]+" ");
                left = left + a[i];
            }
           // System.out.print("|| ");
            for (int i=iter+1;i<a.length;i++){
                //System.out.print(a[i]+" ");
                rigth = rigth + a[i];
            }
            //System.out.println(" Left="+left+" rigth="+rigth);
            if (left==rigth){
                return true;
            }
            iter++;
        } while (iter<a.length-1);
        return false;
    }

    public static void job7(int a[],int shift){
        System.out.println("Job 7");
        printArray(a);
        int temp;
        for (int i=0;i<Math.abs(shift);i++){
            if (shift>0){
                temp=a[0];
            } else {
                temp=a[a.length-1];
            }
            for (int j=0;j<a.length-1;j++){
                if (shift>0) {
                    a[j]=a[j+1];
                } else {
                    a[a.length-j-1]=a[a.length-j-2];
                }
            }
            if (shift>0) {
                a[a.length-1]=temp;
            } else if (shift<0) {
                a[0]=temp;
            }
        }
        printArray(a);
    }

    public static void main(String[] args) {
        job1();
        job2();
        job3();
        job4();
        job5();
        int[] testArray={16,2,1,1,2,1,1,8};
        System.out.println(job6(testArray));

        job7(testArray,-2);
    }
}
