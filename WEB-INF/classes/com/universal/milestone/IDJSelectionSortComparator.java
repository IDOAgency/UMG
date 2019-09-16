package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.IDJSelectionSortComparator;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSubConfiguration;
import java.util.Calendar;
import java.util.Comparator;

public class IDJSelectionSortComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Selection sel1 = (Selection)o1;
    Selection sel2 = (Selection)o2;
    boolean sel1TBS = isTBS(sel1);
    boolean sel2TBS = isTBS(sel2);
    if (sel1TBS && !sel2TBS)
      return 1; 
    if (!sel1TBS && sel2TBS)
      return -1; 
    if (sel1TBS && sel2TBS)
      return compareTBSSelections(sel1, sel2); 
    boolean sel1Physical = !sel1.getIsDigital();
    boolean sel2Physical = !sel2.getIsDigital();
    return compareDateAndTitle(sel1, sel2);
  }
  
  public int compareDateAndTitle(Selection sel1, Selection sel2) {
    Calendar calEntry2;
    Calendar calEntry1;
    if (sel1.getIsDigital()) {
      calEntry1 = sel1.getDigitalRlsDate();
    } else {
      calEntry1 = sel1.getStreetDate();
    } 
    if (sel2.getIsDigital()) {
      calEntry2 = sel2.getDigitalRlsDate();
    } else {
      calEntry2 = sel2.getStreetDate();
    } 
    if ((calEntry1 == null && calEntry2 == null) || calEntry1.equals(calEntry2))
      return compareSelectionsWithSameDate(sel1, sel2); 
    if (calEntry1 == null || calEntry2 == null) {
      if (calEntry1 == null)
        return 1; 
      if (calEntry2 == null)
        return -1; 
    } 
    if (calEntry1.before(calEntry2))
      return -1; 
    if (calEntry1.after(calEntry2))
      return 1; 
    return 0;
  }
  
  public int compareSelectionsWithSameDate(Selection sel1, Selection sel2) {
    boolean sel1NonActive = (isTBS(sel1) && !isActiveStreetDate(sel1));
    boolean sel2NonActive = (isTBS(sel2) && !isActiveStreetDate(sel1));
    if (sel1NonActive && sel2NonActive) {
      String titleEntry1 = sel1.getTitle().toUpperCase();
      String titleEntry2 = sel2.getTitle().toUpperCase();
      return titleEntry1.compareTo(titleEntry2);
    } 
    boolean isSel1CommercialPhysicalFullLength = (isPhysicalFullLength(sel1) && isCommercial(sel1));
    boolean isSel2CommercialPhysicalFullLength = (isPhysicalFullLength(sel2) && isCommercial(sel2));
    boolean isSel1PromoPhysicalFullLength = (isPhysicalFullLength(sel1) && !isCommercial(sel1));
    boolean isSel2PromoPhysicalFullLength = (isPhysicalFullLength(sel2) && !isCommercial(sel2));
    boolean isSel1DigitalFullLength = isDigitalFullLength(sel1);
    boolean isSel2DigitalFullLength = isDigitalFullLength(sel2);
    boolean isSel1CommercialPhysicalSingle = (isPhysicalSingle(sel1) && isCommercial(sel1));
    boolean isSel2CommercialPhysicalSingle = (isPhysicalSingle(sel2) && isCommercial(sel2));
    boolean isSel1PromoPhysicalSingle = (isPhysicalSingle(sel1) && !isCommercial(sel1));
    boolean isSel2PromoPhysicalSingle = (isPhysicalSingle(sel2) && !isCommercial(sel2));
    boolean isSel1DigitalSingle = isDigitalSingle(sel1);
    boolean isSel2DigitalSingle = isDigitalSingle(sel2);
    if (isSel1CommercialPhysicalFullLength && !isSel2CommercialPhysicalFullLength)
      return -1; 
    if (!isSel1CommercialPhysicalFullLength && isSel2CommercialPhysicalFullLength)
      return 1; 
    if (isSel1PromoPhysicalFullLength && !isSel2PromoPhysicalFullLength)
      return -1; 
    if (!isSel1PromoPhysicalFullLength && isSel2PromoPhysicalFullLength)
      return 1; 
    if (isSel1PromoPhysicalSingle && !isSel2PromoPhysicalSingle)
      return -1; 
    if (!isSel1PromoPhysicalSingle && isSel2PromoPhysicalSingle)
      return 1; 
    if (isSel1CommercialPhysicalSingle && !isSel2CommercialPhysicalSingle)
      return -1; 
    if (!isSel1CommercialPhysicalSingle && isSel2CommercialPhysicalSingle)
      return 1; 
    if (isSel1DigitalFullLength && !isSel2DigitalFullLength)
      return -1; 
    if (!isSel1DigitalFullLength && isSel2DigitalFullLength)
      return 1; 
    if (isSel1DigitalSingle && !isSel2DigitalSingle)
      return -1; 
    if (!isSel1DigitalSingle && isSel2DigitalSingle)
      return 1; 
    String subConfigString1 = (sel1.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel1.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
    String subConfigString2 = (sel2.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel2.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
    if (subConfigString1.equals("eVideo") && !subConfigString2.equals("eVideo"))
      return -1; 
    if (!subConfigString1.equals("eVideo") && subConfigString2.equals("eVideo"))
      return 1; 
    String titleEntry1 = sel1.getTitle().toUpperCase();
    String titleEntry2 = sel2.getTitle().toUpperCase();
    if (!titleEntry1.equals(titleEntry2))
      return titleEntry1.compareTo(titleEntry2); 
    return 0;
  }
  
  public boolean isTBS(Selection sel) { return SelectionManager.getLookupObjectValue(sel.getSelectionStatus())
      .equalsIgnoreCase("TBS"); }
  
  public int compareTBSSelections(Selection sel1, Selection sel2) {
    boolean sel1Active = isActiveStreetDate(sel1);
    boolean sel2Active = isActiveStreetDate(sel2);
    if (sel1Active && sel2Active)
      return compareDateAndTitle(sel1, sel2); 
    if (sel1Active && !sel2Active)
      return -1; 
    if (!sel1Active && sel2Active)
      return 1; 
    return compareSelectionsWithSameDate(sel1, sel2);
  }
  
  public boolean isActiveStreetDate(Selection sel) {
    Calendar streetCal;
    String streetDate = "";
    if (sel.getIsDigital()) {
      streetCal = sel.getDigitalRlsDate();
    } else {
      streetCal = sel.getStreetDate();
    } 
    streetDate = MilestoneHelper.getFormatedDate(streetCal);
    return !streetDate.equals("");
  }
  
  public boolean isPhysicalFullLength(Selection sel) {
    boolean fullLength = false;
    String subconfigAbbreviation = "";
    SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
    if (subconfig != null)
      subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
    String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
    if (subconfigAbbreviation.equalsIgnoreCase("DVDVID") || 
      subconfigAbbreviation.equalsIgnoreCase("ECD") || 
      subconfigAbbreviation.equalsIgnoreCase("ECDEP") || 
      subconfigAbbreviation.equalsIgnoreCase("CASS") || 
      subconfigAbbreviation.equalsIgnoreCase("CASSEP") || 
      subconfigAbbreviation.equalsIgnoreCase("8TRK") || 
      subconfigAbbreviation.equalsIgnoreCase("CDROM") || 
      subconfigAbbreviation.equalsIgnoreCase("CDVID") || 
      subconfigAbbreviation.equalsIgnoreCase("DCCASS") || 
      subconfigAbbreviation.equalsIgnoreCase("LASER") || 
      subconfigAbbreviation.equalsIgnoreCase("MIXED") || 
      subconfigAbbreviation.equalsIgnoreCase("VIDEO") || 
      subconfigAbbreviation.equalsIgnoreCase("CD") || 
      subconfigAbbreviation.equalsIgnoreCase("CDEP") || 
      subconfigAbbreviation.equalsIgnoreCase("CDADVD") || 
      subconfigAbbreviation.equalsIgnoreCase("DP") || 
      subconfigAbbreviation.equalsIgnoreCase("DVDAUD") || 
      subconfigAbbreviation.equalsIgnoreCase("SACD1") || 
      subconfigAbbreviation.equalsIgnoreCase("SACD2") || 
      subconfigAbbreviation.equalsIgnoreCase("SACD3") || 
      subconfigAbbreviation.equalsIgnoreCase("SACD4") || 
      subconfigAbbreviation.equalsIgnoreCase("ALBUM") || 
      subconfigAbbreviation.equalsIgnoreCase("DualDisc"))
      fullLength = true; 
    return fullLength;
  }
  
  public boolean isPhysicalSingle(Selection sel) {
    boolean fullLength = false;
    String subconfigAbbreviation = "";
    SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
    if (subconfig != null)
      subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
    String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
    if (subconfigAbbreviation.equalsIgnoreCase("DVDVSL") || 
      subconfigAbbreviation.equalsIgnoreCase("ECDMX") || 
      subconfigAbbreviation.equalsIgnoreCase("ECDSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("CASSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("CASSMX") || 
      subconfigAbbreviation.equalsIgnoreCase("eSNGL") || 
      subconfigAbbreviation.equalsIgnoreCase("CDMX") || 
      subconfigAbbreviation.equalsIgnoreCase("CDSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("DVDASL") || 
      subconfigAbbreviation.equalsIgnoreCase("VNYL10") || 
      subconfigAbbreviation.equalsIgnoreCase("VNYL12") || 
      subconfigAbbreviation.equalsIgnoreCase("VNYL7"))
      fullLength = true; 
    return fullLength;
  }
  
  public boolean isDigitalFullLength(Selection sel) {
    boolean fullLength = false;
    String subconfigAbbreviation = "";
    SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
    if (subconfig != null)
      subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
    String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
    if (subconfigAbbreviation.equalsIgnoreCase("DOALB") || 
      subconfigAbbreviation.equalsIgnoreCase("PTCALB") || 
      subconfigAbbreviation.equalsIgnoreCase("PTBALB") || 
      subconfigAbbreviation.equalsIgnoreCase("SWPALB"))
      fullLength = true; 
    return fullLength;
  }
  
  public boolean isDigitalSingle(Selection sel) {
    boolean fullLength = false;
    String subconfigAbbreviation = "";
    SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
    if (subconfig != null)
      subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
    String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
    if (subconfigAbbreviation.equalsIgnoreCase("DOSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("PTCSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("PTPSGL") || 
      subconfigAbbreviation.equalsIgnoreCase("SWPSGL"))
      fullLength = true; 
    return fullLength;
  }
  
  public boolean isCommercial(Selection sel) {
    String releaseTypeString = "";
    ReleaseType releaseType = sel.getReleaseType();
    releaseTypeString = releaseType.getName();
    return releaseTypeString.equals("Promotional");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\IDJSelectionSortComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */