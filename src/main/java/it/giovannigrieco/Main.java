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
        String outputFile = null;
        boolean templateFlag = false;
        boolean valuesFlag = false;
        boolean outputFlag = false;
        for(int i = 0 ; i < args.length ; i++){
            String arg = args[i];
            if( arg.equals("--help") || arg.equals("-h")){
                printHelp();
                System.exit(0);
            }

            if( arg.equals("--template") || arg.equals("-t") ){
                templateFlag = true;
                if( i + 1 < args.length ){
                    if(args[i+1].equals("-v") || args[i+1].equals("--values") || args[i+1].equals("-o") || args[i+1].equals("--output")){
                        System.err.println("Error: missing argument for option " + arg);
                        System.exit(1);
                    }
                    templateFile = args[i+1];
                    i++;
                }else{
                    System.err.println("Error: missing argument for option " + arg);
                    System.exit(1);
                }
                continue;
            }

            if( arg.equals("--values") || arg.equals("-v") ){
                valuesFlag = true;
                if( i + 1 < args.length ){
                    if(args[i+1].equals("-t") || args[i+1].equals("--template") || args[i+1].equals("-o") || args[i+1].equals("--output")){
                        System.err.println("Error: missing argument for option " + arg);
                        System.exit(1);
                    }
                    String[] values = args[i+1].split(",");
                    valuesFiles.addAll(Arrays.asList(values));
                    i++;
                }else{
                    System.err.println("Error: missing argument for option " + arg);
                    System.exit(1);
                }
                continue;
            }

            if( arg.equals("--output") || arg.equals("-o")){
                outputFlag=true;
                if( i + 1 < args.length ) {
                    if (args[i + 1].equals("-t") || args[i + 1].equals("--template") || args[i + 1].equals("-v") || args[i + 1].equals("--values")) {
                        System.err.println("Error: missing argument for option " + arg);
                        System.exit(1);
                    }
                    outputFile = args[i + 1];
                    i++;
                }
                continue;
            }

            System.err.println("Invalid argument passed.");
            System.err.println("If you need help use --help or -h");
            System.exit(1);
        }

        if(!templateFlag){
            System.err.println("Error: missing template file");
            System.err.println("If you need help use --help or -h");
            //printHelp();
            System.exit(1);
        }

        if(!valuesFlag){
            System.err.println("Error: missing values files");
            System.err.println("If you need help use --help or -h");
            //printHelp();
            System.exit(1);
        }

        if (!outputFlag) {
            System.out.println("Warning: missing output file");
            System.out.println("Falling back on default output file: output.txt");
            outputFile = "output.txt";
        }

        System.out.println("Template file: " + templateFile);
        System.out.println("Values files: " + valuesFiles);

        TemplateParser templateParser = new TemplateParser(templateFile);
        Template template = templateParser.parse();
        System.out.println(template);

        TemplateResolver templateResolver = new TemplateResolver(template, valuesFiles);
        String result = templateResolver.generate();
        Files.write(Paths.get(outputFile), result.getBytes());
    }

    private static void printHelp(){
        System.out.println("Usage: java -jar jtr.jar [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h  Show this help");
        System.out.println("  --template <path>, -t <path>  Path to the template file");
        System.out.println("  --values <path1, path2, ...>, -v <path1, path2, ...>  Paths to the values files");
        System.out.println("  --output <path>, -o <path>  Path to the output file");
        System.out.println("Usage example: java -jar jtr.jar -t template_file.txt -v value_file1,value_file2 -o output.txt");
    }
}