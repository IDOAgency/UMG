package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.LookupObject;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.Table;

public class Table extends LookupObject {
  protected LookupObject detail;
  
  protected PrefixCode subdetail;
  
  public LookupObject getDetail() { return this.detail; }
  
  public void setDetail(LookupObject detail) { this.detail = detail; }
  
  public PrefixCode getSubDetail() { return this.subdetail; }
  
  public void setSubDetail(PrefixCode subdetail) { this.subdetail = subdetail; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Table.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */