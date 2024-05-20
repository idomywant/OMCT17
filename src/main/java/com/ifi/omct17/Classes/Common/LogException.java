package com.ifi.omct17.Classes.Common;

public class LogException extends Exception {
    public LogException(String message)
    {
        super(message);
        FJLogger.Error(message);
    }
}
