package ui.logger

import java.awt.Color
import groovy.swing.SwingBuilder

import javax.swing.JTextArea
import javax.swing.WindowConstants
import java.awt.Font
import java.beans.PropertyChangeListener

class LoggerView {
    static SwingBuilder sb = new SwingBuilder()

    public static JTextArea textArea

    static def initialize(){
        sb.frame(title: "FMX - Logger", defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE, size: [800, 600], show: true){
            scrollPane{
                textArea = textArea(background: Color.BLACK, enabled: false){

                }
            }
        }
        textArea.setDisabledTextColor(Color.WHITE)
        textArea.setCaretColor(Color.WHITE)
        textArea.setLineWrap(true)
    }
}

