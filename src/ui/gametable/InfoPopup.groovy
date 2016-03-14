package ui.gametable

import ui.MainWindowView
import groovy.swing.SwingBuilder

import javax.swing.BoxLayout

/**
 * Created by m.guriev on 11.02.2016.
 */
class InfoPopup {
    static void initialize(String text){
        def sb = new SwingBuilder()
        def popup = sb.optionPane().createDialog(MainWindowView.instance.getFrame(), "Info")
        popup.contentPane.clear()
        popup.setSize(240, 120)
        //popup.setMinimumSize(new Dimension(240, 120))
        def view = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)
            panel{
                label(text: text)
            }
            panel(maximumSize: [4000, 80]){
                button(text: "OK", actionPerformed: { popup.dispose() })
            }
        }
        popup.contentPane.add(view)
        popup.pack()
        popup.show(true)
    }
}
