package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormDateField;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.ReleaseWeek;
import com.universal.milestone.ReleaseWeekHandler;
import com.universal.milestone.ReleaseWeekManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import java.util.Calendar;
import java.util.Vector;

public class ReleaseWeekHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hRwk";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ReleaseWeekHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hRwk");
  }
  
  public String getDescription() { return "Release Week Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("release-week"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("release-week-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("release-week-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("release-week-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("release-week-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("release-week-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("release-week-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("release-week-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    ReleaseWeekManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "release-week-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
      ReleaseWeekManager.getInstance();
      notepad.setSearchQuery("SELECT [name], [start_date], [end_date]FROM vi_Date_Period");
    } 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_RELEASE_WEEK[sort]);
    notepad.setAllContents(null);
    notepad = getReleaseWeekNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "release-week-editor");
    return true;
  }
  
  public Notepad getReleaseWeekNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(15, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
      if (notepad.getAllContents() == null) {
        contents = ReleaseWeekManager.getInstance().getReleaseWeekNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Name", "Start Date", "End Date" };
    contents = ReleaseWeekManager.getInstance().getReleaseWeekNotepadList(userId, null);
    return new Notepad(contents, 0, 15, "Release Week", 15, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
    if (releaseWeek != null) {
      Form form = null;
      if (releaseWeek != null) {
        form = buildForm(context, releaseWeek);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("release-week-editor.jsp");
    } 
    goToBlank(context);
    return context.includeJSP("blank-release-week-editor.jsp");
  }
  
  private Form buildForm(Context context, ReleaseWeek releaseWeek) {
    Form releaseWeekForm = new Form(this.application, "releaseWeekForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    String name = "";
    name = releaseWeek.getName();
    FormTextField theName = new FormTextField("theName", name, true, 50, 50);
    theName.setTabIndex(1);
    releaseWeekForm.addElement(theName);
    String cycle = "";
    cycle = releaseWeek.getCycle();
    FormTextField Cycle = new FormTextField("Cycle", cycle, true, 8, 8);
    Cycle.setTabIndex(2);
    releaseWeekForm.addElement(Cycle);
    String startDate = "";
    startDate = MilestoneHelper.getFormatedDate(releaseWeek.getStartDate());
    FormDateField StartDate = new FormDateField("StartDate", startDate, true, 10);
    StartDate.addFormEvent("onBlur", "checkField( this );");
    StartDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    StartDate.setTabIndex(3);
    releaseWeekForm.addElement(StartDate);
    String endDate = "";
    endDate = MilestoneHelper.getFormatedDate(releaseWeek.getEndDate());
    FormDateField EndDate = new FormDateField("EndDate", endDate, true, 10);
    EndDate.addFormEvent("onBlur", "checkField( this );");
    EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    EndDate.setTabIndex(4);
    releaseWeekForm.addElement(EndDate);
    String solDate = "";
    solDate = MilestoneHelper.getFormatedDate(releaseWeek.getSolDate());
    FormDateField SolDate = new FormDateField("SolDate", solDate, false, 10);
    SolDate.addFormEvent("onBlur", "checkField( this );");
    SolDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    SolDate.setTabIndex(5);
    releaseWeekForm.addElement(SolDate);
    FormTextField NameSearch = new FormTextField("NameSearch", "", false, 20);
    NameSearch.setId("NameSearch");
    releaseWeekForm.addElement(NameSearch);
    FormTextField CycleSearch = new FormTextField("CycleSearch", "", false, 8);
    CycleSearch.setId("CycleSearch");
    releaseWeekForm.addElement(CycleSearch);
    FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", false, 10);
    StartDateSearch.setId("StartDateSearch");
    releaseWeekForm.addElement(StartDateSearch);
    FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", false, 10);
    EndDateSearch.setId("EndDateSearch");
    releaseWeekForm.addElement(EndDateSearch);
    releaseWeekForm.addElement(new FormHidden("cmd", "release-week-editor", true));
    releaseWeekForm.addElement(new FormHidden("OrderBy", "", true));
    context.putSessionValue("releaseWeek", releaseWeek);
    if (context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE")); 
    return releaseWeekForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
    Form form = buildForm(context, releaseWeek);
    if (ReleaseWeekManager.getInstance().isTimestampValid(releaseWeek)) {
      form.setValues(context);
      String nameString = form.getStringValue("theName");
      String cycleString = form.getStringValue("Cycle");
      String startDateString = form.getStringValue("StartDate");
      String endDateString = form.getStringValue("EndDate");
      String solDateString = form.getStringValue("SolDate");
      releaseWeek.setName(nameString);
      releaseWeek.setCycle(cycleString);
      releaseWeek.setStartDate(MilestoneHelper.getDate(startDateString));
      releaseWeek.setEndDate(MilestoneHelper.getDate(endDateString));
      releaseWeek.setSolDate(MilestoneHelper.getDate(solDateString));
      if (!ReleaseWeekManager.getInstance().isDuplicate(releaseWeek)) {
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            ReleaseWeek saveReleaseWeek = ReleaseWeekManager.getInstance().saveReleaseWeek(releaseWeek, user.getUserId());
            notepad.setAllContents(null);
            notepad = getReleaseWeekNotepad(context, user.getUserId());
            notepad.setSelected(saveReleaseWeek);
            releaseWeek = (ReleaseWeek)notepad.validateSelected();
            context.putSessionValue("ReleaseWeek", releaseWeek);
            if (releaseWeek == null) {
              goToBlank(context);
            } else {
              form = buildForm(context, releaseWeek);
            } 
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
      } else {
        context.putDelivery("AlertMessage", 
            MilestoneMessage.getMessage(5, 
              
              new String[] { "Release Week", releaseWeek.getName() }));
        context.putDelivery("Form", form);
        return edit(context);
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
      context.putDelivery("Form", form);
      return edit(context);
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("release-week-editor.jsp");
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
    if (releaseWeek != null) {
      ReleaseWeekManager.getInstance().deleteReleaseWeek(releaseWeek, user.getUserId());
      notepad.setAllContents(null);
      notepad = getReleaseWeekNotepad(context, user.getUserId());
      notepad.setSelected(null);
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    return context.includeJSP("release-week-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form releaseWeekForm = new Form(this.application, "releaseWeekForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormTextField theName = new FormTextField("theName", "", true, 50, 50);
    theName.setTabIndex(1);
    releaseWeekForm.addElement(theName);
    FormTextField Cycle = new FormTextField("Cycle", "", true, 8, 8);
    Cycle.setTabIndex(2);
    releaseWeekForm.addElement(Cycle);
    FormTextField StartDate = new FormTextField("StartDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
    StartDate.addFormEvent("onBlur", "checkField( this );");
    StartDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    StartDate.setTabIndex(3);
    releaseWeekForm.addElement(StartDate);
    FormTextField EndDate = new FormTextField("EndDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
    EndDate.addFormEvent("onBlur", "checkField( this );");
    EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    EndDate.setTabIndex(4);
    releaseWeekForm.addElement(EndDate);
    FormDateField SolDate = new FormDateField("SolDate", "", false, 10);
    SolDate.addFormEvent("onBlur", "checkField( this );");
    SolDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    SolDate.setTabIndex(5);
    releaseWeekForm.addElement(SolDate);
    FormTextField NameSearch = new FormTextField("NameSearch", "", false, 20);
    NameSearch.setId("NameSearch");
    releaseWeekForm.addElement(NameSearch);
    FormTextField CycleSearch = new FormTextField("CycleSearch", "", false, 8);
    CycleSearch.setId("CycleSearch");
    releaseWeekForm.addElement(CycleSearch);
    FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", false, 10);
    StartDateSearch.setId("StartDateSearch");
    releaseWeekForm.addElement(StartDateSearch);
    FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", false, 10);
    EndDateSearch.setId("EndDateSearch");
    releaseWeekForm.addElement(EndDateSearch);
    releaseWeekForm.addElement(new FormHidden("cmd", "release-week-edit-new", true));
    releaseWeekForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE")); 
    return releaseWeekForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    ReleaseWeek releaseWeek = new ReleaseWeek();
    Form form = buildNewForm(context);
    form.setValues(context);
    String nameString = form.getStringValue("theName");
    String cycleString = form.getStringValue("Cycle");
    String startString = form.getStringValue("StartDate");
    String endString = form.getStringValue("EndDate");
    String solString = form.getStringValue("SolDate");
    releaseWeek.setName(nameString);
    releaseWeek.setCycle(cycleString);
    releaseWeek.setStartDate(MilestoneHelper.getDate(startString));
    releaseWeek.setEndDate(MilestoneHelper.getDate(endString));
    releaseWeek.setSolDate(MilestoneHelper.getDate(solString));
    if (!ReleaseWeekManager.getInstance().isDuplicate(releaseWeek)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          ReleaseWeek saveNewReleaseWeek = ReleaseWeekManager.getInstance().saveNewReleaseWeek(releaseWeek, user.getUserId());
          context.putSessionValue("ReleaseWeek", saveNewReleaseWeek);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setSearchQuery(""); 
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getReleaseWeekNotepad(context, user.getUserId());
          notepad.setSelected(releaseWeek);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("release-week-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Release Week", releaseWeek.getName() }));
    } 
    return edit(context);
  }
  
  private Context goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(15, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "release-week-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField NameSearch = new FormTextField("NameSearch", "", true, 20);
    NameSearch.setId("NameSearch");
    form.addElement(NameSearch);
    FormTextField CycleSearch = new FormTextField("CycleSearch", "", true, 8);
    CycleSearch.setId("CycleSearch");
    form.addElement(CycleSearch);
    FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", true, 10);
    StartDateSearch.setId("StartDateSearch");
    form.addElement(StartDateSearch);
    FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", true, 10);
    EndDateSearch.setId("EndDateSearch");
    form.addElement(EndDateSearch);
    context.putDelivery("Form", form);
    return context;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReleaseWeekHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */