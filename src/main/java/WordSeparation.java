import java.util.Map;

public class WordSeparation {
    private final int page;
    private final Map<String, Integer> wordSeparation;

    public WordSeparation(int page, Map<String, Integer> wordSeparation) {
        this.page = page;
        this.wordSeparation = wordSeparation;
    }

    public int getPage() {
        return page;
    }

    public Map<String, Integer> getWordSeparation() {
        return wordSeparation;
    }
}
