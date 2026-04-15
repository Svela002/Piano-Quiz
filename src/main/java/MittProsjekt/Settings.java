package MittProsjekt;

public class Settings {
    
    private static boolean MajorChords=true;
    private static boolean MinorChords=false;
    private static boolean HalfToneChords=false;

    //Setters
    public static void setMajorChords() {
        if (MajorChords==false) {MajorChords=true;}
        else MajorChords=false;
    }

    public static void setMinorChords() {
        if (MinorChords==false) {MinorChords=true;}
        else MinorChords=false;
    }

    public static void setHalfToneChords() {
        if (HalfToneChords==false) {HalfToneChords=true;}
        else HalfToneChords=false;
    }

    //Getters
    public static boolean isMajorChords() {
        return MajorChords;
    }

    public static boolean isMinorChords() {
        return MinorChords;
    }

    public static boolean isHalfToneChords() {
        return HalfToneChords;
    }
}
