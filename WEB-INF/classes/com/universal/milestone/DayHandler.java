package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormDateField;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Day;
import com.universal.milestone.DayHandler;
import com.universal.milestone.DayManager;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Vector;

public class DayHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hDay";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public DayHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hDay");
  }
  
  public String getDescription() { return "Day Type Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("daytype"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("daytype-search")) {
      search(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("daytype-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("daytype-edit-save")) {
      editSave(context);
    } else if (command.equalsIgnoreCase("daytype-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("daytype-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("daytype-edit-new")) {
      newForm(context);
    } else if (command.equalsIgnoreCase("daytype-sort")) {
      sort(dispatcher, context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    notepad.setAllContents(null);
    notepad.setSelected(null);
    DayManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "daytype-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = getDayNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
      DayManager.getInstance();
      notepad.setSearchQuery("SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type");
    } 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_DAYTYPE[sort]);
    notepad.setAllContents(null);
    notepad = getDayNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "daytype-editor");
    return true;
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Day day = MilestoneHelper.getScreenDay(context);
    if (day != null) {
      Form form = null;
      if (day != null) {
        form = buildForm(context, day);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("daytype-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  protected Form buildForm(Context context, Day day) {
    DateFormat dateFormatter = DateFormat.getDateInstance(3);
    Form daytypeForm = new Form(this.application, "daytypeForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormDropDownMenu Group = new FormDropDownMenu("Group", Integer.toString(day.getCalendarGroup()), "2,1", "Canada, United States", true);
    Group.setTabIndex(1);
    daytypeForm.addElement(Group);
    FormTextField DayType = new FormTextField("DayType", "", false, 2, 2);
    DayType.setTabIndex(2);
    DayType.setValue(day.getDayType());
    daytypeForm.addElement(DayType);
    FormTextField Description = new FormTextField("Description", "", false, 50, 50);
    Description.setTabIndex(3);
    Description.setValue(day.getDescription());
    daytypeForm.addElement(Description);
    FormTextField SpecialDate = new FormTextField("SpecialDate", "", true, 10);
    SpecialDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    SpecialDate.addFormEvent("onBlur", "Javascript:checkField( this )");
    SpecialDate.setTabIndex(4);
    SpecialDate.setValue(dateFormatter.format(day.getSpecificDate().getTime()));
    daytypeForm.addElement(SpecialDate);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (day.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(day.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    daytypeForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(day.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(day.getLastUpdatingUser()).getName()); 
    daytypeForm.addElement(lastUpdatedBy);
    FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", false);
    GroupSearch.setId("GroupSearch");
    daytypeForm.addElement(GroupSearch);
    FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
    SpecialDateSearch.setId("SpecialDateSearch");
    daytypeForm.addElement(SpecialDateSearch);
    FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
    DayTypeSearch.setId("DayTypeSearch");
    daytypeForm.addElement(DayTypeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    daytypeForm.addElement(DescriptionSearch);
    daytypeForm.addElement(new FormHidden("cmd", "daytype-editor"));
    daytypeForm.addElement(new FormHidden("OrderBy", "", true));
    context.putSessionValue("day", day);
    if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE")); 
    return daytypeForm;
  }
  
  public Notepad getDayNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(6, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(6, context);
      if (notepad.getAllContents() == null) {
        contents = DayManager.getInstance().getDayNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Group", "Special Date", "Day Type" };
    contents = DayManager.getInstance().getDayNotepadList(userId, null);
    return new Notepad(contents, 0, 7, "Day Type", 6, columnNames);
  }
  
  private boolean editSave(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Day day = MilestoneHelper.getScreenDay(context);
    Form form = buildForm(context, day);
    if (DayManager.getInstance().isTimestampValid(day)) {
      form.setValues(context);
      int groupNum = 0;
      try {
        groupNum = Integer.parseInt(form.getStringValue("Group"));
      } catch (Exception exception) {}
      String dayTypeString = form.getStringValue("DayType");
      if (dayTypeString.equals(""))
        dayTypeString = "H"; 
      String descriptionString = form.getStringValue("Description");
      String dateString = form.getStringValue("SpecialDate");
      day.setCalendarGroup(groupNum);
      day.setDayType(dayTypeString);
      day.setDescription(descriptionString);
      day.setSpecificDate(MilestoneHelper.getDate(dateString));
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          if (!DayManager.getInstance().isDuplicate(day)) {
            Day savedDay = DayManager.getInstance().saveDay(day, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            lastUpdated.setValue(MilestoneHelper.getLongDate(savedDay.getLastUpdateDate()));
            notepad.setAllContents(null);
            Cache.flushDayTypes();
            notepad = getDayNotepad(context, user.getUserId());
            notepad.setSelected(savedDay);
            day = (Day)notepad.validateSelected();
            context.putSessionValue("Day", day);
            if (day == null)
              return goToBlank(context); 
            form = buildForm(context, day);
          } else {
            context.putDelivery("AlertMessage", "Could not save the day type because there is already such a record. Please try it again later");
            return edit(context);
          } 
        } else {
          context.putDelivery("FormValidation", formValidation);
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
      return edit(context);
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("daytype-editor.jsp");
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Day day = MilestoneHelper.getScreenDay(context);
    if (day != null) {
      DayManager.getInstance().deleteDay(day, user.getUserId());
      notepad.setSelected(null);
      notepad.setAllContents(null);
      Cache.flushDayTypes();
      notepad = getDayNotepad(context, user.getUserId());
      notepad.setSelected(null);
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE")); 
    return context.includeJSP("daytype-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    DateFormat dateFormatter = DateFormat.getDateInstance(3);
    Form daytypeForm = new Form(this.application, "daytypeForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormDropDownMenu Group = new FormDropDownMenu("Group", "0", "2,1", "Canada, United States", true);
    Group.setTabIndex(1);
    daytypeForm.addElement(Group);
    FormTextField DayType = new FormTextField("DayType", "", false, 2, 2);
    DayType.setTabIndex(2);
    daytypeForm.addElement(DayType);
    FormTextField Description = new FormTextField("Description", "", false, 50, 50);
    Description.setTabIndex(3);
    daytypeForm.addElement(Description);
    FormDateField SpecialDate = new FormDateField("SpecialDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
    SpecialDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    SpecialDate.addFormEvent("onBlur", "Javascript:checkField( this )");
    SpecialDate.setTabIndex(4);
    daytypeForm.addElement(SpecialDate);
    FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", false);
    GroupSearch.setId("GroupSearch");
    daytypeForm.addElement(GroupSearch);
    FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
    SpecialDateSearch.setId("SpecialDateSearch");
    daytypeForm.addElement(SpecialDateSearch);
    FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
    DayTypeSearch.setId("DayTypeSearch");
    daytypeForm.addElement(DayTypeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    daytypeForm.addElement(DescriptionSearch);
    daytypeForm.addElement(new FormHidden("cmd", "daytype-edit-new"));
    daytypeForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE")); 
    return daytypeForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDayNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Day dayType = new Day();
    Form form = buildNewForm(context);
    form.setValues(context);
    String groupString = form.getStringValue("Group");
    int groupNum = 0;
    try {
      groupNum = Integer.parseInt(form.getStringValue("Group"));
    } catch (Exception exception) {}
    String dayTypeString = form.getStringValue("DayType");
    if (dayTypeString.equals(""))
      dayTypeString = "H"; 
    String descriptionString = form.getStringValue("Description");
    String dateString = form.getStringValue("SpecialDate");
    dayType.setCalendarGroup(groupNum);
    dayType.setDayType(dayTypeString);
    dayType.setDescription(descriptionString);
    dayType.setSpecificDate(MilestoneHelper.getDate(dateString));
    if (!DayManager.getInstance().isDuplicate(dayType)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Day saveNewDayType = DayManager.getInstance().saveNewDayType(dayType, user.getUserId());
          context.putSessionValue("DayType", saveNewDayType);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setSearchQuery(""); 
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          Cache.flushDayTypes();
          notepad = getDayNotepad(context, user.getUserId());
          notepad.setSelected(saveNewDayType);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("daytype-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", "Could not save the day type because there is already such a record. Please try it again later");
    } 
    return edit(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(6, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "daytype-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", true);
    GroupSearch.setId("GroupSearch");
    form.addElement(GroupSearch);
    FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
    SpecialDateSearch.setId("SpecialDateSearch");
    form.addElement(SpecialDateSearch);
    FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
    DayTypeSearch.setId("DayTypeSearch");
    form.addElement(DayTypeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    form.addElement(DescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-daytype-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DayHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */