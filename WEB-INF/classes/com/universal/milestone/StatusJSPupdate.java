package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.universal.milestone.StatusJSPupdate;
import javax.servlet.http.HttpServletResponse;

public class StatusJSPupdate {
  protected Context context;
  
  protected int count;
  
  protected HttpServletResponse response;
  
  protected int totalCount;
  
  protected int currentCount;
  
  protected int tenth;
  
  protected int percent;
  
  private boolean calculatedTenth;
  
  protected String jspFileName;
  
  protected String contextType;
  
  protected ComponentLog log;
  
  private static final int ten = 10;
  
  private static final int ninty = 90;
  
  private static final String COMPONENT_CODE = "hRep";
  
  private boolean logflag;
  
  private int internalCounter;
  
  private boolean useInternalCounter;
  
  private String lastStatusReport;
  
  StringBuffer blog;
  
  public StatusJSPupdate(Context context) {
    this.count = -1;
    this.response = null;
    this.totalCount = 0;
    this.currentCount = 0;
    this.tenth = 0;
    this.percent = 0;
    this.calculatedTenth = false;
    this.jspFileName = "status.jsp";
    this.contextType = "text/plain";
    this.logflag = false;
    this.internalCounter = 0;
    this.useInternalCounter = false;
    this.lastStatusReport = "";
    this.blog = null;
    this.context = context;
    this.log = context.getApplication().getLog("hRep");
    this.response = context.getResponse();
  }
  
  public StatusJSPupdate(Context context, String jspFileName) {
    this(context);
    this.jspFileName = jspFileName;
  }
  
  public void updateStatus(int totalCount, int currentCount, String report, int defaultPercent) {
    if (!report.equals(this.lastStatusReport))
      setDefaults(report); 
    int currStatusCount = 0;
    if (this.useInternalCounter) {
      currStatusCount = this.internalCounter;
    } else {
      currStatusCount = currentCount;
    } 
    try {
      if (!this.calculatedTenth)
        this.tenth = calculateTenth(totalCount); 
      if (this.count < currStatusCount / this.tenth || defaultPercent != 0) {
        this.count = currStatusCount / this.tenth;
        if (defaultPercent == 0) {
          this.percent = this.count * 10;
        } else {
          this.percent = defaultPercent;
        } 
        if (this.logflag)
          this.log.log("<<<<<<  updating status: "); 
        this.context.putDelivery("status", new String(report));
        this.context.putDelivery("percent", new String(String.valueOf(this.percent)));
        this.context.includeJSP(this.jspFileName, "hiddenFrame");
        this.response.setContentType(this.contextType);
        this.response.flushBuffer();
      } 
      if (this.useInternalCounter && this.calculatedTenth)
        this.internalCounter++; 
    } catch (Exception e) {
      this.log.log("<<<<<<<<<<<<<< Unable to udpate status: " + e.getMessage());
    } 
    if (this.logflag) {
      this.blog = new StringBuffer("%(");
      this.blog.append(this.percent);
      this.blog.append(") tenth(");
      this.blog.append(this.tenth);
      this.blog.append(") count(");
      this.blog.append(this.count);
      this.blog.append(") intCount(");
      this.blog.append(this.internalCounter);
      this.blog.append(") recCount(");
      this.blog.append(currentCount);
      this.blog.append(") totCount(");
      this.blog.append(totalCount);
      this.blog.append(")");
      this.log.log(this.blog.toString());
      this.blog = null;
    } 
  }
  
  private int calculateTenth(int totalCount) {
    if (totalCount == 0)
      return 10; 
    this.calculatedTenth = true;
    return (totalCount > 10) ? (totalCount / 10) : 1;
  }
  
  public void setInternalCounter(boolean useInternalCounter) { this.useInternalCounter = useInternalCounter; }
  
  public void setLogging(boolean logFlag) { this.logflag = logFlag; }
  
  private void setDefaults(String report) {
    this.count = -1;
    this.lastStatusReport = report;
    this.calculatedTenth = false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\StatusJSPupdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */