package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;

public class CompanyAcl extends Company {
  protected String companyName;
  
  protected int companyId;
  
  protected int mbAccessSelection;
  
  protected int mbAccessSchedule;
  
  protected int mbAccessManufacturing;
  
  protected int mbAccessPfmForm;
  
  protected int mbAccessBomForm;
  
  protected int mbAccessReport;
  
  protected int mbAccessTemplate;
  
  protected int mbAccessTask;
  
  protected int mbAccessDayType;
  
  protected int mbAccessUser;
  
  protected int mbAccessFamily;
  
  protected int mbAccessEnvironment;
  
  protected int mbAccessCompany;
  
  protected int mbAccessDivision;
  
  protected int mbAccessLabel;
  
  protected int mbAccessTable;
  
  protected int mbAccessParameter;
  
  protected int mbAccessAuditTrail;
  
  protected int mbAccessReportConfig;
  
  protected int mbAccessPriceCode;
  
  protected CompanyAcl() {}
  
  protected CompanyAcl(int pUserID, int pCompanyID) {}
  
  public int getAccessSelection() { return this.mbAccessSelection; }
  
  public void setAccessSelection(int pAccessSelection) { this.mbAccessSelection = pAccessSelection; }
  
  public int getAccessSchedule() { return this.mbAccessSchedule; }
  
  public void setAccessSchedule(int pAccessSchedule) { this.mbAccessSchedule = pAccessSchedule; }
  
  public int getAccessManufacturing() { return this.mbAccessManufacturing; }
  
  public void setAccessManufacturing(int pAccessManufacturing) { this.mbAccessManufacturing = pAccessManufacturing; }
  
  public int getAccessPfmForm() { return this.mbAccessPfmForm; }
  
  public void setAccessPfmForm(int pAccessPfmForm) { this.mbAccessPfmForm = pAccessPfmForm; }
  
  public int getAccessBomForm() { return this.mbAccessBomForm; }
  
  public void setAccessBomForm(int pAccessBomForm) { this.mbAccessBomForm = pAccessBomForm; }
  
  public int getAccessReport() { return this.mbAccessReport; }
  
  public void setAccessReport(int pAccessReport) { this.mbAccessReport = pAccessReport; }
  
  public int getAccessTemplate() { return this.mbAccessTemplate; }
  
  public void setAccessTemplate(int pAccessTemplate) { this.mbAccessTemplate = pAccessTemplate; }
  
  public int getAccessTask() { return this.mbAccessTask; }
  
  public void setAccessTask(int pAccessTask) { this.mbAccessTask = pAccessTask; }
  
  public int getAccessDayType() { return this.mbAccessDayType; }
  
  public void setAccessDayType(int pAccessDayType) { this.mbAccessDayType = pAccessDayType; }
  
  public int getAccessUser() { return this.mbAccessUser; }
  
  public void setAccessUser(int pAccessUser) { this.mbAccessUser = pAccessUser; }
  
  public int getAccessFamily() { return this.mbAccessFamily; }
  
  public void setAccessFamily(int pAccessFamily) { this.mbAccessFamily = pAccessFamily; }
  
  public int getAccessEnvironment() { return this.mbAccessEnvironment; }
  
  public void setAccessEnvironment(int pAccessEnvironment) { this.mbAccessEnvironment = pAccessEnvironment; }
  
  public int getAccessCompany() { return this.mbAccessCompany; }
  
  public void setAccessCompany(int pAccessCompany) { this.mbAccessCompany = pAccessCompany; }
  
  public int getAccessDivision() { return this.mbAccessDivision; }
  
  public void setAccessDivision(int pAccessDivision) { this.mbAccessDivision = pAccessDivision; }
  
  public int getAccessLabel() { return this.mbAccessLabel; }
  
  public void setAccessLabel(int pAccessLabel) { this.mbAccessLabel = pAccessLabel; }
  
  public int getAccessTable() { return this.mbAccessTable; }
  
  public void setAccessTable(int pAccessTable) { this.mbAccessTable = pAccessTable; }
  
  public int getAccessParameter() { return this.mbAccessParameter; }
  
  public void setAccessParameter(int pAccessParameter) { this.mbAccessParameter = pAccessParameter; }
  
  public int getAccessAuditTrail() { return this.mbAccessAuditTrail; }
  
  public void setAccessAuditTrail(int pAccessAuditTrail) { this.mbAccessAuditTrail = pAccessAuditTrail; }
  
  public int getAccessReportConfig() { return this.mbAccessReportConfig; }
  
  public void setAccessReportConfig(int pAccessReportConfig) { this.mbAccessReportConfig = pAccessReportConfig; }
  
  public int getAccessPriceCode() { return this.mbAccessPriceCode; }
  
  public void setAccessPriceCode(int pAccessPriceCode) { this.mbAccessPriceCode = pAccessPriceCode; }
  
  public String getCompanyName() { return this.companyName; }
  
  public void setCompanyName(String companyName) { this.companyName = companyName; }
  
  public int getCompanyId() { return this.companyId; }
  
  public void setCompanyId(int companyId) { this.companyId = companyId; }
  
  public String toString() {
    return "--CompanyAcl--\nCompany name: " + 
      getCompanyName() + "\n" + 
      "Selection: " + String.valueOf(getAccessSelection()) + "\n" + 
      "Schedule:" + String.valueOf(getAccessSchedule()) + "\n" + 
      "Manufacturing " + String.valueOf(getAccessManufacturing()) + "\n" + 
      "PfmForm " + String.valueOf(getAccessPfmForm()) + "\n" + 
      "BomForm " + String.valueOf(getAccessBomForm()) + "\n" + 
      "Report " + String.valueOf(getAccessReport()) + "\n" + 
      "Template " + String.valueOf(getAccessTemplate()) + "\n" + 
      "Task " + String.valueOf(getAccessTask()) + "\n" + 
      "DayType " + String.valueOf(getAccessDayType()) + "\n" + 
      "User " + String.valueOf(getAccessUser()) + "\n" + 
      "Family " + String.valueOf(getAccessFamily()) + "\n" + 
      "Company " + String.valueOf(getAccessCompany()) + "\n" + 
      "Division " + String.valueOf(getAccessDivision()) + "\n" + 
      "Label " + String.valueOf(getAccessLabel()) + "\n" + 
      "Table " + String.valueOf(getAccessTable()) + "\n" + 
      "Parameter " + String.valueOf(getAccessParameter()) + "\n" + 
      "AuditTrail " + String.valueOf(getAccessAuditTrail()) + "\n" + 
      "ReportConfig " + String.valueOf(getAccessReportConfig()) + "\n" + 
      "PriceCode " + String.valueOf(getAccessPriceCode()) + "\n--end company acl--\n";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CompanyAcl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */