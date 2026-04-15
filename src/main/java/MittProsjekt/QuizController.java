package MittProsjekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuizController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private Label chordLabel;
    @FXML private Label chordListLabel;
    @FXML private Button nextChordButton;
    @FXML private Text LivesCounter;
    @FXML private Text GameOverMessage;
    @FXML private Label ScoreCounter;
    @FXML private Label FinalScoreCounter;
    @FXML private Text CorrectText;
    @FXML private Text FailText;
    @FXML private Pane submitScorePane;
    @FXML private TextField submitScoreTextField;

    private Chord askedChord;
    private Chord inputChord;
    private Lives livescounter;
    private int score=0;
    private HighscoreList leaderboard;

    @FXML private Circle C1Circle; //Det er nok en smartere måte å gjøre dette på
    @FXML private Text C1Text;
    @FXML private Circle CsDb1Circle;
    @FXML private Text CsDb1Text;
    @FXML private Circle D1Circle;
    @FXML private Text D1Text;
    @FXML private Circle DsEb1Circle;
    @FXML private Text DsEb1Text;
    @FXML private Circle E1Circle;
    @FXML private Text E1Text;
    @FXML private Circle F1Circle;
    @FXML private Text F1Text;
    @FXML private Circle FsGb1Circle;
    @FXML private Text FsGb1Text;
    @FXML private Circle G1Circle;
    @FXML private Text G1Text;
    @FXML private Circle GsAb1Circle;
    @FXML private Text GsAb1Text;
    @FXML private Circle A1Circle;
    @FXML private Text A1Text;
    @FXML private Circle AsBb1Circle;
    @FXML private Text AsBb1Text;
    @FXML private Circle B1Circle;
    @FXML private Text B1Text;
    @FXML private Circle C2Circle;
    @FXML private Text C2Text;
    @FXML private Circle CsDb2Circle;
    @FXML private Text CsDb2Text;
    @FXML private Circle D2Circle;
    @FXML private Text D2Text;
    @FXML private Circle DsEb2Circle;
    @FXML private Text DsEb2Text;
    @FXML private Circle E2Circle;
    @FXML private Text E2Text;
    @FXML private Circle F2Circle;
    @FXML private Text F2Text;
    @FXML private Circle FsGb2Circle;
    @FXML private Text FsGb2Text;
    @FXML private Circle G2Circle;
    @FXML private Text G2Text;
    @FXML private Circle GsAb2Circle;
    @FXML private Text GsAb2Text;
    @FXML private Circle A2Circle;
    @FXML private Text A2Text;
    @FXML private Circle AsBb2Circle;
    @FXML private Text AsBb2Text;
    @FXML private Circle B2Circle;
    @FXML private Text B2Text;
    @FXML private Polygon C1Polygon; //Alle hvite noter
    @FXML private Polygon D1Polygon;
    @FXML private Polygon E1Polygon;
    @FXML private Polygon F1Polygon;
    @FXML private Polygon G1Polygon;
    @FXML private Polygon A1Polygon;
    @FXML private Polygon B1Polygon;
    @FXML private Polygon C2Polygon;
    @FXML private Polygon D2Polygon;
    @FXML private Polygon E2Polygon;
    @FXML private Polygon F2Polygon;
    @FXML private Polygon G2Polygon;
    @FXML private Polygon A2Polygon;
    @FXML private Polygon B2Polygon;
    @FXML private Rectangle CsDb1Rectangle; //Alle sorte noter
    @FXML private Rectangle DsEb1Rectangle;
    @FXML private Rectangle FsGb1Rectangle;
    @FXML private Rectangle GsAb1Rectangle;
    @FXML private Rectangle AsBb1Rectangle;
    @FXML private Rectangle CsDb2Rectangle;
    @FXML private Rectangle DsEb2Rectangle;
    @FXML private Rectangle FsGb2Rectangle;
    @FXML private Rectangle GsAb2Rectangle;
    @FXML private Rectangle AsBb2Rectangle;
    

    private List<Circle> noteCircles;
    private List<Text> noteTexts;
    private Map<Node, Integer> noteMap=new HashMap<>();

    @FXML

    
    public void switchToMenu(ActionEvent event) throws IOException { //Bytter til hovedmenyen fra quizen
        root = FXMLLoader.load(getClass().getResource("Hovedmeny.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        askedChord=Chord.makeRandomChord();
        inputChord=new Chord();
        chordLabel.setText(askedChord.getChordName()); //Setter label til verdien av Rot-noten
        chordListLabel.setText(askedChord.getChordList().toString()); 
        nextChordButton.setDisable(true);
        livescounter= new Lives(3);
        LivesCounter.setText(livescounter.getLivesStr()); 
        ScoreCounter.setText(String.valueOf(score));
        leaderboard=HighscoreList.getInstance(); //Samme instans som i menuController

        noteCircles= new ArrayList<>(List.of(C1Circle, CsDb1Circle, D1Circle, DsEb1Circle, E1Circle, F1Circle, FsGb1Circle,
        G1Circle, GsAb1Circle, A1Circle, AsBb1Circle, B1Circle, C2Circle, CsDb2Circle, D2Circle, DsEb2Circle, E2Circle, F2Circle, FsGb2Circle,
        G2Circle, GsAb2Circle, A2Circle, AsBb2Circle, B2Circle));
        noteTexts= new ArrayList<>(List.of(C1Text, CsDb1Text, D1Text, DsEb1Text, E1Text, F1Text, FsGb1Text, G1Text,
        GsAb1Text, A1Text, AsBb1Text, B1Text, C2Text, CsDb2Text, D2Text, DsEb2Text, E2Text, F2Text, FsGb2Text, G2Text, GsAb2Text,
        A2Text, AsBb2Text, B2Text));
        noteMap.put(C1Polygon, 0);
        noteMap.put(CsDb1Rectangle, 1);
        noteMap.put(D1Polygon, 2);
        noteMap.put(DsEb1Rectangle, 3);
        noteMap.put(E1Polygon, 4);
        noteMap.put(F1Polygon, 5);
        noteMap.put(FsGb1Rectangle, 6);
        noteMap.put(G1Polygon, 7);
        noteMap.put(GsAb1Rectangle, 8);
        noteMap.put(A1Polygon, 9);
        noteMap.put(AsBb1Rectangle, 10);
        noteMap.put(B1Polygon, 11);
        noteMap.put(C2Polygon, 12);
        noteMap.put(CsDb2Rectangle, 13);
        noteMap.put(D2Polygon, 14);
        noteMap.put(DsEb2Rectangle, 15);
        noteMap.put(E2Polygon, 16);
        noteMap.put(F2Polygon, 17);
        noteMap.put(FsGb2Rectangle, 18);
        noteMap.put(G2Polygon, 19);
        noteMap.put(GsAb2Rectangle, 20);
        noteMap.put(A2Polygon, 21);
        noteMap.put(AsBb2Rectangle, 22);
        noteMap.put(B2Polygon, 23);
    }

    public void nextChord() {
        askedChord=Chord.makeRandomChord();
        inputChord=new Chord();
        chordLabel.setText(askedChord.getChordName()); //Setter label til verdien av Rot-noten
        chordListLabel.setText(askedChord.getChordList().toString()); 
        CorrectText.setVisible(false);
        chordListLabel.setVisible(false);
        nextChordButton.setDisable(true);

        for (Circle noteCircle : noteCircles) {
            noteCircle.setVisible(false);
        }
        for (Text notetext : noteTexts) {
            notetext.setVisible(false);
        }
    }

    public void checkIfChordEquals() {
        if (inputChord.ChordEqual(askedChord)) {
            nextChordButton.setDisable(false);
            score++;
            ScoreCounter.setText(String.valueOf(score));
            CorrectText.setVisible(true);
            chordListLabel.setVisible(true);
        }
    }

    public boolean checkIfNoteAlreadyPressed(Note inputNote) {
        if (findCorrectCircle(inputNote).isVisible()) {
            return true;
        }
        return false;
    }

    public void GameOver() {
        FailText.setVisible(true);
        chordListLabel.setVisible(true);
    }

    public void submitScore(ActionEvent event) throws IOException {
        String submiterName=submitScoreTextField.getText();
        int submitValue=score;
        leaderboard.submitScore(submitValue, submiterName);
        // System.out.println(leaderboard.getScoreList());
        // System.out.println(leaderboard.getScoreOwnerList());

        root = FXMLLoader.load(getClass().getResource("Hovedmeny.fxml")); //Bytter til hovedskjerm
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toggleSubmitPaneVisibility(ActionEvent event) {
        if (submitScorePane.isVisible()) {submitScorePane.setVisible(false);}
        else {submitScorePane.setVisible(true);}
    }

    public Circle findCorrectCircle(Note inputNote) {
        if (inputNote.getNotePianoIndex()>11&&(!Note.getBaseNoteList().contains(inputNote.getNote()))) { //tilfellet hvor noten er sort og er 2. oktav
            for (Circle circle : noteCircles) {
                if (circle.getId().contains(inputNote.getNote().replace("#", "s"))&&
                    (circle.getId().contains("2"))) {
                    return circle;
                }
            }
        }
        else if (inputNote.getNotePianoIndex()<=11&&(!Note.getBaseNoteList().contains(inputNote.getNote()))){ //Tilfellet hvor noten er sort og er 1. oktav
            for (Circle circle : noteCircles) {
                if (circle.getId().contains(inputNote.getNote().replace("#", "s"))&&
                    (circle.getId().contains("1"))) {
                    return circle;
                }
            }
        }
        else if (inputNote.getNotePianoIndex()>11) { //Tilfellet hvor noten er hvit og er 2. oktav
            for (Circle circle : noteCircles) {
                if (circle.getId().contains(inputNote.getNote()+"2")) {
                    return circle;
                }
            }
        }
        else {
            for (Circle circle : noteCircles) { //Tilfellet hvor noten er hvit og er 1. oktav
                if (circle.getId().contains(inputNote.getNote()+"1")) {
                    return circle;
                }
            }
        }
        return C1Circle; //Burde aldri skje
    }

    public Text findCorrectText(Note inputNote) {
        if (inputNote.getNotePianoIndex()>11&&(!Note.getBaseNoteList().contains(inputNote.getNote()))) { //tilfellet hvor noten er sort og er 2. oktav
            for (Text text : noteTexts) {
                if (text.getId().contains(inputNote.getNote().replace("#", "s"))&&
                    (text.getId().contains("2"))) {
                    return text;
                }
            }
        }
        else if (inputNote.getNotePianoIndex()<=11&&(!Note.getBaseNoteList().contains(inputNote.getNote()))){ //Tilfellet hvor noten er sort og er 1. oktav
            for (Text text : noteTexts) {
                if (text.getId().contains(inputNote.getNote().replace("#", "s"))&&
                    (text.getId().contains("1"))) {
                    return text;
                }
            }
        }
        else if (inputNote.getNotePianoIndex()>11) { //Tilfellet hvor noten er hvit og er 2. oktav
            for (Text text : noteTexts) {
                if (text.getId().contains(inputNote.getNote()+"2")) {
                    return text;
                }
            }
        }
        else {
            for (Text text : noteTexts) { //Tilfellet hvor noten er hvit og er 1. oktav
                if (text.getId().contains(inputNote.getNote()+"1")) {
                    return text;
                }
            }
        }
        return C1Text; //Burde aldri skje
    }

    public void markWrongNote(Note inputNote) {
        findCorrectCircle(inputNote).setFill(Color.RED);
        findCorrectCircle(inputNote).setVisible(true);
        findCorrectText(inputNote).setText(inputNote.getNote());
        findCorrectText(inputNote).setVisible(true);
        livescounter.decreaseLives();
        LivesCounter.setText(livescounter.getLivesStr());
        if (livescounter.getLives()<=0) {
            GameOver();
        }

    }

    public void markCorrectNote(Note inputNote) {
        findCorrectCircle(inputNote).setFill(Color.GREEN);
        findCorrectCircle(inputNote).setVisible(true);
        findCorrectText(inputNote).setText(inputNote.getNote());
        findCorrectText(inputNote).setVisible(true);
    }

    public void checkIfInputNoteIsCorrect(Note inputNote) {
        // System.out.println(inputChord.getChordList().toString());
        if (!Chord.NoteInChord(inputNote, askedChord)) {//Sjekker om noten ikke skal være i akkorden
            markWrongNote(inputNote);
        }
        else if ((!inputChord.getChordList().isEmpty())&&!inputNote.isWithinOctave(inputChord,inputNote)) { //sjekker om noten er innen en oktav av alle noter i akkorden
            markWrongNote(inputNote);
            }

        else if (Chord.NoteInChord(inputNote, inputChord)) { //sjekker om noten allerede er i akkorden
            markWrongNote(inputNote);
        }
        
        else { //Riktige noter
            inputChord.addNoteToChord(inputNote);
            //Vis grønn sirkel med tonens navn på pianoet.
            markCorrectNote(inputNote);
            checkIfChordEquals();
        }
    }
    
    public void NoteInput(MouseEvent event) {
        Node source= (Node) event.getSource();

        Integer notePitch=noteMap.get(source);
        String noteName=getNoteName(notePitch);
        Note inputNote = new Note(noteName, notePitch);

        if(!checkIfNoteAlreadyPressed(inputNote)&&!livescounter.ifNoLives()&&!inputChord.ChordEqual(askedChord)) {
            checkIfInputNoteIsCorrect(inputNote);
        }
    }

    private String getNoteName(int notePitch) {
    List<String> sharps=new ArrayList<>(List.of("C","C#","D","D#","E","F","F#","G","G#","A","A#","B"));
    List<String> flats = new ArrayList<>(List.of("C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"));

    int index = notePitch%12;

    if (Chord.usesFlats(askedChord)) {
        return flats.get(index);
        } 
    else {
        return sharps.get(index);
        }
    }
}