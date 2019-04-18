package com.datagroup.eslstest.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public  abstract class LoginViewEntity implements Initializable {
    @FXML
    protected TextField username;
    @FXML
    protected TextField password;
}
