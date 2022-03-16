package main;

import org.h2.Driver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/*
public class WriteTsv {


    private Connection connection;
    private String[] colonne;
    private String[] data;
    private String ip;
    private String port;
    private String name;
    private String user;
    private String password;

    public WriteTsv(int ip, int port, String name, String user, String password){

        colonne = new String[]{"Cas","He","Hs","Te","Ts","Diam_WR","WRyoung","offset ini","mu_ini","Force","G"};
        data = new String[]{"","","","","","","","","","",""};

        this.ip = String.valueOf(ip);
        this.port = String.valueOf(port);
        this.name = name;
        this.user = user;
        this.password = password;

        try
        {
            connection = DriverManager.getConnection(" jdbc:h2:tcp://"+ip+port+"/"+name+"user="+user+"password="+password);
        }
        catch(SQLException e)
        {
            while (e!=null)
            {
                System.out.println(e.getErrorCode());
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
                e.printStackTrace();
                e=e.getNextException();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    private ResultSet readData(int cas) throws SQLException {

        Statement statement = connection.createStatement();
        //boolean rs = statement.execute(SELECT ... REQUETE SQL ICI);
        ResultSet resultSet;

        if(rs){
            resultSet = statement.getResultSet();
        } else {
            System.out.println("Il n'y a pas de ResultSet, peut-être une mise à jour : ");
            int nbTuples = statement.getUpdateCount();
            System.out.println("Nombre de tuples mis à jour = "+nbTuples);
        }

        return resultSet;
    }

    public void write() throws SQLException {
        for (int cas=1; cas < 6; cas++) {
            ResultSet resultSet = readData(cas);

            try {
                FileWriter writer = new FileWriter("./ressources/inv_cst.txt");

                for (int i = 0; i < 11; i++) {
                    data[i] = resultSet.getString(colonne[i]);
                    writer.write(data[i] + "\t");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

}*/
