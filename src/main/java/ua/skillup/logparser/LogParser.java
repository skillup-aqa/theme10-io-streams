package ua.skillup.logparser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class LogParser {
    private final LogEntry[] logEntries;

    private LogParser(LogEntry[] logEntries) {
        this.logEntries = logEntries;
    }

    public static LogParser fromLog(String log) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] \\[(.*?) (.*?)\\] (.*)");
        String[] lines = log.split("\n");
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
        return new LogParser(logEntries);
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
        for (int i = 0; i < count; i++) {
            result[i] = filtered[i];
        }

        return result;
    }
}
