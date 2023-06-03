import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Set<String> stopWords;
    private final Map<String, Set<WordSeparation>> indexDocs;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        Processor processor = new Processor();
        stopWords = processor.getStopWords();
        indexDocs = processor.processPdfFiles(pdfsDir);
    }

    @Override
    public List<PageEntry> search(String searchWord) {
        List<String> words = extractValidWords(searchWord);
        List<PageEntry> response = new ArrayList<>();

        for (Map.Entry<String, Set<WordSeparation>> entry : indexDocs.entrySet()) {
            String pdfName = entry.getKey();
            Set<WordSeparation> pages = entry.getValue();

            for (WordSeparation page : pages) {
                int count = countWordsInPage(page, words);
                if (count > 0) {
                    response.add(new PageEntry(pdfName, page.getPage() + 1, count));
                }
            }
        }
        response.sort(Comparator.comparingInt(PageEntry::getCount).reversed());
        return response;
    }

    private List<String> extractValidWords(String searchWord) {
        return Arrays.stream(searchWord.toLowerCase().split("\\P{IsAlphabetic}+"))
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.toList());
    }

    private int countWordsInPage(WordSeparation page, List<String> words) {
        int count = 0;
        Map<String, Integer> wordSeparation = page.getWordSeparation();

        for (String word : words) {
            count += wordSeparation.getOrDefault(word, 0);
        }

        return count;
    }
}
