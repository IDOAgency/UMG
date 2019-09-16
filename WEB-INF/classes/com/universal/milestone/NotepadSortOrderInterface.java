package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.universal.milestone.Notepad;

public interface NotepadSortOrderInterface {
  public static final String descendOrder = " DESC ";
  
  public static final String notepadSortOrderSessionObjectName = "notepadSortOrder";
  
  boolean sortHelper(Dispatcher paramDispatcher, Context paramContext, Notepad paramNotepad);
  
  boolean isSortAscending();
  
  String getNotepadOrderCol();
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\NotepadSortOrderInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */