/**
 * Tracks how many times each resource has been viewed in the spike aware system.
 * Records and increments view counts per resource.
 *
 * @author SID 2503921
 */
public class Analytics {

    final int ID;
    final int resourceId;
    private int viewCount;
    final String resourceType;

    public Analytics (int id, int resourceId, String resourceType){
        this.ID = id;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.viewCount = 0;
    }
    public void incrementView(){
    this.viewCount++;
    }
    public void getStatistics(){
        System.out.println("Views: " + viewCount);
    }
    public int getViewCount() { return viewCount; }
    public String getResourceType() {return resourceType;}
    }

