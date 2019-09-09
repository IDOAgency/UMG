/*     */ package WEB-INF.classes.com.universal.milestone.push;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Pfm;
/*     */ import com.universal.milestone.ReleasingFamily;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.jms.MessageObject;
/*     */ import com.universal.milestone.push.PushCommunication;
/*     */ import com.universal.milestone.push.PushHome;
/*     */ import com.universal.milestone.push.PushPFM;
/*     */ import com.universal.milestone.push.PushRemote;
/*     */ import com.universal.milestone.xml.XMLUtil;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
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
/*     */ public class PushCommunication
/*     */ {
/*     */   public static final boolean VERBOSE = true;
/*  53 */   public static final String[] legacyNodeList = { "configuration", "legacy" };
/*  54 */   public static final String[] emailNodeList = { "configuration", "email" };
/*  55 */   public static final String[] milestoneNodeList = { "configuration", "milestone" };
/*     */   
/*     */   public static final int PFMPushReceiveLen = 542;
/*     */   
/*     */   public static final int PFMPushMaxReceiveLen = 550;
/*     */   public static final int PFMPushErrorStrPosition = 342;
/*     */   public static final String PFMPushOK = "PFM Push to Legacy Completed Successfully";
/*     */   public static final String PFMPushProductAdded = "PRODUCT ADDED";
/*     */   public static final String PFMPushProductChanged = "PRODUCT CHANGED";
/*     */   public static final String PFMPushNonFilter = "PFMNONPUSH";
/*     */   public static final String BrokerError = "BROKER ERROR: ** PFM PUSH ** ";
/*     */   public static final String ServiceError = "SERVICE ERROR: ** PFM RECEIVE ** ";
/*     */   public static final String LegacyProgramError = "LEGACY/MILESTONE INTERFACE PROGRAM";
/*     */   public static final String LegacyProgramErrorSubject = "PFM Push - Legacy Program Error!";
/*     */   public static final String LegacyProgramErrorBody = "The Milestone PFM Push application is receiving an error when communicating with the Legacy Service Program";
/*     */   public static final String MODIFIED = "has been modified:";
/*     */   public static final String CREATED = "has been created:";
/*     */   public static final String COMPONENT_CODE = "Push PFM";
/*     */   public static final String configFile = "pushbean.config";
/*  74 */   public static String destEmailAddr = "LCSSupport@umusic.com";
/*  75 */   public static String ccEmailAddr = "milestone@umusic.com";
/*  76 */   public static String emailHostServer = "usushntws51";
/*  77 */   public static String adminEmailAddr = "Milestone";
/*  78 */   public static String adminEmailName = "Milestone";
/*  79 */   public static String emailSubject = "Milestone Application Error - EJB communication error!";
/*  80 */   public static String emailMessage = "The Milestone application is receiving an error when communicating with the Enterprise Java Bean";
/*  81 */   public static String emailContentType = "text/html";
/*  82 */   public static String MilestoneServer = "";
/*  83 */   public static String emailLegacySupport = "FishersLegacySupport@umusic.com";
/*     */ 
/*     */   
/*  86 */   public static final String[] appSettingsNodeList = { "configuration", "appSettings" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean auditLogActive = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean debug = false;
/*     */ 
/*     */ 
/*     */   
/*  99 */   protected static PushCommunication pushCommunication = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PushCommunication() {
/* 108 */     XMLUtil xmlUtil = new XMLUtil("pushbean.config");
/*     */     
/* 110 */     if (xmlUtil.getDocument() != null) {
/*     */ 
/*     */       
/* 113 */       adminEmailAddr = xmlUtil.getElementValueByParentList(emailNodeList, "admin-addr", adminEmailAddr);
/* 114 */       adminEmailName = xmlUtil.getElementValueByParentList(emailNodeList, "admin-name", adminEmailName);
/* 115 */       emailHostServer = xmlUtil.getElementValueByParentList(emailNodeList, "host-server", emailHostServer);
/* 116 */       emailContentType = xmlUtil.getElementValueByParentList(emailNodeList, "content-type", emailContentType);
/* 117 */       emailLegacySupport = xmlUtil.getElementValueByParentList(emailNodeList, "legacy-support", emailLegacySupport);
/* 118 */       ccEmailAddr = xmlUtil.getElementValueByParentList(emailNodeList, "cc-list", ccEmailAddr);
/*     */ 
/*     */       
/* 121 */       MilestoneServer = xmlUtil.getElementValueByParentList(milestoneNodeList, "server", "");
/*     */ 
/*     */       
/* 124 */       auditLogActive = xmlUtil.getBooleanValueByParentList(appSettingsNodeList, "auditLogActive", auditLogActive);
/*     */     } else {
/*     */       
/* 127 */       sendJavaMailOpt("", "", "** Push Communication unable to initialize **\n\nUnable to find document", "Initialize", "");
/*     */     } 
/*     */   }
/*     */   
/* 131 */   public static void log(String s) { System.out.println("Push PFM: " + s); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PushCommunication getInstance() {
/* 139 */     log("<<<< PushCommunication get instance call");
/* 140 */     if (pushCommunication == null)
/*     */     {
/* 142 */       pushCommunication = new PushCommunication();
/*     */     }
/* 144 */     return pushCommunication;
/*     */   }
/*     */ 
/*     */   
/*     */   public String SendPFM(PushPFM pushPFM) {
/* 149 */     String strReply = "";
/*     */ 
/*     */     
/* 152 */     PushHome pushHome = null;
/* 153 */     PushRemote pushRemote = null;
/*     */     
/*     */     try {
/* 156 */       Properties h = new Properties();
/* 157 */       h.put("java.naming.factory.initial", JdbcConnector.INITIAL_CONTEXT_FACTORY);
/* 158 */       h.put("java.naming.provider.url", JdbcConnector.PROVIDER_URL);
/* 159 */       Context initial = new InitialContext(h);
/*     */       
/* 161 */       pushHome = (PushHome)initial.lookup("PushBean");
/*     */       
/* 163 */       if (pushHome == null) {
/*     */         
/* 165 */         log("NULL PushHome.");
/* 166 */         sendJavaMailOpt("", "", "** Unable to find EJB home object for (PushBean) **", pushPFM.getUserId(), "");
/* 167 */         strReply = "** Unable to find EJB home object for (PushBean) **";
/*     */       }
/*     */       else {
/*     */         
/* 171 */         log("PushHome not null.");
/* 172 */         pushRemote = pushHome.create();
/* 173 */         if (pushRemote == null)
/*     */         {
/* 175 */           sendJavaMailOpt("", "", "** Unable to create EJB remote object for (PushBean) **", pushPFM.getUserId(), "");
/* 176 */           strReply = "** Unable to create EJB remote object for (PushBean) **";
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 186 */           strReply = pushRemote.SendPFM(pushPFM.toString(), pushPFM.getUserId());
/*     */         }
/*     */       
/*     */       } 
/* 190 */     } catch (Exception e) {
/*     */       
/* 192 */       log(e.getMessage());
/* 193 */       strReply = "BROKER ERROR: ** PFM PUSH ** " + e.getMessage();
/* 194 */       sendJavaMailOpt("", "", e.getMessage(), pushPFM.getUserId(), "");
/*     */     } 
/*     */     
/* 197 */     return strReply;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String pushPFMLegacy(Pfm pfm, Selection selection, Context context) {
/* 206 */     boolean IsAPNG = selection.getLabel().getAPGNInd();
/*     */     
/* 208 */     boolean IsExclude = false;
/*     */ 
/*     */ 
/*     */     
/* 212 */     String familyName = ReleasingFamily.getName(selection.getReleaseFamilyId());
/* 213 */     if (PushPFM.getPushExcludeFamilies() != null && PushPFM.getPushExcludeFamilies().containsKey(familyName.trim().toUpperCase())) {
/* 214 */       IsExclude = true;
/*     */     }
/*     */     
/* 217 */     if (IsExclude) {
/* 218 */       return "PFMNONPUSH";
/*     */     }
/*     */ 
/*     */     
/* 222 */     String userIdStr = ((User)context.getSessionValue("user")).getLogin();
/* 223 */     if (userIdStr == null || userIdStr.equals("")) {
/* 224 */       userIdStr = "Mileston";
/*     */     }
/*     */     
/* 227 */     PushPFM pushPFM = new PushPFM(selection.getIsDigital(), 
/* 228 */         selection.getReleaseType().getAbbreviation(), 
/* 229 */         userIdStr, IsAPNG);
/*     */ 
/*     */     
/* 232 */     pushPFM.setPFM(pfm, selection);
/*     */ 
/*     */     
/* 235 */     PushCommunication push = getInstance();
/*     */ 
/*     */     
/* 238 */     String result = push.SendPFM(pushPFM);
/*     */ 
/*     */ 
/*     */     
/* 242 */     if (result.indexOf("BROKER ERROR: ** PFM PUSH ** ") != -1) {
/* 243 */       pushPFM.setError(result.trim());
/*     */     } else {
/* 245 */       pushPFM.setError(result.substring(342, result.length()).trim());
/* 246 */     }  sendJavaMailOpt("", "", "test erro push to legacy", "839", "");
/*     */ 
/*     */ 
/*     */     
/* 250 */     String reply = pushPFM.getError().trim();
/* 251 */     if (reply.equals("") || reply.equals("PRODUCT ADDED") || reply.equals("PRODUCT CHANGED")) {
/*     */ 
/*     */       
/* 254 */       if (reply.equals(""))
/* 255 */         pushPFM.setError("PFM Push to Legacy Completed Successfully"); 
/* 256 */       pushAudit(pushPFM, true);
/* 257 */       return pushPFM.getError();
/*     */     } 
/* 259 */     pushAudit(pushPFM, false);
/* 260 */     return pushPFM.getError().replace('\'', ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void pushAudit(PushPFM pushPFM, boolean result) {
/*     */     try {
/* 269 */       String resultStr = result ? "1" : "0";
/*     */       
/* 271 */       StringBuffer query = new StringBuffer();
/* 272 */       query.append("SET QUOTED_IDENTIFIER OFF INSERT INTO [dbo].[Push_Audit]([push_user_id], [push_type], [push_id], [push_message], [push_reply], [push_ok], [push_release_id])");
/* 273 */       query.append(" VALUES(");
/* 274 */       query.append("'" + pushPFM.getUserId() + "',");
/* 275 */       query.append("'" + pushPFM.getPushType() + "',");
/* 276 */       query.append("'" + pushPFM.getProductNo() + "',");
/*     */       
/* 278 */       query.append("'" + MilestoneHelper.escapeSingleQuotes(pushPFM.getMessage()) + "',");
/* 279 */       query.append("\"" + pushPFM.getError() + "\",");
/* 280 */       query.append(String.valueOf(resultStr) + ",");
/* 281 */       query.append(String.valueOf(String.valueOf(pushPFM.getReleaseId())) + ")");
/*     */       
/* 283 */       JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 284 */       connector.runQuery();
/* 285 */       connector.close();
/* 286 */       log("<<< PFM Push Audit added " + pushPFM.getError());
/* 287 */     } catch (Exception e) {
/*     */       
/* 289 */       log("<<< PFM Push Audit write error:\n" + e.getMessage());
/* 290 */       WriteCrossRoadsAuditLog("ErrorEmail", "Push PFM", "Push PFM: " + MilestoneServer, "PFM Push audit write error:<br>" + e.getMessage(), "PFM Push audit write error:");
/*     */     } 
/*     */ 
/*     */     
/* 294 */     if (pushPFM.getError().indexOf("LEGACY/MILESTONE INTERFACE PROGRAM") != -1) {
/* 295 */       sendJavaMailOpt("The Milestone PFM Push application is receiving an error when communicating with the Legacy Service Program", "PFM Push - Legacy Program Error!", pushPFM.getError(), pushPFM.getUserId(), emailLegacySupport);
/*     */     }
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
/*     */   public static void pushPFMLegacyAppendMessge(MessageObject messageObject, String pushMessage) {
/* 310 */     if (pushMessage.equals("PFMNONPUSH")) {
/*     */       
/* 312 */       log("<<< PFM Push NON-PUSH FILTER");
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     log("<<< PFM Push Message added to Email " + pushMessage);
/*     */     
/* 318 */     boolean IsPushOk = false;
/*     */ 
/*     */     
/* 321 */     if (pushMessage.equals("PRODUCT ADDED") || 
/* 322 */       pushMessage.equals("PRODUCT CHANGED") || 
/* 323 */       pushMessage.equals("PFM Push to Legacy Completed Successfully")) {
/* 324 */       IsPushOk = true;
/*     */     }
/*     */     
/* 327 */     StringBuffer newBody = new StringBuffer();
/*     */ 
/*     */     
/* 330 */     String emailBody = messageObject.getEmailBody();
/*     */ 
/*     */     
/* 333 */     int pos = -1;
/* 334 */     if (IsPushOk) {
/*     */       
/* 336 */       newBody.append(emailBody);
/*     */       
/* 338 */       pos = IndexOfInsert(newBody.toString());
/* 339 */       if (pos > -1)
/*     */       {
/* 341 */         newBody.deleteCharAt(pos);
/* 342 */         newBody.insert(pos, " and updated in Legacy<BR>");
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 348 */       messageObject.setEmailSubject("FAILED - " + messageObject.getEmailSubject());
/*     */       
/* 350 */       newBody.append("<span STYLE='font-size: 12pt; color: #ff0000'>");
/* 351 */       newBody.append("<b>FAILED - Legacy Not Updated</b>");
/* 352 */       newBody.append("</span><BR>");
/*     */ 
/*     */       
/* 355 */       newBody.append(emailBody);
/*     */ 
/*     */       
/* 358 */       pos = IndexOfInsert(newBody.toString());
/* 359 */       if (pos > -1)
/*     */       {
/*     */         
/* 362 */         if (pushMessage.indexOf("BROKER ERROR: ** PFM PUSH ** ") != -1) {
/* 363 */           newBody.insert(pos, " and NOT updated in Legacy <br>");
/*     */         } else {
/* 365 */           newBody.deleteCharAt(pos);
/* 366 */           newBody.insert(pos, " and NOT updated in Legacy <br><span STYLE='font-size: 12pt; color: #ff0000'><b>" + 
/*     */               
/* 368 */               pushMessage + "</b></span><BR>");
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 374 */     messageObject.setEmailBody(newBody.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int IndexOfInsert(String inStr) {
/* 382 */     int pos = inStr.indexOf("has been modified:");
/* 383 */     if (pos != -1) {
/* 384 */       pos += "has been modified:".length() - 1;
/*     */     } else {
/*     */       
/* 387 */       pos = inStr.indexOf("has been created:");
/* 388 */       if (pos != -1)
/* 389 */         pos += "has been created:".length() - 1; 
/*     */     } 
/* 391 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean sendJavaMailOpt(String ovrEmailBody, String ovrEmailSubject, String errorMsg, String userID, String ovrEmailToList) {
/* 401 */     boolean sendStatus = false;
/*     */     
/* 403 */     String body = "<b>" + emailMessage + "</b>";
/* 404 */     String subject = emailSubject;
/* 405 */     String toList = destEmailAddr;
/*     */     
/* 407 */     if (!ovrEmailToList.equals(""))
/* 408 */       toList = ovrEmailToList; 
/* 409 */     if (!ovrEmailBody.equals(""))
/* 410 */       body = "<b>" + ovrEmailBody + "</b>"; 
/* 411 */     if (!ovrEmailSubject.equals("")) {
/* 412 */       subject = ovrEmailSubject;
/*     */     }
/*     */     
/* 415 */     StringTokenizer Addrtokenizer = new StringTokenizer(toList, ";");
/* 416 */     StringTokenizer ccAddrtokenizer = new StringTokenizer(ccEmailAddr, ";");
/*     */     
/* 418 */     InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
/* 419 */     InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
/*     */     
/* 421 */     int x = 0;
/* 422 */     while (Addrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 424 */         iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
/* 425 */         x++;
/* 426 */       } catch (Exception e) {
/* 427 */         log(e.toString());
/* 428 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 433 */     x = 0;
/* 434 */     while (ccAddrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 436 */         ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
/* 437 */         x++;
/* 438 */       } catch (Exception e) {
/* 439 */         log(e.toString());
/* 440 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 446 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */       
/* 450 */       props.put("mail.smtp.host", emailHostServer.trim());
/*     */ 
/*     */       
/* 453 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */       
/* 456 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */       
/* 459 */       message.setFrom(new InternetAddress(adminEmailAddr, adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 463 */       message.setSubject(subject, "utf-8");
/*     */ 
/*     */       
/* 466 */       body = String.valueOf(body) + "<BR><BR><b>" + errorMsg + "</b>" + 
/* 467 */         "<BR><BR><font color='#0000FF'><B> Server: " + MilestoneServer + "</B>:</font>" + 
/* 468 */         "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>";
/*     */ 
/*     */       
/* 471 */       message.setContent(body, emailContentType);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 476 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 479 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 482 */         Transport.send(message);
/* 483 */         log("<<< Session Email Address - Java Mail - Message Sent");
/*     */         
/* 485 */         sendStatus = true;
/*     */       }
/* 487 */       catch (Exception e) {
/* 488 */         e.printStackTrace();
/* 489 */         log("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
/* 490 */         sendStatus = false;
/*     */       } 
/* 492 */     } catch (Exception e) {
/* 493 */       log("<<< Session Email Transport exception " + e.getMessage());
/* 494 */       sendStatus = false;
/*     */     } 
/*     */     
/* 497 */     return sendStatus;
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
/* 535 */   public static boolean WriteCrossRoadsAuditLog(String type, String method, String description, String errMsg, String subject) { return true; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\push\PushCommunication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */