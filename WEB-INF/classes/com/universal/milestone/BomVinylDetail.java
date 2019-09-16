package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.BomVinylDetail;

public class BomVinylDetail {
  public boolean recordStatusIndicator = false;
  
  public int recordPartId = -1;
  
  public String recordPart = "";
  
  public int recordSupplierId = -1;
  
  public String recordSupplier = "";
  
  public String recordColor = "";
  
  public String recordInfo = "";
  
  public boolean labelStatusIndicator = false;
  
  public int labelSupplierId = -1;
  
  public String labelSupplier = "";
  
  public int labelPartId = -1;
  
  public String labelPart = "";
  
  public String labelInk1 = "";
  
  public String labelInk2 = "";
  
  public String labelInfo = "";
  
  public boolean sleeveStatusIndicator = false;
  
  public int sleeveSupplierId = -1;
  
  public String sleeveSupplier = "";
  
  public int sleevePartId = -1;
  
  public String sleevePart = "";
  
  public String sleeveInk1 = "";
  
  public String sleeveInk2 = "";
  
  public String sleeveInfo = "";
  
  public boolean jacketStatusIndicator = false;
  
  public int jacketSupplierId = -1;
  
  public String jacketSupplier = "";
  
  public int jacketPartId = -1;
  
  public String jacketPart = "";
  
  public String jacketInk1 = "";
  
  public String jacketInk2 = "";
  
  public String jacketInfo = "";
  
  public boolean insertStatusIndicator = false;
  
  public int insertSupplierId = -1;
  
  public String insertSupplier = "";
  
  public int insertPartId = -1;
  
  public String insertPart = "";
  
  public String insertInk1 = "";
  
  public String insertInk2 = "";
  
  public String insertInfo = "";
  
  public boolean stickerOneStatusIndicator = false;
  
  public int stickerOneSupplierId = -1;
  
  public String stickerOneSupplier = "";
  
  public int stickerOnePartId = -1;
  
  public String stickerOnePart = "";
  
  public String stickerOneInk1 = "";
  
  public String stickerOneInk2 = "";
  
  public String stickerOnePlaces = "";
  
  public String stickerOneInfo = "";
  
  public boolean stickerTwoStatusIndicator = false;
  
  public int stickerTwoSupplierId = -1;
  
  public String stickerTwoSupplier = "";
  
  public int stickerTwoPartId = -1;
  
  public String stickerTwoPart = "";
  
  public String stickerTwoInk1 = "";
  
  public String stickerTwoInk2 = "";
  
  public String stickerTwoPlaces = "";
  
  public String stickerTwoInfo = "";
  
  public boolean otherStatusIndicator = false;
  
  public int otherSupplierId = -1;
  
  public String otherSupplier = "";
  
  public int otherPartId = -1;
  
  public String otherPart = "";
  
  public String otherInk1 = "";
  
  public String otherInk2 = "";
  
  public String otherInfo = "";
  
  public String getRecordColor() { return this.recordColor.equalsIgnoreCase("[none]") ? "" : this.recordColor; }
  
  public String getRecordInfo() { return this.recordInfo.equalsIgnoreCase("[none]") ? "" : this.recordInfo; }
  
  public String getLabelInk1() { return this.labelInk1.equalsIgnoreCase("[none]") ? "" : this.labelInk1; }
  
  public String getLabelInk2() { return this.labelInk2.equalsIgnoreCase("[none]") ? "" : this.labelInk2; }
  
  public String getLabelInfo() { return this.labelInfo.equalsIgnoreCase("[none]") ? "" : this.labelInfo; }
  
  public String getSleeveInk1() { return this.sleeveInk1.equalsIgnoreCase("[none]") ? "" : this.sleeveInk1; }
  
  public String getSleeveInk2() { return this.sleeveInk2.equalsIgnoreCase("[none]") ? "" : this.sleeveInk2; }
  
  public String getSleeveInfo() { return this.sleeveInfo.equalsIgnoreCase("[none]") ? "" : this.sleeveInfo; }
  
  public String getJacketInk1() { return this.jacketInk1.equalsIgnoreCase("[none]") ? "" : this.jacketInk1; }
  
  public String getJacketInk2() { return this.jacketInk2.equalsIgnoreCase("[none]") ? "" : this.jacketInk2; }
  
  public String getJacketInfo() { return this.jacketInfo.equalsIgnoreCase("[none]") ? "" : this.jacketInfo; }
  
  public String getInsertInk1() { return this.insertInk1.equalsIgnoreCase("[none]") ? "" : this.insertInk1; }
  
  public String getInsertInk2() { return this.insertInk2.equalsIgnoreCase("[none]") ? "" : this.insertInk2; }
  
  public String getInsertInfo() { return this.insertInfo.equalsIgnoreCase("[none]") ? "" : this.insertInfo; }
  
  public String getStickerOneInk1() { return this.stickerOneInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk1; }
  
  public String getStickerOneInk2() { return this.stickerOneInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk2; }
  
  public String getStickerOnePlaces() { return this.stickerOnePlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOnePlaces; }
  
  public String getStickerOneInfo() { return this.stickerOneInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneInfo; }
  
  public String getStickerTwoInk1() { return this.stickerTwoInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk1; }
  
  public String getStickerTwoInk2() { return this.stickerTwoInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk2; }
  
  public String getStickerTwoPlaces() { return this.stickerTwoPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoPlaces; }
  
  public String getStickerTwoInfo() { return this.stickerTwoInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInfo; }
  
  public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
  
  public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
  
  public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomVinylDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */