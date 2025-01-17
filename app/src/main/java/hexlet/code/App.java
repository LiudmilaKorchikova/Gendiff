package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        usageHelpAutoWidth = true)
public final class App implements Runnable {

    @Parameters(index = "0", paramLabel = "FILEPATH1", description = "Path to the first file", arity = "1..1")
    private String filepath1;

    @Parameters(index = "1", paramLabel = "FILEPATH2", description = "Path to the second file", arity = "1..1")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "Output format", paramLabel = "FORMAT", defaultValue = "stylish")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            String diff = Differ.generate(filepath1, filepath2, format);
            System.out.println(diff);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
