package it.giovannigrieco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> valuesFiles = new ArrayList<>();
        String templateFile = null;
        boolean templateFlag = false;
        boolean valuesFlag = false;
        for(int i = 0 ; i < args.length ; i++){
            String arg = args[i];
            if( arg.equals("--help") || arg.equals("-h") ){
                System.out.println("Usage: java -jar <jarfile> [options]");
                System.out.println("Options:");
                System.out.println("  --help, -h  Show this help");
                System.out.println("  --template <path>, -t <path>  Path to the template file");
                System.out.println("  --values <path1, path2, ...>, -v <path1, path2, ...>  Paths to the values files");
                System.exit(0);
            }

            if( arg.equals("--template") || arg.equals("-t") ){
                templateFlag = true;
                if( i + 1 < args.length ){
                    if(args[i+1].equals("-v") || args[i+1].equals("--values")){
                        System.out.println("Error: missing argument for option " + arg);
                        System.exit(1);
                    }
                    templateFile = args[i+1];
                    i++;
                }else{
                    System.out.println("Error: missing argument for option " + arg);
                    System.exit(1);
                }
            }

            if( arg.equals("--values") || arg.equals("-v") ){
                valuesFlag = true;
                if( i + 1 < args.length ){
                    if(args[i+1].equals("-t") || args[i+1].equals("--template")){
                        System.out.println("Error: missing argument for option " + arg);
                        System.exit(1);
                    }
                    String[] values = args[i+1].split(",");
                    valuesFiles.addAll(Arrays.asList(values));
                    i++;
                }else{
                    System.out.println("Error: missing argument for option " + arg);
                    System.exit(1);
                }
            }

        }

        if( !templateFlag ){
            System.out.println("Error: missing template file");
            System.exit(1);
        }

        if( !valuesFlag ){
            System.out.println("Error: missing values files");
            System.exit(1);
        }

        println("Template file: " + templateFile);
        println("Values files: " + valuesFiles);

        TemplateParser templateParser = new TemplateParser(templateFile);
        Template template = templateParser.parse();
        System.out.println(template);

        TemplateResolver templateResolver = new TemplateResolver(template, valuesFiles);
        String result = templateResolver.generate();
        Files.write(Paths.get("output.txt"), result.getBytes());
    }

    public static void println(String message){
        System.out.println(message);
    }
}