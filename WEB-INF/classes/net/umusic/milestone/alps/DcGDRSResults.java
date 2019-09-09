/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcGDRSResults;
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
/*     */ public class DcGDRSResults
/*     */   implements Serializable
/*     */ {
/*     */   private Integer releaseID;
/*     */   private String status;
/*     */   private Boolean forceNoDigitalRelease;
/*     */   private String exceptionMessage;
/*     */   
/*     */   public DcGDRSResults(Integer releaseID, String status, Boolean forceNoDigitalRelease, String exceptionMessage) {
/*  27 */     this.releaseID = releaseID;
/*  28 */     this.status = status;
/*  29 */     this.forceNoDigitalRelease = forceNoDigitalRelease;
/*  30 */     this.exceptionMessage = exceptionMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public Integer getReleaseID() { return this.releaseID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void setReleaseID(Integer releaseID) { this.releaseID = releaseID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public String getStatus() { return this.status; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void setStatus(String status) { this.status = status; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public Boolean getForceNoDigitalRelease() { return this.forceNoDigitalRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setForceNoDigitalRelease(Boolean forceNoDigitalRelease) { this.forceNoDigitalRelease = forceNoDigitalRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public String getExceptionMessage() { return this.exceptionMessage; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setExceptionMessage(String exceptionMessage) { this.exceptionMessage = exceptionMessage; }
/*     */ 
/*     */   
/* 113 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 115 */     if (!(obj instanceof DcGDRSResults)) return false; 
/* 116 */     DcGDRSResults other = (DcGDRSResults)obj;
/* 117 */     if (obj == null) return false; 
/* 118 */     if (this == obj) return true; 
/* 119 */     if (this.__equalsCalc != null) {
/* 120 */       return (this.__equalsCalc == obj);
/*     */     }
/* 122 */     this.__equalsCalc = obj;
/*     */     
/* 124 */     boolean _equals = 
/* 125 */       (((this.releaseID == null && other.getReleaseID() == null) || (
/* 126 */       this.releaseID != null && 
/* 127 */       this.releaseID.equals(other.getReleaseID()))) && ((
/* 128 */       this.status == null && other.getStatus() == null) || (
/* 129 */       this.status != null && 
/* 130 */       this.status.equals(other.getStatus()))) && ((
/* 131 */       this.forceNoDigitalRelease == null && other.getForceNoDigitalRelease() == null) || (
/* 132 */       this.forceNoDigitalRelease != null && 
/* 133 */       this.forceNoDigitalRelease.equals(other.getForceNoDigitalRelease()))) && ((
/* 134 */       this.exceptionMessage == null && other.getExceptionMessage() == null) || (
/* 135 */       this.exceptionMessage != null && 
/* 136 */       this.exceptionMessage.equals(other.getExceptionMessage()))));
/* 137 */     this.__equalsCalc = null;
/* 138 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 143 */     if (this.__hashCodeCalc) {
/* 144 */       return 0;
/*     */     }
/* 146 */     this.__hashCodeCalc = true;
/* 147 */     int _hashCode = 1;
/* 148 */     if (getReleaseID() != null) {
/* 149 */       _hashCode += getReleaseID().hashCode();
/*     */     }
/* 151 */     if (getStatus() != null) {
/* 152 */       _hashCode += getStatus().hashCode();
/*     */     }
/* 154 */     if (getForceNoDigitalRelease() != null) {
/* 155 */       _hashCode += getForceNoDigitalRelease().hashCode();
/*     */     }
/* 157 */     if (getExceptionMessage() != null) {
/* 158 */       _hashCode += getExceptionMessage().hashCode();
/*     */     }
/* 160 */     this.__hashCodeCalc = false;
/* 161 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 166 */   private static TypeDesc typeDesc = new TypeDesc(DcGDRSResults.class, true);
/*     */   
/*     */   static  {
/* 169 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcGDRSResults"));
/* 170 */     elemField = new ElementDesc();
/* 171 */     elemField.setFieldName("releaseID");
/* 172 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ReleaseID"));
/* 173 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 174 */     elemField.setMinOccurs(0);
/* 175 */     elemField.setNillable(false);
/* 176 */     typeDesc.addFieldDesc(elemField);
/* 177 */     elemField = new ElementDesc();
/* 178 */     elemField.setFieldName("status");
/* 179 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Status"));
/* 180 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 181 */     elemField.setMinOccurs(0);
/* 182 */     elemField.setNillable(true);
/* 183 */     typeDesc.addFieldDesc(elemField);
/* 184 */     elemField = new ElementDesc();
/* 185 */     elemField.setFieldName("forceNoDigitalRelease");
/* 186 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ForceNoDigitalRelease"));
/* 187 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 188 */     elemField.setMinOccurs(0);
/* 189 */     elemField.setNillable(false);
/* 190 */     typeDesc.addFieldDesc(elemField);
/* 191 */     elemField = new ElementDesc();
/* 192 */     elemField.setFieldName("exceptionMessage");
/* 193 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ExceptionMessage"));
/* 194 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 195 */     elemField.setMinOccurs(0);
/* 196 */     elemField.setNillable(true);
/* 197 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 214 */     return 
/* 215 */       new BeanSerializer(
/* 216 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 226 */     return 
/* 227 */       new BeanDeserializer(
/* 228 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcGDRSResults() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcGDRSResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */