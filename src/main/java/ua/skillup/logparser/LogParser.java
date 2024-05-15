package ua.skillup.logparser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser {
    private final LogEntry[] logEntries;

    public LogParser(String logFileContent) {
        this.logEntries = fromLog(logFileContent.split("\n"));
    }

    public LogParser(File logFile) {
        String[] logFileContent = readLogFile(logFile);
        this.logEntries = fromLog(logFileContent);
    }

    public LogEntry[] getLogEntries() {
        return logEntries;
    }

    public LogEntry[] filterByLevel(LogLevel level) {
        LogEntry[] filtered = new LogEntry[logEntries.length];

        int count = 0;
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getLevel() == level) {
                filtered[count++] = logEntry;
            }
        }

        LogEntry[] result = new LogEntry[count];
        System.arraycopy(filtered, 0, result, 0, count);

        return result;
    }

    public void writeLog(File file) {
        writeLog(file, logEntries);
    }

    public static void writeLog(File file, LogEntry[] logEntries) {
       try (FileWriter fileWriter = new FileWriter(file);
       BufferedWriter writer = new BufferedWriter(fileWriter)
       ) {
            for (LogEntry logEntry : logEntries){
                writer.write(logEntry.toString());
                writer.newLine();
            }
       } catch (Exception e){
           throw new RuntimeException(e);
       }
    }

    private String[] readLogFile(File file) {
       try (FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader)
       ) {
           return reader.lines().toArray(String[]::new);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    private LogEntry[] fromLog(String[] lines) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] \\[(.*?) (.*?)\\] (.*)");
        LogEntry[] logEntries = new LogEntry[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), formatter);
                LogLevel level = LogLevel.valueOf(matcher.group(2));
                String clientName = matcher.group(3);
                String clientIp = matcher.group(4);
                String message = matcher.group(5);
                logEntries[i] = new LogEntry(dateTime, level, clientName, clientIp, message);
            }
        }
        return logEntries;
    }
}
