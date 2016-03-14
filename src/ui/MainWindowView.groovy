package ui

import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JFrame
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Point

class MainWindowView {

    private static MainWindowView instance

    public static MainWindowView getInstance(){
        if(instance == null) instance = new MainWindowView()
        return instance
    }

    private MainWindowView(){
        frame = new JFrame()
        frame.setResizable(false)
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        setLocation()
    }

    private JFrame frame
    private SwingBuilder sb = new SwingBuilder()

    public void setContent(JComponent content){
        frame.show(false)
        frame.contentPane.clear()
        frame.contentPane.add(content)
        frame.show(true)
    }

    public void setTitle(String title){
        frame.setTitle(title)
    }

    public JFrame getFrame(){ return frame }

    public void setSize(Integer x, Integer y){
        frame.setSize(x, y)
    }

    public void show(){
        frame.show(true)
    }

    public void close(){
        frame.show(false)
        frame.dispose()
    }

    public void exception(String text){
        def popup = sb.optionPane().createDialog(frame, "Critical Error")
        popup.contentPane.clear()
        //popup.setSize(240, 120)
        popup.setMinimumSize(new Dimension(240, 120))
        def view = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)
            scrollPane(){
                textArea(text: text)
            }
            panel(maximumSize: [4000, 80]){
                button(text: "OK", actionPerformed: { popup.dispose() })
            }
        }
        popup.contentPane.add(view)
        popup.pack()
        popup.show(true)
    }

    private void setLocation(){
        Point cp = GraphicsEnvironment.localGraphicsEnvironment.centerPoint
        frame.location = new Point((int)(cp.x - frame.width/2), (int)(cp.y - frame.height/2))
    }
}
