package main;

import java.io.*;
import java.util.ArrayList;

public class Application {

    public Application() {
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
    /*
    public void startSimulation(Database db, String inv_file,String out_file) throws IOException {
        //Lis les lignes de SQL une par une, pour chaque ligne on update inv_file avec le contenu de la ligne et on lance Orowan

    }
    */
}
