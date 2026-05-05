/**
 * class represents the research resources within the spike aware system.
 * extends the Resource class with specific attributes.
 * deviated from UML Class diagram due to SQLite data base handling attributes
 *
 * @author SID 2503921
 */

public class ResearchResource extends Resource {

    private int publicationYear;
    private String authors;
    private String keyWords;


    public void setPublicationYear (int publicationYear) { this.publicationYear = publicationYear; }
    public void setAuthors (String authors) { this.authors = authors; }
    public void setKeyWords (String keyWords) { this.keyWords = keyWords; }

    public boolean getByKeywords (String keyword)  {
        return keyWords != null && keyWords.contains(keyword);
   }

   public boolean filterByYear (int year) {
        return publicationYear == year;
   }
}



