package MittProsjekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private CheckBox FlatNotationBox;
    @FXML
    private CheckBox MajorChordsBox;
    @FXML
    private CheckBox MinorChordsBox;
    @FXML
    private CheckBox HalfToneChordsBox;
    @FXML
    private Label ReminderLabel;
    @FXML Label name1Label;
    @FXML Label name2Label;
    @FXML Label name3Label;
    @FXML Label name4Label;
    @FXML Label name5Label;
    @FXML Label name6Label;
    @FXML Label name7Label;
    @FXML Label score1Label;
    @FXML Label score2Label;
    @FXML Label score3Label;
    @FXML Label score4Label;
    @FXML Label score5Label;
    @FXML Label score6Label;
    @FXML Label score7Label;
    
    
    private HighscoreList leaderboard; //Samme instans som i Quizcontroller

    private List<Label> nameLabels;
    private List<Label> scoreLabels;

    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MajorChordsBox.setSelected(Settings.isMajorChords());
        MinorChordsBox.setSelected(Settings.isMinorChords());
        HalfToneChordsBox.setSelected(Settings.isHalfToneChords());
        leaderboard=HighscoreList.getInstance();
        nameLabels=new ArrayList<>(List.of(name1Label, name2Label, name3Label, name4Label, name5Label, name6Label, name7Label));
        scoreLabels=new ArrayList<>(List.of(score1Label, score2Label, score3Label, score4Label, score5Label, score6Label, score7Label));
        updateLeaderboard();
    }


    public void changeMajorChords(ActionEvent event) {
        Settings.setMajorChords();
        // System.out.println(Settings.isMajorChords());
    }

    public void changeMinorChords(ActionEvent event) {
        Settings.setMinorChords();
        // System.out.println(Settings.isMinorChords());
    }

    public void changeHalfNoteChords(ActionEvent event) {
        Settings.setHalfToneChords();
        // System.out.println(Settings.isHalfToneChords());
    }

    public void exportLeaderBoard(ActionEvent event) throws IOException {
        leaderboard.writeToFile();
    }

    public void importLeaderBoard(ActionEvent event) throws IOException {
        leaderboard.readFromFile();
        updateLeaderboard();
    }

    public void switchToQuiz(ActionEvent event) throws IOException{ //Bytter til quizen fra hovedmenyen, gitt minst én akkordtype er valgt
        if (!(Settings.isMajorChords()||Settings.isMinorChords())) {
            ReminderLabel.setText("Du må ha med enten dur- eller moll akkorder!");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("Quiz-side.fxml")); //Bytter scene
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void updateLeaderboard() {
        int index=0; //Setter leaderboardet riktig
        for (Label namelabel : nameLabels) {
            namelabel.setText(leaderboard.getScoreOwnerList().get(index));
            index=index+1;
        }
        index=0;
        for (Label scorelabel : scoreLabels) {
            scorelabel.setText(String.valueOf(leaderboard.getScoreList().get(index)));
            index=index+1;
        }
    }

    public HighscoreList getLeaderboard() {
        return leaderboard;
    }
}
