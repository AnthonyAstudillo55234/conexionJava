import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

public class form1 {
    private JButton buscarButton;
    public JPanel mainPanel;
    private JTextField cedulaTxt;
    private JLabel nombre;
    private JLabel nota1;
    private JLabel nota2;

    public form1() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/estudiantes2024A";
                String user = "root";
                String password = "123456";
                Scanner sc = new Scanner(System.in);
                try (Connection connection = DriverManager.getConnection(url,user,password)){
                    System.out.println("Conectando a la base de datos");
                    System.out.println("Ingrese el numero de cedula del estudiante: ");
                    String cedula = cedulaTxt.getText();
                    String query = "select * from estudiante where cedula = '" + cedula + "'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    System.out.println();
                    System.out.println("Notas Estudiantes\n");
                    while(resultSet.next()){
                        System.out.println("\nCedula: " + resultSet.getString("cedula"));
                        cedulaTxt.setText(resultSet.getString("cedula"));
                        System.out.println("Nombre Estudiante: " + resultSet.getString("nombre"));
                        nombre.setText("Nombre Estudiante: " + resultSet.getString("nombre"));
                        System.out.println("Nota Bimestre Uno: " + resultSet.getString("bimestreUno"));
                        nota1.setText("Nota Bimestre Uno: " + resultSet.getString("bimestreUno"));
                        System.out.println("Nota Bimestre Dos: " + resultSet.getString("bimestreDos")+"\n");
                        nota2.setText("Nota Bimestre Dos: " + resultSet.getString("bimestreDos"));
                        float nota1 = resultSet.getFloat("bimestreUno");
                        float nota2 = resultSet.getFloat("bimestreDos");
                        float suma = nota1 + nota2;
                        System.out.println("Promedio: " + suma);
                        if (suma > 28){
                            System.out.println("El estudiante aprueba sin supletorio");
                        } else if (18 < suma && suma < 28) {
                            System.out.println("El estudiante entra supletorio y para pasar le hace falta: " + String.format("%.2f",40 - suma));
                        }else {
                            System.out.println("El estudiante reprueba sin supletorio para llegar al supletorio le falta: " + String.format("%.2f",18 - suma));
                        }
                    }
                }
                catch (SQLException e1){
                    System.out.println(e1.getMessage());
                }
            }
        });
    }
}
