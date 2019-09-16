package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Acl;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.EnvironmentAcl;
import java.util.Hashtable;
import java.util.Vector;

public class Acl extends DataEntity {
  protected Vector objCompanyAcl;
  
  protected boolean mbAccessSelection = false;
  
  protected boolean mbAccessSchedule = false;
  
  protected boolean mbAccessManufacturing = false;
  
  protected boolean mbAccessPfmForm = false;
  
  protected boolean mbAccessBomForm = false;
  
  protected boolean mbAccessReport = false;
  
  protected boolean mbAccessTemplate = false;
  
  protected boolean mbAccessTask = false;
  
  protected boolean mbAccessDayType = false;
  
  protected boolean mbAccessUser = false;
  
  protected boolean mbAccessFamily = false;
  
  protected boolean mbAccessCompany = false;
  
  protected boolean mbAccessDivision = false;
  
  protected boolean mbAccessLabel = false;
  
  protected boolean mbAccessTable = false;
  
  protected boolean mbAccessParameter = false;
  
  protected boolean mbAccessAuditTrail = false;
  
  protected boolean mbAccessReportConfig = false;
  
  protected boolean mbAccessPriceCode = false;
  
  protected Vector objEnvironmentAcl;
  
  protected boolean mbAccessEnvironment = false;
  
  protected Hashtable mbFamilyAccessHash = null;
  
  protected Hashtable objCompanyAclHash = null;
  
  public Acl() {}
  
  public Acl(int pUserID) {}
  
  public Acl(String pUserName) {
    this.objCompanyAcl = new Vector();
    this.objCompanyAcl.addElement(new CompanyAcl(100, 100));
    this.mbAccessSelection = true;
    this.mbAccessManufacturing = true;
    this.mbAccessPfmForm = true;
    this.mbAccessBomForm = true;
    this.mbAccessReport = true;
    this.mbAccessTemplate = true;
    this.mbAccessTask = true;
    this.mbAccessDayType = true;
    this.mbAccessUser = true;
    this.mbAccessFamily = true;
    this.mbAccessCompany = true;
    this.mbAccessDivision = true;
    this.mbAccessLabel = true;
    this.mbAccessTable = true;
    this.mbAccessParameter = true;
    this.mbAccessAuditTrail = true;
    this.mbAccessReportConfig = true;
    this.mbAccessPriceCode = true;
    this.objEnvironmentAcl = new Vector();
    this.objEnvironmentAcl.addElement(new EnvironmentAcl(100, 100));
    this.mbAccessEnvironment = true;
  }
  
  public Vector getCompanyAcl() { return this.objCompanyAcl; }
  
  public void setCompanyAcl(Vector pCompanyAcl) { this.objCompanyAcl = pCompanyAcl; }
  
  public Hashtable getCompanyAclHash() { return this.objCompanyAclHash; }
  
  public void setCompanyAcl(Hashtable pCompanyAclHash) { this.objCompanyAclHash = pCompanyAclHash; }
  
  public boolean getAccessSelection() { return this.mbAccessSelection; }
  
  public void setAccessSelection(boolean pAccessSelection) { this.mbAccessSelection = pAccessSelection; }
  
  public boolean getAccessManufacturing() { return this.mbAccessManufacturing; }
  
  public void setAccessManufacturing(boolean pAccessManufacturing) { this.mbAccessManufacturing = pAccessManufacturing; }
  
  public boolean getAccessPfmForm() { return this.mbAccessPfmForm; }
  
  public void setAccessPfmForm(boolean pAccessPfmForm) { this.mbAccessPfmForm = pAccessPfmForm; }
  
  public boolean getAccessBomForm() { return this.mbAccessBomForm; }
  
  public void setAccessBomForm(boolean pAccessBomForm) { this.mbAccessBomForm = pAccessBomForm; }
  
  public boolean getAccessReport() { return this.mbAccessReport; }
  
  public void setAccessReport(boolean pAccessReport) { this.mbAccessReport = pAccessReport; }
  
  public boolean getAccessTemplate() { return this.mbAccessTemplate; }
  
  public void setAccessTemplate(boolean pAccessTemplate) { this.mbAccessTemplate = pAccessTemplate; }
  
  public boolean getAccessTask() { return this.mbAccessTask; }
  
  public void setAccessTask(boolean pAccessTask) { this.mbAccessTask = pAccessTask; }
  
  public boolean getAccessDayType() { return this.mbAccessDayType; }
  
  public void setAccessDayType(boolean pAccessDayType) { this.mbAccessDayType = pAccessDayType; }
  
  public boolean getAccessUser() { return this.mbAccessUser; }
  
  public void setAccessUser(boolean pAccessUser) { this.mbAccessUser = pAccessUser; }
  
  public boolean getAccessFamily() { return this.mbAccessFamily; }
  
  public void setAccessFamily(boolean pAccessFamily) { this.mbAccessFamily = pAccessFamily; }
  
  public boolean getAccessCompany() { return this.mbAccessCompany; }
  
  public void setAccessCompany(boolean pAccessCompany) { this.mbAccessCompany = pAccessCompany; }
  
  public boolean getAccessDivision() { return this.mbAccessDivision; }
  
  public void setAccessDivision(boolean pAccessDivision) { this.mbAccessDivision = pAccessDivision; }
  
  public boolean getAccessLabel() { return this.mbAccessLabel; }
  
  public void setAccessLabel(boolean pAccessLabel) { this.mbAccessLabel = pAccessLabel; }
  
  public boolean getAccessTable() { return this.mbAccessTable; }
  
  public void setAccessTable(boolean pAccessTable) { this.mbAccessTable = pAccessTable; }
  
  public boolean getAccessParameter() { return this.mbAccessParameter; }
  
  public void setAccessParameter(boolean pAccessParameter) { this.mbAccessParameter = pAccessParameter; }
  
  public boolean getAccessAuditTrail() { return this.mbAccessAuditTrail; }
  
  public void setAccessAuditTrail(boolean pAccessAuditTrail) { this.mbAccessAuditTrail = pAccessAuditTrail; }
  
  public boolean getAccessReportConfig() { return this.mbAccessReportConfig; }
  
  public void setAccessReportConfig(boolean pAccessReportConfig) { this.mbAccessReportConfig = pAccessReportConfig; }
  
  public boolean getAccessPriceCode() { return this.mbAccessPriceCode; }
  
  public void setAccessPriceCode(boolean pAccessPriceCode) { this.mbAccessPriceCode = pAccessPriceCode; }
  
  public boolean getAccessSchedule() { return this.mbAccessSchedule; }
  
  public void setAccessSchedule(boolean pAccessSchedule) { this.mbAccessSchedule = pAccessSchedule; }
  
  public boolean getAccessLabels() { return !(!this.mbAccessSchedule && !this.mbAccessSelection && !this.mbAccessManufacturing && !this.mbAccessPfmForm && !this.mbAccessBomForm && !this.mbAccessReport); }
  
  public boolean getAccessAdmin() { return !(!this.mbAccessTemplate && !this.mbAccessTask && !this.mbAccessDayType); }
  
  public boolean getAccessSecurity() { return !(!this.mbAccessUser && !this.mbAccessFamily && !this.mbAccessCompany && !this.mbAccessDivision && !this.mbAccessLabel); }
  
  public boolean getAccessUtilities() { return !(!this.mbAccessTable && !this.mbAccessParameter && !this.mbAccessReportConfig && !this.mbAccessPriceCode); }
  
  public Vector getEnvironmentAcl() { return this.objEnvironmentAcl; }
  
  public void setEnvironmentAcl(Vector pEnvironmentAcl) { this.objEnvironmentAcl = pEnvironmentAcl; }
  
  public boolean getAccessEnvironment() { return this.mbAccessEnvironment; }
  
  public void setAccessEnvironment(boolean pAccessEnvironment) { this.mbAccessEnvironment = pAccessEnvironment; }
  
  public void setFamilyAccessHash(Hashtable familyAccessHash) { this.mbFamilyAccessHash = familyAccessHash; }
  
  public Hashtable getFamilyAccessHash() { return this.mbFamilyAccessHash; }
  
  public void setScreenPermissionsHash() {
    Vector companyAcls = getCompanyAcl();
    if (companyAcls != null) {
      this.objCompanyAclHash = new Hashtable();
      for (int i = 0; i < companyAcls.size(); i++) {
        CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
        int aclCompanyId = companyAcl.getCompanyId();
        if (!this.objCompanyAclHash.containsKey(String.valueOf(aclCompanyId)))
          this.objCompanyAclHash.put(String.valueOf(aclCompanyId), companyAcl); 
      } 
    } 
  }
  
  public String toString() {
    return "\n--ACL--\nSelection: " + String.valueOf(getAccessSelection()) + "\n" + 
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
      "Enviroment " + String.valueOf(getAccessEnvironment()) + "\n" + 
      "Company " + String.valueOf(getAccessCompany()) + "\n" + 
      "Division " + String.valueOf(getAccessDivision()) + "\n" + 
      "Label " + String.valueOf(getAccessLabel()) + "\n" + 
      "Table " + String.valueOf(getAccessTable()) + "\n" + 
      "Parameter " + String.valueOf(getAccessParameter()) + "\n" + 
      "AuditTrail " + String.valueOf(getAccessAuditTrail()) + "\n" + 
      "ReportConfig " + String.valueOf(getAccessReportConfig()) + "\n" + 
      "PriceCode " + String.valueOf(getAccessPriceCode()) + "\n--end acl--\n";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Acl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */