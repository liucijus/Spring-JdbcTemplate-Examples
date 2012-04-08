package lt.vilniusjug.talks.jdbctemplate.services;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Vaidas Pilkauskas
 */
public class AbstractServiceTest {
  @Autowired
  private DataSource dataSource;
  private IDataSet dataSet;

  public AbstractServiceTest(String dataSetName) throws Exception {
    dataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(dataSetName));
  }

  protected void createTablesAndData() throws Exception {
    IDatabaseConnection connection = new DatabaseDataSourceConnection(dataSource);
    createTables(dataSet, dataSource.getConnection());
    DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
  }

  //author and idea: http://stackoverflow.com/questions/1531324/is-there-any-way-for-dbunit-to-automatically-create-tables
  //another idea is to load DB structure DDL, but there then we need to find way to solve cross DB problems
  protected void createTables(IDataSet dataSet, Connection connection) throws Exception {
    String sql = "";
    for (String tableName : dataSet.getTableNames()) {
      ITable table = dataSet.getTable(tableName);
      ITableMetaData metadata = table.getTableMetaData();
      Column[] columns = metadata.getColumns();

      sql += "create table " + tableName + "( ";
      boolean first = true;
      for (Column column : columns) {
        if (!first) {
          sql += ", ";
        }
        String columnName = column.getColumnName();
        String type = "varchar";
        sql += columnName + " " + type;
        if (first) {
          sql += " primary key";
          first = false;
        }
      }
      sql += "); ";
    }
    PreparedStatement pp = connection.prepareStatement(sql);
    pp.executeUpdate();
  }

  protected void dropTables() throws Exception {
    String sql = "";
    for (String tableName : dataSet.getTableNames()) {
      sql += "drop table " + tableName + "; ";
    }
    PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
    statement.executeUpdate();
  }

  @Before
  public void before() throws Exception {
    createTablesAndData();
  }

  @After
  public void after() throws Exception {
    dropTables();
  }
}
