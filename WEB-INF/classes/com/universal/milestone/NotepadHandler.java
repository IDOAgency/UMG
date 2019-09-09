/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.NotepadHandler;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.User;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotepadHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hNtp";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public NotepadHandler(GeminiApplication application) {
/*  60 */     this.application = application;
/*  61 */     this.log = application.getLog("hNtp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public String getDescription() { return "Notepad"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  79 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  81 */       if (command.startsWith("notepad"))
/*     */       {
/*  83 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*     */     
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  99 */     if (command.equalsIgnoreCase("notepad-previous"))
/*     */     {
/* 101 */       return previous(dispatcher, context, command);
/*     */     }
/* 103 */     if (command.equalsIgnoreCase("notepad-next"))
/*     */     {
/* 105 */       return next(dispatcher, context, command);
/*     */     }
/* 107 */     if (command.equalsIgnoreCase("notepad-toggle"))
/*     */     {
/* 109 */       return toggle(dispatcher, context, command);
/*     */     }
/* 111 */     if (command.equalsIgnoreCase("notepad-first"))
/*     */     {
/* 113 */       return first(dispatcher, context, command);
/*     */     }
/* 115 */     if (command.equalsIgnoreCase("notepad-last"))
/*     */     {
/* 117 */       return last(dispatcher, context, command);
/*     */     }
/*     */     
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean next(Dispatcher dispatcher, Context context, String command) {
/* 128 */     int notepadType = -1;
/* 129 */     notepadType = context.getIntRequestValue("notepadType", notepadType);
/* 130 */     String lastCommand = context.getParameter("lastCommand");
/*     */     
/* 132 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
/*     */     
/* 134 */     int maxpages = notepad.getMaxRecords() / notepad.getRecordsPerPage();
/* 135 */     if (notepad.getMaxRecords() % notepad.getRecordsPerPage() != 0) maxpages++;
/*     */     
/* 137 */     if (notepad.getAllContents() != null && notepad.allContents.size() < notepad.getTotalRecords() && maxpages < notepad.getTotalPages())
/*     */     {
/* 139 */       if (notepadType == 0) {
/*     */         
/* 141 */         notepad.setAllContents(null);
/* 142 */         notepad.setMaxRecords(0);
/*     */       } 
/*     */     }
/*     */     
/* 146 */     notepad.incrementPage();
/*     */     
/* 148 */     if (notepadType == 0) {
/* 149 */       setSearchInitiated(context);
/*     */     }
/* 151 */     return dispatcher.redispatch(context, lastCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean previous(Dispatcher dispatcher, Context context, String command) {
/* 159 */     int notepadType = -1;
/* 160 */     notepadType = context.getIntRequestValue("notepadType", notepadType);
/* 161 */     String lastCommand = context.getParameter("lastCommand");
/*     */     
/* 163 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
/* 164 */     notepad.decrementPage();
/*     */     
/* 166 */     return dispatcher.redispatch(context, lastCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean toggle(Dispatcher dispatcher, Context context, String command) {
/* 177 */     String notepadType = "";
/* 178 */     notepadType = context.getRequestValue("notepadType", notepadType);
/* 179 */     String lastCommand = context.getParameter("lastCommand");
/*     */ 
/*     */ 
/*     */     
/* 183 */     Boolean notepadVisible = new Boolean(true);
/*     */     
/* 185 */     if (context.getSessionValue(notepadType) != null) {
/* 186 */       notepadVisible = (Boolean)context.getSessionValue(notepadType);
/*     */     }
/* 188 */     if (notepadVisible.booleanValue()) {
/* 189 */       notepadVisible = new Boolean(false);
/*     */     } else {
/* 191 */       notepadVisible = new Boolean(true);
/*     */     } 
/* 193 */     context.putSessionValue(notepadType, notepadVisible);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return dispatcher.redispatch(context, lastCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean first(Dispatcher dispatcher, Context context, String command) {
/* 210 */     int notepadType = -1;
/* 211 */     notepadType = context.getIntRequestValue("notepadType", notepadType);
/* 212 */     String lastCommand = context.getParameter("lastCommand");
/*     */     
/* 214 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
/* 215 */     notepad.firstPage();
/*     */     
/* 217 */     return dispatcher.redispatch(context, lastCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean last(Dispatcher dispatcher, Context context, String command) {
/* 225 */     int notepadType = -1;
/* 226 */     notepadType = context.getIntRequestValue("notepadType", notepadType);
/* 227 */     String lastCommand = context.getParameter("lastCommand");
/* 228 */     User user = (User)context.getSessionValue("user");
/*     */     
/* 230 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(notepadType, context);
/*     */ 
/*     */     
/* 233 */     if (notepad.getAllContents() != null && notepad.allContents.size() < notepad.getTotalRecords())
/*     */     {
/* 235 */       if (notepadType == 0) {
/*     */         
/* 237 */         notepad.setAllContents(null);
/* 238 */         notepad.setMaxRecords(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (notepadType == 0)
/* 245 */       setSearchInitiated(context); 
/* 246 */     notepad.lastPage();
/*     */     
/* 248 */     return dispatcher.redispatch(context, lastCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSearchInitiated(Context context) {
/* 258 */     User user = (User)context.getSessionValue("user");
/* 259 */     if (user != null)
/* 260 */       user.SS_searchInitiated = true; 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NotepadHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */