package database;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Database {

    Connection dbConnection;
    PreparedStatement insertion;

    public Database() {
    }

    public void createTcpServer(String port) throws SQLException {
        Server tcpServer = Server.createWebServer("-tcp","-tcpAllowOthers","-tcpPort",port);
        tcpServer.start();
        System.out.println(tcpServer.getURL());

    }

    public void createServer(String port) throws SQLException {
        Server webServer = Server.createWebServer("-web", "-webAllowOthers","-tcp","-tcpAllowOthers", "-webPort", port,"-tcpPort","8082");
        webServer.start();

    }

    // Etablit la connexion avec la base de données.
    // peut être long
    public void openDBConnection(String ip) {

        JdbcDataSource dataSource = new JdbcDataSource();
        //tcp://"+ip+"
        //dataSource.setURL("jdbc:h2:tcp://"+ip+"/~/test");
        dataSource.setURL("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        try {
            dbConnection=dataSource.getConnection();
            //dbConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ip+"/~/test");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void createDB() {
        String entree = "CREATE TABLE Entrees (\"Case\" int GENERATED BY DEFAULT AS IDENTITY, He float, Hs float, Te float, Diam_WR float, WRyoung int, \"offset ini\" int, mu_ini float, Force float, G float, PRIMARY KEY (\"Case\"))";
        String sortie = "CREATE TABLE Sorties (\"Case\" int NOT NULL, Errors varchar(255), OffsetYield float, Friction float, Rolling_Torque float, Sigma_Moy float, Sigma_Ini float, Sigma_Out float, Sigma_Max float, Force_Error float, Slip_Error float, Has_Converged varchar(255) NOT NULL, PRIMARY KEY (\"Case\"))";
        try {
            PreparedStatement insEntree = dbConnection.prepareStatement(entree);
            PreparedStatement insSortie = dbConnection.prepareStatement(sortie);
            insEntree.execute();
            insSortie.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    //Initialise le PreparedStatement utilisé pour insérer des données.
    public void initInsertPreparedStatementSortie() {
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

    //Récupérer les données dans la table entrée
    public Collection<Object> recupereEntree(int cas) {

        ArrayList<Object> parametres = new ArrayList<>();

        // Utilisation d'une clause try-ressource permettant de gérer les exceptions d'ouverture
        // et de fermeture (automatique) d'une ressource (interface Closeable)
        try (Statement st = dbConnection.createStatement()) {

            // Les requêtes de consultation sont éxécutées avec la méthode executeQuery.
            // Cette méthode retourne une objet ResultSet contenant le résultat.
            // Si cette requête est récurrente, il est possible d'utiliser un PreparedStatement.
            ResultSet rs = st.executeQuery("select * from ENTREES where CASE = cas ");

            //Itérateur. Retourne True quand il se positionne sur le tuple résultat suivant.
            while (rs.next())
            {
                // De manière alternative, les méthodes get d'un ResultSet peuvent utiliser le nom de la colonne
                // à la place de l'indice de la colonne sélectionnée dans la requête.
                // En SQL, les indices démarrent à 1 et non 0.
                parametres.add(rs.getInt("Case"));
                parametres.add(rs.getFloat("He"));
                parametres.add(rs.getFloat("Hs"));
                parametres.add(rs.getFloat("Te"));
                parametres.add(rs.getFloat("Diam_WR"));
                parametres.add(rs.getFloat("WRyoung"));
                parametres.add(rs.getFloat("offset ini"));
                parametres.add(rs.getFloat("mu_ini"));
                parametres.add(rs.getFloat("Force"));
                parametres.add(rs.getFloat("G"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return parametres;
    }
}