import database.Database;

import java.io.IOException;
import java.sql.SQLException;

public class test_db {

    public static void main(String[] args) throws IOException, SQLException {
        Database db = new Database();
        db.createTcpServer("8082");
        db.openDBConnection();
        db.dropDB();
        db.createDB();


        System.out.println((db.recupereEntree(1)).isEmpty());

    }

}
