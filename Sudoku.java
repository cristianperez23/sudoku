import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sudoku extends JFrame {
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    private JButton solveButton;

    public Sudoku() {
        setTitle("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("SansSerif", Font.BOLD, 20));
                add(cells[row][col]);
            }
        }

        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });
        add(solveButton);

        setVisible(true);
    }

    private boolean isValid(int row, int col, int num) {
        for (int c = 0; c < GRID_SIZE; c++) {
            if (c != col && Integer.parseInt(cells[row][c].getText()) == num) {
                return false;
            }
        }

        for (int r = 0; r < GRID_SIZE; r++) {
            if (r != row && Integer.parseInt(cells[r][col].getText()) == num) {
                return false;
            }
        }

        int subgridRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        for (int r = subgridRow; r < subgridRow + SUBGRID_SIZE; r++) {
            for (int c = subgridCol; c < subgridCol + SUBGRID_SIZE; c++) {
                if ((r != row || c != col) && Integer.parseInt(cells[r][c].getText()) == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == GRID_SIZE) {
            return true;
        }

        if (col == GRID_SIZE) {
            return solve(row + 1, 0);
        }

        if (!cells[row][col].getText().isEmpty()) {
            return solve(row, col + 1);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                cells[row][col].setText(Integer.toString(num));
                if (solve(row, col + 1)) {
                    return true;
                }
                cells[row][col].setText("");
            }
        }

        return false;
    }

    public static void main(String[] args) {
        new Sudoku();
    }
}
