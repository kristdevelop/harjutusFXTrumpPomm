import javafx.application.Application;
//application.Platform exit kasutamine aitas programmi kinni panna, mis FX jääbki jooksma, kui just pole joonistatud kastike, mida saab kinni klikata
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    GridPane laud;
    int lauaPikkusLaevades = 9;
    int lauaLaiuslaevades = 9;
    int laevaPikkusPx = 50;
    Stage mainGameSquare;
    Image avapilt = new Image("piraadilaev.png");
    ImagePattern laevaMuster = new ImagePattern(avapilt);

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistanMänguväljaku();
        loonLaevad();
        reageeriklikile();
    }
    private void reageeriklikile() {
        laud.setOnMouseClicked(event -> {
            //klikkimine laua peal on näha tulemit KLIKK-sõnana   System.out.println("KLIKK");
            Rectangle ruut = (Rectangle) event.getTarget();
            String tyyp = ruut.getId();
            //nüüd hakkame iffitama: tahan teada kas kast on laev v meri, string puhul sa ei saa == kasutada
            if (tyyp.equals("MERI")){
                ruut.setFill(Color.DARKBLUE);
            } else if (tyyp.equals("LAEV")) {
                ruut.setFill(laevaMuster);
                ruut.setId("pihtaspõhjas");
            }
            if (!laevadTabamata()) {
                //gameover kui pole alles, hüüumärk muudab väite vastupidiseks
                gameovermeetod();
            }
                }  );
    }
    //midagi teha, uus aken - ehk alljärgneb "võitsidki" tuleb eraldi kirjutada kui lihtsalt labast helloprinti  ei tee
    private void gameovermeetod() {
        mainGameSquare.close();
        StackPane stack = new StackPane();
        Label go = new Label("Võitsidki!!!");
        stack.getChildren().add(go);
        Scene scene = new Scene(stack, 300, 300);
        Stage goStage = new Stage();
        goStage.setScene(scene);
        goStage.show();
    }

    private boolean laevadTabamata() {
        //loop loomine gridi mänguväljaku sees, et ta vaataks KÕIK kastid üle, et mäng oleks ikkagi läbi!
        for (Node ruut : laud.getChildren()) {
           if (ruut.getId().equals("LAEV")) {
               return true;
           }
        }
        return false;
    }


    //siia lauale tulen 9*9 korda vaatama kas sai piht laevaruudule. Laev on piksleid suur ning laud võrdub 9 korda laevapiksleid.
    private void loonLaevad() {
        for (int i = 0; i < lauaPikkusLaevades; i++) {
            for (int j = 0; j < lauaLaiuslaevades; j++) {
                Rectangle kastike = new Rectangle(laevaPikkusPx, laevaPikkusPx);
                int rand = (int) (Math.random() * 1.3);
                if (rand == 1) {
                    kastike.setId("LAEV");
                } else {
                    kastike.setId("MERI");
                }
                kastike.setFill(Color.DEEPSKYBLUE);
                kastike.setStroke(Color.RED);
                laud.add(kastike, i, j);
            }
        }
    }
    private void seadistanMänguväljaku() {
        laud = new GridPane();
        Scene scene = new Scene(laud, lauaPikkusLaevades * laevaPikkusPx, lauaLaiuslaevades * laevaPikkusPx);
        mainGameSquare = new Stage();
        scene.setFill(Color.DEEPSKYBLUE);
        mainGameSquare.setScene(scene);
        mainGameSquare.show();
    }

}
