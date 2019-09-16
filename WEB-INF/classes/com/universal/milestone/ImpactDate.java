package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ImpactDate;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import java.util.Calendar;

public class ImpactDate extends MilestoneDataEntity implements NotepadContentObject {
  protected int impactDateID = -1;
  
  protected int selectionID = -1;
  
  protected String format = "";
  
  protected String formatDescription = "";
  
  protected Calendar impactDate = null;
  
  protected boolean tbi = false;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  public String getTableName() { return "impact_dates"; }
  
  public int getIdentity() { return getImpactDateID(); }
  
  public int getImpactDateID() { return this.impactDateID; }
  
  public void setImpactDateID(int id) { this.impactDateID = id; }
  
  public int getSelectionID() { return this.selectionID; }
  
  public void setSelectionID(int id) { this.selectionID = id; }
  
  public Calendar getImpactDate() { return this.impactDate; }
  
  public void setImpactDate(Calendar impactDate) { this.impactDate = impactDate; }
  
  public String getFormat() { return this.format; }
  
  public void setFormat(String format) { this.format = format; }
  
  public String getFormatDescription() { return this.formatDescription; }
  
  public void setFormatDescription(String formatDescription) { this.formatDescription = formatDescription; }
  
  public boolean getTbi() { return this.tbi; }
  
  public void setTbi(boolean tbi) { this.tbi = tbi; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.impactDateID); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ImpactDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */