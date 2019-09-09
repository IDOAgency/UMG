/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
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
/*     */ public class DcMilestoneProjectSearchFilter
/*     */   implements Serializable
/*     */ {
/*     */   private String artistFirstName;
/*     */   private String artistLastNameGroupName;
/*     */   private String projectID;
/*     */   private String projectTitle;
/*     */   private String projectDescription;
/*     */   private boolean active;
/*     */   private int[] levelOfActivityID;
/*     */   private int rowsToReturn;
/*     */   
/*     */   public DcMilestoneProjectSearchFilter(String artistFirstName, String artistLastNameGroupName, String projectID, String projectTitle, String projectDescription, boolean active, int[] levelOfActivityID, int rowsToReturn) {
/*  39 */     this.artistFirstName = artistFirstName;
/*  40 */     this.artistLastNameGroupName = artistLastNameGroupName;
/*  41 */     this.projectID = projectID;
/*  42 */     this.projectTitle = projectTitle;
/*  43 */     this.projectDescription = projectDescription;
/*  44 */     this.active = active;
/*  45 */     this.levelOfActivityID = levelOfActivityID;
/*  46 */     this.rowsToReturn = rowsToReturn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public String getArtistFirstName() { return this.artistFirstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getArtistLastNameGroupName() { return this.artistLastNameGroupName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void setArtistLastNameGroupName(String artistLastNameGroupName) { this.artistLastNameGroupName = artistLastNameGroupName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String getProjectID() { return this.projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setProjectID(String projectID) { this.projectID = projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public String getProjectTitle() { return this.projectTitle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public String getProjectDescription() { return this.projectDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public boolean isActive() { return this.active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void setActive(boolean active) { this.active = active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public int[] getLevelOfActivityID() { return this.levelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public void setLevelOfActivityID(int[] levelOfActivityID) { this.levelOfActivityID = levelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public int getRowsToReturn() { return this.rowsToReturn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void setRowsToReturn(int rowsToReturn) { this.rowsToReturn = rowsToReturn; }
/*     */ 
/*     */   
/* 209 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 211 */     if (!(obj instanceof DcMilestoneProjectSearchFilter)) return false; 
/* 212 */     DcMilestoneProjectSearchFilter other = (DcMilestoneProjectSearchFilter)obj;
/* 213 */     if (obj == null) return false; 
/* 214 */     if (this == obj) return true; 
/* 215 */     if (this.__equalsCalc != null) {
/* 216 */       return (this.__equalsCalc == obj);
/*     */     }
/* 218 */     this.__equalsCalc = obj;
/*     */     
/* 220 */     boolean _equals = 
/* 221 */       (((this.artistFirstName == null && other.getArtistFirstName() == null) || (
/* 222 */       this.artistFirstName != null && 
/* 223 */       this.artistFirstName.equals(other.getArtistFirstName()))) && ((
/* 224 */       this.artistLastNameGroupName == null && other.getArtistLastNameGroupName() == null) || (
/* 225 */       this.artistLastNameGroupName != null && 
/* 226 */       this.artistLastNameGroupName.equals(other.getArtistLastNameGroupName()))) && ((
/* 227 */       this.projectID == null && other.getProjectID() == null) || (
/* 228 */       this.projectID != null && 
/* 229 */       this.projectID.equals(other.getProjectID()))) && ((
/* 230 */       this.projectTitle == null && other.getProjectTitle() == null) || (
/* 231 */       this.projectTitle != null && 
/* 232 */       this.projectTitle.equals(other.getProjectTitle()))) && ((
/* 233 */       this.projectDescription == null && other.getProjectDescription() == null) || (
/* 234 */       this.projectDescription != null && 
/* 235 */       this.projectDescription.equals(other.getProjectDescription()))) && 
/* 236 */       this.active == other.isActive() && ((
/* 237 */       this.levelOfActivityID == null && other.getLevelOfActivityID() == null) || (
/* 238 */       this.levelOfActivityID != null && 
/* 239 */       Arrays.equals(this.levelOfActivityID, other.getLevelOfActivityID()))) && 
/* 240 */       this.rowsToReturn == other.getRowsToReturn());
/* 241 */     this.__equalsCalc = null;
/* 242 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 247 */     if (this.__hashCodeCalc) {
/* 248 */       return 0;
/*     */     }
/* 250 */     this.__hashCodeCalc = true;
/* 251 */     int _hashCode = 1;
/* 252 */     if (getArtistFirstName() != null) {
/* 253 */       _hashCode += getArtistFirstName().hashCode();
/*     */     }
/* 255 */     if (getArtistLastNameGroupName() != null) {
/* 256 */       _hashCode += getArtistLastNameGroupName().hashCode();
/*     */     }
/* 258 */     if (getProjectID() != null) {
/* 259 */       _hashCode += getProjectID().hashCode();
/*     */     }
/* 261 */     if (getProjectTitle() != null) {
/* 262 */       _hashCode += getProjectTitle().hashCode();
/*     */     }
/* 264 */     if (getProjectDescription() != null) {
/* 265 */       _hashCode += getProjectDescription().hashCode();
/*     */     }
/* 267 */     _hashCode += (isActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 268 */     if (getLevelOfActivityID() != null) {
/* 269 */       for (int i = 0; 
/* 270 */         i < Array.getLength(getLevelOfActivityID()); 
/* 271 */         i++) {
/* 272 */         Object obj = Array.get(getLevelOfActivityID(), i);
/* 273 */         if (obj != null && 
/* 274 */           !obj.getClass().isArray()) {
/* 275 */           _hashCode += obj.hashCode();
/*     */         }
/*     */       } 
/*     */     }
/* 279 */     _hashCode += getRowsToReturn();
/* 280 */     this.__hashCodeCalc = false;
/* 281 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 286 */   private static TypeDesc typeDesc = new TypeDesc(DcMilestoneProjectSearchFilter.class, true);
/*     */   
/*     */   static  {
/* 289 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcMilestoneProjectSearchFilter"));
/* 290 */     elemField = new ElementDesc();
/* 291 */     elemField.setFieldName("artistFirstName");
/* 292 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistFirstName"));
/* 293 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 294 */     elemField.setMinOccurs(0);
/* 295 */     elemField.setNillable(true);
/* 296 */     typeDesc.addFieldDesc(elemField);
/* 297 */     elemField = new ElementDesc();
/* 298 */     elemField.setFieldName("artistLastNameGroupName");
/* 299 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistLastNameGroupName"));
/* 300 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 301 */     elemField.setMinOccurs(0);
/* 302 */     elemField.setNillable(true);
/* 303 */     typeDesc.addFieldDesc(elemField);
/* 304 */     elemField = new ElementDesc();
/* 305 */     elemField.setFieldName("projectID");
/* 306 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectID"));
/* 307 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 308 */     elemField.setMinOccurs(0);
/* 309 */     elemField.setNillable(true);
/* 310 */     typeDesc.addFieldDesc(elemField);
/* 311 */     elemField = new ElementDesc();
/* 312 */     elemField.setFieldName("projectTitle");
/* 313 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectTitle"));
/* 314 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 315 */     elemField.setMinOccurs(0);
/* 316 */     elemField.setNillable(true);
/* 317 */     typeDesc.addFieldDesc(elemField);
/* 318 */     elemField = new ElementDesc();
/* 319 */     elemField.setFieldName("projectDescription");
/* 320 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectDescription"));
/* 321 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 322 */     elemField.setMinOccurs(0);
/* 323 */     elemField.setNillable(true);
/* 324 */     typeDesc.addFieldDesc(elemField);
/* 325 */     elemField = new ElementDesc();
/* 326 */     elemField.setFieldName("active");
/* 327 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
/* 328 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 329 */     elemField.setNillable(false);
/* 330 */     typeDesc.addFieldDesc(elemField);
/* 331 */     elemField = new ElementDesc();
/* 332 */     elemField.setFieldName("levelOfActivityID");
/* 333 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LevelOfActivityID"));
/* 334 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 335 */     elemField.setNillable(true);
/* 336 */     elemField.setItemQName(new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int"));
/* 337 */     typeDesc.addFieldDesc(elemField);
/* 338 */     elemField = new ElementDesc();
/* 339 */     elemField.setFieldName("rowsToReturn");
/* 340 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "rowsToReturn"));
/* 341 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 342 */     elemField.setNillable(false);
/* 343 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 350 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 360 */     return 
/* 361 */       new BeanSerializer(
/* 362 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 372 */     return 
/* 373 */       new BeanDeserializer(
/* 374 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcMilestoneProjectSearchFilter() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcMilestoneProjectSearchFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */