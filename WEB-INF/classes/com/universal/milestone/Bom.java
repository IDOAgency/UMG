package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Bom;
import com.universal.milestone.BomCassetteDetail;
import com.universal.milestone.BomDVDDetail;
import com.universal.milestone.BomDiskDetail;
import com.universal.milestone.BomVinylDetail;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.Selection;
import java.util.Calendar;

public class Bom extends MilestoneDataEntity implements Cloneable {
  protected int bomId = -1;
  
  protected int releaseId = -1;
  
  protected Selection selection = null;
  
  protected String format = "";
  
  protected Calendar date = null;
  
  protected String type = "";
  
  protected String changeNumber = "";
  
  protected String submitter = "";
  
  protected String email = "";
  
  protected String phone = "";
  
  protected String comments = "";
  
  protected int labelId = -1;
  
  protected String releasingCompanyId = "";
  
  protected boolean isRetail = false;
  
  protected String selectionNumber = "";
  
  protected Calendar streetDateOnBom = null;
  
  protected int unitsPerKG = 0;
  
  protected String runTime = "";
  
  protected String configuration = "";
  
  protected boolean hasSpineSticker = true;
  
  protected boolean useShrinkWrap = true;
  
  protected String specialInstructions = "";
  
  protected int enteredBy = -1;
  
  protected int modifiedBy = -1;
  
  protected Calendar modifiedOn = null;
  
  protected byte[] recordVersionId;
  
  protected int releaseLabelId = -1;
  
  protected long lastUpdatedCheck = -1L;
  
  protected String printOption = "Draft";
  
  protected BomCassetteDetail bomCassetteDetail = null;
  
  protected BomDiskDetail bomDiskDetail = null;
  
  protected BomVinylDetail bomVinylDetail = null;
  
  protected BomDVDDetail bomDVDDetail = null;
  
  protected String title = "";
  
  protected String artist = "";
  
  protected String status = "";
  
  protected String upc;
  
  public String getTableName() { return "Bom_Header"; }
  
  public int getIdentity() { return getBomId(); }
  
  public int getBomId() { return this.bomId; }
  
  public void setBomId(int value) { this.bomId = value; }
  
  public int getReleaseId() { return this.releaseId; }
  
  public void setReleaseId(int value) { this.releaseId = value; }
  
  public Selection getSelection() { return this.selection; }
  
  public void setSelection(Selection value) { this.selection = value; }
  
  public String getFormat() { return this.format; }
  
  public void setFormat(String value) {
    auditCheck("format", this.format, value);
    this.format = value;
  }
  
  public Calendar getDate() { return this.date; }
  
  public void setDate(Calendar value) {
    auditCheck("date", this.date, value);
    this.date = value;
  }
  
  public String getType() { return this.type; }
  
  public void setType(String value) {
    auditCheck("type", this.type, value);
    this.type = value;
  }
  
  public String getChangeNumber() { return this.changeNumber; }
  
  public void setChangeNumber(String value) {
    auditCheck("change_number", this.changeNumber, value);
    this.changeNumber = value;
  }
  
  public void setPrintOption(String printOption) { this.printOption = printOption; }
  
  public String getPrintOption() { return this.printOption; }
  
  public String getSubmitter() { return this.submitter; }
  
  public void setSubmitter(String value) {
    auditCheck("submitted", this.submitter, value);
    this.submitter = value;
  }
  
  public String getEmail() { return this.email; }
  
  public void setEmail(String value) {
    auditCheck("email", this.email, value);
    this.email = value;
  }
  
  public String getPhone() { return this.phone; }
  
  public void setPhone(String value) {
    auditCheck("phone", this.phone, value);
    this.phone = value;
  }
  
  public String getComments() { return this.comments; }
  
  public void setComments(String value) {
    auditCheck("comments", this.comments, value);
    this.comments = value;
  }
  
  public int getLabelId() { return this.labelId; }
  
  public void setLabelId(int value) {
    auditCheck("label_id", this.labelId, value);
    this.labelId = value;
  }
  
  public String getReleasingCompanyId() { return this.releasingCompanyId; }
  
  public void setReleasingCompanyId(String value) {
    auditCheck("release_comp_id", this.releasingCompanyId, value);
    this.releasingCompanyId = value;
  }
  
  public boolean isRetail() { return this.isRetail; }
  
  public void setIsRetail(boolean value) { // Byte code:
    //   0: aload_0
    //   1: ldc 'retail_indicator'
    //   3: new java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getfield isRetail : Z
    //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   17: invokevirtual toString : ()Ljava/lang/String;
    //   20: new java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: iload_1
    //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_0
    //   38: iload_1
    //   39: putfield isRetail : Z
    //   42: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #311	-> 0
    //   #312	-> 37
    //   #313	-> 42
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	43	0	this	Lcom/universal/milestone/Bom;
    //   0	43	1	value	Z }
  
  public String getSelectionNumber() { return this.selectionNumber; }
  
  public void setSelectionNumber(String value) {
    auditCheck("selection_number", this.selectionNumber, value);
    this.selectionNumber = value;
  }
  
  public Calendar getStreetDateOnBom() { return this.streetDateOnBom; }
  
  public void setStreetDateOnBom(Calendar value) {
    auditCheck("due_date", this.streetDateOnBom, value);
    this.streetDateOnBom = value;
  }
  
  public int getUnitsPerKG() { return this.unitsPerKG; }
  
  public void setUnitsPerKG(int value) {
    auditCheck("units_per_pkg", this.unitsPerKG, value);
    this.unitsPerKG = value;
  }
  
  public String getRunTime() { return this.runTime; }
  
  public void setRunTime(String value) {
    auditCheck("run_time", this.runTime, value);
    this.runTime = value;
  }
  
  public String getConfiguration() { return this.configuration; }
  
  public void setConfiguration(String value) {
    auditCheck("configuration", this.configuration, value);
    this.configuration = value;
  }
  
  public boolean hasSpineSticker() { return this.hasSpineSticker; }
  
  public void setHasSpineSticker(boolean value) { // Byte code:
    //   0: aload_0
    //   1: ldc 'spine_sticker_indicator'
    //   3: new java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getfield hasSpineSticker : Z
    //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   17: invokevirtual toString : ()Ljava/lang/String;
    //   20: new java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: iload_1
    //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_0
    //   38: iload_1
    //   39: putfield hasSpineSticker : Z
    //   42: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #377	-> 0
    //   #378	-> 37
    //   #379	-> 42
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	43	0	this	Lcom/universal/milestone/Bom;
    //   0	43	1	value	Z }
  
  public boolean useShrinkWrap() { return this.useShrinkWrap; }
  
  public void setUseShrinkWrap(boolean value) { // Byte code:
    //   0: aload_0
    //   1: ldc 'shrink_wrap_indicator'
    //   3: new java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getfield useShrinkWrap : Z
    //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   17: invokevirtual toString : ()Ljava/lang/String;
    //   20: new java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: iload_1
    //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_0
    //   38: iload_1
    //   39: putfield useShrinkWrap : Z
    //   42: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #388	-> 0
    //   #389	-> 37
    //   #390	-> 42
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	43	0	this	Lcom/universal/milestone/Bom;
    //   0	43	1	value	Z }
  
  public String getSpecialInstructions() { return this.specialInstructions; }
  
  public void setSpecialInstructions(String value) {
    auditCheck("special_instructions", this.specialInstructions, value);
    this.specialInstructions = value;
  }
  
  public int getEnteredBy() { return this.enteredBy; }
  
  public void setEnteredBy(int value) { this.enteredBy = value; }
  
  public int getModifiedBy() { return this.modifiedBy; }
  
  public void setModifiedBy(int value) { this.modifiedBy = value; }
  
  public Calendar getModifiedOn() { return this.modifiedOn; }
  
  public void setModifiedOn(Calendar value) { this.modifiedOn = value; }
  
  public byte[] getRecordVersionId() { return this.recordVersionId; }
  
  public void setRecordVersionId(byte[] value) { this.recordVersionId = value; }
  
  public int getReleaseLabelId() { return this.releaseLabelId; }
  
  public void setReleaseLabelId(int value) { this.releaseLabelId = value; }
  
  public BomCassetteDetail getBomCassetteDetail() { return this.bomCassetteDetail; }
  
  public void setBomCassetteDetail(BomCassetteDetail bomCassetteDetail) { this.bomCassetteDetail = bomCassetteDetail; }
  
  public BomDiskDetail getBomDiskDetail() { return this.bomDiskDetail; }
  
  public void setBomDiskDetail(BomDiskDetail bomDiskDetail) { this.bomDiskDetail = bomDiskDetail; }
  
  public BomVinylDetail getBomVinylDetail() { return this.bomVinylDetail; }
  
  public void setBomVinylDetail(BomVinylDetail bomVinylDetail) { this.bomVinylDetail = bomVinylDetail; }
  
  public BomDVDDetail getBomDVDDetail() { return this.bomDVDDetail; }
  
  public void setBomDVDDetail(BomDVDDetail bomDVDDetail) { this.bomDVDDetail = bomDVDDetail; }
  
  public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
  
  public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public String getArtist() { return this.artist; }
  
  public void setArtist(String value) { this.artist = value; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String value) { this.title = value; }
  
  public String getStatus() { return this.status; }
  
  public void setStatus(String value) { this.status = value; }
  
  public String getUpc() { return this.upc; }
  
  public void setUpc(String upc) { this.upc = upc; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Bom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */