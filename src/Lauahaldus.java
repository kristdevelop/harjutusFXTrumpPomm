import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
//import java.awt.*; viska välja see, see on vaba algne grafaikapakett ja läheb uutega tylli!!!

/**
 * Created by kullirist on 13/01/2017.
 */
public class Lauahaldus {                  //järgnev on klassika, alamklass saab sisendid põhiklassist, mis peavad alguses nullid olema
    GridPane manguvaljak;
    int lauaPikkusLaevades = 0;            //mis peavad alguses nullid olema
    int lauaLaiuslaevades = 0;
    int laevaPikkusPx = 0;

    //järgnev on klassika, ringi-defineerimine
    // loome uue mänguväljaku ja selle uued näitajad, andes vanadele muutujatele uued muutujad
    //MIKS? Main-klass ei saa sisse ju siia, ei saa otse ligi laevapikkustele..
public Lauahaldus (GridPane uusmanguvaljak, int lauaPikkusLaevades2, int lauaLaiuslaevades2, int laevaPikkusPx2 ) {
    manguvaljak = uusmanguvaljak;
    lauaPikkusLaevades = lauaPikkusLaevades2;
    lauaLaiuslaevades = lauaLaiuslaevades2;
    laevaPikkusPx = laevaPikkusPx2;
}

    //JÄRGNEV ON KOOPIA ESMASEST KOODIST JUBA; genreerib kas on pihtas või põhjas - kas Trump
    // siia lauale tulen 9*9 korda vaatama kas sai piht laevaruudule. Laev on piksleid suur ning manguvaljak võrdub 9 korda laevapiksleid.
    public void loonLaevad() {
    //see oli algselt private, teen public et ligi saaks, ning järgvenalt saan kustutdad MAIN alt laevaloomise loonlaevad...

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

}
