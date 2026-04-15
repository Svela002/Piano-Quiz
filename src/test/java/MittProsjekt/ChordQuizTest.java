package MittProsjekt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ChordQuizTest {
    
    @Test
    void testDecideNotationMajorSharpVsFlat() { //Tester om en 
        // C-dur skal bruke #, ikke b
        Note c = new Note("C");
        List<Note> sharpNotes = Note.decideNotation(c, true);

        assertTrue(
            sharpNotes.stream().noneMatch(n -> n.getNote().contains("b")),
            "C major should not contain flat notes"
        );

        // F-dur skal bruke b, ikke #
        Note f = new Note("F");
        List<Note> flatNotes = Note.decideNotation(f, true);

        assertTrue(
            flatNotes.stream().noneMatch(n -> n.getNote().contains("#")),
            "F major should not contain sharp notes"
        );
    }

    @Test
    void testMajorChordConstruction() { //Tester at alle akkorder blir bygd på riktig måte, samt at rekkefølgen på notene ikke er viktige
        // Bygger en C-dur
        Note root = new Note("C");
        Chord cMajor = new Chord(root, true);

        // Lag en "fasit"-akkord: C - E - G
        Chord expected = new Chord();
        expected.addNoteToChord(new Note("C"));
        expected.addNoteToChord(new Note("E"));
        expected.addNoteToChord(new Note("G"));

        // Test at akkordene er like uansett rekkefølge
        assertTrue(
            cMajor.ChordEqual(expected),
            "C major chord should contain C, E, and G"
        );
    }


    @Test
    void testSubmitScoreInsertsCorrectly() { //Tester at submit metoden funker som den skal
        HighscoreList list = HighscoreList.getInstance();

        // Reset test data
        list.getScoreList().clear();
        list.getScoreOwnerList().clear();

        list.getScoreList().addAll(List.of(100, 80, 60, 40, 20, 10, 0));
        list.getScoreOwnerList().addAll(List.of(
                "A", "B", "C", "D", "E", "F", "G"
        ));

        // Submit a new score that should end up in the middle
        list.submitScore(50, "NEW");

        // Expected score list after insertion
        List<Integer> expectedScores = List.of(100, 80, 60, 50, 40, 20, 10);
        List<String> expectedOwners = List.of("A", "B", "C", "NEW", "D", "E", "F");

        assertEquals(expectedScores, list.getScoreList(), "Scores should be sorted and updated");
        assertEquals(expectedOwners, list.getScoreOwnerList(), "Names should match score positions");
        assertEquals(7, list.getScoreList().size(), "Leaderboard must always contain 7 scores");
    }


    @TempDir
    Path tempDir;  // JUnit lager en midlertidig mappe automatisk

    @Test
    void testWriteAndReadLeaderboard() throws IOException { //Sjekker at leaderboardinnhold er identisk før og etter exportering og importering.
        // Arrange
        HighscoreList list = HighscoreList.getInstance();

        // Sett opp testdata
        list.getScoreOwnerList().clear();
        list.getScoreList().clear();

        list.getScoreOwnerList().addAll(List.of("Ola", "Kari", "Per", "---", "---", "---", "---"));
        list.getScoreList().addAll(List.of(12, 9, 2, 0, 0, 0, 0));

        // Midlertidige filer
        Path ownersFile = tempDir.resolve("Leaderboard.txt");
        Path scoresFile = tempDir.resolve("LeaderboardInt.txt");

        // Skriv til fil
        list.writeToFile(ownersFile, scoresFile);

        // Lag en ny instans for å teste lesing
        HighscoreList loaded = HighscoreList.getInstance();
        loaded.getScoreOwnerList().clear();
        loaded.getScoreList().clear();

        // Les fra fil
        loaded.readFromFile(ownersFile, scoresFile);

        // Assert – sjekk at dataene er like
        assertEquals(
                List.of("Ola", "Kari", "Per", "---", "---", "---", "---"),
                loaded.getScoreOwnerList(),
                "Owner list should match after reading from file"
        );

        assertEquals(
                List.of(12, 9, 2, 0, 0, 0, 0),
                loaded.getScoreList(),
                "Score list should match after reading from file"
        );
    }



}
