package database;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Database {

    Boolean open = false;
    Boolean running = false;
    Connection dbConnection;
    PreparedStatement insertion;
    Server server;

    public Database() {
    }


    public void createTcpServer(String port) throws SQLException {
        server = Server.createWebServer("-tcp","-tcpAllowOthers","-tcpPort",port);
        server.start();
        System.out.println(server.getURL());
        running = true;
    }

    public void createWebServer(String port) throws SQLException {
        server = Server.createWebServer("-web", "-webAllowOthers","-tcp","-tcpAllowOthers", "-webPort", port,"-tcpPort","8082");
        server.start();
        running = true;
    }

    // Etablit la connexion avec la base de données.
    public void openDBConnection() {

        JdbcDataSource dataSource = new JdbcDataSource();
        //tcp://"+ip+"
        //dataSource.setURL("jdbc:h2:tcp://"+ip+"/~/database");
        dataSource.setURL("jdbc:h2:~/database");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        try {
            dbConnection=dataSource.getConnection();
            open = true;
            //dbConnection = DriverManager.getConnection("jdbc:h2:tcp://"+ip+"/~/database");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void openDBConnection(String url, String user, String password) {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        try {
            dbConnection=dataSource.getConnection();
            open = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Ferme la connexion avec la base de données.
    public void closeDBConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDB() {
        String entree = "CREATE TABLE ENTREES4 (\"Case\" int GENERATED BY DEFAULT AS IDENTITY, He float, Hs float, Te float, Diam_WR float, WRyoung int, \"offset ini\" int, mu_ini float, Force float, G float, PRIMARY KEY (\"Case\"))";
        String sortie = "CREATE TABLE SORTIES4 (\"Case\" int NOT NULL, Errors varchar(255), OffsetYield float, Friction float, Rolling_Torque float, Sigma_Moy float, Sigma_Ini float, Sigma_Out float, Sigma_Max float, Force_Error float, Slip_Error float, Has_Converged varchar(255) NOT NULL, PRIMARY KEY (\"Case\"))";
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
    //Initialise le PreparedStatement utilisé pour insérer des données. Toujours à exécuter avant
    //insertEntree
    public void initInsertPreparedStatementEntrees() {
        // TODO Auto-generated method stub
        try {
            insertion=dbConnection.prepareStatement("INSERT INTO ENTREES(\"Case\", He, Hs, Te, Diam_WR, WRyoung, \"offset ini\", mu_ini, Force, G) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Insère une nouvelle ligne dans la table Sortie à l'aide du PreparedStatement
    public void insertEntree(int cas,float he, float hs, float te, float diam_wr, int wryoung, int offset, float mu_ini, float force, float g){
        try {
            // Fixe la valeur des paramètres de la requête avant exécution.
            // Les indices numériques sont numérotés à partir de 1 et non 0.
            insertion.setInt(1, cas);
            insertion.setFloat(2, he);
            insertion.setFloat(3, hs);
            insertion.setFloat(4, te);
            insertion.setFloat(5, diam_wr);
            insertion.setInt(6, wryoung);
            insertion.setInt(7, offset);
            insertion.setFloat(8, mu_ini);
            insertion.setFloat(9, force);
            insertion.setFloat(10, g);
            //L'exécution des requêtes de modification est déclenchée par la méthode executeUpdate
            insertion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initInsertPreparedStatementSorties() {
        // TODO Auto-generated method stub
        try {
            insertion=dbConnection.prepareStatement("INSERT INTO SORTIES(case, errors, offsetYield, friction, rolling_torque, sigma_moy, sigma_ini, sigma_out, sigma_max, force_error, slip_error, has_converged) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

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

    public Collection<Object> recupereSortie(int cas) {

        ArrayList<Object> parametres = new ArrayList<>();

        // Utilisation d'une clause try-ressource permettant de gérer les exceptions d'ouverture
        // et de fermeture (automatique) d'une ressource (interface Closeable)
        try (Statement st = dbConnection.createStatement()) {

            // Les requêtes de consultation sont éxécutées avec la méthode executeQuery.
            // Cette méthode retourne une objet ResultSet contenant le résultat.
            // Si cette requête est récurrente, il est possible d'utiliser un PreparedStatement.
            ResultSet rs = st.executeQuery("select * from SORTIES where CASE = cas ");

            //Itérateur. Retourne True quand il se positionne sur le tuple résultat suivant.
            while (rs.next())
            {
                // De manière alternative, les méthodes get d'un ResultSet peuvent utiliser le nom de la colonne
                // à la place de l'indice de la colonne sélectionnée dans la requête.
                // En SQL, les indices démarrent à 1 et non 0.
                parametres.add(rs.getInt("Case"));
                parametres.add(rs.getString("Errors"));
                parametres.add(rs.getFloat("OffsetYield"));
                parametres.add(rs.getFloat("Friction"));
                parametres.add(rs.getFloat("Rolling_Torque"));
                parametres.add(rs.getFloat("Sigma_Moy"));
                parametres.add(rs.getFloat("Sigma_Ini"));
                parametres.add(rs.getFloat("Sigma_Out"));
                parametres.add(rs.getFloat("Sigma_Max"));
                parametres.add(rs.getFloat("Force_Error"));
                parametres.add(rs.getFloat("Slip_Error"));
                parametres.add(rs.getString("Has_Converged"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return parametres;
    }

    public void dropDB(){
        try (Statement st = dbConnection.createStatement()) {
            st.executeUpdate("DROP TABLE ENTREES");
            st.executeUpdate("DROP TABLE SORTIES");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeServer(){
        server.shutdown();
    }

    public boolean isOpen(){
        return open;
    }

    public boolean isRunning(){
        return running;
    }
}