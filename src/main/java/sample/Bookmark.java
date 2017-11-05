package sample;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Bookmark {

    private final String directory;
    private final String name;
    private final String url;

    /*
    @Override
    public String toString() {
        return name;
    }
    */
}
