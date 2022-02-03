package training.lepeskin.system_data.collectors.impl;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.system_data.exceptions.CollectingException;
import training.lepeskin.utility.logger.FilesLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProcessCollector implements ParameterCollector<Set<String>> {
    private static final FilesLogger filesLogger =
            new FilesLogger(ProcessCollector.class.getName(), "process-collector-log.txt");

    private HashSet<String> processSet;

    public ProcessCollector() {
    }

    @Override
    public Set<String> collect() throws CollectingException {
        processSet = new HashSet<>();

        Process process;
        String command = System.getProperty("os.name").startsWith("Windows") ?
                System.getenv("windir") + "\\system32\\" + "tasklist.exe" : "ps -e";

        try {
            process = Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            String logString = String.format("Class %s object cannot be created. Command '%s' not executed. ",
                    Process.class.getName(), command);
            filesLogger.log(logString, e);
            throw new CollectingException(e);
        }

        try (BufferedReader input =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            String line;
            while ((line = input.readLine()) != null) {
                processSet.add(line);
            }

        } catch (Exception e) {
            filesLogger.log("System process data cannot be collected. ", e);
            throw new CollectingException(e);
        }

        return processSet;
    }

    @Override
    public Optional<Set<String>> getLastCollectedValue() {
        return Optional.ofNullable(processSet);
    }

    @Override
    public String getParameterName() {
        return "Process list";
    }

    @Override
    public ParameterType getValueType() {
        return ParameterType.VECTOR;
    }
}
