import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StockManagement extends JFrame {
    private JButton deleteButton;
    private Connection connection;
    private JButton updateButton1;
    private JButton addButton1;
    private JButton backButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JPanel stock;

    public StockManagement() {
    setContentPane(stock);
    setTitle("StockManagement");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600,700);
    setVisible(true);
    setLocation(400,0);


    connectToDatabase();
    loadStockDataFromDatabase();

    addButton1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
// Code for the add button action
            int id = Integer.parseInt(textField1.getText());
            String name = textField2.getText();
            int quantity = Integer.parseInt(textField3.getText());

            try {
                String query = "INSERT INTO inventory (ID, Name, Quantity) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setInt(3, quantity);
                statement.executeUpdate();
                loadStockDataFromDatabase();
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                JOptionPane.showMessageDialog(null, "Data saved successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while saving data.");
            }

        }

    });

    deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                return;
            }
            int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            try {
                String query = "DELETE FROM inventory WHERE ID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                statement.executeUpdate();
                loadStockDataFromDatabase();
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                JOptionPane.showMessageDialog(null, "Data deleted successfully.");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlPanel controlPanel = new ControlPanel();
                controlPanel.setVisible(true);
                setVisible(false);
            }
        });
        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.");
                    return;

                }
                {

                }
                int id = Integer.parseInt(textField1.getText());
                String name = textField2.getText();
                int quantity = Integer.parseInt(textField3.getText());
                try {
                    String query = "UPDATE inventory SET NAME = ?, QUANTITY = ? WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setInt(2, quantity);
                    statement.setInt(3, id);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    JOptionPane.showMessageDialog(null, "Data updated successfully.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });
    }
    private void connectToDatabase() {
        try {
            final String DB_URL = "jdbc:mysql://localhost/20200305010";
            final String USERNAME = "root";
            final String PASSWORD = "karakartal123";
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        SwingUtilities.invokeLater(() -> {
            StockManagement StockManagement = new StockManagement();
            StockManagement.setVisible(true);
        });
    }
}