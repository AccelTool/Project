package main;

import java.io.*;

public class ApplicationB {

    public ApplicationB() {
    }

    public void execute() throws IOException {

        ApplicationB app = new ApplicationB();
        String fileOrowan = "Orowan_x64.exe";

        String path = app.getRessource(fileOrowan);
        System.out.println("path : " + path);

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("process "+fileOrowan+" executed.\n");

        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        String path_folder = path.substring(1, path.length()-fileOrowan.length());

        writer.write("i\nc\n");
        writer.write(path_folder +"inv_cst.txt\n");
        writer.write(path_folder + "out.txt\n");
        writer.flush();
        writer.close();

        String reading_str = "nothing";
        while (reading_str != null){
            reading_str = reader.readLine();
            System.out.println(reading_str);
        }


    }

    private String getRessource(String fileName) {
        //adapted from https://mkyong.com/java/java-read-a-file-from-resources-folder/
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName).getPath();

    }
}
