import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DatabaseManagerTest {

    @Test
    public void testGetPendingResources() {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        ArrayList<Resource> results = db.getPendingResources();

        assertNotNull(results);
        assertFalse(results.isEmpty(), "Expected at least one pending resource for this test to be meaningful");

        for (Resource r : results) {
            System.out.println("Found pending resource: " + r.getTitle());
            assertEquals("pending", r.getStatus());
        }
    }
}