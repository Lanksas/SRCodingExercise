package viewing;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        //Zugriff Controller Klasse
        Controller cr = new Controller();

        //JPanel
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        //northPanel - Content
        northPanel.setLayout(new GridLayout(1,1,5,5));

        northPanel.add(cr.getContent());

        //centerPanel - Admin
        centerPanel.setLayout(new GridLayout(2,3,0,5));
        JLabel datumLabel = new JLabel("Mon., 01.01.20XX", SwingConstants.CENTER); JLabel sportLabel = new JLabel("SportArt: \"Basketball\"", SwingConstants.CENTER); JLabel whoLabel = new JLabel("Who plays", SwingConstants.CENTER);
        TextField datumField = new TextField(); TextField sportArea = new TextField(); TextField whoArea = new TextField();

        centerPanel.add(datumLabel); centerPanel.add(sportLabel); centerPanel.add(whoLabel);
        centerPanel.add(datumField); centerPanel.add(sportArea); centerPanel.add(whoArea);

        //southPanel - Button
        southPanel.setLayout(new GridLayout(2,3, 5,-40));
        JLabel fake1 = new JLabel(); JLabel fake2 = new JLabel(); JLabel fake3 = new JLabel();
        JButton showButton = new JButton("Show Content"); JButton insertButton = new JButton("Insert"); JButton deleteButton = new JButton("Delete");

        southPanel.add(fake1); southPanel.add(fake2); southPanel.add(fake3);
        southPanel.add(showButton); southPanel.add(insertButton); southPanel.add(deleteButton);

        //Action Events Buttons
        showButton.addActionListener(cr::refreshContent);
        insertButton.addActionListener(e -> cr.expandContent(datumField.getText(), sportArea.getText(), whoArea.getText()));
        deleteButton.addActionListener(cr::removeContent);

        //Rahmen allgemein Einstellungen
        this.setLayout(new GridLayout(3, 1));
        this.add(northPanel);
        this.add(centerPanel);
        this.add(southPanel);

        this.setLocation(600, 300);
        this.setTitle("SportRadar Calendar");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(450, 500);
        this.setResizable(false);
        this.setVisible(true);
    }
}
