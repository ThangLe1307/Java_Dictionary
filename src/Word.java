import java.util.List;

public class Word<T> {
    String text;
    List<T> meaning;

    public Word(String text, List<T> meaning) {
        this.text = text;
        this.meaning = meaning;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<T> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<T> meaning) {
        this.meaning = meaning;
    }
}