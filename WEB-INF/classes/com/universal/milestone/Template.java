/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.NotepadContentObject;
/*     */ import com.universal.milestone.ProductCategory;
/*     */ import com.universal.milestone.ReleaseType;
/*     */ import com.universal.milestone.SelectionConfiguration;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
/*     */ import com.universal.milestone.Template;
/*     */ import com.universal.milestone.WeeksToRelease;
/*     */ import java.util.Calendar;
/*     */ import java.util.Vector;
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
/*     */ public class Template
/*     */   extends DataEntity
/*     */   implements Cloneable, NotepadContentObject, Comparable
/*     */ {
/*     */   protected int templateID;
/*     */   protected ProductCategory productCategory;
/*     */   protected SelectionConfiguration selectionConfig;
/*     */   protected SelectionSubConfiguration selectionSubConfig;
/*     */   protected int lastUpdatingUser;
/*     */   protected ReleaseType releaseType;
/*     */   protected String comments;
/*     */   protected String templateName;
/*     */   protected WeeksToRelease weeksToRelease;
/*     */   protected Family owner;
/*     */   protected Calendar lastUpdateDate;
/*     */   protected long lastUpdatedCk;
/*  54 */   protected Vector tasks = null;
/*     */ 
/*     */   
/*  57 */   protected int order = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int prodType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public int getTemplateID() { return this.templateID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getTempateName() { return this.templateName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setTemplateName(String templateName) { this.templateName = templateName; }
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
/*  99 */   public void setTemplateID(int templateID) { this.templateID = templateID; }
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
/* 110 */   public ProductCategory getProductCategory() { return this.productCategory; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public SelectionConfiguration getSelectionConfig() { return this.selectionConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public void setSelectionConfig(SelectionConfiguration selectionConfig) { this.selectionConfig = selectionConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public SelectionSubConfiguration getSelectionSubConfig() { return this.selectionSubConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public void setSelectionSubConfig(SelectionSubConfiguration selectionSubConfig) { this.selectionSubConfig = selectionSubConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   public WeeksToRelease getWeeksToRelease() { return this.weeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 211 */   public void setWeeksToRelease(WeeksToRelease weeksToRelease) { this.weeksToRelease = weeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   public Family getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public void setOwner(Family owner) { this.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public ReleaseType getReleaseType() { return this.releaseType; }
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
/* 250 */   public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   public String getComments() { return this.comments; }
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
/* 269 */   public void setComments(String comments) { this.comments = comments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 279 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
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
/* 290 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public Vector getTasks() { return this.tasks; }
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
/* 311 */   public void setTasks(Vector tasks) { this.tasks = tasks; }
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
/* 322 */   public String getNotepadContentObjectId() { return Integer.toString(this.templateID); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
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
/* 339 */   public int compareTo(Object template) throws ClassCastException { return compareByOrder(template); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int compareByOrder(Object template) throws ClassCastException {
/* 350 */     Integer thisTemplate = new Integer(this.order);
/*     */ 
/*     */     
/* 353 */     Integer thatTemplate = new Integer(((Template)template).order);
/*     */     
/* 355 */     return thisTemplate.compareTo(thatTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   public int getProdType() { return this.prodType; }
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
/* 376 */   public void setProdType(int prodType) { this.prodType = prodType; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Template.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */