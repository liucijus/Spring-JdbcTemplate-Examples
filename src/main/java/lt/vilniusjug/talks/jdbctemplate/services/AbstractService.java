package lt.vilniusjug.talks.jdbctemplate.services;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Vaidas Pilkauskas
 */
public class AbstractService extends NamedParameterJdbcDaoSupport {
  private Map<String, String> queries;

  protected <T> List<T> query(String queryId, RowMapper<T> rowMapper, Object... args) {
    return getJdbcTemplate().query(getQuery(queryId), rowMapper, args);

  }

  protected <T extends Serializable> int update(String queryId, T object){
    return getNamedParameterJdbcTemplate().update(getQuery(queryId), new BeanPropertySqlParameterSource(object));
  }

  protected <T extends Serializable> int[] update(String queryId, List<T> objects){
    SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(objects.toArray());
    return getNamedParameterJdbcTemplate().batchUpdate(getQuery(queryId), batch);
  }

  protected <T extends Serializable> int[] batchUpdateObjects(String queryId, List<T> objects){
    SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(objects.toArray());
    return getNamedParameterJdbcTemplate().batchUpdate(getQuery(queryId), batch);
  }


  protected String prepareForLike(String name) {
    return name + "%";
  }

  protected String getQuery(String key) {
    return (queries != null) ? queries.get(key) : null;
  }

  public void setQueries(Map<String, String> queries) {
    this.queries = queries;
  }
}
