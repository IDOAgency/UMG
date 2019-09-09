/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*     */ import java.util.ArrayList;
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
/*     */ public class MilestoneFormDropDownMenu
/*     */   extends FormDropDownMenu
/*     */ {
/*     */   protected boolean multiple = false;
/*  37 */   protected ArrayList multSelections = new ArrayList();
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
/*     */   public MilestoneFormDropDownMenu(String name, String selection, String[] values, String[] menuText, boolean required) {
/*  56 */     super(name, selection, required);
/*  57 */     setMenuTextList(menuText);
/*  58 */     setValueList(values);
/*  59 */     this.multSelections.add(selection);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MilestoneFormDropDownMenu(FormDropDownMenu formDropDownMenu) {
/*  74 */     super(formDropDownMenu.getName(), formDropDownMenu.getStringValue(), formDropDownMenu.isRequired());
/*  75 */     setMenuTextList(formDropDownMenu.getMenuTextList());
/*  76 */     setValueList(formDropDownMenu.getValueList());
/*  77 */     this.multSelections.add(formDropDownMenu.getStringValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MilestoneFormDropDownMenu(String name, String selection, Integer[] values, String[] menuText, boolean required) {
/*  95 */     super(name, selection, required);
/*  96 */     setMenuTextList(menuText);
/*  97 */     setValueList(values);
/*  98 */     this.multSelections.add(selection);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MilestoneFormDropDownMenu(String name, String selection, String values, String menuText, boolean required) {
/* 115 */     super(name, selection, required);
/* 116 */     setMenuTextList(menuText);
/* 117 */     setValueList(values);
/* 118 */     this.multSelections.add(selection);
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
/*     */   public MilestoneFormDropDownMenu(String name, String selection, String[] values, boolean required) {
/* 130 */     this(name, selection, values, values, required);
/* 131 */     this.multSelections.add(selection);
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
/*     */   
/*     */   public MilestoneFormDropDownMenu(String name, String selection, String values, boolean required) {
/* 144 */     this(name, selection, values, values, required);
/* 145 */     this.multSelections.add(selection);
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
/*     */   
/* 157 */   public MilestoneFormDropDownMenu(String name, String values, boolean required) { this(name, "", values, required); }
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
/* 168 */   public MilestoneFormDropDownMenu(String name, String[] values, boolean required) { this(name, "", values, required); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public MilestoneFormDropDownMenu(String name, String values) { this(name, "", values, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public MilestoneFormDropDownMenu(String name) { this(name, "", "", false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public void setMultiple(boolean multiple) { this.multiple = multiple; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public boolean getMultiple() { return this.multiple; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public ArrayList getMultipleSelections() { return this.multSelections; }
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
/*     */   public void setValue(Context context) {
/* 221 */     if (this.multiple) {
/* 222 */       String[] multSels = (String[])null;
/*     */       try {
/* 224 */         multSels = context.getRequest().getParameterValues(getName());
/* 225 */       } catch (IllegalArgumentException iaexc) {
/*     */         
/* 227 */         System.out.println("Illegal argument exception: " + this.name);
/*     */       } 
/* 229 */       if (multSels != null) {
/* 230 */         this.multSelections = new ArrayList();
/* 231 */         for (int i = 0; i < multSels.length; i++)
/*     */         {
/*     */           
/* 234 */           this.multSelections.add(multSels[i]);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 239 */       setValue(context.getRequestValue(getName(), this.selection));
/* 240 */       this.multSelections = new ArrayList();
/* 241 */       this.multSelections.add(context.getRequestValue(getName(), this.selection));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public ArrayList getStringValues() { return this.multSelections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String render() {
/* 260 */     String action = " ";
/* 261 */     if (getAction() != null)
/*     */     {
/* 263 */       action = " onChange=\"" + getAction() + "\" ";
/*     */     }
/*     */     
/* 266 */     StringBuffer buffer = new StringBuffer(100);
/*     */     
/* 268 */     buffer.append("<select name=\"");
/* 269 */     buffer.append(getName());
/* 270 */     buffer.append('"');
/* 271 */     buffer.append(getTabIndex());
/* 272 */     buffer.append(getFormEvents());
/* 273 */     buffer.append(getEnabledString());
/* 274 */     buffer.append(getId());
/* 275 */     buffer.append(getClassName());
/* 276 */     buffer.append(action);
/*     */ 
/*     */     
/* 279 */     if (this.multiple) {
/* 280 */       buffer.append("MULTIPLE");
/*     */     }
/* 282 */     buffer.append('>');
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     for (int i = 0; i < this.values.size(); i++) {
/*     */       
/* 289 */       String currentElement = (String)this.values.elementAt(i);
/*     */       
/* 291 */       buffer.append("<option value=\"");
/* 292 */       buffer.append(currentElement);
/* 293 */       buffer.append('"');
/*     */ 
/*     */       
/* 296 */       if (this.multiple && this.multSelections != null) {
/* 297 */         for (int m = 0; m < this.multSelections.size(); m++) {
/* 298 */           if (currentElement.equals((String)this.multSelections.get(m))) {
/* 299 */             buffer.append(" selected");
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 304 */       } else if (currentElement.equals(getStringValue())) {
/*     */         
/* 306 */         buffer.append(" selected");
/*     */       } 
/*     */ 
/*     */       
/* 310 */       buffer.append('>');
/* 311 */       buffer.append(this.menuText.elementAt(i));
/*     */     } 
/*     */     
/* 314 */     buffer.append("</select>");
/*     */     
/* 316 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public int getTabIndexInt() { return this.tabIndex; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public void setMultSelections(ArrayList multSelections) { this.multSelections = multSelections; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   public ArrayList getMultSelections() { return this.multSelections; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneFormDropDownMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */