package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.BomDVDDetail;
import com.universal.milestone.BomDiskDetail;

public class BomDVDDetail extends BomDiskDetail {
  public String discSelectionInfo = "";
  
  public boolean wrapStatusIndicator = false;
  
  public int wrapPartId = -1;
  
  public String wrapPart = "";
  
  public int wrapSupplierId = -1;
  
  public String wrapSupplier = "";
  
  public String wrapInk1 = "";
  
  public String wrapInk2 = "";
  
  public String wrapInfo = "";
  
  public boolean dvdStatusIndicator = false;
  
  public int dvdCasePartId = -1;
  
  public String dvdPart = "";
  
  public String dvdInk1 = "";
  
  public String dvdInk2 = "";
  
  public String dvdSelectionInfo = "";
  
  public String dvdInfo = "";
  
  public boolean bluRayStatusIndicator = false;
  
  public int bluRayCasePartId = -1;
  
  public String bluRayPart = "";
  
  public String bluRayInk1 = "";
  
  public String bluRayInk2 = "";
  
  public String bluRaySelectionInfo = "";
  
  public String bluRayInfo = "";
  
  public String getDiscSelectionInfo() { return this.discSelectionInfo.equalsIgnoreCase("[none]") ? "" : this.discSelectionInfo; }
  
  public String getDVDPart() { return this.dvdPart.equalsIgnoreCase("[none]") ? "" : this.dvdPart; }
  
  public String getDVDInk1() { return this.dvdInk1.equalsIgnoreCase("[none]") ? "" : this.dvdInk1; }
  
  public String getDVDInk2() { return this.dvdInk2.equalsIgnoreCase("[none]") ? "" : this.dvdInk2; }
  
  public String getDVDInfo() { return this.dvdInfo.equalsIgnoreCase("[none]") ? "" : this.dvdInfo; }
  
  public String getDVDSelectionInfo() { return this.dvdSelectionInfo.equalsIgnoreCase("[none]") ? "" : this.dvdSelectionInfo; }
  
  public String getBluRayPart() { return this.bluRayPart.equalsIgnoreCase("[none]") ? "" : this.bluRayPart; }
  
  public String getBluRayInk1() { return this.bluRayInk1.equalsIgnoreCase("[none]") ? "" : this.bluRayInk1; }
  
  public String getBluRayInk2() { return this.bluRayInk2.equalsIgnoreCase("[none]") ? "" : this.bluRayInk2; }
  
  public String getBluRayInfo() { return this.bluRayInfo.equalsIgnoreCase("[none]") ? "" : this.bluRayInfo; }
  
  public String getBluRaySelectionInfo() { return this.bluRaySelectionInfo.equalsIgnoreCase("[none]") ? "" : this.bluRaySelectionInfo; }
  
  public String getWrapyPart() { return this.wrapPart.equalsIgnoreCase("[none]") ? "" : this.wrapPart; }
  
  public String getWrapSupplier() { return this.wrapSupplier.equalsIgnoreCase("[none]") ? "" : this.wrapSupplier; }
  
  public String getWrapInk1() { return this.wrapInk1.equalsIgnoreCase("[none]") ? "" : this.wrapInk1; }
  
  public String getWrapInk2() { return this.wrapInk2.equalsIgnoreCase("[none]") ? "" : this.wrapInk2; }
  
  public String getWrapInfo() { return this.wrapInfo.equalsIgnoreCase("[none]") ? "" : this.wrapInfo; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomDVDDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */