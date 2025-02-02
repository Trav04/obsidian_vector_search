package projects;

public class ObsidianNote {

    private String filename;
    private String contents;

    public ObsidianNote(String fileName, String contents) {
        this.contents = contents;
        this.filename = fileName;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getContents() {
        return contents;
    }
}
