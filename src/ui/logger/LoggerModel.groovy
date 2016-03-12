package ui.logger

import groovy.beans.Bindable
import javafx.beans.value.ObservableStringValue

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

    public static log(String module, String message){
        //LoggerModel.getInstace().logs.add([timestamp: new Date().toString(), module: module, message: message])
        LoggerView.textArea.append("[#[${new Date().getDateTimeString()}|${module}|${message}]]\r\n")
    }

}
