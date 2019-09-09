/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CompanyAcl;
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
/*     */ public class CompanyAcl
/*     */   extends Company
/*     */ {
/*     */   protected String companyName;
/*     */   protected int companyId;
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
/*     */   protected CompanyAcl() {}
/*     */   
/*     */   protected CompanyAcl(int pUserID, int pCompanyID) {}
/*     */   
/*  77 */   public int getAccessSelection() { return this.mbAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public void setAccessSelection(int pAccessSelection) { this.mbAccessSelection = pAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public int getAccessSchedule() { return this.mbAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public void setAccessSchedule(int pAccessSchedule) { this.mbAccessSchedule = pAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public int getAccessManufacturing() { return this.mbAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public void setAccessManufacturing(int pAccessManufacturing) { this.mbAccessManufacturing = pAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public int getAccessPfmForm() { return this.mbAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public void setAccessPfmForm(int pAccessPfmForm) { this.mbAccessPfmForm = pAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public int getAccessBomForm() { return this.mbAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public void setAccessBomForm(int pAccessBomForm) { this.mbAccessBomForm = pAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public int getAccessReport() { return this.mbAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public void setAccessReport(int pAccessReport) { this.mbAccessReport = pAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public int getAccessTemplate() { return this.mbAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void setAccessTemplate(int pAccessTemplate) { this.mbAccessTemplate = pAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public int getAccessTask() { return this.mbAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public void setAccessTask(int pAccessTask) { this.mbAccessTask = pAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public int getAccessDayType() { return this.mbAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public void setAccessDayType(int pAccessDayType) { this.mbAccessDayType = pAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   public int getAccessUser() { return this.mbAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   public void setAccessUser(int pAccessUser) { this.mbAccessUser = pAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public int getAccessFamily() { return this.mbAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public void setAccessFamily(int pAccessFamily) { this.mbAccessFamily = pAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public int getAccessEnvironment() { return this.mbAccessEnvironment; }
/*     */ 
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
/* 400 */   public String getCompanyName() { return this.companyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 408 */   public void setCompanyName(String companyName) { this.companyName = companyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 416 */   public int getCompanyId() { return this.companyId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   public void setCompanyId(int companyId) { this.companyId = companyId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 432 */     return "--CompanyAcl--\nCompany name: " + 
/* 433 */       getCompanyName() + "\n" + 
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
/* 445 */       "Company " + String.valueOf(getAccessCompany()) + "\n" + 
/* 446 */       "Division " + String.valueOf(getAccessDivision()) + "\n" + 
/* 447 */       "Label " + String.valueOf(getAccessLabel()) + "\n" + 
/* 448 */       "Table " + String.valueOf(getAccessTable()) + "\n" + 
/* 449 */       "Parameter " + String.valueOf(getAccessParameter()) + "\n" + 
/* 450 */       "AuditTrail " + String.valueOf(getAccessAuditTrail()) + "\n" + 
/* 451 */       "ReportConfig " + String.valueOf(getAccessReportConfig()) + "\n" + 
/* 452 */       "PriceCode " + String.valueOf(getAccessPriceCode()) + "\n--end company acl--\n";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CompanyAcl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */