/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Bom;
/*     */ import com.universal.milestone.BomCassetteDetail;
/*     */ import com.universal.milestone.BomDVDDetail;
/*     */ import com.universal.milestone.BomDiskDetail;
/*     */ import com.universal.milestone.BomVinylDetail;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.Selection;
/*     */ import java.util.Calendar;
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
/*     */ public class Bom
/*     */   extends MilestoneDataEntity
/*     */   implements Cloneable
/*     */ {
/*  38 */   protected int bomId = -1;
/*  39 */   protected int releaseId = -1;
/*  40 */   protected Selection selection = null;
/*  41 */   protected String format = "";
/*  42 */   protected Calendar date = null;
/*  43 */   protected String type = "";
/*  44 */   protected String changeNumber = "";
/*  45 */   protected String submitter = "";
/*  46 */   protected String email = "";
/*  47 */   protected String phone = "";
/*  48 */   protected String comments = "";
/*  49 */   protected int labelId = -1;
/*  50 */   protected String releasingCompanyId = "";
/*     */   protected boolean isRetail = false;
/*  52 */   protected String selectionNumber = "";
/*  53 */   protected Calendar streetDateOnBom = null;
/*  54 */   protected int unitsPerKG = 0;
/*  55 */   protected String runTime = "";
/*  56 */   protected String configuration = "";
/*     */   protected boolean hasSpineSticker = true;
/*     */   protected boolean useShrinkWrap = true;
/*  59 */   protected String specialInstructions = "";
/*  60 */   protected int enteredBy = -1;
/*  61 */   protected int modifiedBy = -1;
/*  62 */   protected Calendar modifiedOn = null;
/*     */   protected byte[] recordVersionId;
/*  64 */   protected int releaseLabelId = -1;
/*  65 */   protected long lastUpdatedCheck = -1L;
/*  66 */   protected String printOption = "Draft";
/*     */ 
/*     */ 
/*     */   
/*  70 */   protected BomCassetteDetail bomCassetteDetail = null;
/*  71 */   protected BomDiskDetail bomDiskDetail = null;
/*  72 */   protected BomVinylDetail bomVinylDetail = null;
/*     */ 
/*     */   
/*  75 */   protected BomDVDDetail bomDVDDetail = null;
/*     */ 
/*     */   
/*  78 */   protected String title = "";
/*  79 */   protected String artist = "";
/*     */ 
/*     */   
/*  82 */   protected String status = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String upc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public String getTableName() { return "Bom_Header"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public int getIdentity() { return getBomId(); }
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
/* 121 */   public int getBomId() { return this.bomId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public void setBomId(int value) { this.bomId = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public int getReleaseId() { return this.releaseId; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public void setReleaseId(int value) { this.releaseId = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public Selection getSelection() { return this.selection; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void setSelection(Selection value) { this.selection = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public String getFormat() { return this.format; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormat(String value) {
/* 161 */     auditCheck("format", this.format, value);
/* 162 */     this.format = value;
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
/* 175 */   public Calendar getDate() { return this.date; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(Calendar value) {
/* 180 */     auditCheck("date", this.date, value);
/* 181 */     this.date = value;
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
/* 194 */   public String getType() { return this.type; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String value) {
/* 199 */     auditCheck("type", this.type, value);
/* 200 */     this.type = value;
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
/* 213 */   public String getChangeNumber() { return this.changeNumber; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChangeNumber(String value) {
/* 218 */     auditCheck("change_number", this.changeNumber, value);
/* 219 */     this.changeNumber = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public void setPrintOption(String printOption) { this.printOption = printOption; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   public String getPrintOption() { return this.printOption; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public String getSubmitter() { return this.submitter; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubmitter(String value) {
/* 245 */     auditCheck("submitted", this.submitter, value);
/* 246 */     this.submitter = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 251 */   public String getEmail() { return this.email; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmail(String value) {
/* 256 */     auditCheck("email", this.email, value);
/* 257 */     this.email = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 262 */   public String getPhone() { return this.phone; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPhone(String value) {
/* 267 */     auditCheck("phone", this.phone, value);
/* 268 */     this.phone = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 273 */   public String getComments() { return this.comments; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComments(String value) {
/* 278 */     auditCheck("comments", this.comments, value);
/* 279 */     this.comments = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 284 */   public int getLabelId() { return this.labelId; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabelId(int value) {
/* 289 */     auditCheck("label_id", this.labelId, value);
/* 290 */     this.labelId = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 295 */   public String getReleasingCompanyId() { return this.releasingCompanyId; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReleasingCompanyId(String value) {
/* 300 */     auditCheck("release_comp_id", this.releasingCompanyId, value);
/* 301 */     this.releasingCompanyId = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 306 */   public boolean isRetail() { return this.isRetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsRetail(boolean value) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ldc 'retail_indicator'
/*     */     //   3: new java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: invokespecial <init> : ()V
/*     */     //   10: aload_0
/*     */     //   11: getfield isRetail : Z
/*     */     //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   17: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   20: new java/lang/StringBuilder
/*     */     //   23: dup
/*     */     //   24: invokespecial <init> : ()V
/*     */     //   27: iload_1
/*     */     //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   37: aload_0
/*     */     //   38: iload_1
/*     */     //   39: putfield isRetail : Z
/*     */     //   42: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #311	-> 0
/*     */     //   #312	-> 37
/*     */     //   #313	-> 42
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	43	0	this	Lcom/universal/milestone/Bom;
/*     */     //   0	43	1	value	Z }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public String getSelectionNumber() { return this.selectionNumber; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionNumber(String value) {
/* 322 */     auditCheck("selection_number", this.selectionNumber, value);
/* 323 */     this.selectionNumber = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 328 */   public Calendar getStreetDateOnBom() { return this.streetDateOnBom; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStreetDateOnBom(Calendar value) {
/* 333 */     auditCheck("due_date", this.streetDateOnBom, value);
/* 334 */     this.streetDateOnBom = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 339 */   public int getUnitsPerKG() { return this.unitsPerKG; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnitsPerKG(int value) {
/* 344 */     auditCheck("units_per_pkg", this.unitsPerKG, value);
/* 345 */     this.unitsPerKG = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 350 */   public String getRunTime() { return this.runTime; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRunTime(String value) {
/* 355 */     auditCheck("run_time", this.runTime, value);
/* 356 */     this.runTime = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 361 */   public String getConfiguration() { return this.configuration; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfiguration(String value) {
/* 366 */     auditCheck("configuration", this.configuration, value);
/* 367 */     this.configuration = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 372 */   public boolean hasSpineSticker() { return this.hasSpineSticker; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHasSpineSticker(boolean value) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ldc 'spine_sticker_indicator'
/*     */     //   3: new java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: invokespecial <init> : ()V
/*     */     //   10: aload_0
/*     */     //   11: getfield hasSpineSticker : Z
/*     */     //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   17: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   20: new java/lang/StringBuilder
/*     */     //   23: dup
/*     */     //   24: invokespecial <init> : ()V
/*     */     //   27: iload_1
/*     */     //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   37: aload_0
/*     */     //   38: iload_1
/*     */     //   39: putfield hasSpineSticker : Z
/*     */     //   42: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #377	-> 0
/*     */     //   #378	-> 37
/*     */     //   #379	-> 42
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	43	0	this	Lcom/universal/milestone/Bom;
/*     */     //   0	43	1	value	Z }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public boolean useShrinkWrap() { return this.useShrinkWrap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseShrinkWrap(boolean value) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ldc 'shrink_wrap_indicator'
/*     */     //   3: new java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: invokespecial <init> : ()V
/*     */     //   10: aload_0
/*     */     //   11: getfield useShrinkWrap : Z
/*     */     //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   17: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   20: new java/lang/StringBuilder
/*     */     //   23: dup
/*     */     //   24: invokespecial <init> : ()V
/*     */     //   27: iload_1
/*     */     //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   37: aload_0
/*     */     //   38: iload_1
/*     */     //   39: putfield useShrinkWrap : Z
/*     */     //   42: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #388	-> 0
/*     */     //   #389	-> 37
/*     */     //   #390	-> 42
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	43	0	this	Lcom/universal/milestone/Bom;
/*     */     //   0	43	1	value	Z }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 394 */   public String getSpecialInstructions() { return this.specialInstructions; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecialInstructions(String value) {
/* 399 */     auditCheck("special_instructions", this.specialInstructions, value);
/* 400 */     this.specialInstructions = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 405 */   public int getEnteredBy() { return this.enteredBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 410 */   public void setEnteredBy(int value) { this.enteredBy = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 415 */   public int getModifiedBy() { return this.modifiedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   public void setModifiedBy(int value) { this.modifiedBy = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public Calendar getModifiedOn() { return this.modifiedOn; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 430 */   public void setModifiedOn(Calendar value) { this.modifiedOn = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 435 */   public byte[] getRecordVersionId() { return this.recordVersionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 440 */   public void setRecordVersionId(byte[] value) { this.recordVersionId = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 445 */   public int getReleaseLabelId() { return this.releaseLabelId; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 450 */   public void setReleaseLabelId(int value) { this.releaseLabelId = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 455 */   public BomCassetteDetail getBomCassetteDetail() { return this.bomCassetteDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 460 */   public void setBomCassetteDetail(BomCassetteDetail bomCassetteDetail) { this.bomCassetteDetail = bomCassetteDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 465 */   public BomDiskDetail getBomDiskDetail() { return this.bomDiskDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   public void setBomDiskDetail(BomDiskDetail bomDiskDetail) { this.bomDiskDetail = bomDiskDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 475 */   public BomVinylDetail getBomVinylDetail() { return this.bomVinylDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 480 */   public void setBomVinylDetail(BomVinylDetail bomVinylDetail) { this.bomVinylDetail = bomVinylDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 486 */   public BomDVDDetail getBomDVDDetail() { return this.bomDVDDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 492 */   public void setBomDVDDetail(BomDVDDetail bomDVDDetail) { this.bomDVDDetail = bomDVDDetail; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 500 */   public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 508 */   public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
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
/* 524 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 529 */   public String getArtist() { return this.artist; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 534 */   public void setArtist(String value) { this.artist = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 539 */   public String getTitle() { return this.title; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 544 */   public void setTitle(String value) { this.title = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 550 */   public String getStatus() { return this.status; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 555 */   public void setStatus(String value) { this.status = value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 563 */   public String getUpc() { return this.upc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 571 */   public void setUpc(String upc) { this.upc = upc; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Bom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */