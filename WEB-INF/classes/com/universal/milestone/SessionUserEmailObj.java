package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.SessionUserEmailObj;

public class SessionUserEmailObj {
  String emailAddr;
  
  boolean sendEmail;
  
  public SessionUserEmailObj(String emailAddr, boolean sendEmail) {
    this.emailAddr = null;
    this.sendEmail = false;
    this.emailAddr = emailAddr;
    this.sendEmail = sendEmail;
  }
  
  public void setEmailAddr(String emailAddr) { this.emailAddr = emailAddr; }
  
  public String getEmailAddr() { return this.emailAddr; }
  
  public void setSendEmail(boolean sendEmail) { this.sendEmail = sendEmail; }
  
  public boolean getSendEmail() { return this.sendEmail; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SessionUserEmailObj.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */