package lt.vilniusjug.talks.jdbctemplate.services.event;

import lt.vilniusjug.talks.jdbctemplate.services.AbstractService;
import lt.vilniusjug.talks.jdbctemplate.services.ServiceException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.List;

/**
 * @author Vaidas Pilkauskas
 */
public class EventServiceImpl extends AbstractService implements EventService {
  private static final RowMapper<Event> EVENT_ROW_MAPPER = ParameterizedBeanPropertyRowMapper.newInstance(Event.class);

  private SimpleJdbcCall countByNameCall;
  private SimpleJdbcCall exceptionCall;

  @Override
  public List<Event> findAll() {
    return query("select-events", EVENT_ROW_MAPPER);
  }

  @Override
  public List<Event> findAllByName(String name) {
    return query("select-events-by-name", EVENT_ROW_MAPPER, prepareForLike(name));
  }

  @Override
  public void add(Event event) {
    update("insert-event", event);
  }

  @Override
  public void addAll(List<Event> events) {
    batchUpdateObjects("insert-event", events);
  }

  @Override
  public void delete(Event event) {
    update("delete-event", event);
  }

  @Override
  public void deleteAll(List<Event> events) {
    update("delete-event", events);
  }

  @Override
  public int countEventsByName(String name) {
    return (Integer)countByNameCall.execute(prepareForLike(name)).get("total");
  }

  @Override
  public void raiseException(String message) throws ServiceException {
    exceptionCall.execute("$" + message + "$");
  }

  public void setCountByNameCall(SimpleJdbcCall countByNameCall) {
    this.countByNameCall = countByNameCall;
  }

  public void setExceptionCall(SimpleJdbcCall exceptionCall) {
    this.exceptionCall = exceptionCall;
  }
}
