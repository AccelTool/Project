package main;

import database.Database;
import gui.CanvasLineChart;
import gui.Fenetre_principale;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Application {
    private Database db;
    String fileOrowan = "Orowan_x64.exe";

    public Application(Database db) {
        this.db = db;
    }


    private String getRessource(String fileName) {
        //adapted from https://mkyong.com/java/java-read-a-file-from-resources-folder/
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName).getPath();

    }

    private void launchOrowan(String path_inv, String path_out, String path) throws IOException {

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

        writer.write("i\nc\n");
        writer.write( path_inv.substring(1, path_inv.length())+"\n");
        writer.write(path_out.substring(1, path_out.length())+"\n");
        writer.flush();
        writer.close();

        String reading_str = "nothing";
        while (reading_str != null){
            reading_str = reader.readLine();
            System.out.println(reading_str);
        }
    }

    public boolean startSimulation(Database db, String inv_file,String out_file, ArrayList<CanvasLineChart> charts, int id) throws IOException {

        Application app = new Application(db);

        String path = app.getRessource(fileOrowan);
        String path_inv = app.getRessource(inv_file);
        String path_out = app.getRessource(out_file);

        System.out.println("path : " + path);
        System.out.println(id);

        String path_folder = path.substring(1, path.length()-fileOrowan.length());

        //Lis les lignes de SQL une par une, pour chaque ligne on update inv_file avec le contenu de la ligne et on lance Orowan
        ArrayList<Object> entree = new ArrayList<>();
        //entree.add(0,1);
        //entree = db.recupereEntree(1);
        //int i = 2;
        entree = db.recupereEntree(id);

        if (entree.isEmpty()== false) { // replace by while later
            //System.out.println(entree);
            //i++;

            //entree contains ID    Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G
            //we want to write into inv_file the header "Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G"
            //then the content of entree
            //System.out.println("-----"+path_folder);
            path_folder = path_folder;//+"resources/";
            //System.out.println("-----"+path_folder);
            //delete the file if it exists
            File file = new File(path_inv);
            if (file.exists()) {
                file.delete();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path_inv, true), "utf-8"));
            writer.write("Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G\n");
            writer.write(entree.get(1).toString()+"\t"+entree.get(2).toString()+"\t"+entree.get(3).toString()+"\t"+entree.get(4).toString()+"\t"+entree.get(5).toString()+"\t"+entree.get(6).toString()+"\t"+entree.get(7).toString()+"\t"+entree.get(8).toString()+"\t"+entree.get(9).toString()+"\t"+entree.get(10).toString()+"\t"+entree.get(11).toString()+"\n");
            writer.close();



            //launch Orowan
            launchOrowan(path_inv,path_out,path);


            //get the output data from out_file
            // the output file has a header "case	Errors		OffsetYield	Friction	Rolling_Torque	Sigma_Moy	Sigma_Ini	Sigma_Out	Sigma_Max	Force_Error(%)	Slip_Error(%)	Has_Converged"
            //we want to read the data of the second line from the file out_file and put it into an arraylist
            ArrayList<Object> sortie = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(path_folder+out_file));
            String line = ((BufferedReader) reader).readLine();
            //get only the second line
            line = ((BufferedReader) reader).readLine();
            //split the line into an array
            String[] split = line.split("\t");
            //put the data into an arraylist
            sortie.add(0,split[0]);
            sortie.add(1,split[1]);
            sortie.add(2,split[2]);
            sortie.add(3,split[3]);
            sortie.add(4,split[4]);
            sortie.add(5,split[5]);
            sortie.add(6,split[6]);
            sortie.add(7,split[7]);
            sortie.add(8,split[8]);
            sortie.add(9,split[9]);
            sortie.add(10,split[10]);
            sortie.add(11,split[11]);

            //System.out.println(sortie);

            Double friction = Double.parseDouble(sortie.get(3).toString());
            Double rolling_torque = Double.parseDouble(sortie.get(4).toString());
            System.out.println("friction = " + friction);
            System.out.println("rolling torque =" + rolling_torque);
            // ajouter friciton et rolling torque a la courbe
            charts.get(0).update(friction, 200);
            charts.get(1).update(rolling_torque, 0.002);

            return false;
        }
        return true;
    }


    public void initializeDB(Database db){
        Application app = new Application(db);
        String path_inv = app.getRessource("inv_cst.txt");

        File file = new File(path_inv);
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

            db.openDBConnection();
            db.initInsertPreparedStatementEntrees();
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
