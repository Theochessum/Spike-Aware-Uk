/**
 * represents the Awareness resources in the Spike Aware system.
 * extends resource class with additional attributes specific to the
 * awareness resources category
 *
 * @author SID 2503921
 */

public class AwarenessResources extends Resource {

        private String dateAdded;

        public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

        public boolean filterByDate(String date) {
                return dateAdded != null && dateAdded.contains(date);
        }
}

