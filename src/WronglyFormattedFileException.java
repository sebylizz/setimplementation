import java.io.IOException;

public class WronglyFormattedFileException extends IOException {
    public WronglyFormattedFileException(String str) {
        super("File is wrongly formatted: " + str);
    }
}
