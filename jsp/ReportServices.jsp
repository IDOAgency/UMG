<%@ include file="template-top-page.shtml"%>

<%
String reportId = context.getDelivery("ReportServicesReportId") != null ? (String)context.getDelivery("ReportServicesReportId") : "";
String reportName = context.getDelivery("ReportServicesReportName") != null ? (String)context.getDelivery("ReportServicesReportName") : "";
%>


<script>
var isMAC = false;
// determine if a MAC
var navCom = navigator.platform;
if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" )
  isMAC = true;

var url = "http://10.130.187.169/MilestoneSQLReportSvc/Download_Report.aspx?report_id=" + "<%=reportId%>" + "&report_name=" + "<%=reportName%>";
if(isMAC)
{
  url += "&ismac=true";
  var winPrefs = "width=275,height=275,top=130,left=300,resizable=no,menubar=no,toolbar=no,location=no,scrollbars=no";
  reportSvcWin = window.open(url,"ReportServices",winPrefs);

  if(reportSvcWin.opener.parent.top.menuFrame && reportSvcWin.opener.parent.top.menuFrame.document.all.WaitLayerDiv)
    reportSvcWin.opener.parent.top.menuFrame.document.all.WaitLayerDiv.style.display = "none";

  reportSvcWin.focus();


} else {

  url += "&ismac=false";
  var winPrefs = "width=275,height=275,top=130,left=300,resizable=no,menubar=no,toolbar=no,location=no,scrollbars=no";
  reportSvcWin = window.open(url,"ReportServices",winPrefs);

  if(reportSvcWin.opener.parent.top.menuFrame && reportSvcWin.opener.parent.top.menuFrame.document.all.WaitLayerDiv)
    reportSvcWin.opener.parent.top.menuFrame.document.all.WaitLayerDiv.style.display = "none";

  reportSvcWin.focus();
}

</script>



