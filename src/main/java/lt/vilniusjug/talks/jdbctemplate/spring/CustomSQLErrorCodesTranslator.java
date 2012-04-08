package lt.vilniusjug.talks.jdbctemplate.spring;

import lt.vilniusjug.talks.jdbctemplate.services.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vaidas Pilkauskas
 */
public class CustomSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {
  private Pattern pattern = Pattern.compile("\\$(.+)\\$");

  @Override
  public DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
    Matcher matcher = pattern.matcher(sqlEx.getMessage());
    if (matcher.find() && (matcher.groupCount() > 0)) {
      String extractedMessage = matcher.group(1);
      return new ServiceException(extractedMessage);
    }

    return null;
  }
}
