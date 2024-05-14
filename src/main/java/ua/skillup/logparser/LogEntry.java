package ua.skillup.logparser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LogEntry {
    private final LocalDateTime dateTime;
    private final LogLevel level;
    private final String clientName;
    private final String clientIp;
    private final String message;

    public LogEntry(LocalDateTime dateTime, LogLevel level, String clientName, String clientIp, String message) {
        this.dateTime = dateTime;
        this.level = level;
        this.clientName = clientName;
        this.clientIp = clientIp;
        this.message = message;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public LogLevel getLevel() {
        return level;
    }
    public String getClientName() {
        return clientName;
    }
    public String getClientIp() {
        return clientIp;
    }
    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return String.format("[%s] [%s] [%s %s] %s", dateTime.format(formatter), level, clientName, clientIp, message);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return Objects.equals(dateTime, logEntry.dateTime)
                && level == logEntry.level
                && Objects.equals(clientName, logEntry.clientName)
                && Objects.equals(clientIp, logEntry.clientIp)
                && Objects.equals(message, logEntry.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, level, clientName, clientIp, message);
    }
}
