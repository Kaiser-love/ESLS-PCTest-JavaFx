package com.datagroup.eslstest.controller;

import com.datagroup.eslstest.EslstestApplication;
import com.datagroup.eslstest.entity.User;
import com.datagroup.eslstest.service.UserService;
import com.datagroup.eslstest.utils.SpringContextUtil;
import com.datagroup.eslstest.view.LoginViewEntity;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;

import java.net.URL;
import java.util.ResourceBundle;

@Lazy
@FXMLController
public class LoginViewController extends LoginViewEntity {
    private static EslstestApplication application;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    @FXML
    public void login(ActionEvent event) throws Exception {
        UserService userService = (UserService) SpringContextUtil.getBean("UserService");
        if("".equals(username.getText())) {
            displayDialog("请输入用户名");
            return;
        }
        User user = userService.findByName(username.getText());
        if(user==null) {
            displayDialog("用户不存在");
            return;
        }
        if("".equals(password.getText())) {
            displayDialog("请输入密码");
            return;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getName());
        Object obj = new SimpleHash("MD5", password.getText(), credentialsSalt, 3);
        String passWordHash = ((SimpleHash) obj).toHex();
        if(username.getText().equals(user.getName()) && passWordHash.equals(user.getPasswd())) {
            Stage stage=application.primaryStage;
        	Parent root;
            root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
            stage.setTitle("测试界面");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            displayDialog("账号或密码错误");
        }
    }
    public static void setApp(EslstestApplication application){
        LoginViewController.application = application;
    }
    private void displayDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("错误");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }
}
