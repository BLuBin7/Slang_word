package Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class SearchedSlangWord extends SlangWord implements Serializable {

    /**
     * The searched time
     */
    private final LocalDateTime time;

    /**
     * Construct a new searched slang word with full information
     *
     * @param word       the slang word
     * @param definition the definition
     * @param time       the searched time
     */
    public SearchedSlangWord(String word, List<String> definition, LocalDateTime time) {
        super(word, definition);
        this.time = time;
    }

    /**
     * Construct a new search slang word with a slang word object (time will be current system time)
     *
     * @param slangWord the slang word used to construct the searched word
     */
    public SearchedSlangWord(SlangWord slangWord) {
        super(slangWord.word, slangWord.definition);
        this.time = LocalDateTime.now();
    }

    /**
     * Convert the word to the form "dd-MM-yyyy HH:mm:ss: slang word (definition)"
     *
     * @return the converted string
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return time.format(formatter) + ": " + super.toString();
    }
}
