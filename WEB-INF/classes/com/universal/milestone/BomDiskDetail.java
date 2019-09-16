package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.BomDiskDetail;

public class BomDiskDetail {
  public boolean discStatusIndicator = false;
  
  public int discPartId = -1;
  
  public int diskSupplierId = -1;
  
  public String discPart = "";
  
  public String discSupplier = "";
  
  public String discInk1 = "";
  
  public String discInk2 = "";
  
  public String discInfo = "";
  
  public boolean jewelStatusIndicator = false;
  
  public int jewelPartId = -1;
  
  public String jewelPart = "";
  
  public String jewelColor = "";
  
  public String jewelInfo = "";
  
  public boolean trayStatusIndicator = false;
  
  public int trayPartId = -1;
  
  public String trayPart = "";
  
  public String trayColor = "";
  
  public String trayInfo = "";
  
  public boolean inlayStatusIndicator = false;
  
  public int inlayPartId = -1;
  
  public String inlayPart = "";
  
  public int inlaySupplierId = -1;
  
  public String inlaySupplier = "";
  
  public String inlayInk1 = "";
  
  public String inlayInk2 = "";
  
  public String inlayInfo = "";
  
  public boolean frontStatusIndicator = false;
  
  public int frontPartId = -1;
  
  public String frontPart = "";
  
  public int frontSupplierId = -1;
  
  public String frontSupplier = "";
  
  public String frontInk1 = "";
  
  public String frontInk2 = "";
  
  public String frontInfo = "";
  
  public boolean folderStatusIndicator = false;
  
  public int folderPartId = -1;
  
  public String folderPart = "";
  
  public int folderSupplierId = -1;
  
  public String folderSupplier = "";
  
  public String folderInk1 = "";
  
  public String folderInk2 = "";
  
  public String folderPages = "";
  
  public String folderInfo = "";
  
  public boolean bookletStatusIndicator = false;
  
  public int bookletPartId = -1;
  
  public String bookletPart = "";
  
  public int bookletSupplierId = -1;
  
  public String bookletSupplier = "";
  
  public String bookletInk1 = "";
  
  public String bookletInk2 = "";
  
  public String bookletPages = "";
  
  public String bookletInfo = "";
  
  public boolean brcStatusIndicator = false;
  
  public int brcPartId = -1;
  
  public String brcPart = "";
  
  public int brcSupplierId = -1;
  
  public String brcSupplier = "";
  
  public String brcInk1 = "";
  
  public String brcInk2 = "";
  
  public String brcSize = "";
  
  public String brcInfo = "";
  
  public boolean miniStatusIndicator = false;
  
  public int miniPartId = -1;
  
  public String miniPart = "";
  
  public int miniSupplierId = -1;
  
  public String miniSupplier = "";
  
  public String miniInk1 = "";
  
  public String miniInk2 = "";
  
  public String miniInfo = "";
  
  public boolean digiPakStatusIndicator = false;
  
  public int digiPakPartId = -1;
  
  public String digiPakPart = "";
  
  public int digiPakSupplierId = -1;
  
  public String digiPakSupplier = "";
  
  public String digiPakInk1 = "";
  
  public String digiPakInk2 = "";
  
  public String digiPakTray = "";
  
  public String digiPakInfo = "";
  
  public boolean softPakStatusIndicator = false;
  
  public int softPakPartId = -1;
  
  public String softPakPart = "";
  
  public int softPakSupplierId = -1;
  
  public String softPakSupplier = "";
  
  public String softPakInk1 = "";
  
  public String softPakInk2 = "";
  
  public String softPakTray = "";
  
  public String softPakInfo = "";
  
  public boolean stickerOneStatusIndicator = false;
  
  public int stickerOnePartId = -1;
  
  public String stickerOnePart = "";
  
  public int stickerOneSupplierId = -1;
  
  public String stickerOneSupplier = "";
  
  public String stickerOneInk1 = "";
  
  public String stickerOneInk2 = "";
  
  public String stickerOnePlaces = "";
  
  public String stickerOneInfo = "";
  
  public boolean stickerTwoStatusIndicator = false;
  
  public int stickerTwoPartId = -1;
  
  public String stickerTwoPart = "";
  
  public int stickerTwoSupplierId = -1;
  
  public String stickerTwoSupplier = "";
  
  public String stickerTwoInk1 = "";
  
  public String stickerTwoInk2 = "";
  
  public String stickerTwoPlaces = "";
  
  public String stickerTwoInfo = "";
  
  public boolean bookStatusIndicator = false;
  
  public int bookPartId = -1;
  
  public String bookPart = "";
  
  public int bookSupplierId = -1;
  
  public String bookSupplier = "";
  
  public String bookInk1 = "";
  
  public String bookInk2 = "";
  
  public String bookPages = "";
  
  public String bookInfo = "";
  
  public boolean boxStatusIndicator = false;
  
  public int boxPartId = -1;
  
  public String boxPart = "";
  
  public int boxSupplierId = -1;
  
  public String boxSupplier = "";
  
  public String boxInk1 = "";
  
  public String boxInk2 = "";
  
  public String boxSizes = "";
  
  public String boxInfo = "";
  
  public boolean otherStatusIndicator = false;
  
  public int otherPartId = -1;
  
  public String otherPart = "";
  
  public int otherSupplierId = -1;
  
  public String otherSupplier = "";
  
  public String otherInk1 = "";
  
  public String otherInk2 = "";
  
  public String otherInfo = "";
  
  public String getDiscPart() { return this.discPart.equalsIgnoreCase("[none]") ? "" : this.discPart; }
  
  public String getDiscPartSuplier() { return this.discSupplier.equalsIgnoreCase("[none]") ? "" : this.discSupplier; }
  
  public String getDiscInk1() { return this.discInk1.equalsIgnoreCase("[none]") ? "" : this.discInk1; }
  
  public String getDiscInk2() { return this.discInk2.equalsIgnoreCase("[none]") ? "" : this.discInk2; }
  
  public String getDiscInfo() { return this.discInfo.equalsIgnoreCase("[none]") ? "" : this.discInfo; }
  
  public String getJewelPart() { return this.jewelPart.equalsIgnoreCase("[none]") ? "" : this.jewelPart; }
  
  public String getJewelColor() { return this.jewelColor.equalsIgnoreCase("[none]") ? "" : this.jewelColor; }
  
  public String getJewelInfo() { return this.jewelInfo.equalsIgnoreCase("[none]") ? "" : this.jewelInfo; }
  
  public String getTrayPart() { return this.trayPart.equalsIgnoreCase("[none]") ? "" : this.trayPart; }
  
  public String getTrayColor() { return this.trayColor.equalsIgnoreCase("[none]") ? "" : this.trayColor; }
  
  public String getTrayInfo() { return this.trayInfo.equalsIgnoreCase("[none]") ? "" : this.trayInfo; }
  
  public String getInlayPart() { return this.inlayPart.equalsIgnoreCase("[none]") ? "" : this.inlayPart; }
  
  public String getInlaySupplier() { return this.inlaySupplier.equalsIgnoreCase("[none]") ? "" : this.inlaySupplier; }
  
  public String getInlayInk1() { return this.inlayInk1.equalsIgnoreCase("[none]") ? "" : this.inlayInk1; }
  
  public String getInlayInk2() { return this.inlayInk2.equalsIgnoreCase("[none]") ? "" : this.inlayInk2; }
  
  public String getInlayInfo() { return this.inlayInfo.equalsIgnoreCase("[none]") ? "" : this.inlayInfo; }
  
  public String getFrontPart() { return this.frontPart.equalsIgnoreCase("[none]") ? "" : this.frontPart; }
  
  public String getFrontInk1() { return this.frontInk1.equalsIgnoreCase("[none]") ? "" : this.frontInk1; }
  
  public String getFrontInk2() { return this.frontInk2.equalsIgnoreCase("[none]") ? "" : this.frontInk2; }
  
  public String getFrontInfo() { return this.frontInfo.equalsIgnoreCase("[none]") ? "" : this.frontInfo; }
  
  public String getFolderInk1() { return this.folderInk1.equalsIgnoreCase("[none]") ? "" : this.folderInk1; }
  
  public String getFolderInk2() { return this.folderInk2.equalsIgnoreCase("[none]") ? "" : this.folderInk2; }
  
  public String getFolderPages() { return this.folderPages.equalsIgnoreCase("[none]") ? "" : this.folderPages; }
  
  public String getFolderInfo() { return this.folderInfo.equalsIgnoreCase("[none]") ? "" : this.folderInfo; }
  
  public String getBookletInk1() { return this.bookletInk1.equalsIgnoreCase("[none]") ? "" : this.bookletInk1; }
  
  public String getBookletInk2() { return this.bookletInk2.equalsIgnoreCase("[none]") ? "" : this.bookletInk2; }
  
  public String getBookletPages() { return this.bookletPages.equalsIgnoreCase("[none]") ? "" : this.bookletPages; }
  
  public String getBookletInfo() { return this.bookletInfo.equalsIgnoreCase("[none]") ? "" : this.bookletInfo; }
  
  public String getBrcInk1() { return this.brcInk1.equalsIgnoreCase("[none]") ? "" : this.brcInk1; }
  
  public String getBrcInk2() { return this.brcInk2.equalsIgnoreCase("[none]") ? "" : this.brcInk2; }
  
  public String getBrcSize() { return this.brcSize.equalsIgnoreCase("[none]") ? "" : this.brcSize; }
  
  public String getBrcInfo() { return this.brcInfo.equalsIgnoreCase("[none]") ? "" : this.brcInfo; }
  
  public String getMiniInk1() { return this.miniInk1.equalsIgnoreCase("[none]") ? "" : this.miniInk1; }
  
  public String getMiniInk2() { return this.miniInk2.equalsIgnoreCase("[none]") ? "" : this.miniInk2; }
  
  public String getMiniInfo() { return this.miniInfo.equalsIgnoreCase("[none]") ? "" : this.miniInfo; }
  
  public String getDigiPakInk1() { return this.digiPakInk1.equalsIgnoreCase("[none]") ? "" : this.digiPakInk1; }
  
  public String getDigiPakInk2() { return this.digiPakInk2.equalsIgnoreCase("[none]") ? "" : this.digiPakInk2; }
  
  public String getDigiPakTray() { return this.digiPakTray.equalsIgnoreCase("[none]") ? "" : this.digiPakTray; }
  
  public String getDigiPakInfo() { return this.digiPakInfo.equalsIgnoreCase("[none]") ? "" : this.digiPakInfo; }
  
  public String getSoftPakInk1() { return this.softPakInk1.equalsIgnoreCase("[none]") ? "" : this.softPakInk1; }
  
  public String getSoftPakInk2() { return this.softPakInk2.equalsIgnoreCase("[none]") ? "" : this.softPakInk2; }
  
  public String getSoftPakTray() { return this.softPakTray.equalsIgnoreCase("[none]") ? "" : this.softPakTray; }
  
  public String getSoftPakInfo() { return this.softPakInfo.equalsIgnoreCase("[none]") ? "" : this.softPakInfo; }
  
  public String getStickerOneInk1() { return this.stickerOneInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk1; }
  
  public String getStickerOneInk2() { return this.stickerOneInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk2; }
  
  public String getStickerOnePlaces() { return this.stickerOnePlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOnePlaces; }
  
  public String getStickerOneInfo() { return this.stickerOneInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneInfo; }
  
  public String getStickerTwoInk1() { return this.stickerTwoInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk1; }
  
  public String getStickerTwoInk2() { return this.stickerTwoInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk2; }
  
  public String getStickerTwoPlaces() { return this.stickerTwoPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoPlaces; }
  
  public String getStickerTwoInfo() { return this.stickerTwoInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInfo; }
  
  public String getBookInk1() { return this.bookInk1.equalsIgnoreCase("[none]") ? "" : this.bookInk1; }
  
  public String getBookInk2() { return this.bookInk2.equalsIgnoreCase("[none]") ? "" : this.bookInk2; }
  
  public String getBookPages() { return this.bookPages.equalsIgnoreCase("[none]") ? "" : this.bookPages; }
  
  public String getBookInfo() { return this.bookInfo.equalsIgnoreCase("[none]") ? "" : this.bookInfo; }
  
  public String getBoxInk1() { return this.boxInk1.equalsIgnoreCase("[none]") ? "" : this.boxInk1; }
  
  public String getBoxInk2() { return this.boxInk2.equalsIgnoreCase("[none]") ? "" : this.boxInk2; }
  
  public String getBoxSizes() { return this.boxSizes.equalsIgnoreCase("[none]") ? "" : this.boxSizes; }
  
  public String getBoxInfo() { return this.boxInfo.equalsIgnoreCase("[none]") ? "" : this.boxInfo; }
  
  public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
  
  public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
  
  public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\BomDiskDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */