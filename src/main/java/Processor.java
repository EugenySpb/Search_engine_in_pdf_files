import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.*;
import java.util.*;

public class Processor {
    public static final String STOP_FILE = "stop-ru.txt";

    public Set<String> getStopWords() throws IOException {
        Set<String> words = new TreeSet<>();
        File file = new File(STOP_FILE);

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = in.readLine()) != null) {
                words.add(word);
            }
        }
        return words;
    }

    public Map<String, Set<WordSeparation>> processPdfFiles(File pdfDir) throws IOException {
        Map<String, Set<WordSeparation>> indexData = new HashMap<>();
        for (File file : Objects.requireNonNull(pdfDir.listFiles())) {
            String fileName = file.getName();
            Set<WordSeparation> pagesFromDoc = new HashSet<>();
            indexData.put(fileName, pagesFromDoc);

            try (PdfDocument document = new PdfDocument(new PdfReader(file))) {
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    PdfPage page = document.getPage(i + 1);
                    String text = PdfTextExtractor.getTextFromPage(page);
                    String[] words = text.toLowerCase().split("\\P{IsAlphabetic}+");

                    Map<String, Integer> wordDistribution = new HashMap<>();
                    for (String word : words) {
                        wordDistribution.put(word, wordDistribution.getOrDefault(word, 0) + 1);
                    }

                    pagesFromDoc.add(new WordSeparation(i, wordDistribution));
                }
            }
        }
        return indexData;
    }
}
