import com.google.common.collect.Lists;
import lt.vilniusjug.talks.jdbctemplate.services.ServiceException;
import lt.vilniusjug.talks.jdbctemplate.services.event.Event;
import lt.vilniusjug.talks.jdbctemplate.services.event.EventService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class Launcher {
  private static final Log log = LogFactory.getLog(Launcher.class);
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("/app-context.xml");

    EventService eventService = (EventService) context.getBean("lt.vilniusjug.talks.jdbctemplate.services.EventService");

    //query
    List<Event> events = eventService.findAll();
    log.info(events);

    //insert
    Event event = new Event();
    event.setName("test event");
    event.setVenue("some venue");
    event.setEventDate(new Date());
    eventService.add(event);
    log.info(eventService.findAll());

    //insert list of objects
    Event testEvent1 = new Event();
    testEvent1.setName("test event2");
    testEvent1.setVenue("some venue");
    testEvent1.setEventDate(new Date());
    Event testEvent2 = new Event();
    testEvent2.setName("test event3");
    testEvent2.setVenue("some venue");
    testEvent2.setEventDate(new Date());

    eventService.addAll(Lists.newArrayList(testEvent1, testEvent2));

    log.info(eventService.findAll());

    //find events by name
    List<Event> testEvents = eventService.findAllByName("test");

    log.info(testEvents);

    //remove
    eventService.deleteAll(testEvents);

    log.info(eventService.findAll());

    //procedure call
    log.info(eventService.countEventsByName("jug"));
    //procedure with exception
    try {
      eventService.raiseException("test exception message");
    } catch (ServiceException e) {
      log.info("Exception was thrown:" + e.getMessage());
    }

    //todo: git
    //todo: slides
    //todo sun jdk
  }
}