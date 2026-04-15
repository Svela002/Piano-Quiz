package MittProsjekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HighscoreList {
    private List<Integer> scoreList;
    private List<String> scoreOwnerList;

    private static HighscoreList instance; //Singleton, ett spesifikt instans av objektet.


    private HighscoreList() {
        scoreList=new ArrayList<>(List.of(0,0,0,0,0,0,0));
        scoreOwnerList=new ArrayList<>(List.of("---", "---", "---", "---", "---", "---", "---"));
    }


    public boolean checkIfScoreBigEnough(int submittingScore) {
        if (submittingScore>scoreList.getLast()) {return true;}
        else {return false;}
    }

    public void submitScore(int submittingScore, String submittingName) {
        if (checkIfScoreBigEnough(submittingScore)) {
            scoreList.removeLast();
            scoreList.add(submittingScore);
            Collections.sort(scoreList, Collections.reverseOrder()); //Sorterer i riktig rekkefølge (descending)
            scoreOwnerList.removeLast();
            scoreOwnerList.add(scoreList.indexOf(submittingScore), submittingName); //Plasserer navn der den skal være i lista mht den sorterte scorelisten
        }
    }

    public static HighscoreList getInstance() {
        if (instance == null) {
            instance = new HighscoreList();
        }
        return instance;
    }
    
    public List<Integer> getScoreList() {
        return scoreList;
    }

    public List<String> getScoreOwnerList() {
        return scoreOwnerList;
    }

    public List<String> getScoreListStr() {
        List<String> scoreListStr=new ArrayList<>();
        for (Integer number : scoreList) {
            scoreListStr.add(String.valueOf(number));
        }
        return scoreListStr;
    }

    public void writeToFile() throws IOException { //skriver til to filer
        Path scoreOwnerPath =Path.of("data", "Leaderboard.txt");
        Path scoreListPath=Path.of("data", "LeaderboardInt.txt");
        Files.write(scoreOwnerPath, scoreOwnerList, StandardCharsets.UTF_8);
        Files.write(scoreListPath, getScoreListStr(), StandardCharsets.UTF_8);
    }

    public void readFromFile() throws IOException {
        Path scoreOwnerPath =Path.of("data", "Leaderboard.txt");
        Path scoreListPath=Path.of("data", "LeaderboardInt.txt");
        List<String> names=Files.readAllLines(scoreOwnerPath, StandardCharsets.UTF_8);
        List<String> scores=Files.readAllLines(scoreListPath, StandardCharsets.UTF_8);
        scoreList.clear();
        scoreOwnerList.clear();
        scoreOwnerList.addAll(names);

        for (String number : scores) {
            scoreList.add(Integer.valueOf(number));
        }
    }

    public void writeToFile(Path owners, Path scores) throws IOException { //For Junit testing only
        Files.write(owners, scoreOwnerList, StandardCharsets.UTF_8);
        Files.write(scores, getScoreListStr(), StandardCharsets.UTF_8);
    }

    public void readFromFile(Path owners, Path scores) throws IOException {
        List<String> ownersList = Files.readAllLines(owners, StandardCharsets.UTF_8);
        List<String> scoresList = Files.readAllLines(scores, StandardCharsets.UTF_8);

        scoreOwnerList.clear();
        scoreList.clear();

        scoreOwnerList.addAll(ownersList);

        for (String s : scoresList) {
            scoreList.add(Integer.parseInt(s.trim()));
        }
    }

}
