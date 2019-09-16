package WEB-INF.classes.com.universal.milestone.jms;

import com.universal.milestone.Bom;
import com.universal.milestone.Pfm;
import com.universal.milestone.Selection;
import com.universal.milestone.jms.MessageObject;
import java.io.Serializable;

public class MessageObject implements Serializable {
  public int selectionId = -1;
  
  public String emailCC = "";
  
  public String emailBody = "";
  
  public String emailSubject = "";
  
  public String emailTo = "";
  
  public String emailFrom = "";
  
  public String emailAttach = "";
  
  public Pfm pfmObj = null;
  
  public Bom bomObj = null;
  
  public Selection selectionObj = null;
  
  public boolean IsPushOnDemand = false;
  
  public boolean IsPushPFM = false;
  
  public void setEmailBody(String emailBody) { this.emailBody = emailBody; }
  
  public String getEmailBody() { return this.emailBody; }
  
  public void setEmailSubject(String emailSubject) { this.emailSubject = emailSubject; }
  
  public String getEmailSubject() { return this.emailSubject; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\jms\MessageObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */