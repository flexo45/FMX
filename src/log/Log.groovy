package log

import ui.logger.LoggerModel

class Log {
    static void print(Object ob, String message){
        LoggerModel.log(ob.getClass().getName(), message)
    }


}
