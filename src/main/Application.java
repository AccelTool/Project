package main;

import java.io.*;
import java.util.ArrayList;
import database.Database;

public class Application {
    private Database db;
    private String path_folder;

    public Application() {
    }

    public void execute() throws IOException {

        Application app = new Application();
        String fileOrowan = "Orowan_x64.exe";

        String path = app.getRessource(fileOrowan);
        System.out.println("path : " + path);

        String path_folder = path.substring(1, path.length()-fileOrowan.length());
        this.path_folder = path_folder;

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

    public void startSimulation(Database db, String inv_file,String out_file) throws IOException {
        //Lis les lignes de SQL une par une, pour chaque ligne on update inv_file avec le contenu de la ligne et on lance Orowan
        ArrayList<Object> entree = new ArrayList<>();
        entree.add(0,1);
        if (entree.isEmpty()== false) { // replace by while later
            //entree = db.recupereEntree(1);
            entree.add(0,1); //1	1   30.223	17.934	0	5.567	690.742	210000	200	0.2	1482.582651	6.7
            entree.add(1,1);
            entree.add(2,30.223);
            entree.add(3,17.934);
            entree.add(4,0);
            entree.add(5,5.567);
            entree.add(6,690.742);
            entree.add(7,210000);
            entree.add(8,200);
            entree.add(9,0.2);
            entree.add(10,1482.582651);
            entree.add(11,6.7);

            //entree contains ID    Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G
            //we want to write into inv_file the header "Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G"
            //then the content of entree
            System.out.println("-----"+path_folder);
            //delete the file if it exists
            File file = new File(path_folder+inv_file);
            if (file.exists()) {
                file.delete();
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path_folder+inv_file, true), "utf-8"));
            writer.write("Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G\n");
            writer.write(entree.get(1).toString()+"\t"+entree.get(2).toString()+"\t"+entree.get(3).toString()+"\t"+entree.get(4).toString()+"\t"+entree.get(5).toString()+"\t"+entree.get(6).toString()+"\t"+entree.get(7).toString()+"\t"+entree.get(8).toString()+"\t"+entree.get(9).toString()+"\t"+entree.get(10).toString()+"\t"+entree.get(11).toString()+"\n");
            writer.close();
        }
    }

}
