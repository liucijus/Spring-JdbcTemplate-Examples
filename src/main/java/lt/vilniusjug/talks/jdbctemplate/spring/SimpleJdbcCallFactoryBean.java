package lt.vilniusjug.talks.jdbctemplate.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.StringUtils;

/**
 * @author Vilmantas Augutis
 */
public class SimpleJdbcCallFactoryBean implements FactoryBean {
  private JdbcTemplate jdbcTemplate;
  private boolean compileOnGet;
  private String schema;
  private String catalog;
  private String dbObject;
  private boolean function;

  public Object getObject() throws Exception {
    SimpleJdbcCall result = new SimpleJdbcCall(jdbcTemplate);
    if (StringUtils.hasLength(schema)) {
      result.setSchemaName(schema);
    }
    if (StringUtils.hasLength(catalog)) {
      result.setCatalogName(catalog);
    }
    if (function) {
      result.withFunctionName(dbObject);
    }
    else {
      result.withProcedureName(dbObject);
    }
    if (compileOnGet) {
      result.compile();
    }
    return result;
  }

  public Class getObjectType() {
    return SimpleJdbcCall.class;
  }

  public boolean isSingleton() {
    return false;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public void setDbObject(String dbObject) {
    this.dbObject = dbObject;
  }

  public void setFunction(boolean function) {
    this.function = function;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public void setCompileOnGet(boolean compileOnGet) {
    this.compileOnGet = compileOnGet;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("SimpleJdbcCallFactoryBean");
    builder.append("{schema='").append(schema).append('\'');
    builder.append(", catalog='").append(catalog).append('\'');
    builder.append(", dbObject='").append(dbObject).append('\'');
    builder.append(", function=").append(function);
    builder.append('}');
    return builder.toString();
  }
}
