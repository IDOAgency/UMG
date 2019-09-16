package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.DatePeriod;
import java.util.Calendar;

public class DatePeriod extends DataEntity {
  protected int datePeriodId;
  
  protected String name;
  
  protected String cycle;
  
  protected Calendar startDate = Calendar.getInstance();
  
  protected Calendar endDate = Calendar.getInstance();
  
  protected Calendar solDate = Calendar.getInstance();
  
  public DatePeriod() {}
  
  public DatePeriod(int id, String name, String cycle, Calendar startDate, Calendar endDate, Calendar solDate) {
    this.datePeriodId = id;
    this.cycle = cycle;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.solDate = solDate;
  }
  
  public int getId() { return this.datePeriodId; }
  
  public void setId(int id) { this.datePeriodId = id; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getCycle() { return this.cycle; }
  
  public void setCycle(String cycle) { this.cycle = cycle; }
  
  public Calendar getStartDate() { return this.startDate; }
  
  public void setStartDate(Calendar startDate) { this.startDate = startDate; }
  
  public Calendar getEndDate() { return this.endDate; }
  
  public void setEndDate(Calendar endDate) { this.endDate = endDate; }
  
  public Calendar getSolDate() { return this.solDate; }
  
  public void setSolDate(Calendar solDate) { this.solDate = solDate; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DatePeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */