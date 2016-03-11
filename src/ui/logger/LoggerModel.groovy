package ui.logger

import groovy.beans.Bindable

class LoggerModel {

    private static LoggerModel instance

    public static LoggerModel getInstace(){
        if(instance == null) {instance = new LoggerModel()}
        return instance
    }

    private LoggerModel() {}

    @Bindable
    ObservableList logs = []

    public static log(String module, String message){
        LoggerModel.getInstace().logs.add([timestamp: new Date().toString(), module: module, message: message])
    }

}
