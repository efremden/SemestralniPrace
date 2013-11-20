package semestralniprace;

/**
 *
 * @author Efremov Denis
 */
public class Functions {
    //Сложeние матриц//+
    public double[][]soucet(double[][] matrix1, double[][] matrix2){
        int m = matrix1.length;
        int n = matrix1[0].length;
        double[][]c = new double[m][n];
        for(int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){
                c[i][j] = matrix1[i][j]+matrix2[i][j];
            }
        }
    return c;
    }
    //умножение//*
    public double[][] soucin(double[][] matrix1, double[][] matrix2)
    {
        int m = matrix1.length;
        int n = matrix1[0].length;
        int k = matrix2.length;
        int l = matrix2[0].length;

            double[][] result = new double[m][l];
            for(int i=0; i<m; i++){
                for(int j=0; j<l; j++){
                    result[i][j] = 0;
                    for (int s=0; s<n; s++){
                        result[i][j] += matrix1[i][s] * matrix2[s][j];
                    }
                }
            }       
     return result;}
    
    public void print(double[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){
                String cs;
                matrix[i][j] = matrix[i][j]*100;
                int l= (int)Math.round(matrix[i][j]);
                matrix[i][j] = (double)l/100;
                cs = Double.toString(matrix[i][j]);
                switch (cs.length()){
                    case 3:{System.out.print("      "+matrix[i][j]);break;}
                    case 4:{System.out.print("     "+matrix[i][j]);break;}
                    case 5:{System.out.print("    "+matrix[i][j]);break;}
                    case 6:{System.out.print("   "+matrix[i][j]);break;}
                    case 7:{System.out.print("  "+matrix[i][j]);break;}
                    case 8:{System.out.print(" "+matrix[i][j]);break;}    
                }
            }
            System.out.print("\n");
        }
    }
    //Determinant
    
    /*

        Рассматриваем строку i(начиная с первой). Если, элемент aii равен нулю, 
        * меняем местами i-ю и i+1-ю строки матрицы. 
        * Знак определителя при этом изменится на противоположный. 
        * Если a11 отличен от нуля - переходим к следующему шагу;
        Для каждой строки j, ниже i-й находим значение коэффициента Kj=aji/aii;
        Пересчитываем элементы всех строк j, расположенных ниже текущей строки i,
        * с использованием соответствующих коэффициентов по формуле: 
        * ajkнов.=ajk-Kj*aik; После чего, возвращаемся к первому шагу
        * алгоритма и рассматриваем следующую строку, пока не доберемся
        * до строки i=n-1, где n - размерность матрицы A
        В полученной треугольной матрице расчитываем произведение 
        * всех элементов главной диагонали Пaii, которое и будет являтся определителем;
*/
    
    public double determinant(double A[][]) {
        int n = A.length;
        double D = 1.0;                 // определитель//det
        double B[][] = new double[n][n];  // рабочая матрица//pracovni matice
        int row[] = new int[n];
        int hold, I_pivot;
        double pivot;
        double abs_pivot;

        if (A[0].length != n) {
            System.out.println("Error in Matrix.determinant, inconsistent array sizes.");
        }
        // создаем рабочую матрицу//nova pr matice
        for (int i = 0; i < n; i++)
            System.arraycopy(A[i], 0, B[i], 0, n);
        // заполняем вектор перестановок//vektor perestanovok
        for (int k = 0; k < n; k++) {
            row[k] = k;
        }
        // начало основного цикла //novy ciklus
        for (int k = 0; k < n - 1; k++) {
            // находим наиболший элемент для основы// nejvetsi prvek
            pivot = B[row[k]][k];
            abs_pivot = Math.abs(pivot);
            I_pivot = k;
            for (int i = k; i < n; i++) {
                if (Math.abs(B[row[i]][k]) > abs_pivot) {
                    I_pivot = i;
                    pivot = B[row[i]][k];
                    abs_pivot = Math.abs(pivot);
                }
            }
            // если нашлась такая основа, то меняем знак определителя и меняем местами столбцы
            //je-li je 0 na zacatku, zmenime sloupci, a nasobim -1 determinant
            if (I_pivot != k) {
                hold = row[k];
                row[k] = row[I_pivot];
                row[I_pivot] = hold;
                D = -D;
            }
            // проверка на ноль//kontrola nuly
            if (abs_pivot < 1.0E-10) {
                return 0.0;
            } else {
                D = D * pivot;
                // делим на основу
                for (int j = k + 1; j < n; j++) {
                    B[row[k]][j] = B[row[k]][j] / B[row[k]][k];
                }
                //  внутренний цикл
                for (int i = 0; i < n; i++) {
                    if (i != k) {
                        for (int j = k + 1; j < n; j++) {
                            B[row[i]][j] = B[row[i]][j] - B[row[i]][k] * B[row[k]][j];
                        }
                    }
                }
            }
            // конец внутреннего цикла
        }
        // конец главного цикла
        return D * B[row[n - 1]][n - 1];
    }
    /*public double determinant(double[][] matrix){
        double c=1;
        int m = matrix.length;
        if(m==1)c=matrix[0][0];
        if (m==2) c = matrix[0][0]*matrix[1][1]-matrix[1][0]*matrix[0][1];
        else {
            for(int i=0; i < m-1; i++){
                if (matrix[i][i]==0){
                    for(int j=0; j< m; j++){
                        double tmp;
                        tmp = matrix[j][i];
                        matrix[j][i] = matrix[j][i+1];
                        matrix[j][i]=tmp;
                    }
                    c *=-1;
                }
                double[]k = new double[m];
                for (int j = 0; j < m; j++){
                    k[j] = matrix[j][i]/matrix[j][i+1]; 
                }
                for(int j=i+1; j<m;j++){
                    for (int l = i+1; l<m;l++){
                        matrix[l][j] = matrix[l][j] - k[j]*matrix[l][i]; 
                    }
                }  
            }
            for (int i=0; i< m; i++){
                c*=matrix[i][i];
            }
        }
    return c;
    }*/
      
}
