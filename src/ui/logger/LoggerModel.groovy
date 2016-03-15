package ui.logger

import groovy.beans.Bindable
import javafx.beans.value.ObservableStringValue

import java.awt.Color

class LoggerModel {

    private static LoggerModel instance

    public static LoggerModel getInstace(){
        if(instance == null) {instance = new LoggerModel()}
        return instance
    }

    private LoggerModel() {}

    @Bindable
    ObservableList logs = []

    @Bindable
    ObservableStringValue = ""

    private static final String format = "[#[%TIMESTAMP%]%LVL%|%MODULE%|%MESSAGE%]"
    private static final String time_stamp = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    private static String getLogString(String lvl, String module, String message){
        String logString = format.replace("%TIMESTAMP%", getTimeStamp())
        logString = logString.replace("%LVL%", lvl)
        logString = logString.replace("%MODULE%", module)
        logString = logString.replace("%MESSAGE%", message)
        return logString
    }

    private static String getTimeStamp(){
        Date date = new Date()
        return date.format(time_stamp)
    }

    public static debug(String module, String message){
        LoggerView.textArea.append("${getLogString("DEBUG", module, message )}\r\n")
    }

    public static info(String module, String message){
        LoggerView.textArea.append("${getLogString("INFO", module, message )}\r\n")
    }

    public static error(String module, String message){
        LoggerView.textArea.append("${getLogString("ERROR", module, message )}\r\n")
    }

}
