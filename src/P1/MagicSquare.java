package P1;

import java.io.*;

public class MagicSquare {
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
            int rows = digits.length;
            int cols = 0;
            String[][] Msquare = new String[rows][rows];
            Msquare[cols++] = digits;
            //other lines
            while ((squline = square.readLine()) != null) {
                if (!isLegalchar(squline)) {
                    return false;
                } else {
                    digits = squline.split("\t");//split numbers
                    if (cols >= rows) {
                        System.out.println("The number of rows is not equal to cols.");
                        return false;
                    }
                    if (digits.length != rows) {
                        System.out.println("The number of rows is not equal to rows.");
                        return false;
                    }
                    Msquare[cols++] = digits;
                }
            }
            if (cols != rows) {
                System.out.println("The number of rows in not equal to cols");
                return false;
            }
            int[][] MagicSquare = new int[cols][rows];
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
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
            int first = 0;//sum of the first col
            int other;//sum of other
            for (int i = 0; i < rows; i++)
                first += MagicSquare[0][i];
            //judge cols
            for (int i = 1; i < cols; i++) {
                other = 0;
                for (int j = 0; j < rows; j++) {
                    other += MagicSquare[i][j];
                }
                if (other != first) {
                    System.out.println("The sum of col[" + i + "] is not equal to col[0]");
                    return false;
                }
            }
            //judge rows
            for (int j = 0; j < rows; j++) {
                other = 0;
                for (int i = 0; i < cols; i++) {
                    other += MagicSquare[i][j];
                }
                if (other != first) {
                    System.out.println("The sum of row[" + j + "] is not equal to col[0]");
                    return false;
                }
            }
            //judge diagonals
            other = 0;
            int other1 = 0;
            for (int i = 0; i < cols; i++) {
                other += MagicSquare[i][i];
                other1 += MagicSquare[i][cols - i - 1];
            }
            if (other != first | other1 != first) {
                System.out.println("The sum of diagonal is not equal to col[0]");
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

            boolean result;
            String txt;
            for(int i = 1; i <= 5; i++) {
                txt = "src/P1/txt/" + i + ".txt";
                result = isLegalMagicSquare(txt);
                System.out.println(i + ".txt" + " is " + result);
            }
        }
    }