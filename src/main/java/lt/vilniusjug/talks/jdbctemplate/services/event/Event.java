package lt.vilniusjug.talks.jdbctemplate.services.event;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vaidas Pilkauskas
 */
public class Event implements Serializable {
  private int id;
  private String name;
  private String venue;
  private Date eventDate;

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVenue(String venue) {
    this.venue = venue;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getVenue() {
    return venue;
  }

  public Date getEventDate() {
    return eventDate;
  }

  @Override
  public String toString() {
    return "Event{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", venue='" + venue + '\'' +
        ", eventDate=" + eventDate +
        '}';
  }
}
