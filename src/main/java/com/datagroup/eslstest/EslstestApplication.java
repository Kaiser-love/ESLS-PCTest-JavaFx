package com.datagroup.eslstest;

import com.datagroup.eslstest.controller.LoginViewController;
import com.datagroup.eslstest.netty.executor.TaskThreadPoolConfig;
import com.datagroup.eslstest.view.LoginView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.SplashScreen;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.swing.*;
import java.io.InputStream;
import java.net.URL;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class}) // 开启配置属性支持
public class EslstestApplication extends AbstractJavaFxApplicationSupport {
    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        primaryStage.setX(150);
        primaryStage.setY(150);
        primaryStage.setTitle("登录");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        LoginViewController.setApp(this);
    }
    public static void main(String[] args) {
        launch(EslstestApplication.class);
    }
}
