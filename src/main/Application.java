package main;

import database.Database;

import java.io.*;
import java.util.ArrayList;

public class Application {

    private Database db;
    String path;


    public Application(Database db) {
        this.db = db;
        path = "./resources/inv_cst.txt";
    }

    public Application(){

    }

    public void execute() throws IOException {

        Application app = new Application();
        String fileOrowan = "Orowan_x64.exe";

        String path = app.getRessource(fileOrowan);
        System.out.println("path : " + path);

        launchOrowan("inv.txt", "out2.txt", path,fileOrowan);

    }

    private String getRessource(String fileName) {
        //adapted from https://mkyong.com/java/java-read-a-file-from-resources-folder/
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName).getPath();

    }

    private void launchOrowan(String inv_file, String out_file, String path, String file_name) throws IOException {

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("process "+file_name+" executed.\n");

        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        String path_folder = path.substring(1, path.length()-file_name.length());

        writer.write("i\nc\n");
        writer.write(path_folder +inv_file+"\n");
        writer.write(path_folder + out_file+"\n");
        writer.flush();
        writer.close();

        String reading_str = "nothing";
        while (reading_str != null){
            reading_str = reader.readLine();
            System.out.println(reading_str);
        }
    }

    public void initializeDB(){
        File file = new File("C:\\Users\\fafou\\IdeaProjects\\Project\\src\\resources\\inv_cst.txt");
        //System.out.println(file.length());
        ArrayList<String[]> data = tsvr(file);

        for (int i = 1; i < data.size(); i++){
            int cas = Integer.parseInt(data.get(i)[0]);
            float he = Float.parseFloat(data.get(i)[1]);
            float hs = Float.parseFloat(data.get(i)[2]);
            float te = Float.parseFloat(data.get(i)[3]);
            float ts = Float.parseFloat(data.get(i)[4]);
            float diam_wr = Float.parseFloat(data.get(i)[5]);
            int wryoug = Integer.parseInt(data.get(i)[6]);
            int offset = Integer.parseInt(data.get(i)[7]);
            float mu_ini = Float.parseFloat(data.get(i)[8]);
            float force = Float.parseFloat(data.get(i)[9]);
            float g = Float.parseFloat(data.get(i)[10]);

            db.insertEntree(i, cas, he, hs, te, ts, diam_wr, wryoug, offset, mu_ini, force, g);
        }
    }

    private ArrayList<String[]> tsvr(File file) {
        ArrayList<String[]> Data = new ArrayList<>(); //initializing a new ArrayList out of String[]'s
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]
                Data.add(lineItems); //adding the splitted line array to the ArrayList
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }
}
