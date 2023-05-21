import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class ControlPanel extends JFrame {
    private JButton goButton;
    private JButton goButton1;
    private JButton exitButton;
    private JPanel stock;



    public ControlPanel() {
        setTitle("Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(stock);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentStock currentStock = new CurrentStock();
                currentStock.setVisible(true);
                setVisible(false);
            }
        });
        goButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockManagement stockManagement = new StockManagement();
                stockManagement.setVisible(true);
                setVisible(false);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    System.exit(0);
                }
            }
        });

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlPanel ControlPanel = new ControlPanel();
            ControlPanel.setVisible(true);
        });

    }
}
