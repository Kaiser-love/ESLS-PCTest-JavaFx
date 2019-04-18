package com.datagroup.eslstest.controller;

import com.datagroup.eslstest.entity.Router;
import com.datagroup.eslstest.netty.command.CommandConstant;
import com.datagroup.eslstest.service.RouterService;
import com.datagroup.eslstest.utils.*;
import com.datagroup.eslstest.view.MainViewEntity;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.context.annotation.Lazy;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Lazy
@FXMLController
public class FxmlViewController extends MainViewEntity {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        SpringContextUtil.setResultTextArea(resultTextArea);
    }

    private void initView() {
        RouterService routerService = (RouterService) SpringContextUtil.getBean("RouterService");
        List<Router> routers = routerService.findAll();
        List<String> routerNames = new ArrayList<>();
        for (Router r : routers)
            routerNames.add(r.getBarCode());
        routersComboBox.setItems(FXCollections.observableArrayList(routerNames));
    }

    @FXML
    private void sendAPByChannelId(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        if ("".equals(channelIdText.getText())) {
            displayDialog("请设置信道");
            return;
        }
        if (!(Integer.valueOf(channelIdText.getText())>0  && Integer.valueOf(channelIdText.getText())<9) ) {
            displayDialog("路由器信道范围为0-8");
            return;
        }
        SocketChannelHelper.initRssiMap();
        SendCommandUtil.sendAPByChannelId(getRoutersBySelectItem(), channelIdText.getText());
    }

    @FXML
    private void sendAPStopByChannelId(ActionEvent event) {
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.APBYCHANNELIDSTOP, CommandConstant.COMMANDTYPE_ROUTER);
    }

    @FXML
    private void sendAPReceiveByChannelId(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        if ("".equals(channelIdText.getText())) {
            displayDialog("请设置信道");
            return;
        }
        if (!(Integer.valueOf(channelIdText.getText())>0  && Integer.valueOf(channelIdText.getText())<9) ) {
            displayDialog("路由器信道范围为0-8");
            return;
        }
        SendCommandUtil.sendAPReceiveByChannelId(getRoutersBySelectItem(), channelIdText.getText());
    }

    @FXML
    private void sendAPReceiveStopByChannelId(ActionEvent event) {
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.APRECEIVEBYCHANNELIDSTOP, CommandConstant.COMMANDTYPE_ROUTER);
    }

    @FXML
    private void sendAPWrite(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        if ("".equals(barCodeText.getText())) {
            displayDialog("请设置条码");
            return;
        }
        if (barCodeText.getText().length()!=12) {
            displayDialog("路由器条码信息错误(长度12位)");
            return;
        }
        if ("".equals(channelIdText.getText())) {
            displayDialog("请设置信道");
            return;
        }
        if (!(Integer.valueOf(channelIdText.getText())>0  && Integer.valueOf(channelIdText.getText())<9) ) {
            displayDialog("路由器信道范围为0-8");
            return;
        }
        if ("".equals(hardVersionText.getText())) {
            displayDialog("请设置硬件版本号");
            return;
        }
        if (!RegUtil.isboolHardVersion(hardVersionText.getText())) {
            displayDialog("硬件版本号格式不正确(V1.00)");
            return;
        }
        SendCommandUtil.sendAPWrite(getRoutersBySelectItem(), barCodeText.getText(), channelIdText.getText(), hardVersionText.getText());
        initView();
    }

    @FXML
    private void sendAPRead(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.APREAD, CommandConstant.COMMANDTYPE_ROUTER);
    }

    @FXML
    private void setLocalhostIp(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        if ("".equals(outNetIp.getText())) {
            displayDialog("请设置外网IP");
            return;
        }
        if (!RegUtil.isboolIp(outNetIp.getText())) {
            displayDialog("外网IP格式不正确");
            return;
        }
        SendCommandUtil.setLocalhostIp(getRoutersBySelectItem(), outNetIp.getText());
    }

    @FXML
    private void deleteIpRecord(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.DELETEIPRECORD, CommandConstant.COMMANDTYPE_ROUTER);
    }

    @FXML
    private void getIpRecord(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.GETIPRECORD, CommandConstant.COMMANDTYPE_ROUTER);
    }

    @FXML
    private void removeRouter(ActionEvent event) {
        if (routersComboBox.getSelectionModel().isEmpty()) {
            displayDialog("请选择路由器");
            return;
        }
        SendCommandUtil.sendCommandWithRouters(getRoutersBySelectItem(), CommandConstant.ROUTERREMOVE, CommandConstant.COMMANDTYPE_ROUTER);
    }

    private List<Router> getRoutersBySelectItem() {
        RouterService routerService = (RouterService) SpringContextUtil.getBean("RouterService");
        String routerBarcode = routersComboBox.getSelectionModel().getSelectedItem();
        Router router = routerService.findByBarCode(routerBarcode);
        List<Router> routers = new ArrayList<>();
        routers.add(router);
        return routers;
    }

    private void displayDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("错误");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }
}