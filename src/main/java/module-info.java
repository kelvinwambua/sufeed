module com.karanja.sufeed {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    requires javafx.web;

    requires javafx.baseEmpty;
    requires javafx.graphics;
    opens com.karanja.sufeed to javafx.fxml;
    exports com.karanja.sufeed;
    opens com.karanja.sufeed.models to javafx.base;

    exports com.karanja.sufeed.models;

    exports com.karanja.sufeed.utils;
    exports com.karanja.sufeed.controllers to javafx.fxml;

    opens com.karanja.sufeed.controllers to javafx.fxml;





}
