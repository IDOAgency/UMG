/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.Acl;
/*     */ import com.universal.milestone.CompanyAcl;
/*     */ import com.universal.milestone.EnvironmentAcl;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Acl
/*     */   extends DataEntity
/*     */ {
/*     */   protected Vector objCompanyAcl;
/*     */   protected boolean mbAccessSelection = false;
/*     */   protected boolean mbAccessSchedule = false;
/*     */   protected boolean mbAccessManufacturing = false;
/*     */   protected boolean mbAccessPfmForm = false;
/*     */   protected boolean mbAccessBomForm = false;
/*     */   protected boolean mbAccessReport = false;
/*     */   protected boolean mbAccessTemplate = false;
/*     */   protected boolean mbAccessTask = false;
/*     */   protected boolean mbAccessDayType = false;
/*     */   protected boolean mbAccessUser = false;
/*     */   protected boolean mbAccessFamily = false;
/*     */   protected boolean mbAccessCompany = false;
/*     */   protected boolean mbAccessDivision = false;
/*     */   protected boolean mbAccessLabel = false;
/*     */   protected boolean mbAccessTable = false;
/*     */   protected boolean mbAccessParameter = false;
/*     */   protected boolean mbAccessAuditTrail = false;
/*     */   protected boolean mbAccessReportConfig = false;
/*     */   protected boolean mbAccessPriceCode = false;
/*     */   protected Vector objEnvironmentAcl;
/*     */   protected boolean mbAccessEnvironment = false;
/*  61 */   protected Hashtable mbFamilyAccessHash = null;
/*     */ 
/*     */   
/*  64 */   protected Hashtable objCompanyAclHash = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Acl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Acl(int pUserID) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Acl(String pUserName) {
/*  85 */     this.objCompanyAcl = new Vector();
/*  86 */     this.objCompanyAcl.addElement(new CompanyAcl(100, 100));
/*  87 */     this.mbAccessSelection = true;
/*  88 */     this.mbAccessManufacturing = true;
/*  89 */     this.mbAccessPfmForm = true;
/*  90 */     this.mbAccessBomForm = true;
/*  91 */     this.mbAccessReport = true;
/*  92 */     this.mbAccessTemplate = true;
/*  93 */     this.mbAccessTask = true;
/*  94 */     this.mbAccessDayType = true;
/*  95 */     this.mbAccessUser = true;
/*  96 */     this.mbAccessFamily = true;
/*  97 */     this.mbAccessCompany = true;
/*  98 */     this.mbAccessDivision = true;
/*  99 */     this.mbAccessLabel = true;
/* 100 */     this.mbAccessTable = true;
/* 101 */     this.mbAccessParameter = true;
/* 102 */     this.mbAccessAuditTrail = true;
/* 103 */     this.mbAccessReportConfig = true;
/* 104 */     this.mbAccessPriceCode = true;
/*     */     
/* 106 */     this.objEnvironmentAcl = new Vector();
/* 107 */     this.objEnvironmentAcl.addElement(new EnvironmentAcl(100, 100));
/* 108 */     this.mbAccessEnvironment = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public Vector getCompanyAcl() { return this.objCompanyAcl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public void setCompanyAcl(Vector pCompanyAcl) { this.objCompanyAcl = pCompanyAcl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public Hashtable getCompanyAclHash() { return this.objCompanyAclHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public void setCompanyAcl(Hashtable pCompanyAclHash) { this.objCompanyAclHash = pCompanyAclHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public boolean getAccessSelection() { return this.mbAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public void setAccessSelection(boolean pAccessSelection) { this.mbAccessSelection = pAccessSelection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public boolean getAccessManufacturing() { return this.mbAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public void setAccessManufacturing(boolean pAccessManufacturing) { this.mbAccessManufacturing = pAccessManufacturing; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public boolean getAccessPfmForm() { return this.mbAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public void setAccessPfmForm(boolean pAccessPfmForm) { this.mbAccessPfmForm = pAccessPfmForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public boolean getAccessBomForm() { return this.mbAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public void setAccessBomForm(boolean pAccessBomForm) { this.mbAccessBomForm = pAccessBomForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public boolean getAccessReport() { return this.mbAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   public void setAccessReport(boolean pAccessReport) { this.mbAccessReport = pAccessReport; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   public boolean getAccessTemplate() { return this.mbAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public void setAccessTemplate(boolean pAccessTemplate) { this.mbAccessTemplate = pAccessTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public boolean getAccessTask() { return this.mbAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public void setAccessTask(boolean pAccessTask) { this.mbAccessTask = pAccessTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public boolean getAccessDayType() { return this.mbAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public void setAccessDayType(boolean pAccessDayType) { this.mbAccessDayType = pAccessDayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public boolean getAccessUser() { return this.mbAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   public void setAccessUser(boolean pAccessUser) { this.mbAccessUser = pAccessUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public boolean getAccessFamily() { return this.mbAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 301 */   public void setAccessFamily(boolean pAccessFamily) { this.mbAccessFamily = pAccessFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 309 */   public boolean getAccessCompany() { return this.mbAccessCompany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public void setAccessCompany(boolean pAccessCompany) { this.mbAccessCompany = pAccessCompany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public boolean getAccessDivision() { return this.mbAccessDivision; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public void setAccessDivision(boolean pAccessDivision) { this.mbAccessDivision = pAccessDivision; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 341 */   public boolean getAccessLabel() { return this.mbAccessLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 349 */   public void setAccessLabel(boolean pAccessLabel) { this.mbAccessLabel = pAccessLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 357 */   public boolean getAccessTable() { return this.mbAccessTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   public void setAccessTable(boolean pAccessTable) { this.mbAccessTable = pAccessTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 373 */   public boolean getAccessParameter() { return this.mbAccessParameter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 381 */   public void setAccessParameter(boolean pAccessParameter) { this.mbAccessParameter = pAccessParameter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   public boolean getAccessAuditTrail() { return this.mbAccessAuditTrail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 397 */   public void setAccessAuditTrail(boolean pAccessAuditTrail) { this.mbAccessAuditTrail = pAccessAuditTrail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 405 */   public boolean getAccessReportConfig() { return this.mbAccessReportConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 413 */   public void setAccessReportConfig(boolean pAccessReportConfig) { this.mbAccessReportConfig = pAccessReportConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 421 */   public boolean getAccessPriceCode() { return this.mbAccessPriceCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 429 */   public void setAccessPriceCode(boolean pAccessPriceCode) { this.mbAccessPriceCode = pAccessPriceCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 437 */   public boolean getAccessSchedule() { return this.mbAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 445 */   public void setAccessSchedule(boolean pAccessSchedule) { this.mbAccessSchedule = pAccessSchedule; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   public boolean getAccessLabels() { return !(!this.mbAccessSchedule && !this.mbAccessSelection && !this.mbAccessManufacturing && !this.mbAccessPfmForm && !this.mbAccessBomForm && !this.mbAccessReport); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 462 */   public boolean getAccessAdmin() { return !(!this.mbAccessTemplate && !this.mbAccessTask && !this.mbAccessDayType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   public boolean getAccessSecurity() { return !(!this.mbAccessUser && !this.mbAccessFamily && !this.mbAccessCompany && !this.mbAccessDivision && !this.mbAccessLabel); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 478 */   public boolean getAccessUtilities() { return !(!this.mbAccessTable && !this.mbAccessParameter && !this.mbAccessReportConfig && !this.mbAccessPriceCode); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 487 */   public Vector getEnvironmentAcl() { return this.objEnvironmentAcl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 496 */   public void setEnvironmentAcl(Vector pEnvironmentAcl) { this.objEnvironmentAcl = pEnvironmentAcl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 505 */   public boolean getAccessEnvironment() { return this.mbAccessEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 514 */   public void setAccessEnvironment(boolean pAccessEnvironment) { this.mbAccessEnvironment = pAccessEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 522 */   public void setFamilyAccessHash(Hashtable familyAccessHash) { this.mbFamilyAccessHash = familyAccessHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 531 */   public Hashtable getFamilyAccessHash() { return this.mbFamilyAccessHash; }
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
/*     */   public void setScreenPermissionsHash() {
/* 549 */     Vector companyAcls = getCompanyAcl();
/* 550 */     if (companyAcls != null) {
/*     */       
/* 552 */       this.objCompanyAclHash = new Hashtable();
/*     */ 
/*     */       
/* 555 */       for (int i = 0; i < companyAcls.size(); i++) {
/*     */         
/* 557 */         CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/*     */         
/* 559 */         int aclCompanyId = companyAcl.getCompanyId();
/*     */ 
/*     */         
/* 562 */         if (!this.objCompanyAclHash.containsKey(String.valueOf(aclCompanyId)))
/*     */         {
/* 564 */           this.objCompanyAclHash.put(String.valueOf(aclCompanyId), companyAcl);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 578 */     return "\n--ACL--\nSelection: " + String.valueOf(getAccessSelection()) + "\n" + 
/* 579 */       "Schedule:" + String.valueOf(getAccessSchedule()) + "\n" + 
/* 580 */       "Manufacturing " + String.valueOf(getAccessManufacturing()) + "\n" + 
/* 581 */       "PfmForm " + String.valueOf(getAccessPfmForm()) + "\n" + 
/* 582 */       "BomForm " + String.valueOf(getAccessBomForm()) + "\n" + 
/* 583 */       "Report " + String.valueOf(getAccessReport()) + "\n" + 
/* 584 */       "Template " + String.valueOf(getAccessTemplate()) + "\n" + 
/* 585 */       "Task " + String.valueOf(getAccessTask()) + "\n" + 
/* 586 */       "DayType " + String.valueOf(getAccessDayType()) + "\n" + 
/* 587 */       "User " + String.valueOf(getAccessUser()) + "\n" + 
/* 588 */       "Family " + String.valueOf(getAccessFamily()) + "\n" + 
/* 589 */       "Enviroment " + String.valueOf(getAccessEnvironment()) + "\n" + 
/* 590 */       "Company " + String.valueOf(getAccessCompany()) + "\n" + 
/* 591 */       "Division " + String.valueOf(getAccessDivision()) + "\n" + 
/* 592 */       "Label " + String.valueOf(getAccessLabel()) + "\n" + 
/* 593 */       "Table " + String.valueOf(getAccessTable()) + "\n" + 
/* 594 */       "Parameter " + String.valueOf(getAccessParameter()) + "\n" + 
/* 595 */       "AuditTrail " + String.valueOf(getAccessAuditTrail()) + "\n" + 
/* 596 */       "ReportConfig " + String.valueOf(getAccessReportConfig()) + "\n" + 
/* 597 */       "PriceCode " + String.valueOf(getAccessPriceCode()) + "\n--end acl--\n";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Acl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */