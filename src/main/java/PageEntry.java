public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry other) {
        return Integer.compare(other.getCount(), this.getCount());
    }

    @Override
    public String toString() {
        return String.format("%s page: %d count: %d%n", pdfName, page, count);
    }
}
