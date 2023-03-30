import java.util.List;

public class Word {
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<String> meaning) {
        this.meaning = meaning;
    }

    List<String> meaning;

    public Word(String text, List<String> meaning) {
        this.text = text;
        this.meaning = meaning;
    }




}

