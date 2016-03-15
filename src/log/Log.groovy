package log

import ui.gametable.GameTableModel
import ui.logger.LoggerModel

class Log {

    public Log(String reporter){
        this.reporter = reporter
    }

    private String reporter

    public void debug(String message){
        LoggerModel.debug(reporter, message)
    }

    public void info(String message){
        LoggerModel.info(reporter, message)
        GameTableModel.instance.addLog(message)
    }

    public void error(String message, Exception e){
        LoggerModel.error(reporter, "$message\r\n\r\n$e\r\n")
    }
}
