import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class lienSqlJava {

    Connection dbConnection;
    PreparedStatement insertion;

    // Etablit la connexion avec la base de données.
    public void openDBConnection()
    {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        try {
            dbConnection=dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //Initialise le PreparedStatement utilisé pour insérer des données.
    public void initInsertPreparedStatement() {
        // TODO Auto-generated method stub
        try {
            insertion=dbConnection.prepareStatement("INSERT INTO SORTIES(case, errors, offsetYield, friction, rolling_torque, sigma_moy, sigma_ini, sigma_out, sigma_max, force_error, slip_error, has_converged) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Insère une nouvelle ligne dans la table Sortie à l'aide du PreparedStatement
    public void insertSortie(int cas,String errors, float offsetYield, float friction, float rolling_tongue, float sigma_moy, float sigma_ini, float sigma_out, float sigma_max, float force_error, float slip_error, String has_converged) {
        try {
            // Fixe la valeur des paramètres de la requête avant exécution.
            // Les indices numériques sont numérotés à partir de 1 et non 0.
            insertion.setInt(1, cas);
            insertion.setString(2, errors);
            insertion.setFloat(3, offsetYield);
            insertion.setFloat(4, friction);
            insertion.setFloat(5, rolling_tongue);
            insertion.setFloat(6, sigma_moy);
            insertion.setFloat(7, sigma_ini);
            insertion.setFloat(8, sigma_out);
            insertion.setFloat(9, sigma_max);
            insertion.setFloat(10, force_error);
            insertion.setFloat(11, slip_error);
            insertion.setString(12, has_converged);
            //L'exécution des requêtes de modification est déclenchée par la méthode executeUpdate
            insertion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
