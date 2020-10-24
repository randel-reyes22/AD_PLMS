package sample.WindowState;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Close {

    //closes this window
    public static void ThisWindow(MouseEvent event){
        Window window = ((Node)(event.getSource())).getScene().getWindow();
        if (window instanceof Stage){
            ((Stage) window).close();
        }
    }
}
