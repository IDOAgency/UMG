package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.BomHandler;
import com.universal.milestone.CacheRefreshHandler;
import com.universal.milestone.CompanyHandler;
import com.universal.milestone.DayHandler;
import com.universal.milestone.DivisionHandler;
import com.universal.milestone.EmailDistributionHandler;
import com.universal.milestone.EnvironmentHandler;
import com.universal.milestone.FamilyHandler;
import com.universal.milestone.LabelHandler;
import com.universal.milestone.LoginHandler;
import com.universal.milestone.MainTopMenuHandler;
import com.universal.milestone.MilestoneDispatcher;
import com.universal.milestone.NotepadHandler;
import com.universal.milestone.PfmHandler;
import com.universal.milestone.PriceCodeHandler;
import com.universal.milestone.ProjectSearchHandler;
import com.universal.milestone.ReleaseCalendarHandler;
import com.universal.milestone.ReleaseWeekHandler;
import com.universal.milestone.ReportConfigHandler;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.ScheduleHandler;
import com.universal.milestone.SecurityHandler;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SessionUser;
import com.universal.milestone.TablesHeaderHandler;
import com.universal.milestone.TaskHandler;
import com.universal.milestone.TemplateHandler;

public class MilestoneDispatcher extends Dispatcher {
  protected LoginHandler loginHandler;
  
  public MilestoneDispatcher(GeminiApplication application) { super(application); }
  
  protected void installHandlers() {
    this.loginHandler = new LoginHandler(this.application);
    this.dispatchHandlers.addElement(this.loginHandler);
    this.dispatchHandlers.addElement(new SelectionHandler(this.application));
    this.dispatchHandlers.addElement(new ScheduleHandler(this.application));
    this.dispatchHandlers.addElement(new PriceCodeHandler(this.application));
    this.dispatchHandlers.addElement(new ReportConfigHandler(this.application));
    this.dispatchHandlers.addElement(new ReleaseWeekHandler(this.application));
    this.dispatchHandlers.addElement(new TablesHeaderHandler(this.application));
    this.dispatchHandlers.addElement(new FamilyHandler(this.application));
    this.dispatchHandlers.addElement(new EnvironmentHandler(this.application));
    this.dispatchHandlers.addElement(new CompanyHandler(this.application));
    this.dispatchHandlers.addElement(new DivisionHandler(this.application));
    this.dispatchHandlers.addElement(new LabelHandler(this.application));
    this.dispatchHandlers.addElement(new TaskHandler(this.application));
    this.dispatchHandlers.addElement(new SecurityHandler(this.application));
    this.dispatchHandlers.addElement(new TemplateHandler(this.application));
    this.dispatchHandlers.addElement(new MainTopMenuHandler(this.application));
    this.dispatchHandlers.addElement(new PfmHandler(this.application));
    this.dispatchHandlers.addElement(new BomHandler(this.application));
    this.dispatchHandlers.addElement(new ReportHandler(this.application));
    this.dispatchHandlers.addElement(new NotepadHandler(this.application));
    this.dispatchHandlers.addElement(new DayHandler(this.application));
    this.dispatchHandlers.addElement(new CacheRefreshHandler(this.application));
    this.dispatchHandlers.addElement(new EmailDistributionHandler(this.application));
    this.dispatchHandlers.addElement(new ProjectSearchHandler(this.application));
    this.dispatchHandlers.addElement(new ReleaseCalendarHandler(this.application));
    this.defaultHandler = this.loginHandler;
  }
  
  public boolean dispatch(Context context) {
    boolean retValue = super.dispatch(context);
    SessionUser sessionUser = (SessionUser)context.getSessionValue("UserSession");
    if (sessionUser != null && sessionUser.recordPK != -1)
      if (context.getRequest().getQueryString() == null || context.getRequest().getQueryString().equals("")) {
        sessionUser.setLastActiveDate(context.getCommand());
      } else {
        sessionUser.setLastActiveDate(context.getRequest().getQueryString());
      }  
    return retValue;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */