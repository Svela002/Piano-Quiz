package MittProsjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Note{
    private final static List<String> AllowedNotes = new ArrayList<>(List.of
        ("C", "C#", "Db","D", "D#", "Eb", "E", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B"));
    private final static List<String> BaseNotes = new ArrayList<>(List.of
        ("C", "D", "E", "F", "G", "A", "B"));

        //Programmet vil bruke disse listene for å hente en tilfeldignote etter programmet har bestemt om akkorden skal være dur eller moll
    private final static List<String> PromptedMajorNotes = new ArrayList<>(List.of //Liste av alle noter med programmet kan spørre om durakkorder
        ("A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "F#", "G", "Ab"));
    private final static List<String> PromptedMinorNotes = new ArrayList<>(List.of //Liste av alle noter programmet kan spørre om mollakkorder
        ("A", "Bb", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")); 

        //Programemt vil bruke disse listene for å finne ut om akkorden bruker # eller b som symbol.
        //Basert på skaler fra https://pulse.berklee.edu/scales/index.html
    private final static List<String> sharpNotationMajorList=new ArrayList<>(List.of //Liste av durskalaer som bruker #
        ("C", "G", "D", "A", "E", "B", "F#")); 
    private final static List<String> flatNotationMajorList=new ArrayList<>(List.of //Liste av durskalaer som bruker b
        ("F", "Bb", "Eb", "Ab", "Db", "Gb")); 
    private final static List <String> sharpNotationMinorList=new ArrayList<>(List.of //Liste av mollskalaer som bruker #
        ("A", "E", "B", "F#", "C#", "G#", "D#"));
    private final static List <String> flatNotationMinorList=new ArrayList<>(List.of //Liste av mollskalaer som bruker b
        ("D", "G", "C", "F", "Bb", "Eb"));
    

        private String noteValue;
        private List<Note> MajorScaleValue;
        private List<Note> MinorScaleValue;
        private int noteIndex; //Index for plasseringen av en note i en oktav
        private int notePianoIndex; //Index for plasseringen av en note på pianoet (2 oktaver), brukes for å sjekke at to noter i en akkord ikke er en oktav unna hverandre


    public Note(String noteValue) {
        if (!AllowedNotes.contains(noteValue)) {
            throw new IllegalArgumentException("Note must be valid!");
        }
        this.noteValue=noteValue;
    }

    public Note(String noteValue, int notePianoIndex) {
        if (!AllowedNotes.contains(noteValue)) {
            throw new IllegalArgumentException("Note must be valid!");
        }
        this.noteValue=noteValue;
        this.notePianoIndex=notePianoIndex;
    }


    public static List<Note> decideNotation(Note note, boolean MajorOrMinor) {
        List<Note> Keys=new ArrayList<>();
        if ((MajorOrMinor==true&&sharpNotationMajorList.contains(note.getNote()))||
            (MajorOrMinor==false&&sharpNotationMinorList.contains(note.getNote()))) {
            for (String notes : AllowedNotes) { //Hvis noten ikke er 'flat', legg til i brukte toner
                if (!notes.contains("b")) {
                    Keys.add(new Note(notes));
                }
            }
            return Keys;
        }
        else {
            for (String notes : AllowedNotes) {
                if (!notes.contains("#")) { //Hvis noten ikke er 'sharp', legg til i brukte toner
                    Keys.add(new Note(notes));
                }
            }
            return Keys;
        }
    }

    public void makeMajorScale(Note note) { //Lager durskalaer
        List<Note> majorScale=new ArrayList<Note>();
        List<Note> Keys=decideNotation(note, true);
        for (Note notelist : Keys) {
            if (notelist.getNote()==note.getNote()) { //Itererer over ethvert element, hvis verdien på noten er lik, sett den verdien til indexen.
                noteIndex=Keys.indexOf(notelist);
                break; 
            }
        }


        int index=noteIndex;

        majorScale.add(note);
        
        for (int i=0; i<2; i++) { //Lager alle durskalaer, whole-whole-half-whole-whole-whole, her med løkker
            majorScale.add(Keys.get((index+2)%12));
            index=(index+2)%12;
        }
        majorScale.add(Keys.get((index+1)%12));
        index=(index+1)%12;
        for (int i=0; i<3; i++) {
            majorScale.add(Keys.get((index+2)%12));
            index=(index+2)%12;
        }
        MajorScaleValue=majorScale;


    }
    public void MakeMinorScale(Note note) { //Lager mollskalaer
        List<Note> minorScale=new ArrayList<Note>();
        List<Note> Keys=decideNotation(note, false);

        for (Note notelist : Keys) {
            if (notelist.getNote()==note.getNote()) { //Itererer over ethvert element, hvis verdien på noten er lik, sett den verdien til indexen.
                noteIndex=Keys.indexOf(notelist);
                break; 
            }
        }
        int index=noteIndex;
        
        minorScale.add(note);
        minorScale.add(Keys.get((index+2)%12)); //Lager alle moll-skalaer, whole-half-whole-whole-half-whole, uten løkker
        index=(index+2)%12;
        minorScale.add(Keys.get((index+1)%12));
        index=(index+1)%12;
        minorScale.add(Keys.get((index+2)%12));
        index=(index+2)%12;
        minorScale.add(Keys.get((index+2)%12));
        index=(index+2)%12;
        minorScale.add(Keys.get((index+1)%12));
        index=(index+1)%12;
        minorScale.add(Keys.get((index+2)%12));
        index=(index+2)%12;

        MinorScaleValue=minorScale;
    }
    
    public boolean isWithinOctave(Chord buildingChord, Note comparingNote) { //Sjekker om noten er innen en oktav av alle andre noter i akkorden
        for (Note chordNotes : buildingChord.getChordList()) {
            if ((chordNotes.getNotePianoIndex()-comparingNote.notePianoIndex>12)|| //Hvis comparing note er en oktav under
            (comparingNote.notePianoIndex-chordNotes.notePianoIndex>12)) { //Hvis comparing note er en oktav over
                return false;
            }
        }
        return true;
    }

    public static Note getRandomMajorNote() {
        return new Note(PromptedMajorNotes.get(new Random().nextInt(12)));
    }

    public static Note getRandomMinorNote() {
        return new Note(PromptedMinorNotes.get(new Random().nextInt(12)));
    }

    public static Note getRandomNote() { //Gir en tilfeldig note mht. notasjon, static.
        if (!Settings.isHalfToneChords()) {
            return new Note(BaseNotes.get(new Random().nextInt(7)));
        }

        if (Settings.isMajorChords()&&!Settings.isMinorChords()) {
                return getRandomMajorNote();
            }
        else if (!Settings.isMajorChords()&&Settings.isMinorChords()) {
            return getRandomMinorNote();
        }
        else {
            int isMajorRandom = new Random().nextInt(0,2); //50/50 mellom dur og moll akkord, for når begge er tillat
            if (isMajorRandom==1) {
                return getRandomMajorNote();
            }
            else {
                return getRandomMinorNote();
            }
            
        }
    }

    public String getNote() {
        return noteValue;
    }

    public int getNotePianoIndex() {
        return notePianoIndex;
    }

    public List<Note> getMajorScale() {
        return MajorScaleValue;
    }

    public List<Note> getMinorScale() {
        return MinorScaleValue;
    }

    public static List<String> getBaseNoteList() {
        return BaseNotes;
    }

    public List<String> getSharpnotationmajorlist() {
        return sharpNotationMajorList;
    }

    public List<String> getFlatnotationmajorlist() {
        return flatNotationMajorList;
    }

    public List<String> getSharpnotationminorlist() {
        return sharpNotationMinorList;
    }

    public List<String> getFlatnotatoinminorlist() {
        return flatNotationMinorList;
    }


    @Override
    public String toString(){ //Printer note + scale til noten

        // return("Note: " + getNote() + " Majorscale: " + MajorScaleValue.stream().map(Note::getNote).toList() +
        //      "\nNote: " + getNote() + " Minorscale: " + MinorScaleValue.stream().map(Note::getNote).toList());
        return noteValue;
        
    }

    public static void main(String[] args) {
        Note Cnote = new Note("C");
        Note Bnote = new Note ("B");
        Note Dnote = new Note ("D");
        Note Ebnote = new Note ("Eb");
        Note Dsnote = new Note ("D#");
        Cnote.makeMajorScale(Cnote);
        Cnote.MakeMinorScale(Dsnote);
        System.out.println(Cnote);
        Bnote.makeMajorScale(Bnote);
        Bnote.MakeMinorScale(Dsnote);
        System.out.println(Bnote);
        Dnote.makeMajorScale(Dnote);
        Dnote.MakeMinorScale(Dsnote);
        System.out.println(Dnote);
        Ebnote.makeMajorScale(Ebnote);
        Ebnote.MakeMinorScale(Dsnote);
        System.out.println(Ebnote);
        Dsnote.makeMajorScale(Dsnote);
        Dsnote.MakeMinorScale(Dsnote);
        System.out.println(Dsnote);
    }
}