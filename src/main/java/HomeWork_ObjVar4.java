public class HomeWork_ObjVar4 {
    public static final char PLAYER_MOVE = 'X';
    public static final char AI_MOVE = 'O';
    public static final char EMPTY = '-';

    static class Field{
        private char[][] field;
        private int winSize;
        private int sizeX;
        private int sizeY;

        public Field(int sizeY,int sizeX,int winSize){

            this.field = new char[sizeY][sizeX];
            this.winSize = winSize;
            this.sizeY = sizeY;
            this.sizeX = sizeX;
            for (int i = 0; i <field.length ; i++) {
                for (int j = 0; j <field[i].length ; j++) {
                    field[i][j]=EMPTY;
                }
            }
        }

        private boolean isEmpty(int y,int x){
            if (field[y][x]==EMPTY) return true;
            return false;
        }

        private boolean isNotEmpty(int y, int x){
            return !isEmpty(y,x);
        }

        public boolean move(int y,int x,char side){
            if (isNotEmpty(y,x)) return false;
            field[y][x]=side;
            return true;
        }

        public boolean checkWin(char side){
            return true;
        }

        public void printField(){
            for (int i = 0; i <sizeX; i++) {
                System.out.format("__%d_",i+1);
            }
            System.out.println("(X)");
            for (char[] line:field) {
                System.out.print("| ");
                for (char c:line) {
                    System.out.print(c+" | ");
                }
                System.out.println();
                for (int i = 0; i <sizeY*4+1 ; i++) {
                    System.out.print('\u00AF');
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {



                // TODO Auto-generated method stub

                int n = 6;
                int i = 0;
                int j = 0;
                int s = 0;
                int l = 0;
                int mas[][] = new int [n][n];

                for (s = 0; s < n; s ++){
                    for (i = 0; i <= s; i++){
                        j = s+i;
                        if (j < n){
                            mas[i][j] = l++;
                        }
                    }
                }



                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++) {
                        System.out.print (mas[i][j] + " ");
                    }
                    System.out.println();
                }

            }


}
