package WEB-INF.classes.com.universal.milestone;

import com.techempower.EnhancedProperties;
import com.techempower.Version;
import com.techempower.gemini.Configurator;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Audit;
import com.universal.milestone.Cache;
import com.universal.milestone.CompanyManager;
import com.universal.milestone.DayManager;
import com.universal.milestone.DivisionManager;
import com.universal.milestone.FamilyManager;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.JspDebug;
import com.universal.milestone.LabelManager;
import com.universal.milestone.MilestoneConfigurator;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.PriceCodeManager;
import com.universal.milestone.ReleaseWeekManager;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.TableManager;
import com.universal.milestone.TaskManager;
import com.universal.milestone.TemplateManager;
import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
import com.universal.milestone.UserManager;

public class MilestoneConfigurator extends Configurator {
  public static final int DEFAULT_DB_POOLING = 5;
  
  protected MilestoneConfigurator(GeminiApplication application) { super(application); }
  
  protected void customConfiguration(EnhancedProperties props, Version version) {
    boolean supportsAbsolute = props.getYesNoProperty("JDBCDriverSupportsAbsolute", false);
    boolean supportsGetRow = props.getYesNoProperty("JDBCDriverSupportsGetRow", false);
    boolean jdbc1 = props.getYesNoProperty("JDBC1.x", false);
    String urlPrefix = props.getProperty("JDBCURLPrefix", "jdbc:odbc:");
    String driverName = props.getProperty("JDBCDriver", "sun.jdbc.odbc.JdbcOdbcDriver");
    int dbPooling = props.getIntegerProperty("DatabaseConnections", 5);
    JdbcConnector.loadDriver(driverName, urlPrefix, supportsAbsolute, supportsGetRow, jdbc1, dbPooling);
    JdbcConnector.setDefaultJdbcURLPrefix(urlPrefix);
    JdbcConnector.getDataSourceProperties(props);
    this.application.getInfrastructure().configure(props, version);
    MilestoneHelper.configure(props, this.application);
    Cache.configure(props, this.application);
    Notepad.configure(props, this.application);
    MilestoneSecurity.configure(props, this.application);
    MilestoneDataEntity.configure(props, this.application);
    JspDebug.configure(props, this.application);
    CompanyManager.configure(props, this.application);
    DayManager.configure(props, this.application);
    DivisionManager.configure(props, this.application);
    FamilyManager.configure(props, this.application);
    LabelManager.configure(props, this.application);
    PriceCodeManager.configure(props, this.application);
    ReleaseWeekManager.configure(props, this.application);
    ScheduleManager.configure(props, this.application);
    SelectionManager.configure(props, this.application);
    TableManager.configure(props, this.application);
    TaskManager.configure(props, this.application);
    TemplateManager.configure(props, this.application);
    UserManager.configure(props, this.application);
    UmeProdScheduleForPrintSubHandler.configure(props, this.application);
    ReportHandler.configure(props, this.application);
    setupBindings();
  }
  
  public static void setupBindings() {
    JdbcConnector jdbcConnector = MilestoneHelper.getConnector("SELECT * FROM Audit;");
    jdbcConnector.runQuery();
    if (jdbcConnector.more()) {
      Audit audit = new Audit();
      audit.initializeByVariables(jdbcConnector);
    } 
    jdbcConnector.close();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneConfigurator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */