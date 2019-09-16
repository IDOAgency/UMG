package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadHandler;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;

public class NotepadHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hNtp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public NotepadHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hNtp");
  }
  
  public String getDescription() { return "Notepad"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("notepad"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("notepad-previous"))
      return previous(dispatcher, context, command); 
    if (command.equalsIgnoreCase("notepad-next"))
      return next(dispatcher, context, command); 
    if (command.equalsIgnoreCase("notepad-toggle"))
      return toggle(dispatcher, context, command); 
    if (command.equalsIgnoreCase("notepad-first"))
      return first(dispatcher, context, command); 
    if (command.equalsIgnoreCase("notepad-last"))
      return last(dispatcher, context, command); 
    return false;
  }
  
  private boolean next(Dispatcher dispatcher, Context context, String command) {
    int notepadType = -1;
    notepadType = context.getIntRequestValue("notepadType", notepadType);
    String lastCommand = context.getParameter("lastCommand");
    Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
    int maxpages = notepad.getMaxRecords() / notepad.getRecordsPerPage();
    if (notepad.getMaxRecords() % notepad.getRecordsPerPage() != 0)
      maxpages++; 
    if (notepad.getAllContents() != null && notepad.allContents.size() < notepad.getTotalRecords() && maxpages < notepad.getTotalPages())
      if (notepadType == 0) {
        notepad.setAllContents(null);
        notepad.setMaxRecords(0);
      }  
    notepad.incrementPage();
    if (notepadType == 0)
      setSearchInitiated(context); 
    return dispatcher.redispatch(context, lastCommand);
  }
  
  private boolean previous(Dispatcher dispatcher, Context context, String command) {
    int notepadType = -1;
    notepadType = context.getIntRequestValue("notepadType", notepadType);
    String lastCommand = context.getParameter("lastCommand");
    Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
    notepad.decrementPage();
    return dispatcher.redispatch(context, lastCommand);
  }
  
  private boolean toggle(Dispatcher dispatcher, Context context, String command) {
    String notepadType = "";
    notepadType = context.getRequestValue("notepadType", notepadType);
    String lastCommand = context.getParameter("lastCommand");
    Boolean notepadVisible = new Boolean(true);
    if (context.getSessionValue(notepadType) != null)
      notepadVisible = (Boolean)context.getSessionValue(notepadType); 
    if (notepadVisible.booleanValue()) {
      notepadVisible = new Boolean(false);
    } else {
      notepadVisible = new Boolean(true);
    } 
    context.putSessionValue(notepadType, notepadVisible);
    return dispatcher.redispatch(context, lastCommand);
  }
  
  private boolean first(Dispatcher dispatcher, Context context, String command) {
    int notepadType = -1;
    notepadType = context.getIntRequestValue("notepadType", notepadType);
    String lastCommand = context.getParameter("lastCommand");
    Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
    notepad.firstPage();
    return dispatcher.redispatch(context, lastCommand);
  }
  
  private boolean last(Dispatcher dispatcher, Context context, String command) {
    int notepadType = -1;
    notepadType = context.getIntRequestValue("notepadType", notepadType);
    String lastCommand = context.getParameter("lastCommand");
    User user = (User)context.getSessionValue("user");
    Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
    if (notepad.getAllContents() != null && notepad.allContents.size() < notepad.getTotalRecords())
      if (notepadType == 0) {
        notepad.setAllContents(null);
        notepad.setMaxRecords(0);
      }  
    if (notepadType == 0)
      setSearchInitiated(context); 
    notepad.lastPage();
    return dispatcher.redispatch(context, lastCommand);
  }
  
  private void setSearchInitiated(Context context) {
    User user = (User)context.getSessionValue("user");
    if (user != null)
      user.SS_searchInitiated = true; 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\NotepadHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */