import javafx.application.Application;
//application.Platform exit kasutamine aitas kinni panna, FX jääbki jooksma, kui just pole joonistatud kastike, mida saab kinni klikata
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
import java.sql.*;
//import java.util.HashMap;

public class Main extends Application {
    GridPane manguvaljak;                              //defineerib laua olemasolu pluss selle suuruse
    int lauaPikkusLaevades = 6;  //ruutude arv
    int lauaLaiuslaevades = 6;  //ruutude arv
    int laevaPikkusPx = 100;     //ruudu suurus

    Stage mainGameSquare;
    Image avapilt = new Image("piraadilaev.png");
    ImagePattern laevaMuster = new ImagePattern(avapilt);

    Lauahaldus HaldurMina; //lihtalt muutuja väärtuseta, vaata edasi rida 115 seadistamien....

    Connection conn = null; //see tuleb sql-baasi lisamisest, logimise käsu ühendus



    public Main() {

        try {               //andmebaasi SQLite lisamine from Krister Viirsaar
            Class.forName("org.sqlite.JDBC");                          // Lae draiver sqlite.jar failist, TEHTUD!
            conn = DriverManager.getConnection("jdbc:sqlite:test.db"); // loo ühendus andmebaasi failiga TEKKIS!

            String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INT AUTO_INCREMENT, USERNAME TEXT, " + // jätkub järgmisel real
                    "PASSWORD TEXT, FULLNAME TEXT, NUMBER INT, ADDRESS TEXT);";
            // Statement objekt on vajalik, et SQL_Login käsku käivitada
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            stat.close();
        } catch (Exception e) {                                      // püüa kinni võimalikud errorid
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //siin oli viga! see seadistus peab olema ENNE "reageeriklikile, muidu klikk viskab errori no-idea-klikkimisest
    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistanMänguväljaku();
        //loonLaevad();  //see oli algselt enne klassideta, 201701_JK jätsin välja, kuna järgmine rida asendab selle, teine klass Lauahaldus
        //Lauahaldus HaldurMina; on enne defineeritud. Lauahaldus on uus klass.
        HaldurMina.loonLaevad();  // tühjad sulud, et läheb käima, sidusin selle reaga halduriga nr 26 klassiga Lauahaldus


        reageeriklikile();      //defineerib, et on olemas reageerimine klikile
    }

    private void reageeriklikile() {
        manguvaljak.setOnMouseClicked(event -> {

            System.out.println("KLIKK");     //klikkimine laua peal on näha tulemit KLIKK-sõnana
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


    //tegin uue ankna, et alljärgnev "võitsidki" eraldi kirjutada - kui just lihtsalt labast helloprinti ei tee
    private void gameovermeetod() {
        mainGameSquare.close();
        StackPane stack = new StackPane();                  //uus teateaken
        Label go = new Label("Võitsidki!!!");
        stack.getChildren().add(go);
        Scene scene = new Scene(stack, 300, 300);
        Stage goStage = new Stage();
        goStage.setScene(scene);
        goStage.show();
    }

    private boolean laevadTabamata() {
        //loop loomine gridi mänguväljaku sees, et ta vaataks KÕIK kastid üle, et mäng oleks ikkagi läbi!
        for (Node ruut : manguvaljak.getChildren()) {
           if (ruut.getId().equals("LAEV")) {
               return true;
           }
        }
        return false;
    }

   // JÄRGNEV ON VARASEM, asub nüüdsest hoopis uue klassi Lauahaldus all
    // Genereerib kas pihtas - siia lauale tulen 9*9 korda vaatama kas sai piht laevaruudule. Laev on piksleid suur ning manguvaljak võrdub 9 korda laevapiksleid.

    /* private void loonLaevad() {
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
                manguvaljak.add(kastike, i, j);
            }
        }
    }
    */

    private void seadistanMänguväljaku() {          //uue klassiga Lauahaldus ka siia muutus, rida 130 uus

        manguvaljak = new GridPane();
        HaldurMina = new Lauahaldus(manguvaljak, lauaPikkusLaevades, lauaLaiuslaevades, laevaPikkusPx);    //see annabki lauale halduse õigused. Tekkis aga ei tee midagi
        Scene scene = new Scene(manguvaljak, lauaPikkusLaevades * laevaPikkusPx, lauaLaiuslaevades * laevaPikkusPx);
        mainGameSquare = new Stage();
        scene.setFill(Color.DEEPSKYBLUE);
        mainGameSquare.setScene(scene);
        mainGameSquare.show();
    }

}
