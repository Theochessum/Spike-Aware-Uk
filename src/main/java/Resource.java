/**
 * Abstract class representing resources in the spike aware system.
 * provides common attributes and methods inherited by ResearchResources
 * and AwarenessResource subclasses
 *
 * @author SID 2503921
 */

public abstract class Resource  {


    private String title;
    private String url;
    private String status;


    public String getTitle () { return title; }
    public void setTitle (String title) { this.title = title; }

    public String getUrl () { return url; }
    public void setUrl (String url) { this.url = url; }

    public String getStatus() { return status; }
    public void setStatus (String status) { this.status = status; }

    private String category;
    public String getCategory() {return category;}
    public void setCategory(String category) {
        this.category = category;
    }

    private String content;
    public String getContent(){return content;}
    public void setContent(String content) {this.content = content;}
}

