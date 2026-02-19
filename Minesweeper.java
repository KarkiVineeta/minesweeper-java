import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

    static int size;
    static int mines;
    static char[][] board;
    static boolean[][] mineBoard;
    static boolean[][] revealed;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper");
        System.out.print("Enter grid size: ");
        size = sc.nextInt();
        System.out.print("Enter number of mines: ");
        mines = sc.nextInt();
        board = new char[size][size];
        mineBoard = new boolean[size][size];
        revealed = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '_';
            }
        }
        placeMines();
        while (true) {
            printBoard();
            System.out.print("Enter row and column (e.g. 1 2): ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            row--; col--;
            if (row < 0 || row >= size || col < 0 || col >= size) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            if (mineBoard[row][col]) {
                System.out.println("Game Over! You hit a mine.");
                board[row][col] = '*';
                printBoard();
                break;
            }
            revealCell(row, col);
            if (checkWin()) {
                System.out.println("You won!");
                printBoard();
                break;
            }
        }
        sc.close();
    }

    static void placeMines() {
        Random rand = new Random();
        int count = 0;
        while (count < mines) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (!mineBoard[r][c]) {
                mineBoard[r][c] = true;
                count++;
            }
        }
    }

    static void revealCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) return;
        if (revealed[row][col]) return;
        revealed[row][col] = true;
        int adjacent = countAdjacentMines(row, col);
        board[row][col] = (char) (adjacent + '0');
        if (adjacent == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j);
                }
            }
        }
    }

    static int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                    if (mineBoard[newRow][newCol]) count++;
                }
            }
        }
        return count;
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean checkWin() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineBoard[i][j] && !revealed[i][j]) return false;
            }
        }
        return true;
    }
}
