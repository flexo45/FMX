package log

import ui.gametable.GameTableModel
import ui.logger.LoggerModel

class Log {
    static void print(Object ob, String message){
        LoggerModel.log(ob.getClass().getName(), message)

        if(message.contains("INFO:")){
            GameTableModel.instance.addLog(message)
        }
    }


}
