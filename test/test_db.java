import database.Database;

import java.io.IOException;
import java.sql.SQLException;

public class test_db {

    public static void main(String[] args) throws IOException, SQLException {
        Database db = new Database();
        db.createServer();
    }

}
