/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcProjectSearchFilter;
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
/*     */ 
/*     */ 
/*     */ public class DcProjectSearchFilter
/*     */   implements Serializable
/*     */ {
/*     */   private String artistFirstName;
/*     */   private String artistLastNameGroupName;
/*     */   private String projectID;
/*     */   private String projectTitle;
/*     */   private String projectDescription;
/*     */   private int[] archimedesIDs;
/*     */   private boolean active;
/*     */   private int[] levelOfActivityID;
/*     */   private int rowsToReturn;
/*     */   
/*     */   public DcProjectSearchFilter(String artistFirstName, String artistLastNameGroupName, String projectID, String projectTitle, String projectDescription, int[] archimedesIDs, boolean active, int[] levelOfActivityID, int rowsToReturn) {
/*  42 */     this.artistFirstName = artistFirstName;
/*  43 */     this.artistLastNameGroupName = artistLastNameGroupName;
/*  44 */     this.projectID = projectID;
/*  45 */     this.projectTitle = projectTitle;
/*  46 */     this.projectDescription = projectDescription;
/*  47 */     this.archimedesIDs = archimedesIDs;
/*  48 */     this.active = active;
/*  49 */     this.levelOfActivityID = levelOfActivityID;
/*  50 */     this.rowsToReturn = rowsToReturn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public String getArtistFirstName() { return this.artistFirstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getArtistLastNameGroupName() { return this.artistLastNameGroupName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setArtistLastNameGroupName(String artistLastNameGroupName) { this.artistLastNameGroupName = artistLastNameGroupName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public String getProjectID() { return this.projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setProjectID(String projectID) { this.projectID = projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String getProjectTitle() { return this.projectTitle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getProjectDescription() { return this.projectDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public int[] getArchimedesIDs() { return this.archimedesIDs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setArchimedesIDs(int[] archimedesIDs) { this.archimedesIDs = archimedesIDs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public boolean isActive() { return this.active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void setActive(boolean active) { this.active = active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public int[] getLevelOfActivityID() { return this.levelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void setLevelOfActivityID(int[] levelOfActivityID) { this.levelOfActivityID = levelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public int getRowsToReturn() { return this.rowsToReturn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public void setRowsToReturn(int rowsToReturn) { this.rowsToReturn = rowsToReturn; }
/*     */ 
/*     */   
/* 233 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 235 */     if (!(obj instanceof DcProjectSearchFilter)) return false; 
/* 236 */     DcProjectSearchFilter other = (DcProjectSearchFilter)obj;
/* 237 */     if (obj == null) return false; 
/* 238 */     if (this == obj) return true; 
/* 239 */     if (this.__equalsCalc != null) {
/* 240 */       return (this.__equalsCalc == obj);
/*     */     }
/* 242 */     this.__equalsCalc = obj;
/*     */     
/* 244 */     boolean _equals = 
/* 245 */       (((this.artistFirstName == null && other.getArtistFirstName() == null) || (
/* 246 */       this.artistFirstName != null && 
/* 247 */       this.artistFirstName.equals(other.getArtistFirstName()))) && ((
/* 248 */       this.artistLastNameGroupName == null && other.getArtistLastNameGroupName() == null) || (
/* 249 */       this.artistLastNameGroupName != null && 
/* 250 */       this.artistLastNameGroupName.equals(other.getArtistLastNameGroupName()))) && ((
/* 251 */       this.projectID == null && other.getProjectID() == null) || (
/* 252 */       this.projectID != null && 
/* 253 */       this.projectID.equals(other.getProjectID()))) && ((
/* 254 */       this.projectTitle == null && other.getProjectTitle() == null) || (
/* 255 */       this.projectTitle != null && 
/* 256 */       this.projectTitle.equals(other.getProjectTitle()))) && ((
/* 257 */       this.projectDescription == null && other.getProjectDescription() == null) || (
/* 258 */       this.projectDescription != null && 
/* 259 */       this.projectDescription.equals(other.getProjectDescription()))) && ((
/* 260 */       this.archimedesIDs == null && other.getArchimedesIDs() == null) || (
/* 261 */       this.archimedesIDs != null && 
/* 262 */       Arrays.equals(this.archimedesIDs, other.getArchimedesIDs()))) && 
/* 263 */       this.active == other.isActive() && ((
/* 264 */       this.levelOfActivityID == null && other.getLevelOfActivityID() == null) || (
/* 265 */       this.levelOfActivityID != null && 
/* 266 */       Arrays.equals(this.levelOfActivityID, other.getLevelOfActivityID()))) && 
/* 267 */       this.rowsToReturn == other.getRowsToReturn());
/* 268 */     this.__equalsCalc = null;
/* 269 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 274 */     if (this.__hashCodeCalc) {
/* 275 */       return 0;
/*     */     }
/* 277 */     this.__hashCodeCalc = true;
/* 278 */     int _hashCode = 1;
/* 279 */     if (getArtistFirstName() != null) {
/* 280 */       _hashCode += getArtistFirstName().hashCode();
/*     */     }
/* 282 */     if (getArtistLastNameGroupName() != null) {
/* 283 */       _hashCode += getArtistLastNameGroupName().hashCode();
/*     */     }
/* 285 */     if (getProjectID() != null) {
/* 286 */       _hashCode += getProjectID().hashCode();
/*     */     }
/* 288 */     if (getProjectTitle() != null) {
/* 289 */       _hashCode += getProjectTitle().hashCode();
/*     */     }
/* 291 */     if (getProjectDescription() != null) {
/* 292 */       _hashCode += getProjectDescription().hashCode();
/*     */     }
/* 294 */     if (getArchimedesIDs() != null) {
/* 295 */       for (int i = 0; 
/* 296 */         i < Array.getLength(getArchimedesIDs()); 
/* 297 */         i++) {
/* 298 */         Object obj = Array.get(getArchimedesIDs(), i);
/* 299 */         if (obj != null && 
/* 300 */           !obj.getClass().isArray()) {
/* 301 */           _hashCode += obj.hashCode();
/*     */         }
/*     */       } 
/*     */     }
/* 305 */     _hashCode += (isActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 306 */     if (getLevelOfActivityID() != null) {
/* 307 */       for (int i = 0; 
/* 308 */         i < Array.getLength(getLevelOfActivityID()); 
/* 309 */         i++) {
/* 310 */         Object obj = Array.get(getLevelOfActivityID(), i);
/* 311 */         if (obj != null && 
/* 312 */           !obj.getClass().isArray()) {
/* 313 */           _hashCode += obj.hashCode();
/*     */         }
/*     */       } 
/*     */     }
/* 317 */     _hashCode += getRowsToReturn();
/* 318 */     this.__hashCodeCalc = false;
/* 319 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 324 */   private static TypeDesc typeDesc = new TypeDesc(DcProjectSearchFilter.class, true);
/*     */   
/*     */   static  {
/* 327 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"));
/* 328 */     elemField = new ElementDesc();
/* 329 */     elemField.setFieldName("artistFirstName");
/* 330 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistFirstName"));
/* 331 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 332 */     elemField.setMinOccurs(0);
/* 333 */     elemField.setNillable(true);
/* 334 */     typeDesc.addFieldDesc(elemField);
/* 335 */     elemField = new ElementDesc();
/* 336 */     elemField.setFieldName("artistLastNameGroupName");
/* 337 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistLastNameGroupName"));
/* 338 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 339 */     elemField.setMinOccurs(0);
/* 340 */     elemField.setNillable(true);
/* 341 */     typeDesc.addFieldDesc(elemField);
/* 342 */     elemField = new ElementDesc();
/* 343 */     elemField.setFieldName("projectID");
/* 344 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectID"));
/* 345 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 346 */     elemField.setMinOccurs(0);
/* 347 */     elemField.setNillable(true);
/* 348 */     typeDesc.addFieldDesc(elemField);
/* 349 */     elemField = new ElementDesc();
/* 350 */     elemField.setFieldName("projectTitle");
/* 351 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectTitle"));
/* 352 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 353 */     elemField.setMinOccurs(0);
/* 354 */     elemField.setNillable(true);
/* 355 */     typeDesc.addFieldDesc(elemField);
/* 356 */     elemField = new ElementDesc();
/* 357 */     elemField.setFieldName("projectDescription");
/* 358 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectDescription"));
/* 359 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 360 */     elemField.setMinOccurs(0);
/* 361 */     elemField.setNillable(true);
/* 362 */     typeDesc.addFieldDesc(elemField);
/* 363 */     elemField = new ElementDesc();
/* 364 */     elemField.setFieldName("archimedesIDs");
/* 365 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchimedesIDs"));
/* 366 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 367 */     elemField.setNillable(true);
/* 368 */     elemField.setItemQName(new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int"));
/* 369 */     typeDesc.addFieldDesc(elemField);
/* 370 */     elemField = new ElementDesc();
/* 371 */     elemField.setFieldName("active");
/* 372 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
/* 373 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 374 */     elemField.setNillable(false);
/* 375 */     typeDesc.addFieldDesc(elemField);
/* 376 */     elemField = new ElementDesc();
/* 377 */     elemField.setFieldName("levelOfActivityID");
/* 378 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LevelOfActivityID"));
/* 379 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 380 */     elemField.setNillable(true);
/* 381 */     elemField.setItemQName(new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int"));
/* 382 */     typeDesc.addFieldDesc(elemField);
/* 383 */     elemField = new ElementDesc();
/* 384 */     elemField.setFieldName("rowsToReturn");
/* 385 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "rowsToReturn"));
/* 386 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 387 */     elemField.setNillable(false);
/* 388 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 395 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 405 */     return 
/* 406 */       new BeanSerializer(
/* 407 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 417 */     return 
/* 418 */       new BeanDeserializer(
/* 419 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcProjectSearchFilter() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcProjectSearchFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */