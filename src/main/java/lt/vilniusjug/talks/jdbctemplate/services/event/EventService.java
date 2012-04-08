package lt.vilniusjug.talks.jdbctemplate.services.event;

import lt.vilniusjug.talks.jdbctemplate.services.ServiceException;

import java.util.List;

/**
 * @author Vaidas Pilkauskas
 */
public interface EventService {
  List<Event> findAll();
  List<Event> findAllByName(String name);

  void add(Event event);
  void addAll(List<Event> events);

  void delete(Event event);
  void deleteAll(List<Event> events);

  //procedure example
  int countEventsByName(String name);

  void raiseException(String message) throws ServiceException;
}
