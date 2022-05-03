package P1;

import java.io.*;

public class MagicSquare {
    public static boolean generateMagicSquare(int n) {
        if(n % 2 == 0) {
            System.out.println("an even number is not available");
            return false;
        }
        if(n < 0) {
            System.out.println("a negative number is not available");
            return false;
        }
        int magic[][] = new int[n][n];//创建幻方数组
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {//i遍历整个幻方，指向每个元素的偏移量
            //罗伯法构造奇数阶幻方，即从第一行中间列开始依次斜向上递增加一
            magic[row][col] = i;//让当前指向数字与i相等
            if (i % n == 0)//遍历到下一行起始，row行数加一
                row++;
            else {
                if (row == 0)//行在第一行时，让row指向最后一行
                    row = n - 1;
                else
                    row--;//否则让row行数减一
                if (col == (n - 1))//列指向最后一列，重新调到第一列
                    col = 0;
                else
                    col++;//否则，列数加一
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        try {
            File f2 = new File("src/P1/txt/6.txt");//output to the file
            BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    bf.write(magic[i][j] + "\t");
                bf.write("\n");
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean isLegalchar(String str) {
        if (str.replaceAll("-*\\.*\\d*\\t*\\n*", "").length() == 0) {
            return true;
        }
        System.out.println("Contain illegal char");
        return false;
    }
    public static boolean isLegalMagicSquare(String fileName) {
        File file = new File(fileName);
        BufferedReader square = null;
        try {
            square = new BufferedReader(new FileReader(file));
            String squline;
            //the first line
            squline = square.readLine();
            if (!isLegalchar(squline)) {
                return false;
            }
            String[] digits = squline.split("\t");//split numbers
            int cols = digits.length;
            int rows = 0;
            String[][] Msquare = new String[cols][cols];
            Msquare[rows++] = digits;
            //other lines
            while ((squline = square.readLine()) != null) {
                if (!isLegalchar(squline)) {
                    return false;
                } else {
                    digits = squline.split("\t");//split numbers
                    if (rows >= cols) {
                        System.out.println("The number of rows is not equal to cols.");
                        return false;
                    }
                    if (digits.length != cols) {
                        System.out.println("The number of cols is not equal to cols.");
                        return false;
                    }
                    Msquare[rows++] = digits;
                }
            }
            if (cols != rows) {
                System.out.println("The number of rows in not equal to cols");
                return false;
            }
            int[][] MagicSquare = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    try {
                        MagicSquare[i][j] = Integer.valueOf(Msquare[i][j]);
                    } catch (NumberFormatException e) {
                        System.out.println("Square[" + i + "][" + j + "] is not an int");
                        return false;
                    }
                    if (MagicSquare[i][j] <= 0) {
                        System.out.println("Square[" + i + "][" + j + "] is not a positive int");
                        return false;
                    }
                }
            }
            int first = 0;//sum of the first row
            int other;//sum of other
            for (int i = 0; i < cols; i++)
                first += MagicSquare[0][i];
            //judge rows
            for (int i = 1; i < rows; i++) {
                other = 0;
                for (int j = 0; j < cols; j++) {
                    other += MagicSquare[i][j];
                }
                if (other != first) {
                    System.out.println("The sum of row[" + i + "] is not equal to row[0]");
                    return false;
                }
            }
            //judge cols
            for (int j = 0; j < cols; j++) {
                other = 0;
                for (int i = 0; i < rows; i++) {
                    other += MagicSquare[i][j];
                }
                if (other != first) {
                    System.out.println("The sum of col[" + j + "] is not equal to row[0]");
                    return false;
                }
            }
            //judge diagonals
            other = 0;
            int other1 = 0;
            for (int i = 0; i < rows; i++) {
                other += MagicSquare[i][i];
                other1 += MagicSquare[i][rows - i - 1];
            }
            if (other != first | other1 != first) {
                System.out.println("The sum of diagonal is not equal to row[0]");
                return false;
            }
            square.close();
        } catch (FileNotFoundException e2) {
            System.out.println("The txt is not found.");
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        } finally {
            if (square != null) {
                try {
                    square.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
        public static void main (String[]args){

            boolean result, result1, result2, result3;
            String txt;
            for(int i = 1; i <= 5; i++) {
                txt = "src/P1/txt/" + i + ".txt";
                result = isLegalMagicSquare(txt);
                System.out.println(i + ".txt" + " is " + result);
                System.out.println();
            }
            int n1 = 10, n2 = -5, n3 = 7;
            result1 = generateMagicSquare(n1);
            System.out.println("The result of " + n1 + " is " + result1 + "\n");
            result2 = generateMagicSquare(n2);
            System.out.println("The result of " + n2 + " is " + result2 + "\n");
            result3 = generateMagicSquare(n3);
            System.out.println("The result of " + n3 + " is " + result3 + "\n");
            txt = "src/P1/txt/6.txt";
            result = isLegalMagicSquare(txt);
            System.out.println("6.txt" + " is " + result);
            System.out.println();
        }
    }