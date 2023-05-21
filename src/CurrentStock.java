import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CurrentStock extends JFrame{
    private JButton backButton;
    private JPanel panel;
    private JTable table1;

    private Connection connection;


    public CurrentStock() {
        setTitle("Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        connectToDatabase();
        loadStockDataFromDatabase();

    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            ControlPanel controlPanel = new ControlPanel();
            controlPanel.setVisible(true);
            setVisible(false);
        }
    });


            }

    private void connectToDatabase() {
        try {

            String url = "jdbc:mysql://localhost/20200305010";
            String username = "root";
            String password = "karakartal123";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStockDataFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM inventory";
            PreparedStatement statement1 = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            String[] columnNames = {"ID", "Name", "Quantity"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                int quantity = resultSet.getInt("Quantity");
                Object[] rowData = {id, name, quantity};
                model.addRow(rowData);
            }
            table1.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new CurrentStock();}
        });
    }
}