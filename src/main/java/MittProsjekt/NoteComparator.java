package MittProsjekt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NoteComparator implements Comparator<Note> {
    
    private final static List<String> AllowedNotes = new ArrayList<>(List.of
        ("C", "C#", "Db","D", "D#", "Eb", "E", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B"));

    public NoteComparator() {}

    @Override
    public int compare(Note note1, Note note2) {
        int index1=AllowedNotes.indexOf(note1.getNote());
        int index2=AllowedNotes.indexOf(note2.getNote());

        
        return Integer.compare(index1, index2);
    }
}
