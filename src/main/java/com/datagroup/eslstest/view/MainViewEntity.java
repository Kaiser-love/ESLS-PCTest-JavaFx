package com.datagroup.eslstest.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public abstract class MainViewEntity implements Initializable{
	@FXML
	protected ComboBox<String> routersComboBox;
	@FXML
	protected TextArea resultTextArea;
	@FXML
	protected TextField barCodeText;
	@FXML
	protected TextField channelIdText;
	@FXML
	protected TextField outNetIp;
	@FXML
	protected TextField hardVersionText;
}
