/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.EnvironmentAcl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnvironmentAcl
/*     */   extends Environment
/*     */ {
/*     */   protected String envName;
/*     */   protected int envId;
/*     */   protected int mbAccessSelection;
/*     */   protected int mbAccessSchedule;
/*     */   protected int mbAccessManufacturing;
/*     */   protected int mbAccessPfmForm;
/*     */   protected int mbAccessBomForm;
/*     */   protected int mbAccessReport;
/*     */   protected int mbAccessTemplate;
/*     */   protected int mbAccessTask;
/*     */   protected int mbAccessDayType;
/*     */   protected int mbAccessUser;
/*     */   protected int mbAccessFamily;
/*     */   protected int mbAccessEnvironment;
/*     */   protected int mbAccessCompany;
/*     */   protected int mbAccessDivision;
/*     */   protected int mbAccessLabel;
/*     */   protected int mbAccessTable;
/*     */   protected int mbAccessParameter;
/*     */   protected int mbAccessAuditTrail;
/*     */   protected int mbAccessReportConfig;
/*     */   protected int mbAccessPriceCode;
/*     */   
/*     */   protected EnvironmentAcl() {}
/*     */   
/*     */   protected EnvironmentAcl(int pUserID, int pEnvID) {}
/*     */   
/*  79 */   public int getAccessSelection() { return this.mbAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public void setAccessSelection(int pAccessSelection) { this.mbAccessSelection = pAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public int getAccessSchedule() { return this.mbAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void setAccessSchedule(int pAccessSchedule) { this.mbAccessSchedule = pAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public int getAccessManufacturing() { return this.mbAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void setAccessManufacturing(int pAccessManufacturing) { this.mbAccessManufacturing = pAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public int getAccessPfmForm() { return this.mbAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public void setAccessPfmForm(int pAccessPfmForm) { this.mbAccessPfmForm = pAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public int getAccessBomForm() { return this.mbAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void setAccessBomForm(int pAccessBomForm) { this.mbAccessBomForm = pAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public int getAccessReport() { return this.mbAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public void setAccessReport(int pAccessReport) { this.mbAccessReport = pAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public int getAccessTemplate() { return this.mbAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public void setAccessTemplate(int pAccessTemplate) { this.mbAccessTemplate = pAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public int getAccessTask() { return this.mbAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public void setAccessTask(int pAccessTask) { this.mbAccessTask = pAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public int getAccessDayType() { return this.mbAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   public void setAccessDayType(int pAccessDayType) { this.mbAccessDayType = pAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public int getAccessUser() { return this.mbAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public void setAccessUser(int pAccessUser) { this.mbAccessUser = pAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public int getAccessFamily() { return this.mbAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public void setAccessFamily(int pAccessFamily) { this.mbAccessFamily = pAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public int getAccessEnvironment() { return this.mbAccessEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   public void setAccessEnvironment(int pAccessEnvironment) { this.mbAccessEnvironment = pAccessEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public int getAccessCompany() { return this.mbAccessCompany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 279 */   public void setAccessCompany(int pAccessCompany) { this.mbAccessCompany = pAccessCompany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   public int getAccessDivision() { return this.mbAccessDivision; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 295 */   public void setAccessDivision(int pAccessDivision) { this.mbAccessDivision = pAccessDivision; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public int getAccessLabel() { return this.mbAccessLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   public void setAccessLabel(int pAccessLabel) { this.mbAccessLabel = pAccessLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 319 */   public int getAccessTable() { return this.mbAccessTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   public void setAccessTable(int pAccessTable) { this.mbAccessTable = pAccessTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   public int getAccessParameter() { return this.mbAccessParameter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   public void setAccessParameter(int pAccessParameter) { this.mbAccessParameter = pAccessParameter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 351 */   public int getAccessAuditTrail() { return this.mbAccessAuditTrail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   public void setAccessAuditTrail(int pAccessAuditTrail) { this.mbAccessAuditTrail = pAccessAuditTrail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   public int getAccessReportConfig() { return this.mbAccessReportConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 375 */   public void setAccessReportConfig(int pAccessReportConfig) { this.mbAccessReportConfig = pAccessReportConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public int getAccessPriceCode() { return this.mbAccessPriceCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 391 */   public void setAccessPriceCode(int pAccessPriceCode) { this.mbAccessPriceCode = pAccessPriceCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 400 */   public String getEnvironmentName() { return this.envName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 408 */   public void setEnvironmentName(String EnvironmentName) { this.envName = EnvironmentName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 416 */   public int getEnvironmentId() { return this.envId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   public void setEnvironmentId(int envId) { this.envId = envId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 432 */     return "--EnvironmentAcl--\nEnvironment name: " + 
/* 433 */       getEnvironmentName() + "\n" + 
/* 434 */       "Selection: " + String.valueOf(getAccessSelection()) + "\n" + 
/* 435 */       "Schedule:" + String.valueOf(getAccessSchedule()) + "\n" + 
/* 436 */       "Manufacturing " + String.valueOf(getAccessManufacturing()) + "\n" + 
/* 437 */       "PfmForm " + String.valueOf(getAccessPfmForm()) + "\n" + 
/* 438 */       "BomForm " + String.valueOf(getAccessBomForm()) + "\n" + 
/* 439 */       "Report " + String.valueOf(getAccessReport()) + "\n" + 
/* 440 */       "Template " + String.valueOf(getAccessTemplate()) + "\n" + 
/* 441 */       "Task " + String.valueOf(getAccessTask()) + "\n" + 
/* 442 */       "DayType " + String.valueOf(getAccessDayType()) + "\n" + 
/* 443 */       "User " + String.valueOf(getAccessUser()) + "\n" + 
/* 444 */       "Family " + String.valueOf(getAccessFamily()) + "\n" + 
/* 445 */       "Environment " + String.valueOf(getAccessEnvironment()) + "\n" + 
/* 446 */       "Company " + String.valueOf(getAccessCompany()) + "\n" + 
/* 447 */       "Division " + String.valueOf(getAccessDivision()) + "\n" + 
/* 448 */       "Label " + String.valueOf(getAccessLabel()) + "\n" + 
/* 449 */       "Table " + String.valueOf(getAccessTable()) + "\n" + 
/* 450 */       "Parameter " + String.valueOf(getAccessParameter()) + "\n" + 
/* 451 */       "AuditTrail " + String.valueOf(getAccessAuditTrail()) + "\n" + 
/* 452 */       "ReportConfig " + String.valueOf(getAccessReportConfig()) + "\n" + 
/* 453 */       "PriceCode " + String.valueOf(getAccessPriceCode()) + "\n--end company acl--\n";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EnvironmentAcl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */