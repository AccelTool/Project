
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {

        Application app = new Application();
        String fileOrowan = "Orowan_x64.exe";

        System.out.println("getResourceAsStream : " + fileOrowan);
        String path = app.getRessource(fileOrowan);
        System.out.println(path);
        InputStream is = app.getFileFromResourceAsStream(fileOrowan);
        //printInputStream(is);


        Process process = Runtime.getRuntime().exec(path);
        /*
        try (InputStream in = Application.class.getClassLoader().getResourceAsStream("/file.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            // Use resource
        }
         */

        //Process process = Runtime.getRuntime().exec("..\\resources\\Orowan_x64.exe", null, new File("..\\resource\\"));

        /*
        ProcessBuilder builder = new ProcessBuilder("java", "Test");
        builder.directory(new File("../resource/Orowan_x64.exe"));
        Process process = builder.start();
         */

        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        //System.out.println(reader.readLine());



        writer.write("i\nc\n");
        writer.write("../../inv_cst.txt\n");
        writer.write("C:/Users/amino/IdeaProjects/Project/out/production/Project/out.txt\n");
        //\nout.txt");
        writer.flush();
        writer.close();

        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());


        /*
        Scanner scanner = new Scanner(stdout);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

         */
        //process.destroy();

    }

    private static void printInputStream(InputStream is) {

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            System.out.println(streamReader.toString());
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        classLoader.getResource(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
    private String getRessource(String fileName) {
        //adapted from https://mkyong.com/java/java-read-a-file-from-resources-folder/
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName).getPath();

    }
}
