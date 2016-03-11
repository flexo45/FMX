package ui.logger

import groovy.swing.SwingBuilder
import javax.swing.WindowConstants
import java.beans.PropertyChangeListener

class LoggerView {
    static SwingBuilder sb = new SwingBuilder()

    static def initialize(){
        sb.frame(title: "FMX - Logger", defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE, size: [800, 600], show: true){
            scrollPane{
                table{
                    tableModel(id:'p_log', list: LoggerModel.instace.logs){
                        p ->
                            propertyColumn(header: 'Timestamp', propertyName: 'timestamp')
                            propertyColumn(header: 'Module', propertyName: 'module')
                            propertyColumn(header: 'Message', propertyName: 'message')
                    }
                }
                LoggerModel.instace.logs.addPropertyChangeListener({
                    e ->
                        p_log.fireTableDataChanged()
                } as PropertyChangeListener)
            }
        }
    }
}

