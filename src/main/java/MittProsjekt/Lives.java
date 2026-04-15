package MittProsjekt;

public class Lives {
    
    private int livesAmount;
    private String livesAmountStr;

    public Lives(int numberOfLives) {
        livesAmount=numberOfLives;
        livesAmountStr=String.valueOf(numberOfLives);
    }

    public int getLives() {
        return livesAmount;
    }

    public String getLivesStr() {
        return livesAmountStr;
    }

    public void decreaseLives() {
        livesAmount=livesAmount-1;
        livesAmountStr=String.valueOf(livesAmount);
    }

    public boolean ifNoLives() {
        if (livesAmount<=0) {return true;}
        else return false;
    }
}
