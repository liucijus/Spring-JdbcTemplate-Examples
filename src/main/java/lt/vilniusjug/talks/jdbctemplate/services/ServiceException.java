package lt.vilniusjug.talks.jdbctemplate.services;

import org.springframework.dao.DataAccessException;

public class ServiceException extends DataAccessException {
  public ServiceException(String message) {
    super(message);
  }
}
