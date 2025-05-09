module es.uah.matcomp.mp.ed.proyectofinal.pecl_ivanana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana to javafx.fxml;
    exports es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;
}