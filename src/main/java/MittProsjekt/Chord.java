package MittProsjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chord {

    // private List<Note> Keys;
    private List<Note> Scale;
    private List<Note> chordList;
    private String ChordName;

    public Chord() { //Lager en tom akkord som kan fylles inn
        ArrayList<Note> chordList= new ArrayList<>();
        this.chordList=chordList;
    }

    public Chord(Note note, boolean isMajor) {
        ArrayList<Note> chordList= new ArrayList<>(List.of(note));

        if (isMajor==true) {
            note.makeMajorScale(note);
            Scale=note.getMajorScale();
            ChordName=note.getNote();
        }
    
        else {
            note.MakeMinorScale(note);
            Scale=note.getMinorScale();
            ChordName=note.getNote()+"m";

        }        

        chordList.add(Scale.get((Scale.indexOf(note)+2)%7)); //Lager alle akkorder utifra deres skalaer 
        chordList.add (Scale.get((Scale.indexOf(note)+4)%7)); //(Tror det er slik det funker...)

        this.chordList=chordList;
        // if (Settings.isMajorChords()) { //Lager alle 3-noters dur-akkorder --Annen måte å lage akkorder fra en kromatisk skala
        //     chordList.add(Scale.get((Scale.indexOf(note)+4)%7));
        //     chordList.add (Scale.get((Scale.indexOf(note)+7)%7));
        // }
        // 
        // else { //Lager alle 3-noters moll-akkorder
        //     chordList.add(Keys.get(Keys.indexOf(note)+3));
        //     chordList.add(Keys.get(Keys.indexOf(note)+7));
        // }
    }

    public void addNoteToChord(Note inputNote) {
        chordList.add(inputNote);
    }

    public static boolean usesFlats(Chord AskedChord) { //Sjekker om en akkord bruker flats eller sharps.
        for (Note note : AskedChord.getChordList()) {
            if (note.getNote().contains("b")) {
                return true;
            }
        }
        return false;
    }

    public boolean ChordEqual(Chord targetChord) {//Sjekker om to akkorder er like, bruker comparator for å
        List<Note> comparingChord= new ArrayList<>(this.getChordList());
        List<Note> sortedTargetChord = new ArrayList<>(targetChord.getChordList());
        comparingChord.sort(new NoteComparator()); //sikre at variasjoner av en akkord fortsatt er samme akkord.
        sortedTargetChord.sort(new NoteComparator());
        for (int i=0; i<targetChord.getChordLength();i++) {
            if(comparingChord.size()!=targetChord.getChordLength()||!comparingChord.get(i).getNote().equals(sortedTargetChord.get(i).getNote())) { //Hvis én note er annerledes, feil, samt må de være like store
                return false;
            }
        }
        return true;
    }

    public static boolean NoteInChord(Note inputNote, Chord targetChord) { //Sjekker om en inputet note er i en akkord
        for (Note notesInChord : targetChord.getChordList()) {
            if (notesInChord.getNote()==inputNote.getNote()) {
                return true;
            }
        }
        return false;
    }

    public static Chord makeRandomChord() { //Lager en tilfeldig akkord, se getRandomNote() for videre kontekst.
        if (Settings.isMajorChords()&&!Settings.isMinorChords()) {
                return new Chord(Note.getRandomMajorNote(), true);
            }
        else if (!Settings.isMajorChords()&&Settings.isMinorChords()) {
            return new Chord(Note.getRandomMinorNote(), false);
        }
        else {
            int isMajorRandom = new Random().nextInt(0,2); //50/50 mellom dur og moll akkord, for når begge er tillat
            if (isMajorRandom==1) {
                return new Chord(Note.getRandomMajorNote(), true );
            }
            else {
                return new Chord(Note.getRandomMinorNote(), false);
            }
            
        }
    }

    public List<Note> sortChord() { //Sorterer akkorden (se NoteComparator)
        getChordList().sort(new NoteComparator());
        return chordList;
    }

    public List<Note> getChordList() {
        return chordList;
    }

    public String getChordName() {
        return ChordName;
    }

    public int getChordLength() {
        return chordList.size();
    }

    @Override
    public String toString(){
        return "Note: " + chordList.getFirst() + " Chord: " + chordList + " ChordName: " + getChordName();
    }

    // public static void main(String[] args) {
        // Chord C_shuffled = new Chord();
        // C_shuffled.addNoteToChord(new Note("E"));
        // C_shuffled.addNoteToChord(new Note("G"));
        // C_shuffled.addNoteToChord(new Note("C"));
        // System.out.println(C_shuffled);
        // System.out.println(C_shuffled.sortChord());
        // Note Csnote= new Note("C#");
        // Chord Cschord= new Chord(Csnote, true);
        // System.out.println(Cschord);
    // }

}
