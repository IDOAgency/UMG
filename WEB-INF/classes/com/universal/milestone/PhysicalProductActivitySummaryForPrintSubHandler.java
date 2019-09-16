package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.PhysicalProductActivitySummaryForPrintSubHandler;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import inetsoft.report.ReportElement;
import inetsoft.report.XStyleSheet;
import java.util.Collections;
import java.util.Vector;

public class PhysicalProductActivitySummaryForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hNsl";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static Context m_context;
  
  public PhysicalProductActivitySummaryForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hNsl");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillPhysicalProductActivitySummaryScheduleUpdateForPrint(XStyleSheet report, Context context) { // Byte code:
    //   0: getstatic java/awt/Color.lightGray : Ljava/awt/Color;
    //   3: astore_2
    //   4: sipush #4097
    //   7: istore_3
    //   8: bipush #12
    //   10: istore #4
    //   12: bipush #10
    //   14: istore #5
    //   16: ldc2_w 0.3
    //   19: dstore #8
    //   21: new inetsoft/report/SectionBand
    //   24: dup
    //   25: aload_0
    //   26: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   29: astore #10
    //   31: new inetsoft/report/SectionBand
    //   34: dup
    //   35: aload_0
    //   36: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   39: astore #11
    //   41: new inetsoft/report/SectionBand
    //   44: dup
    //   45: aload_0
    //   46: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   49: astore #12
    //   51: new inetsoft/report/SectionBand
    //   54: dup
    //   55: aload_0
    //   56: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   59: astore #13
    //   61: new inetsoft/report/SectionBand
    //   64: dup
    //   65: aload_0
    //   66: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   69: astore #14
    //   71: new inetsoft/report/SectionBand
    //   74: dup
    //   75: aload_0
    //   76: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   79: astore #15
    //   81: new inetsoft/report/SectionBand
    //   84: dup
    //   85: aload_0
    //   86: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   89: astore #16
    //   91: aconst_null
    //   92: astore #17
    //   94: aload_1
    //   95: putstatic com/universal/milestone/PhysicalProductActivitySummaryForPrintSubHandler.m_context : Lcom/techempower/gemini/Context;
    //   98: aload #14
    //   100: iconst_1
    //   101: invokevirtual setVisible : (Z)V
    //   104: aload #14
    //   106: ldc 0.1
    //   108: invokevirtual setHeight : (F)V
    //   111: aload #14
    //   113: iconst_0
    //   114: invokevirtual setShrinkToFit : (Z)V
    //   117: aload #14
    //   119: iconst_0
    //   120: invokevirtual setBottomBorder : (I)V
    //   123: aload #15
    //   125: iconst_1
    //   126: invokevirtual setVisible : (Z)V
    //   129: aload #15
    //   131: ldc 0.05
    //   133: invokevirtual setHeight : (F)V
    //   136: aload #15
    //   138: iconst_0
    //   139: invokevirtual setShrinkToFit : (Z)V
    //   142: aload #15
    //   144: iconst_0
    //   145: invokevirtual setBottomBorder : (I)V
    //   148: aload #16
    //   150: iconst_1
    //   151: invokevirtual setVisible : (Z)V
    //   154: aload #16
    //   156: ldc 0.03
    //   158: invokevirtual setHeight : (F)V
    //   161: aload #16
    //   163: iconst_0
    //   164: invokevirtual setShrinkToFit : (Z)V
    //   167: aload #16
    //   169: iconst_0
    //   170: invokevirtual setBottomBorder : (I)V
    //   173: aload_1
    //   174: invokevirtual getResponse : ()Ljavax/servlet/http/HttpServletResponse;
    //   177: astore #18
    //   179: aload_1
    //   180: ldc 'status'
    //   182: new java/lang/String
    //   185: dup
    //   186: ldc 'start_gathering'
    //   188: invokespecial <init> : (Ljava/lang/String;)V
    //   191: invokevirtual putDelivery : (Ljava/lang/String;Ljava/lang/Object;)V
    //   194: aload_1
    //   195: ldc 'status.jsp'
    //   197: ldc 'hiddenFrame'
    //   199: invokevirtual includeJSP : (Ljava/lang/String;Ljava/lang/String;)Z
    //   202: pop
    //   203: aload #18
    //   205: ldc 'text/plain'
    //   207: invokeinterface setContentType : (Ljava/lang/String;)V
    //   212: aload #18
    //   214: invokeinterface flushBuffer : ()V
    //   219: goto -> 224
    //   222: astore #18
    //   224: aload_1
    //   225: invokestatic getSelectionsForReport : (Lcom/techempower/gemini/Context;)Ljava/util/Vector;
    //   228: astore #18
    //   230: aload_1
    //   231: invokevirtual getResponse : ()Ljavax/servlet/http/HttpServletResponse;
    //   234: astore #19
    //   236: aload_1
    //   237: ldc 'status'
    //   239: new java/lang/String
    //   242: dup
    //   243: ldc 'start_report'
    //   245: invokespecial <init> : (Ljava/lang/String;)V
    //   248: invokevirtual putDelivery : (Ljava/lang/String;Ljava/lang/Object;)V
    //   251: aload_1
    //   252: ldc 'percent'
    //   254: new java/lang/String
    //   257: dup
    //   258: ldc '10'
    //   260: invokespecial <init> : (Ljava/lang/String;)V
    //   263: invokevirtual putDelivery : (Ljava/lang/String;Ljava/lang/Object;)V
    //   266: aload_1
    //   267: ldc 'status.jsp'
    //   269: ldc 'hiddenFrame'
    //   271: invokevirtual includeJSP : (Ljava/lang/String;Ljava/lang/String;)Z
    //   274: pop
    //   275: aload #19
    //   277: ldc 'text/plain'
    //   279: invokeinterface setContentType : (Ljava/lang/String;)V
    //   284: aload #19
    //   286: invokeinterface flushBuffer : ()V
    //   291: goto -> 296
    //   294: astore #19
    //   296: aconst_null
    //   297: astore #19
    //   299: aconst_null
    //   300: astore #20
    //   302: aconst_null
    //   303: astore #21
    //   305: aconst_null
    //   306: astore #22
    //   308: aconst_null
    //   309: astore #23
    //   311: aconst_null
    //   312: astore #24
    //   314: aconst_null
    //   315: astore #25
    //   317: new inetsoft/report/lens/DefaultTableLens
    //   320: dup
    //   321: iconst_2
    //   322: sipush #10000
    //   325: invokespecial <init> : (II)V
    //   328: astore #20
    //   330: iconst_0
    //   331: istore #26
    //   333: aload_1
    //   334: ldc 'reportForm'
    //   336: invokevirtual getSessionValue : (Ljava/lang/String;)Ljava/lang/Object;
    //   339: checkcast com/universal/milestone/Form
    //   342: astore #27
    //   344: aload #18
    //   346: iconst_0
    //   347: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   350: checkcast java/util/Vector
    //   353: astore #28
    //   355: aload #18
    //   357: iconst_1
    //   358: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   361: checkcast java/util/Vector
    //   364: astore #29
    //   366: aload #18
    //   368: iconst_2
    //   369: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   372: checkcast java/util/Vector
    //   375: astore #30
    //   377: aload #18
    //   379: iconst_3
    //   380: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   383: checkcast java/util/Vector
    //   386: astore #31
    //   388: iconst_0
    //   389: istore #32
    //   391: iconst_0
    //   392: istore #33
    //   394: iconst_0
    //   395: istore #34
    //   397: iconst_0
    //   398: istore #35
    //   400: iconst_0
    //   401: istore #36
    //   403: iconst_0
    //   404: istore #37
    //   406: aload #27
    //   408: ldc 'CancelsActivity'
    //   410: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   413: checkcast com/techempower/gemini/FormCheckBox
    //   416: invokevirtual isChecked : ()Z
    //   419: istore #32
    //   421: aload #27
    //   423: ldc 'ChangesActivity'
    //   425: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   428: checkcast com/techempower/gemini/FormCheckBox
    //   431: invokevirtual isChecked : ()Z
    //   434: istore #33
    //   436: aload #27
    //   438: ldc 'DeletesActivity'
    //   440: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   443: checkcast com/techempower/gemini/FormCheckBox
    //   446: invokevirtual isChecked : ()Z
    //   449: istore #34
    //   451: aload #27
    //   453: ldc 'AddsActivity'
    //   455: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   458: checkcast com/techempower/gemini/FormCheckBox
    //   461: invokevirtual isChecked : ()Z
    //   464: istore #35
    //   466: aload #27
    //   468: ldc 'MovesActivity'
    //   470: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   473: checkcast com/techempower/gemini/FormCheckBox
    //   476: invokevirtual isChecked : ()Z
    //   479: istore #36
    //   481: aload #27
    //   483: ldc 'AllActivity'
    //   485: invokevirtual getElement : (Ljava/lang/String;)Lcom/techempower/gemini/FormElement;
    //   488: checkcast com/techempower/gemini/FormCheckBox
    //   491: invokevirtual isChecked : ()Z
    //   494: istore #37
    //   496: aload #27
    //   498: ldc 'beginEffectiveDate'
    //   500: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   503: ifnull -> 532
    //   506: aload #27
    //   508: ldc 'beginEffectiveDate'
    //   510: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   513: invokevirtual length : ()I
    //   516: ifle -> 532
    //   519: aload #27
    //   521: ldc 'beginEffectiveDate'
    //   523: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   526: invokestatic getDate : (Ljava/lang/String;)Ljava/util/Calendar;
    //   529: goto -> 533
    //   532: aconst_null
    //   533: astore #38
    //   535: aload #27
    //   537: ldc 'endEffectiveDate'
    //   539: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   542: ifnull -> 571
    //   545: aload #27
    //   547: ldc 'endEffectiveDate'
    //   549: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   552: invokevirtual length : ()I
    //   555: ifle -> 571
    //   558: aload #27
    //   560: ldc 'endEffectiveDate'
    //   562: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   565: invokestatic getDate : (Ljava/lang/String;)Ljava/util/Calendar;
    //   568: goto -> 572
    //   571: aconst_null
    //   572: astore #39
    //   574: aload #27
    //   576: ldc 'beginDate'
    //   578: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   581: ifnull -> 610
    //   584: aload #27
    //   586: ldc 'beginDate'
    //   588: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   591: invokevirtual length : ()I
    //   594: ifle -> 610
    //   597: aload #27
    //   599: ldc 'beginDate'
    //   601: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   604: invokestatic getDate : (Ljava/lang/String;)Ljava/util/Calendar;
    //   607: goto -> 611
    //   610: aconst_null
    //   611: astore #40
    //   613: aload #27
    //   615: ldc 'endDate'
    //   617: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   620: ifnull -> 649
    //   623: aload #27
    //   625: ldc 'endDate'
    //   627: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   630: invokevirtual length : ()I
    //   633: ifle -> 649
    //   636: aload #27
    //   638: ldc 'endDate'
    //   640: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   643: invokestatic getDate : (Ljava/lang/String;)Ljava/util/Calendar;
    //   646: goto -> 650
    //   649: aconst_null
    //   650: astore #41
    //   652: aload #27
    //   654: ldc 'beginEffectiveDate'
    //   656: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   659: ldc ''
    //   661: invokevirtual equals : (Ljava/lang/Object;)Z
    //   664: ifeq -> 682
    //   667: aload #27
    //   669: ldc 'endEffectiveDate'
    //   671: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   674: ldc ''
    //   676: invokevirtual equals : (Ljava/lang/Object;)Z
    //   679: ifne -> 707
    //   682: aload_0
    //   683: ldc 'StartEffectiveDateText'
    //   685: aload #38
    //   687: invokestatic getFormatedDate : (Ljava/util/Calendar;)Ljava/lang/String;
    //   690: invokevirtual setElement : (Ljava/lang/String;Ljava/lang/Object;)V
    //   693: aload_0
    //   694: ldc 'EndEffectiveDateText'
    //   696: aload #39
    //   698: invokestatic getFormatedDate : (Ljava/util/Calendar;)Ljava/lang/String;
    //   701: invokevirtual setElement : (Ljava/lang/String;Ljava/lang/Object;)V
    //   704: goto -> 731
    //   707: aload_0
    //   708: ldc 'StartEffectiveDateLabel'
    //   710: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   713: aload_0
    //   714: ldc 'EndEffectiveDateLabel'
    //   716: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   719: aload_0
    //   720: ldc 'StartEffectiveDateText'
    //   722: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   725: aload_0
    //   726: ldc 'EndEffectiveDateText'
    //   728: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   731: aload #27
    //   733: ldc 'beginDate'
    //   735: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   738: ldc ''
    //   740: invokevirtual equals : (Ljava/lang/Object;)Z
    //   743: ifeq -> 761
    //   746: aload #27
    //   748: ldc 'endDate'
    //   750: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
    //   753: ldc ''
    //   755: invokevirtual equals : (Ljava/lang/Object;)Z
    //   758: ifne -> 786
    //   761: aload_0
    //   762: ldc 'StartStreetDateText'
    //   764: aload #40
    //   766: invokestatic getFormatedDate : (Ljava/util/Calendar;)Ljava/lang/String;
    //   769: invokevirtual setElement : (Ljava/lang/String;Ljava/lang/Object;)V
    //   772: aload_0
    //   773: ldc 'EndStreetDateText'
    //   775: aload #41
    //   777: invokestatic getFormatedDate : (Ljava/util/Calendar;)Ljava/lang/String;
    //   780: invokevirtual setElement : (Ljava/lang/String;Ljava/lang/Object;)V
    //   783: goto -> 810
    //   786: aload_0
    //   787: ldc 'StartStreetDateLabel'
    //   789: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   792: aload_0
    //   793: ldc 'EndStreetDateLabel'
    //   795: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   798: aload_0
    //   799: ldc 'StartStreetDateText'
    //   801: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   804: aload_0
    //   805: ldc 'EndStreetDateText'
    //   807: invokestatic hideElement : (Linetsoft/report/XStyleSheet;Ljava/lang/String;)V
    //   810: new java/text/SimpleDateFormat
    //   813: dup
    //   814: ldc 'MMMM d, yyyy'
    //   816: invokespecial <init> : (Ljava/lang/String;)V
    //   819: astore #42
    //   821: aload #42
    //   823: new java/util/Date
    //   826: dup
    //   827: invokespecial <init> : ()V
    //   830: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   833: astore #43
    //   835: aload_0
    //   836: ldc 'crs_bottomdate'
    //   838: aload #43
    //   840: invokevirtual setElement : (Ljava/lang/String;Ljava/lang/Object;)V
    //   843: iconst_0
    //   844: istore #44
    //   846: goto -> 3630
    //   849: iload #44
    //   851: ifne -> 867
    //   854: iload #33
    //   856: ifne -> 867
    //   859: iload #37
    //   861: ifne -> 867
    //   864: goto -> 3627
    //   867: iload #44
    //   869: iconst_1
    //   870: if_icmpne -> 886
    //   873: iload #35
    //   875: ifne -> 886
    //   878: iload #37
    //   880: ifne -> 886
    //   883: goto -> 3627
    //   886: iload #44
    //   888: iconst_2
    //   889: if_icmpne -> 905
    //   892: iload #32
    //   894: ifne -> 905
    //   897: iload #37
    //   899: ifne -> 905
    //   902: goto -> 3627
    //   905: iload #44
    //   907: iconst_3
    //   908: if_icmpne -> 924
    //   911: iload #34
    //   913: ifne -> 924
    //   916: iload #37
    //   918: ifne -> 924
    //   921: goto -> 3627
    //   924: iload #44
    //   926: iconst_4
    //   927: if_icmpne -> 943
    //   930: iload #36
    //   932: ifne -> 943
    //   935: iload #37
    //   937: ifne -> 943
    //   940: goto -> 3627
    //   943: aload #18
    //   945: iload #44
    //   947: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   950: checkcast java/util/Vector
    //   953: astore #45
    //   955: aload #45
    //   957: invokevirtual size : ()I
    //   960: istore #46
    //   962: iload #46
    //   964: istore #47
    //   966: bipush #8
    //   968: istore #48
    //   970: iconst_0
    //   971: istore #26
    //   973: ldc ''
    //   975: astore #49
    //   977: iload #44
    //   979: tableswitch default -> 1047, 0 -> 1012, 1 -> 1019, 2 -> 1026, 3 -> 1033, 4 -> 1040
    //   1012: ldc 'Changed'
    //   1014: astore #49
    //   1016: goto -> 1047
    //   1019: ldc 'Added'
    //   1021: astore #49
    //   1023: goto -> 1047
    //   1026: ldc 'Cancelled'
    //   1028: astore #49
    //   1030: goto -> 1047
    //   1033: ldc 'Deleted'
    //   1035: astore #49
    //   1037: goto -> 1047
    //   1040: ldc 'Moved'
    //   1042: astore #49
    //   1044: goto -> 1047
    //   1047: new inetsoft/report/SectionBand
    //   1050: dup
    //   1051: aload_0
    //   1052: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   1055: astore #10
    //   1057: aload #10
    //   1059: ldc 0.65
    //   1061: invokevirtual setHeight : (F)V
    //   1064: aload #10
    //   1066: iconst_1
    //   1067: invokevirtual setShrinkToFit : (Z)V
    //   1070: aload #10
    //   1072: iconst_1
    //   1073: invokevirtual setVisible : (Z)V
    //   1076: iconst_0
    //   1077: istore #26
    //   1079: new inetsoft/report/lens/DefaultTableLens
    //   1082: dup
    //   1083: iconst_1
    //   1084: bipush #8
    //   1086: invokespecial <init> : (II)V
    //   1089: astore #21
    //   1091: aload #21
    //   1093: iconst_0
    //   1094: bipush #100
    //   1096: invokevirtual setColWidth : (II)V
    //   1099: aload #21
    //   1101: iconst_1
    //   1102: bipush #100
    //   1104: invokevirtual setColWidth : (II)V
    //   1107: aload #21
    //   1109: iconst_2
    //   1110: bipush #100
    //   1112: invokevirtual setColWidth : (II)V
    //   1115: aload #49
    //   1117: ldc 'Changed'
    //   1119: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1122: ifne -> 1135
    //   1125: aload #49
    //   1127: ldc 'Moved'
    //   1129: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1132: ifeq -> 1156
    //   1135: aload #21
    //   1137: iconst_3
    //   1138: sipush #200
    //   1141: invokevirtual setColWidth : (II)V
    //   1144: aload #21
    //   1146: iconst_4
    //   1147: sipush #150
    //   1150: invokevirtual setColWidth : (II)V
    //   1153: goto -> 1173
    //   1156: aload #21
    //   1158: iconst_3
    //   1159: sipush #200
    //   1162: invokevirtual setColWidth : (II)V
    //   1165: aload #21
    //   1167: iconst_4
    //   1168: bipush #30
    //   1170: invokevirtual setColWidth : (II)V
    //   1173: aload #21
    //   1175: iconst_5
    //   1176: sipush #200
    //   1179: invokevirtual setColWidth : (II)V
    //   1182: aload #21
    //   1184: bipush #6
    //   1186: sipush #130
    //   1189: invokevirtual setColWidth : (II)V
    //   1192: aload #21
    //   1194: bipush #7
    //   1196: sipush #130
    //   1199: invokevirtual setColWidth : (II)V
    //   1202: aload #21
    //   1204: iconst_0
    //   1205: invokevirtual setColBorder : (I)V
    //   1208: aload #21
    //   1210: iload #26
    //   1212: iconst_1
    //   1213: isub
    //   1214: iconst_0
    //   1215: invokevirtual setRowBorder : (II)V
    //   1218: aload #21
    //   1220: iload #26
    //   1222: sipush #4097
    //   1225: invokevirtual setRowBorder : (II)V
    //   1228: aload #21
    //   1230: iload #26
    //   1232: getstatic java/awt/Color.black : Ljava/awt/Color;
    //   1235: invokevirtual setRowBorderColor : (ILjava/awt/Color;)V
    //   1238: aload #21
    //   1240: iload #26
    //   1242: iconst_0
    //   1243: bipush #17
    //   1245: invokevirtual setAlignment : (III)V
    //   1248: aload #49
    //   1250: ldc 'Changed'
    //   1252: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1255: ifeq -> 1272
    //   1258: aload #21
    //   1260: iload #26
    //   1262: iconst_0
    //   1263: ldc_w 'Date\\nChanged'
    //   1266: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1269: goto -> 1365
    //   1272: aload #49
    //   1274: ldc 'Added'
    //   1276: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1279: ifeq -> 1296
    //   1282: aload #21
    //   1284: iload #26
    //   1286: iconst_0
    //   1287: ldc_w 'Date\\nAdded'
    //   1290: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1293: goto -> 1365
    //   1296: aload #49
    //   1298: ldc 'Cancelled'
    //   1300: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1303: ifeq -> 1320
    //   1306: aload #21
    //   1308: iload #26
    //   1310: iconst_0
    //   1311: ldc_w 'Date\\nCancelled'
    //   1314: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1317: goto -> 1365
    //   1320: aload #49
    //   1322: ldc 'Deleted'
    //   1324: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1327: ifeq -> 1344
    //   1330: aload #21
    //   1332: iload #26
    //   1334: iconst_0
    //   1335: ldc_w 'Date\\nDeleted'
    //   1338: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1341: goto -> 1365
    //   1344: aload #49
    //   1346: ldc 'Moved'
    //   1348: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1351: ifeq -> 1365
    //   1354: aload #21
    //   1356: iload #26
    //   1358: iconst_0
    //   1359: ldc_w 'Date\\nMoved'
    //   1362: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1365: aload #21
    //   1367: iload #26
    //   1369: iconst_1
    //   1370: ldc_w 'Releasing Family/\\nLabel'
    //   1373: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1376: aload #21
    //   1378: iload #26
    //   1380: iconst_2
    //   1381: ldc_w 'Artist'
    //   1384: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1387: aload #21
    //   1389: iload #26
    //   1391: iconst_3
    //   1392: ldc_w 'Title'
    //   1395: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1398: aload #49
    //   1400: ldc 'Changed'
    //   1402: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1405: ifeq -> 1433
    //   1408: aload #21
    //   1410: iload #26
    //   1412: iconst_4
    //   1413: ldc_w 'Previous\\nLocal Prod #/\\nUPC'
    //   1416: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1419: aload #21
    //   1421: iload #26
    //   1423: iconst_5
    //   1424: ldc_w 'Current\\nLocal Prod #/\\nUPC'
    //   1427: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1430: goto -> 1478
    //   1433: aload #49
    //   1435: ldc 'Moved'
    //   1437: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1440: ifeq -> 1457
    //   1443: aload #21
    //   1445: iload #26
    //   1447: iconst_4
    //   1448: ldc_w 'Local Prod #/\\nUPC'
    //   1451: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1454: goto -> 1478
    //   1457: aload #21
    //   1459: iload #26
    //   1461: iconst_4
    //   1462: ldc ''
    //   1464: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1467: aload #21
    //   1469: iload #26
    //   1471: iconst_5
    //   1472: ldc_w 'Local Prod #/\\nUPC'
    //   1475: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1478: aload #49
    //   1480: ldc 'Moved'
    //   1482: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1485: ifeq -> 1502
    //   1488: aload #21
    //   1490: iload #26
    //   1492: iconst_5
    //   1493: ldc_w 'Format/\\nSub-Format'
    //   1496: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1499: goto -> 1514
    //   1502: aload #21
    //   1504: iload #26
    //   1506: bipush #6
    //   1508: ldc_w 'Format/\\nSub-Format'
    //   1511: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1514: aload #49
    //   1516: ldc 'Moved'
    //   1518: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1521: ifeq -> 1551
    //   1524: aload #21
    //   1526: iload #26
    //   1528: bipush #6
    //   1530: ldc_w 'Previous Street/\\nShip Date/\\nStatus'
    //   1533: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1536: aload #21
    //   1538: iload #26
    //   1540: bipush #7
    //   1542: ldc_w 'Current Street/\\nShip Date/\\nStatus'
    //   1545: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1548: goto -> 1563
    //   1551: aload #21
    //   1553: iload #26
    //   1555: bipush #7
    //   1557: ldc_w 'Street/\\nShip Date'
    //   1560: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1563: aload #21
    //   1565: iload #26
    //   1567: new java/awt/Font
    //   1570: dup
    //   1571: ldc_w 'Arial'
    //   1574: iconst_3
    //   1575: bipush #10
    //   1577: invokespecial <init> : (Ljava/lang/String;II)V
    //   1580: invokevirtual setRowFont : (ILjava/awt/Font;)V
    //   1583: aload #21
    //   1585: iload #26
    //   1587: sipush #4097
    //   1590: invokevirtual setRowBorder : (II)V
    //   1593: aload #21
    //   1595: iload #26
    //   1597: getstatic java/awt/Color.black : Ljava/awt/Color;
    //   1600: invokevirtual setRowBorderColor : (ILjava/awt/Color;)V
    //   1603: aload #10
    //   1605: aload #21
    //   1607: new java/awt/Rectangle
    //   1610: dup
    //   1611: iconst_0
    //   1612: iconst_0
    //   1613: sipush #800
    //   1616: bipush #50
    //   1618: invokespecial <init> : (IIII)V
    //   1621: invokevirtual addTable : (Linetsoft/report/TableLens;Ljava/awt/Rectangle;)Ljava/lang/String;
    //   1624: pop
    //   1625: aload #10
    //   1627: iconst_0
    //   1628: invokevirtual setBottomBorder : (I)V
    //   1631: aload #10
    //   1633: iconst_0
    //   1634: invokevirtual setTopBorder : (I)V
    //   1637: new inetsoft/report/lens/DefaultTableLens
    //   1640: dup
    //   1641: iconst_1
    //   1642: bipush #10
    //   1644: invokespecial <init> : (II)V
    //   1647: astore #24
    //   1649: new inetsoft/report/SectionBand
    //   1652: dup
    //   1653: aload_0
    //   1654: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   1657: astore #11
    //   1659: aload #11
    //   1661: ldc_w 0.25
    //   1664: invokevirtual setHeight : (F)V
    //   1667: aload #11
    //   1669: iconst_1
    //   1670: invokevirtual setShrinkToFit : (Z)V
    //   1673: aload #11
    //   1675: iconst_1
    //   1676: invokevirtual setVisible : (Z)V
    //   1679: aload #11
    //   1681: iconst_0
    //   1682: invokevirtual setBottomBorder : (I)V
    //   1685: aload #11
    //   1687: iconst_0
    //   1688: invokevirtual setLeftBorder : (I)V
    //   1691: aload #11
    //   1693: iconst_0
    //   1694: invokevirtual setRightBorder : (I)V
    //   1697: aload #11
    //   1699: iconst_0
    //   1700: invokevirtual setTopBorder : (I)V
    //   1703: iconst_0
    //   1704: istore #26
    //   1706: aload #24
    //   1708: iload #26
    //   1710: iconst_0
    //   1711: iconst_2
    //   1712: invokevirtual setAlignment : (III)V
    //   1715: aload #24
    //   1717: iload #26
    //   1719: iconst_0
    //   1720: new java/awt/Dimension
    //   1723: dup
    //   1724: bipush #10
    //   1726: iconst_1
    //   1727: invokespecial <init> : (II)V
    //   1730: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   1733: aload #24
    //   1735: iload #26
    //   1737: iconst_0
    //   1738: aload #49
    //   1740: invokevirtual setObject : (IILjava/lang/Object;)V
    //   1743: aload #24
    //   1745: iload #26
    //   1747: new java/awt/Font
    //   1750: dup
    //   1751: ldc_w 'Arial'
    //   1754: iconst_3
    //   1755: bipush #12
    //   1757: invokespecial <init> : (Ljava/lang/String;II)V
    //   1760: invokevirtual setRowFont : (ILjava/awt/Font;)V
    //   1763: aload #11
    //   1765: aload #24
    //   1767: new java/awt/Rectangle
    //   1770: dup
    //   1771: sipush #800
    //   1774: sipush #800
    //   1777: invokespecial <init> : (II)V
    //   1780: invokevirtual addTable : (Linetsoft/report/TableLens;Ljava/awt/Rectangle;)Ljava/lang/String;
    //   1783: pop
    //   1784: aload #14
    //   1786: iconst_1
    //   1787: invokevirtual setVisible : (Z)V
    //   1790: aload #14
    //   1792: ldc 0.1
    //   1794: invokevirtual setHeight : (F)V
    //   1797: aload #14
    //   1799: iconst_0
    //   1800: invokevirtual setShrinkToFit : (Z)V
    //   1803: aload #14
    //   1805: iconst_0
    //   1806: invokevirtual setBottomBorder : (I)V
    //   1809: new inetsoft/report/lens/DefaultSectionLens
    //   1812: dup
    //   1813: aconst_null
    //   1814: aload #17
    //   1816: aload #15
    //   1818: invokespecial <init> : (Linetsoft/report/SectionBand;Linetsoft/report/SectionLens;Linetsoft/report/SectionBand;)V
    //   1821: astore #17
    //   1823: new inetsoft/report/lens/DefaultSectionLens
    //   1826: dup
    //   1827: aconst_null
    //   1828: aload #17
    //   1830: aload #11
    //   1832: invokespecial <init> : (Linetsoft/report/SectionBand;Linetsoft/report/SectionLens;Linetsoft/report/SectionBand;)V
    //   1835: astore #17
    //   1837: new inetsoft/report/lens/DefaultSectionLens
    //   1840: dup
    //   1841: aconst_null
    //   1842: aload #17
    //   1844: aload #15
    //   1846: invokespecial <init> : (Linetsoft/report/SectionBand;Linetsoft/report/SectionLens;Linetsoft/report/SectionBand;)V
    //   1849: astore #17
    //   1851: aload #49
    //   1853: ldc 'Changed'
    //   1855: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1858: ifne -> 1871
    //   1861: aload #49
    //   1863: ldc 'Moved'
    //   1865: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1868: ifeq -> 1880
    //   1871: aload #45
    //   1873: invokestatic groupForChanges : (Ljava/util/Vector;)Ljava/util/Vector;
    //   1876: pop
    //   1877: goto -> 1926
    //   1880: aload #45
    //   1882: bipush #6
    //   1884: invokestatic setSelectionSorting : (Ljava/util/Vector;I)V
    //   1887: aload #45
    //   1889: invokestatic sort : (Ljava/util/List;)V
    //   1892: aload #45
    //   1894: iconst_3
    //   1895: invokestatic setSelectionSorting : (Ljava/util/Vector;I)V
    //   1898: aload #45
    //   1900: invokestatic sort : (Ljava/util/List;)V
    //   1903: aload #45
    //   1905: iconst_4
    //   1906: invokestatic setSelectionSorting : (Ljava/util/Vector;I)V
    //   1909: aload #45
    //   1911: invokestatic sort : (Ljava/util/List;)V
    //   1914: aload #45
    //   1916: bipush #27
    //   1918: invokestatic setSelectionSorting : (Ljava/util/Vector;I)V
    //   1921: aload #45
    //   1923: invokestatic sort : (Ljava/util/List;)V
    //   1926: iconst_2
    //   1927: istore #50
    //   1929: iload #47
    //   1931: bipush #10
    //   1933: idiv
    //   1934: istore #51
    //   1936: iconst_0
    //   1937: istore #52
    //   1939: goto -> 3576
    //   1942: iload #47
    //   1944: ifne -> 1950
    //   1947: goto -> 3573
    //   1950: iload #52
    //   1952: iload #51
    //   1954: idiv
    //   1955: istore #53
    //   1957: iload #53
    //   1959: iconst_1
    //   1960: if_icmple -> 1974
    //   1963: iload #53
    //   1965: bipush #10
    //   1967: if_icmpge -> 1974
    //   1970: iload #53
    //   1972: istore #50
    //   1974: aload_1
    //   1975: invokevirtual getResponse : ()Ljavax/servlet/http/HttpServletResponse;
    //   1978: astore #54
    //   1980: aload_1
    //   1981: ldc 'status'
    //   1983: new java/lang/String
    //   1986: dup
    //   1987: ldc 'start_report'
    //   1989: invokespecial <init> : (Ljava/lang/String;)V
    //   1992: invokevirtual putDelivery : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1995: aload_1
    //   1996: ldc 'percent'
    //   1998: new java/lang/String
    //   2001: dup
    //   2002: iload #50
    //   2004: bipush #10
    //   2006: imul
    //   2007: invokestatic valueOf : (I)Ljava/lang/String;
    //   2010: invokespecial <init> : (Ljava/lang/String;)V
    //   2013: invokevirtual putDelivery : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2016: aload_1
    //   2017: ldc 'status.jsp'
    //   2019: ldc 'hiddenFrame'
    //   2021: invokevirtual includeJSP : (Ljava/lang/String;Ljava/lang/String;)Z
    //   2024: pop
    //   2025: aload #54
    //   2027: ldc 'text/plain'
    //   2029: invokeinterface setContentType : (Ljava/lang/String;)V
    //   2034: aload #54
    //   2036: invokeinterface flushBuffer : ()V
    //   2041: goto -> 2046
    //   2044: astore #53
    //   2046: aload #45
    //   2048: iload #52
    //   2050: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   2053: ifnonnull -> 2059
    //   2056: goto -> 3573
    //   2059: aload #45
    //   2061: iload #52
    //   2063: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   2066: checkcast com/universal/milestone/Selection
    //   2069: astore #53
    //   2071: iconst_0
    //   2072: istore #26
    //   2074: new inetsoft/report/lens/DefaultTableLens
    //   2077: dup
    //   2078: iconst_2
    //   2079: bipush #8
    //   2081: invokespecial <init> : (II)V
    //   2084: astore #22
    //   2086: aload #22
    //   2088: getstatic java/awt/Color.lightGray : Ljava/awt/Color;
    //   2091: invokevirtual setRowBorderColor : (Ljava/awt/Color;)V
    //   2094: aload #22
    //   2096: iconst_0
    //   2097: bipush #100
    //   2099: invokevirtual setColWidth : (II)V
    //   2102: aload #22
    //   2104: iconst_1
    //   2105: bipush #100
    //   2107: invokevirtual setColWidth : (II)V
    //   2110: aload #22
    //   2112: iconst_2
    //   2113: bipush #100
    //   2115: invokevirtual setColWidth : (II)V
    //   2118: aload #49
    //   2120: ldc 'Changed'
    //   2122: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2125: ifne -> 2138
    //   2128: aload #49
    //   2130: ldc 'Moved'
    //   2132: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2135: ifeq -> 2159
    //   2138: aload #22
    //   2140: iconst_3
    //   2141: sipush #200
    //   2144: invokevirtual setColWidth : (II)V
    //   2147: aload #22
    //   2149: iconst_4
    //   2150: sipush #150
    //   2153: invokevirtual setColWidth : (II)V
    //   2156: goto -> 2176
    //   2159: aload #22
    //   2161: iconst_3
    //   2162: sipush #200
    //   2165: invokevirtual setColWidth : (II)V
    //   2168: aload #22
    //   2170: iconst_4
    //   2171: bipush #30
    //   2173: invokevirtual setColWidth : (II)V
    //   2176: aload #22
    //   2178: iconst_5
    //   2179: sipush #200
    //   2182: invokevirtual setColWidth : (II)V
    //   2185: aload #22
    //   2187: bipush #6
    //   2189: sipush #130
    //   2192: invokevirtual setColWidth : (II)V
    //   2195: aload #22
    //   2197: bipush #7
    //   2199: sipush #130
    //   2202: invokevirtual setColWidth : (II)V
    //   2205: aload #53
    //   2207: invokevirtual getAuditDate : ()Ljava/util/Calendar;
    //   2210: ldc_w 'MM/dd/yy hh:mm a 'PT''
    //   2213: invokestatic getCustomFormatedDate : (Ljava/util/Calendar;Ljava/lang/String;)Ljava/lang/String;
    //   2216: astore #54
    //   2218: aload #54
    //   2220: iconst_0
    //   2221: bipush #9
    //   2223: invokevirtual substring : (II)Ljava/lang/String;
    //   2226: astore #55
    //   2228: aload #54
    //   2230: bipush #9
    //   2232: aload #54
    //   2234: invokevirtual length : ()I
    //   2237: invokevirtual substring : (II)Ljava/lang/String;
    //   2240: astore #56
    //   2242: aload #53
    //   2244: invokevirtual getReleaseFamilyId : ()I
    //   2247: invokestatic getName : (I)Ljava/lang/String;
    //   2250: astore #57
    //   2252: aload #53
    //   2254: invokevirtual getImprint : ()Ljava/lang/String;
    //   2257: astore #58
    //   2259: aload #53
    //   2261: invokevirtual getArtist : ()Ljava/lang/String;
    //   2264: astore #59
    //   2266: aload #53
    //   2268: invokevirtual getTitle : ()Ljava/lang/String;
    //   2271: astore #60
    //   2273: ldc ''
    //   2275: astore #61
    //   2277: aload #53
    //   2279: invokevirtual getPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2282: invokestatic getLookupObjectValue : (Lcom/universal/milestone/LookupObject;)Ljava/lang/String;
    //   2285: ifnonnull -> 2295
    //   2288: ldc ''
    //   2290: astore #61
    //   2292: goto -> 2302
    //   2295: aload #53
    //   2297: invokevirtual getSelectionNo : ()Ljava/lang/String;
    //   2300: astore #61
    //   2302: ldc ''
    //   2304: astore #62
    //   2306: aload #53
    //   2308: invokevirtual getPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2311: ifnull -> 2335
    //   2314: aload #53
    //   2316: invokevirtual getPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2319: invokevirtual getAbbreviation : ()Ljava/lang/String;
    //   2322: ifnull -> 2335
    //   2325: aload #53
    //   2327: invokevirtual getPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2330: invokevirtual getAbbreviation : ()Ljava/lang/String;
    //   2333: astore #62
    //   2335: new java/lang/StringBuilder
    //   2338: dup
    //   2339: aload #62
    //   2341: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2344: invokespecial <init> : (Ljava/lang/String;)V
    //   2347: aload #61
    //   2349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2352: invokevirtual toString : ()Ljava/lang/String;
    //   2355: astore #61
    //   2357: aload #53
    //   2359: invokevirtual getUpc : ()Ljava/lang/String;
    //   2362: ifnull -> 2373
    //   2365: aload #53
    //   2367: invokevirtual getUpc : ()Ljava/lang/String;
    //   2370: goto -> 2375
    //   2373: ldc ''
    //   2375: astore #63
    //   2377: aload #63
    //   2379: ldc_w 'UPC'
    //   2382: aload #53
    //   2384: invokevirtual getIsDigital : ()Z
    //   2387: invokestatic getRMSReportFormat : (Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;
    //   2390: astore #63
    //   2392: ldc ''
    //   2394: astore #64
    //   2396: aload #53
    //   2398: invokevirtual getAuditPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2401: invokestatic getLookupObjectValue : (Lcom/universal/milestone/LookupObject;)Ljava/lang/String;
    //   2404: ifnonnull -> 2414
    //   2407: ldc ''
    //   2409: astore #64
    //   2411: goto -> 2421
    //   2414: aload #53
    //   2416: invokevirtual getAuditSelectionNo : ()Ljava/lang/String;
    //   2419: astore #64
    //   2421: ldc ''
    //   2423: astore #65
    //   2425: aload #53
    //   2427: invokevirtual getAuditPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2430: ifnull -> 2454
    //   2433: aload #53
    //   2435: invokevirtual getAuditPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2438: invokevirtual getAbbreviation : ()Ljava/lang/String;
    //   2441: ifnull -> 2454
    //   2444: aload #53
    //   2446: invokevirtual getAuditPrefixID : ()Lcom/universal/milestone/PrefixCode;
    //   2449: invokevirtual getAbbreviation : ()Ljava/lang/String;
    //   2452: astore #65
    //   2454: new java/lang/StringBuilder
    //   2457: dup
    //   2458: aload #65
    //   2460: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2463: invokespecial <init> : (Ljava/lang/String;)V
    //   2466: aload #64
    //   2468: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2471: invokevirtual toString : ()Ljava/lang/String;
    //   2474: astore #64
    //   2476: aload #53
    //   2478: invokevirtual getAuditUPC : ()Ljava/lang/String;
    //   2481: ifnull -> 2492
    //   2484: aload #53
    //   2486: invokevirtual getAuditUPC : ()Ljava/lang/String;
    //   2489: goto -> 2494
    //   2492: ldc ''
    //   2494: astore #66
    //   2496: aload #66
    //   2498: ldc_w 'UPC'
    //   2501: aload #53
    //   2503: invokevirtual getIsDigital : ()Z
    //   2506: invokestatic getRMSReportFormat : (Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;
    //   2509: astore #66
    //   2511: ldc ''
    //   2513: astore #67
    //   2515: aload #53
    //   2517: invokevirtual getSelectionSubConfig : ()Lcom/universal/milestone/SelectionSubConfiguration;
    //   2520: ifnull -> 2533
    //   2523: aload #53
    //   2525: invokevirtual getSelectionSubConfig : ()Lcom/universal/milestone/SelectionSubConfiguration;
    //   2528: invokevirtual getSelectionSubConfigurationName : ()Ljava/lang/String;
    //   2531: astore #67
    //   2533: aload #53
    //   2535: invokevirtual getStreetDate : ()Ljava/util/Calendar;
    //   2538: ldc_w 'MM/dd/yy'
    //   2541: invokestatic getCustomFormatedDate : (Ljava/util/Calendar;Ljava/lang/String;)Ljava/lang/String;
    //   2544: astore #68
    //   2546: aload #68
    //   2548: ldc_w '01/01/00'
    //   2551: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2554: ifeq -> 2561
    //   2557: ldc ''
    //   2559: astore #68
    //   2561: aload #53
    //   2563: invokevirtual getAuditStreetDate : ()Ljava/util/Calendar;
    //   2566: ldc_w 'MM/dd/yy'
    //   2569: invokestatic getCustomFormatedDate : (Ljava/util/Calendar;Ljava/lang/String;)Ljava/lang/String;
    //   2572: astore #69
    //   2574: aload #69
    //   2576: ldc_w '01/01/00'
    //   2579: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2582: ifeq -> 2589
    //   2585: ldc ''
    //   2587: astore #69
    //   2589: ldc ''
    //   2591: astore #70
    //   2593: aload #53
    //   2595: invokevirtual getSelectionConfig : ()Lcom/universal/milestone/SelectionConfiguration;
    //   2598: ifnull -> 2611
    //   2601: aload #53
    //   2603: invokevirtual getSelectionConfig : ()Lcom/universal/milestone/SelectionConfiguration;
    //   2606: invokevirtual getSelectionConfigurationName : ()Ljava/lang/String;
    //   2609: astore #70
    //   2611: aload #53
    //   2613: invokevirtual getSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2616: ifnull -> 2641
    //   2619: aload #53
    //   2621: invokevirtual getSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2624: invokevirtual getName : ()Ljava/lang/String;
    //   2627: ifnull -> 2641
    //   2630: aload #53
    //   2632: invokevirtual getSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2635: invokevirtual getName : ()Ljava/lang/String;
    //   2638: goto -> 2643
    //   2641: ldc ''
    //   2643: astore #71
    //   2645: aload #53
    //   2647: invokevirtual getAuditSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2650: ifnull -> 2675
    //   2653: aload #53
    //   2655: invokevirtual getAuditSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2658: invokevirtual getName : ()Ljava/lang/String;
    //   2661: ifnull -> 2675
    //   2664: aload #53
    //   2666: invokevirtual getAuditSelectionStatus : ()Lcom/universal/milestone/SelectionStatus;
    //   2669: invokevirtual getName : ()Ljava/lang/String;
    //   2672: goto -> 2677
    //   2675: ldc ''
    //   2677: astore #72
    //   2679: aload #22
    //   2681: iload #26
    //   2683: new java/awt/Insets
    //   2686: dup
    //   2687: iconst_0
    //   2688: iconst_0
    //   2689: iconst_0
    //   2690: iconst_0
    //   2691: invokespecial <init> : (IIII)V
    //   2694: invokevirtual setRowInsets : (ILjava/awt/Insets;)V
    //   2697: aload #22
    //   2699: iload #26
    //   2701: iconst_0
    //   2702: new java/lang/StringBuilder
    //   2705: dup
    //   2706: aload #55
    //   2708: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2711: invokespecial <init> : (Ljava/lang/String;)V
    //   2714: ldc_w '\\n'
    //   2717: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2720: aload #56
    //   2722: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2725: invokevirtual toString : ()Ljava/lang/String;
    //   2728: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2731: aload #22
    //   2733: iload #26
    //   2735: iconst_1
    //   2736: new java/lang/StringBuilder
    //   2739: dup
    //   2740: aload #57
    //   2742: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2745: invokespecial <init> : (Ljava/lang/String;)V
    //   2748: ldc_w '/\\n'
    //   2751: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2754: aload #58
    //   2756: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2759: invokevirtual toString : ()Ljava/lang/String;
    //   2762: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2765: aload #22
    //   2767: iload #26
    //   2769: iconst_2
    //   2770: aload #59
    //   2772: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2775: aload #22
    //   2777: iload #26
    //   2779: iconst_3
    //   2780: aload #60
    //   2782: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2785: aload #49
    //   2787: ldc 'Changed'
    //   2789: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2792: ifeq -> 2912
    //   2795: aload #22
    //   2797: iload #26
    //   2799: iconst_4
    //   2800: new java/lang/StringBuilder
    //   2803: dup
    //   2804: aload #64
    //   2806: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2809: invokespecial <init> : (Ljava/lang/String;)V
    //   2812: ldc_w '\\n'
    //   2815: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2818: aload #66
    //   2820: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2823: invokevirtual toString : ()Ljava/lang/String;
    //   2826: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2829: aload #22
    //   2831: iload #26
    //   2833: iconst_5
    //   2834: new java/lang/StringBuilder
    //   2837: dup
    //   2838: aload #61
    //   2840: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2843: invokespecial <init> : (Ljava/lang/String;)V
    //   2846: ldc_w '\\n'
    //   2849: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2852: aload #63
    //   2854: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2857: invokevirtual toString : ()Ljava/lang/String;
    //   2860: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2863: aload #22
    //   2865: iload #26
    //   2867: bipush #6
    //   2869: new java/lang/StringBuilder
    //   2872: dup
    //   2873: aload #70
    //   2875: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2878: invokespecial <init> : (Ljava/lang/String;)V
    //   2881: ldc_w '\\n'
    //   2884: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2887: aload #67
    //   2889: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2892: invokevirtual toString : ()Ljava/lang/String;
    //   2895: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2898: aload #22
    //   2900: iload #26
    //   2902: bipush #7
    //   2904: aload #68
    //   2906: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2909: goto -> 3153
    //   2912: aload #49
    //   2914: ldc 'Moved'
    //   2916: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2919: ifeq -> 3063
    //   2922: aload #22
    //   2924: iload #26
    //   2926: iconst_4
    //   2927: new java/lang/StringBuilder
    //   2930: dup
    //   2931: aload #61
    //   2933: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2936: invokespecial <init> : (Ljava/lang/String;)V
    //   2939: ldc_w '\\n'
    //   2942: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2945: aload #63
    //   2947: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2950: invokevirtual toString : ()Ljava/lang/String;
    //   2953: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2956: aload #22
    //   2958: iload #26
    //   2960: iconst_5
    //   2961: new java/lang/StringBuilder
    //   2964: dup
    //   2965: aload #70
    //   2967: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2970: invokespecial <init> : (Ljava/lang/String;)V
    //   2973: ldc_w '\\n'
    //   2976: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2979: aload #67
    //   2981: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2984: invokevirtual toString : ()Ljava/lang/String;
    //   2987: invokevirtual setObject : (IILjava/lang/Object;)V
    //   2990: aload #22
    //   2992: iload #26
    //   2994: bipush #6
    //   2996: new java/lang/StringBuilder
    //   2999: dup
    //   3000: aload #69
    //   3002: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3005: invokespecial <init> : (Ljava/lang/String;)V
    //   3008: ldc_w '\\n'
    //   3011: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3014: aload #72
    //   3016: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3019: invokevirtual toString : ()Ljava/lang/String;
    //   3022: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3025: aload #22
    //   3027: iload #26
    //   3029: bipush #7
    //   3031: new java/lang/StringBuilder
    //   3034: dup
    //   3035: aload #68
    //   3037: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3040: invokespecial <init> : (Ljava/lang/String;)V
    //   3043: ldc_w '\\n'
    //   3046: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3049: aload #71
    //   3051: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3054: invokevirtual toString : ()Ljava/lang/String;
    //   3057: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3060: goto -> 3153
    //   3063: aload #22
    //   3065: iload #26
    //   3067: iconst_4
    //   3068: ldc ''
    //   3070: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3073: aload #22
    //   3075: iload #26
    //   3077: iconst_5
    //   3078: new java/lang/StringBuilder
    //   3081: dup
    //   3082: aload #61
    //   3084: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3087: invokespecial <init> : (Ljava/lang/String;)V
    //   3090: ldc_w '\\n'
    //   3093: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3096: aload #63
    //   3098: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3101: invokevirtual toString : ()Ljava/lang/String;
    //   3104: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3107: aload #22
    //   3109: iload #26
    //   3111: bipush #6
    //   3113: new java/lang/StringBuilder
    //   3116: dup
    //   3117: aload #70
    //   3119: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3122: invokespecial <init> : (Ljava/lang/String;)V
    //   3125: ldc_w '\\n'
    //   3128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3131: aload #67
    //   3133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3136: invokevirtual toString : ()Ljava/lang/String;
    //   3139: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3142: aload #22
    //   3144: iload #26
    //   3146: bipush #7
    //   3148: aload #68
    //   3150: invokevirtual setObject : (IILjava/lang/Object;)V
    //   3153: aload #22
    //   3155: iconst_0
    //   3156: bipush #9
    //   3158: invokevirtual setColAlignment : (II)V
    //   3161: aload #22
    //   3163: iload #26
    //   3165: iconst_0
    //   3166: new java/awt/Dimension
    //   3169: dup
    //   3170: iconst_1
    //   3171: iconst_2
    //   3172: invokespecial <init> : (II)V
    //   3175: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3178: aload #22
    //   3180: iconst_1
    //   3181: bipush #9
    //   3183: invokevirtual setColAlignment : (II)V
    //   3186: aload #22
    //   3188: iload #26
    //   3190: iconst_1
    //   3191: new java/awt/Dimension
    //   3194: dup
    //   3195: iconst_1
    //   3196: iconst_2
    //   3197: invokespecial <init> : (II)V
    //   3200: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3203: aload #22
    //   3205: iconst_2
    //   3206: bipush #9
    //   3208: invokevirtual setColAlignment : (II)V
    //   3211: aload #22
    //   3213: iload #26
    //   3215: iconst_2
    //   3216: new java/awt/Dimension
    //   3219: dup
    //   3220: iconst_1
    //   3221: iconst_2
    //   3222: invokespecial <init> : (II)V
    //   3225: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3228: aload #22
    //   3230: iconst_3
    //   3231: bipush #9
    //   3233: invokevirtual setColAlignment : (II)V
    //   3236: aload #22
    //   3238: iload #26
    //   3240: iconst_3
    //   3241: new java/awt/Dimension
    //   3244: dup
    //   3245: iconst_1
    //   3246: iconst_2
    //   3247: invokespecial <init> : (II)V
    //   3250: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3253: aload #22
    //   3255: iconst_4
    //   3256: bipush #9
    //   3258: invokevirtual setColAlignment : (II)V
    //   3261: aload #22
    //   3263: iload #26
    //   3265: iconst_4
    //   3266: new java/awt/Dimension
    //   3269: dup
    //   3270: iconst_1
    //   3271: iconst_2
    //   3272: invokespecial <init> : (II)V
    //   3275: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3278: aload #22
    //   3280: iconst_5
    //   3281: bipush #9
    //   3283: invokevirtual setColAlignment : (II)V
    //   3286: aload #22
    //   3288: iload #26
    //   3290: iconst_5
    //   3291: new java/awt/Dimension
    //   3294: dup
    //   3295: iconst_1
    //   3296: iconst_2
    //   3297: invokespecial <init> : (II)V
    //   3300: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3303: aload #22
    //   3305: bipush #6
    //   3307: bipush #9
    //   3309: invokevirtual setColAlignment : (II)V
    //   3312: aload #22
    //   3314: iload #26
    //   3316: bipush #6
    //   3318: new java/awt/Dimension
    //   3321: dup
    //   3322: iconst_1
    //   3323: iconst_2
    //   3324: invokespecial <init> : (II)V
    //   3327: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3330: aload #22
    //   3332: bipush #7
    //   3334: bipush #9
    //   3336: invokevirtual setColAlignment : (II)V
    //   3339: aload #22
    //   3341: iload #26
    //   3343: bipush #7
    //   3345: new java/awt/Dimension
    //   3348: dup
    //   3349: iconst_1
    //   3350: iconst_2
    //   3351: invokespecial <init> : (II)V
    //   3354: invokevirtual setSpan : (IILjava/awt/Dimension;)V
    //   3357: aload #22
    //   3359: iload #26
    //   3361: iconst_1
    //   3362: iconst_0
    //   3363: invokevirtual setRowBorder : (III)V
    //   3366: aload #22
    //   3368: iload #26
    //   3370: iconst_1
    //   3371: isub
    //   3372: iconst_0
    //   3373: invokevirtual setRowBorder : (II)V
    //   3376: aload #22
    //   3378: iload #26
    //   3380: new java/awt/Font
    //   3383: dup
    //   3384: ldc_w 'Arial'
    //   3387: iconst_0
    //   3388: bipush #8
    //   3390: invokespecial <init> : (Ljava/lang/String;II)V
    //   3393: invokevirtual setRowFont : (ILjava/awt/Font;)V
    //   3396: aload #22
    //   3398: iconst_1
    //   3399: invokevirtual setRowAutoSize : (Z)V
    //   3402: iinc #26, 1
    //   3405: aload #22
    //   3407: iload #26
    //   3409: iconst_1
    //   3410: bipush #9
    //   3412: invokevirtual setAlignment : (III)V
    //   3415: aload #22
    //   3417: iload #26
    //   3419: new java/awt/Font
    //   3422: dup
    //   3423: ldc_w 'Arial'
    //   3426: iconst_2
    //   3427: bipush #8
    //   3429: invokespecial <init> : (Ljava/lang/String;II)V
    //   3432: invokevirtual setRowFont : (ILjava/awt/Font;)V
    //   3435: aload #22
    //   3437: iconst_1
    //   3438: bipush #9
    //   3440: invokevirtual setColAlignment : (II)V
    //   3443: aload #22
    //   3445: iload #26
    //   3447: getstatic java/awt/Color.lightGray : Ljava/awt/Color;
    //   3450: invokevirtual setRowBorderColor : (ILjava/awt/Color;)V
    //   3453: aload #22
    //   3455: iload #26
    //   3457: iconst_1
    //   3458: isub
    //   3459: getstatic java/awt/Color.lightGray : Ljava/awt/Color;
    //   3462: invokevirtual setRowBorderColor : (ILjava/awt/Color;)V
    //   3465: aload #22
    //   3467: iload #26
    //   3469: iconst_1
    //   3470: iadd
    //   3471: getstatic java/awt/Color.lightGray : Ljava/awt/Color;
    //   3474: invokevirtual setRowBorderColor : (ILjava/awt/Color;)V
    //   3477: aload #22
    //   3479: iconst_0
    //   3480: invokevirtual setColBorder : (I)V
    //   3483: new inetsoft/report/SectionBand
    //   3486: dup
    //   3487: aload_0
    //   3488: invokespecial <init> : (Linetsoft/report/StyleSheet;)V
    //   3491: astore #13
    //   3493: ldc2_w 1.5
    //   3496: dstore #6
    //   3498: aload #13
    //   3500: ldc_w 1.5
    //   3503: invokevirtual setHeight : (F)V
    //   3506: aload #13
    //   3508: ldc_w 0.8
    //   3511: invokevirtual setHeight : (F)V
    //   3514: aload #13
    //   3516: aload #22
    //   3518: new java/awt/Rectangle
    //   3521: dup
    //   3522: sipush #800
    //   3525: sipush #800
    //   3528: invokespecial <init> : (II)V
    //   3531: invokevirtual addTable : (Linetsoft/report/TableLens;Ljava/awt/Rectangle;)Ljava/lang/String;
    //   3534: pop
    //   3535: aload #13
    //   3537: iconst_0
    //   3538: invokevirtual setBottomBorder : (I)V
    //   3541: aload #13
    //   3543: iconst_0
    //   3544: invokevirtual setTopBorder : (I)V
    //   3547: aload #13
    //   3549: iconst_1
    //   3550: invokevirtual setShrinkToFit : (Z)V
    //   3553: aload #13
    //   3555: iconst_1
    //   3556: invokevirtual setVisible : (Z)V
    //   3559: new inetsoft/report/lens/DefaultSectionLens
    //   3562: dup
    //   3563: aconst_null
    //   3564: aload #17
    //   3566: aload #13
    //   3568: invokespecial <init> : (Linetsoft/report/SectionBand;Linetsoft/report/SectionLens;Linetsoft/report/SectionBand;)V
    //   3571: astore #17
    //   3573: iinc #52, 1
    //   3576: iload #52
    //   3578: iload #47
    //   3580: if_icmplt -> 1942
    //   3583: aload #45
    //   3585: ifnull -> 3624
    //   3588: aload #45
    //   3590: invokevirtual size : ()I
    //   3593: ifle -> 3624
    //   3596: new inetsoft/report/lens/DefaultSectionLens
    //   3599: dup
    //   3600: aload #10
    //   3602: aload #17
    //   3604: aconst_null
    //   3605: invokespecial <init> : (Linetsoft/report/SectionBand;Linetsoft/report/SectionLens;Linetsoft/report/SectionBand;)V
    //   3608: astore #17
    //   3610: aload_0
    //   3611: aload #17
    //   3613: aload #20
    //   3615: invokevirtual addSection : (Linetsoft/report/SectionLens;Linetsoft/report/TableLens;)Ljava/lang/String;
    //   3618: pop
    //   3619: aload_0
    //   3620: invokevirtual addPageBreak : ()Ljava/lang/String;
    //   3623: pop
    //   3624: aconst_null
    //   3625: astore #17
    //   3627: iinc #44, 1
    //   3630: iload #44
    //   3632: iconst_5
    //   3633: if_icmplt -> 849
    //   3636: goto -> 3665
    //   3639: astore #19
    //   3641: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   3644: new java/lang/StringBuilder
    //   3647: dup
    //   3648: ldc_w '>>>>>>>>>> Physical Product Activity Summary: exception: '
    //   3651: invokespecial <init> : (Ljava/lang/String;)V
    //   3654: aload #19
    //   3656: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3659: invokevirtual toString : ()Ljava/lang/String;
    //   3662: invokevirtual println : (Ljava/lang/String;)V
    //   3665: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #90	-> 0
    //   #91	-> 4
    //   #92	-> 8
    //   #93	-> 12
    //   #95	-> 16
    //   #97	-> 21
    //   #98	-> 31
    //   #99	-> 41
    //   #100	-> 51
    //   #101	-> 61
    //   #102	-> 71
    //   #103	-> 81
    //   #104	-> 91
    //   #105	-> 94
    //   #107	-> 98
    //   #108	-> 104
    //   #109	-> 111
    //   #110	-> 117
    //   #112	-> 123
    //   #113	-> 129
    //   #114	-> 136
    //   #115	-> 142
    //   #117	-> 148
    //   #118	-> 154
    //   #119	-> 161
    //   #120	-> 167
    //   #123	-> 173
    //   #124	-> 179
    //   #125	-> 194
    //   #126	-> 203
    //   #127	-> 212
    //   #129	-> 222
    //   #133	-> 224
    //   #136	-> 230
    //   #137	-> 236
    //   #138	-> 251
    //   #139	-> 266
    //   #140	-> 275
    //   #141	-> 284
    //   #143	-> 294
    //   #147	-> 296
    //   #148	-> 299
    //   #149	-> 302
    //   #150	-> 305
    //   #151	-> 308
    //   #152	-> 311
    //   #153	-> 314
    //   #155	-> 317
    //   #158	-> 330
    //   #161	-> 333
    //   #163	-> 344
    //   #164	-> 355
    //   #165	-> 366
    //   #166	-> 377
    //   #168	-> 388
    //   #169	-> 391
    //   #170	-> 394
    //   #171	-> 397
    //   #172	-> 400
    //   #173	-> 403
    //   #175	-> 406
    //   #176	-> 408
    //   #175	-> 410
    //   #176	-> 416
    //   #175	-> 419
    //   #177	-> 421
    //   #178	-> 423
    //   #177	-> 425
    //   #178	-> 431
    //   #177	-> 434
    //   #179	-> 436
    //   #180	-> 438
    //   #179	-> 440
    //   #180	-> 446
    //   #179	-> 449
    //   #181	-> 451
    //   #182	-> 453
    //   #181	-> 455
    //   #182	-> 461
    //   #181	-> 464
    //   #183	-> 466
    //   #184	-> 468
    //   #183	-> 470
    //   #184	-> 476
    //   #183	-> 479
    //   #185	-> 481
    //   #186	-> 491
    //   #185	-> 494
    //   #188	-> 496
    //   #190	-> 506
    //   #191	-> 513
    //   #192	-> 519
    //   #193	-> 521
    //   #192	-> 523
    //   #193	-> 532
    //   #188	-> 533
    //   #195	-> 535
    //   #197	-> 545
    //   #199	-> 558
    //   #195	-> 572
    //   #201	-> 574
    //   #203	-> 584
    //   #204	-> 597
    //   #201	-> 611
    //   #206	-> 613
    //   #208	-> 623
    //   #209	-> 636
    //   #206	-> 650
    //   #215	-> 652
    //   #216	-> 667
    //   #217	-> 682
    //   #218	-> 685
    //   #217	-> 690
    //   #219	-> 693
    //   #220	-> 696
    //   #219	-> 701
    //   #224	-> 707
    //   #225	-> 713
    //   #226	-> 719
    //   #227	-> 725
    //   #231	-> 731
    //   #232	-> 746
    //   #233	-> 761
    //   #234	-> 764
    //   #233	-> 769
    //   #235	-> 772
    //   #236	-> 775
    //   #235	-> 780
    //   #240	-> 786
    //   #241	-> 792
    //   #242	-> 798
    //   #243	-> 804
    //   #246	-> 810
    //   #247	-> 821
    //   #248	-> 835
    //   #251	-> 843
    //   #254	-> 849
    //   #255	-> 864
    //   #257	-> 867
    //   #258	-> 883
    //   #260	-> 886
    //   #261	-> 902
    //   #263	-> 905
    //   #264	-> 921
    //   #266	-> 924
    //   #267	-> 940
    //   #269	-> 943
    //   #271	-> 955
    //   #273	-> 962
    //   #276	-> 966
    //   #279	-> 970
    //   #281	-> 973
    //   #282	-> 977
    //   #284	-> 1012
    //   #285	-> 1016
    //   #287	-> 1019
    //   #288	-> 1023
    //   #290	-> 1026
    //   #291	-> 1030
    //   #293	-> 1033
    //   #294	-> 1037
    //   #296	-> 1040
    //   #297	-> 1044
    //   #305	-> 1047
    //   #306	-> 1057
    //   #307	-> 1064
    //   #308	-> 1070
    //   #309	-> 1076
    //   #310	-> 1079
    //   #313	-> 1091
    //   #314	-> 1099
    //   #315	-> 1107
    //   #317	-> 1115
    //   #318	-> 1135
    //   #319	-> 1144
    //   #322	-> 1156
    //   #323	-> 1165
    //   #326	-> 1173
    //   #327	-> 1182
    //   #328	-> 1192
    //   #330	-> 1202
    //   #331	-> 1208
    //   #332	-> 1214
    //   #331	-> 1215
    //   #333	-> 1218
    //   #334	-> 1228
    //   #337	-> 1238
    //   #338	-> 1243
    //   #337	-> 1245
    //   #340	-> 1248
    //   #341	-> 1258
    //   #343	-> 1272
    //   #344	-> 1282
    //   #346	-> 1296
    //   #347	-> 1306
    //   #349	-> 1320
    //   #350	-> 1330
    //   #352	-> 1344
    //   #353	-> 1354
    //   #356	-> 1365
    //   #357	-> 1376
    //   #358	-> 1387
    //   #359	-> 1398
    //   #360	-> 1408
    //   #361	-> 1413
    //   #360	-> 1416
    //   #362	-> 1419
    //   #364	-> 1433
    //   #365	-> 1443
    //   #368	-> 1457
    //   #369	-> 1467
    //   #372	-> 1478
    //   #373	-> 1488
    //   #376	-> 1502
    //   #379	-> 1514
    //   #380	-> 1524
    //   #381	-> 1530
    //   #380	-> 1533
    //   #382	-> 1536
    //   #383	-> 1542
    //   #382	-> 1545
    //   #386	-> 1551
    //   #389	-> 1563
    //   #390	-> 1567
    //   #391	-> 1574
    //   #390	-> 1577
    //   #389	-> 1580
    //   #392	-> 1583
    //   #393	-> 1593
    //   #395	-> 1603
    //   #396	-> 1625
    //   #397	-> 1631
    //   #399	-> 1637
    //   #400	-> 1649
    //   #401	-> 1659
    //   #402	-> 1667
    //   #403	-> 1673
    //   #404	-> 1679
    //   #405	-> 1685
    //   #406	-> 1691
    //   #407	-> 1697
    //   #409	-> 1703
    //   #412	-> 1706
    //   #413	-> 1715
    //   #414	-> 1733
    //   #415	-> 1743
    //   #416	-> 1747
    //   #417	-> 1754
    //   #416	-> 1757
    //   #415	-> 1760
    //   #419	-> 1763
    //   #421	-> 1784
    //   #422	-> 1790
    //   #423	-> 1797
    //   #424	-> 1803
    //   #426	-> 1809
    //   #427	-> 1823
    //   #428	-> 1837
    //   #432	-> 1851
    //   #433	-> 1871
    //   #438	-> 1880
    //   #439	-> 1887
    //   #442	-> 1892
    //   #443	-> 1898
    //   #446	-> 1903
    //   #447	-> 1909
    //   #450	-> 1914
    //   #451	-> 1916
    //   #450	-> 1918
    //   #452	-> 1921
    //   #456	-> 1926
    //   #457	-> 1929
    //   #460	-> 1936
    //   #463	-> 1942
    //   #464	-> 1947
    //   #468	-> 1950
    //   #469	-> 1957
    //   #470	-> 1970
    //   #471	-> 1974
    //   #472	-> 1975
    //   #471	-> 1978
    //   #473	-> 1980
    //   #474	-> 1995
    //   #475	-> 2016
    //   #476	-> 2025
    //   #477	-> 2034
    //   #479	-> 2044
    //   #484	-> 2046
    //   #485	-> 2056
    //   #488	-> 2059
    //   #491	-> 2071
    //   #492	-> 2074
    //   #493	-> 2086
    //   #496	-> 2094
    //   #497	-> 2102
    //   #498	-> 2110
    //   #500	-> 2118
    //   #501	-> 2138
    //   #502	-> 2147
    //   #505	-> 2159
    //   #506	-> 2168
    //   #508	-> 2176
    //   #509	-> 2185
    //   #510	-> 2195
    //   #514	-> 2205
    //   #515	-> 2207
    //   #514	-> 2213
    //   #516	-> 2218
    //   #517	-> 2228
    //   #520	-> 2242
    //   #524	-> 2252
    //   #527	-> 2259
    //   #530	-> 2266
    //   #533	-> 2273
    //   #534	-> 2277
    //   #535	-> 2288
    //   #538	-> 2295
    //   #540	-> 2302
    //   #541	-> 2306
    //   #542	-> 2325
    //   #543	-> 2335
    //   #546	-> 2357
    //   #547	-> 2377
    //   #548	-> 2382
    //   #547	-> 2387
    //   #551	-> 2392
    //   #552	-> 2396
    //   #553	-> 2407
    //   #556	-> 2414
    //   #558	-> 2421
    //   #559	-> 2425
    //   #560	-> 2433
    //   #561	-> 2444
    //   #562	-> 2454
    //   #565	-> 2476
    //   #566	-> 2492
    //   #565	-> 2494
    //   #567	-> 2496
    //   #568	-> 2501
    //   #567	-> 2506
    //   #571	-> 2511
    //   #572	-> 2515
    //   #573	-> 2523
    //   #574	-> 2528
    //   #573	-> 2531
    //   #577	-> 2533
    //   #578	-> 2535
    //   #577	-> 2541
    //   #581	-> 2546
    //   #582	-> 2557
    //   #586	-> 2561
    //   #587	-> 2563
    //   #586	-> 2569
    //   #590	-> 2574
    //   #591	-> 2585
    //   #595	-> 2589
    //   #596	-> 2593
    //   #597	-> 2601
    //   #600	-> 2611
    //   #601	-> 2619
    //   #602	-> 2630
    //   #600	-> 2643
    //   #604	-> 2645
    //   #605	-> 2653
    //   #606	-> 2664
    //   #604	-> 2677
    //   #609	-> 2679
    //   #611	-> 2697
    //   #612	-> 2731
    //   #613	-> 2765
    //   #614	-> 2775
    //   #615	-> 2785
    //   #616	-> 2795
    //   #617	-> 2800
    //   #616	-> 2826
    //   #618	-> 2829
    //   #619	-> 2863
    //   #620	-> 2898
    //   #622	-> 2912
    //   #623	-> 2922
    //   #624	-> 2956
    //   #625	-> 2990
    //   #626	-> 3025
    //   #629	-> 3063
    //   #630	-> 3073
    //   #631	-> 3107
    //   #632	-> 3142
    //   #635	-> 3153
    //   #636	-> 3156
    //   #635	-> 3158
    //   #638	-> 3161
    //   #639	-> 3178
    //   #640	-> 3181
    //   #639	-> 3183
    //   #642	-> 3186
    //   #643	-> 3203
    //   #644	-> 3206
    //   #643	-> 3208
    //   #646	-> 3211
    //   #647	-> 3228
    //   #648	-> 3231
    //   #647	-> 3233
    //   #650	-> 3236
    //   #651	-> 3253
    //   #652	-> 3256
    //   #651	-> 3258
    //   #654	-> 3261
    //   #655	-> 3278
    //   #656	-> 3281
    //   #655	-> 3283
    //   #658	-> 3286
    //   #659	-> 3303
    //   #660	-> 3307
    //   #659	-> 3309
    //   #662	-> 3312
    //   #663	-> 3330
    //   #664	-> 3334
    //   #663	-> 3336
    //   #666	-> 3339
    //   #668	-> 3357
    //   #669	-> 3366
    //   #670	-> 3376
    //   #672	-> 3396
    //   #674	-> 3402
    //   #676	-> 3405
    //   #677	-> 3410
    //   #676	-> 3412
    //   #684	-> 3415
    //   #685	-> 3419
    //   #684	-> 3432
    //   #686	-> 3435
    //   #687	-> 3438
    //   #686	-> 3440
    //   #689	-> 3443
    //   #690	-> 3453
    //   #691	-> 3465
    //   #692	-> 3477
    //   #694	-> 3483
    //   #696	-> 3493
    //   #700	-> 3498
    //   #715	-> 3506
    //   #718	-> 3514
    //   #719	-> 3535
    //   #720	-> 3541
    //   #721	-> 3547
    //   #722	-> 3553
    //   #724	-> 3559
    //   #460	-> 3573
    //   #727	-> 3583
    //   #728	-> 3596
    //   #729	-> 3610
    //   #730	-> 3619
    //   #732	-> 3624
    //   #251	-> 3627
    //   #736	-> 3639
    //   #737	-> 3641
    //   #738	-> 3644
    //   #737	-> 3662
    //   #740	-> 3665
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	3666	0	report	Linetsoft/report/XStyleSheet;
    //   0	3666	1	context	Lcom/techempower/gemini/Context;
    //   4	3662	2	SHADED_AREA_COLOR	Ljava/awt/Color;
    //   8	3658	3	COL_LINE_STYLE	I
    //   12	3654	4	HEADER_FONT_SIZE	I
    //   16	3650	5	NUM_COLUMNS	I
    //   3498	75	6	lfLineCount	D
    //   21	3645	8	ldLineVal	D
    //   31	3635	10	hbandType	Linetsoft/report/SectionBand;
    //   41	3625	11	hbandCategory	Linetsoft/report/SectionBand;
    //   51	3615	12	hbandDate	Linetsoft/report/SectionBand;
    //   61	3605	13	body	Linetsoft/report/SectionBand;
    //   71	3595	14	footer	Linetsoft/report/SectionBand;
    //   81	3585	15	spacer	Linetsoft/report/SectionBand;
    //   91	3575	16	selectionSpacer	Linetsoft/report/SectionBand;
    //   94	3572	17	group	Linetsoft/report/lens/DefaultSectionLens;
    //   179	43	18	sresponse	Ljavax/servlet/http/HttpServletResponse;
    //   230	3436	18	selections	Ljava/util/Vector;
    //   236	58	19	sresponse	Ljavax/servlet/http/HttpServletResponse;
    //   299	3340	19	table_contents	Linetsoft/report/lens/DefaultTableLens;
    //   302	3337	20	rowCountTable	Linetsoft/report/lens/DefaultTableLens;
    //   305	3334	21	columnHeaderTable	Linetsoft/report/lens/DefaultTableLens;
    //   308	3331	22	subTable	Linetsoft/report/lens/DefaultTableLens;
    //   311	3328	23	monthTableLens	Linetsoft/report/lens/DefaultTableLens;
    //   314	3325	24	configTableLens	Linetsoft/report/lens/DefaultTableLens;
    //   317	3322	25	dateTableLens	Linetsoft/report/lens/DefaultTableLens;
    //   333	3306	26	nextRow	I
    //   344	3295	27	reportForm	Lcom/universal/milestone/Form;
    //   355	3284	28	changesVector	Ljava/util/Vector;
    //   366	3273	29	addsVector	Ljava/util/Vector;
    //   377	3262	30	cancelsVector	Ljava/util/Vector;
    //   388	3251	31	deletesVector	Ljava/util/Vector;
    //   391	3248	32	CancelsActivityFlag	Z
    //   394	3245	33	ChangesActivityFlag	Z
    //   397	3242	34	DeletesActivityFlag	Z
    //   400	3239	35	AddsActivityFlag	Z
    //   403	3236	36	MovesActivityFlag	Z
    //   406	3233	37	AllActivityFlag	Z
    //   535	3104	38	beginAuditDate	Ljava/util/Calendar;
    //   574	3065	39	endAuditDate	Ljava/util/Calendar;
    //   613	3026	40	beginStDate	Ljava/util/Calendar;
    //   652	2987	41	endStDate	Ljava/util/Calendar;
    //   821	2818	42	formatter	Ljava/text/SimpleDateFormat;
    //   835	2804	43	todayLong	Ljava/lang/String;
    //   846	2790	44	j	I
    //   955	2672	45	currentVector	Ljava/util/Vector;
    //   962	2665	46	numberSelections	I
    //   966	2661	47	numRows	I
    //   970	2657	48	numColumns	I
    //   977	2650	49	sectionHeading	Ljava/lang/String;
    //   1929	1698	50	count	I
    //   1936	1691	51	chunkSize	I
    //   1939	1644	52	i	I
    //   1957	87	53	myPercent	I
    //   1980	64	54	sresponse	Ljavax/servlet/http/HttpServletResponse;
    //   2071	1502	53	sel	Lcom/universal/milestone/Selection;
    //   2218	1355	54	auditDate	Ljava/lang/String;
    //   2228	1345	55	auditMonthDayYear	Ljava/lang/String;
    //   2242	1331	56	auditTime	Ljava/lang/String;
    //   2252	1321	57	family	Ljava/lang/String;
    //   2259	1314	58	label	Ljava/lang/String;
    //   2266	1307	59	artist	Ljava/lang/String;
    //   2273	1300	60	title	Ljava/lang/String;
    //   2277	1296	61	localProductNumber	Ljava/lang/String;
    //   2306	1267	62	prefix	Ljava/lang/String;
    //   2377	1196	63	upc	Ljava/lang/String;
    //   2396	1177	64	auditLocalProductNumber	Ljava/lang/String;
    //   2425	1148	65	auditprefix	Ljava/lang/String;
    //   2496	1077	66	auditUPC	Ljava/lang/String;
    //   2515	1058	67	subformat	Ljava/lang/String;
    //   2546	1027	68	streetDate	Ljava/lang/String;
    //   2574	999	69	auditStreetDate	Ljava/lang/String;
    //   2593	980	70	format	Ljava/lang/String;
    //   2645	928	71	status	Ljava/lang/String;
    //   2679	894	72	auditStatus	Ljava/lang/String;
    //   3641	24	19	e	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   173	219	222	java/lang/Exception
    //   230	291	294	java/lang/Exception
    //   296	3636	3639	java/lang/Exception
    //   1950	2041	2044	java/lang/Exception }
  
  public static Vector groupForChanges(Vector originalVector) {
    Vector finalVector = new Vector();
    Vector selectionIds = new Vector();
    String selIdStr = "";
    int selId = 0;
    String selIdStrSearch = "";
    int selIdSearch = 0;
    MilestoneHelper.setSelectionSorting(originalVector, 27);
    Collections.sort(originalVector);
    for (int i = 0; i < originalVector.size(); i++) {
      Selection sel = (Selection)originalVector.elementAt(i);
      selId = sel.getSelectionID();
      selIdStr = Integer.toString(sel.getSelectionID());
      if (!selectionIds.contains(selIdStr)) {
        for (int j = 0; j < originalVector.size(); j++) {
          Selection selSearch = (Selection)originalVector.elementAt(j);
          selIdSearch = selSearch.getSelectionID();
          selIdStrSearch = Integer.toString(selSearch.getSelectionID());
          if (selIdSearch == selId)
            finalVector.add(selSearch); 
        } 
        selectionIds.add(selIdStrSearch);
      } 
    } 
    return finalVector;
  }
  
  public static void hideElement(XStyleSheet report, String elementName) {
    ReportElement elementToHide = report.getElement(elementName);
    elementToHide.setVisible(false);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PhysicalProductActivitySummaryForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */