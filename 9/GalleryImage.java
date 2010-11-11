
/**
 * @author Andre Karlsson
 * 
 *
 */
public class GalleryImage {

    private String name = null;
    private String description = null;
    private String filename = null;
    private String path = null;
    private String maker = null;

    public GalleryImage() {
    }

    public GalleryImage(String name, String description, String filename, String path, String maker) {
        this.name = name;
        this.description = description;
        this.filename = filename;
        this.path = path;
        this.maker = maker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
