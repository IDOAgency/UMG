/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.BomDVDDetail;
/*     */ import com.universal.milestone.BomDiskDetail;
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
/*     */ public class BomDVDDetail
/*     */   extends BomDiskDetail
/*     */ {
/*  35 */   public String discSelectionInfo = "";
/*     */   
/*     */   public boolean wrapStatusIndicator = false;
/*     */   
/*  39 */   public int wrapPartId = -1;
/*  40 */   public String wrapPart = "";
/*  41 */   public int wrapSupplierId = -1;
/*  42 */   public String wrapSupplier = "";
/*  43 */   public String wrapInk1 = "";
/*  44 */   public String wrapInk2 = "";
/*  45 */   public String wrapInfo = "";
/*     */   
/*     */   public boolean dvdStatusIndicator = false;
/*     */   
/*  49 */   public int dvdCasePartId = -1;
/*  50 */   public String dvdPart = "";
/*  51 */   public String dvdInk1 = "";
/*  52 */   public String dvdInk2 = "";
/*  53 */   public String dvdSelectionInfo = "";
/*  54 */   public String dvdInfo = "";
/*     */   
/*     */   public boolean bluRayStatusIndicator = false;
/*     */   
/*  58 */   public int bluRayCasePartId = -1;
/*  59 */   public String bluRayPart = "";
/*  60 */   public String bluRayInk1 = "";
/*  61 */   public String bluRayInk2 = "";
/*  62 */   public String bluRaySelectionInfo = "";
/*  63 */   public String bluRayInfo = "";
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
/*  78 */   public String getDiscSelectionInfo() { return this.discSelectionInfo.equalsIgnoreCase("[none]") ? "" : this.discSelectionInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String getDVDPart() { return this.dvdPart.equalsIgnoreCase("[none]") ? "" : this.dvdPart; }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String getDVDInk1() { return this.dvdInk1.equalsIgnoreCase("[none]") ? "" : this.dvdInk1; }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public String getDVDInk2() { return this.dvdInk2.equalsIgnoreCase("[none]") ? "" : this.dvdInk2; }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public String getDVDInfo() { return this.dvdInfo.equalsIgnoreCase("[none]") ? "" : this.dvdInfo; }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public String getDVDSelectionInfo() { return this.dvdSelectionInfo.equalsIgnoreCase("[none]") ? "" : this.dvdSelectionInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public String getBluRayPart() { return this.bluRayPart.equalsIgnoreCase("[none]") ? "" : this.bluRayPart; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public String getBluRayInk1() { return this.bluRayInk1.equalsIgnoreCase("[none]") ? "" : this.bluRayInk1; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public String getBluRayInk2() { return this.bluRayInk2.equalsIgnoreCase("[none]") ? "" : this.bluRayInk2; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public String getBluRayInfo() { return this.bluRayInfo.equalsIgnoreCase("[none]") ? "" : this.bluRayInfo; }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public String getBluRaySelectionInfo() { return this.bluRaySelectionInfo.equalsIgnoreCase("[none]") ? "" : this.bluRaySelectionInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public String getWrapyPart() { return this.wrapPart.equalsIgnoreCase("[none]") ? "" : this.wrapPart; }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public String getWrapSupplier() { return this.wrapSupplier.equalsIgnoreCase("[none]") ? "" : this.wrapSupplier; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getWrapInk1() { return this.wrapInk1.equalsIgnoreCase("[none]") ? "" : this.wrapInk1; }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public String getWrapInk2() { return this.wrapInk2.equalsIgnoreCase("[none]") ? "" : this.wrapInk2; }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public String getWrapInfo() { return this.wrapInfo.equalsIgnoreCase("[none]") ? "" : this.wrapInfo; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomDVDDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */