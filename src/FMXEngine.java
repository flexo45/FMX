import ui.logger.LoggerView;
import ui.mainmenu.MainMenuView;

import java.util.logging.Logger;

public class FMXEngine {
    public static void main(String[] args){
        LoggerView.initialize();
        MainMenuView.initialize();
    }
}
