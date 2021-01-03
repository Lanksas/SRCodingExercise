package controller;

import model.DataBase;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Controller {
    private DataBase dataBase = new DataBase();

    public List getContent() {
        return dataBase.showContent();
    }

    public void refreshContent(ActionEvent e){
        getContent();
    }

    public void expandContent(String eventDateTime, String sportName, String description){
        if(!eventDateTime.equals("") && !sportName.equals("") && !description.equals("")) {
            dataBase.insertContent(eventDateTime, sportName, description);
            getContent();
        }
    }

    public void removeContent(ActionEvent e){
        int index = dataBase.getListIndex();
        if(index != -1) {
            dataBase.deleteContent(index);
            getContent();
        }
    }
}
