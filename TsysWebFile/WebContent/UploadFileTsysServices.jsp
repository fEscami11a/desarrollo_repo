<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function validarFormaUpload(){
	
	 if(document.uploadForm.F1.value == ''){
	 	alert('Favor de ingresar archivo');
		return false;
		}else if(document.uploadForm.F1.value.indexOf('.txt')==-1){
			alert('Debe subir un archivo .txt');
			return false;
		}
	
document.forms[0].setAttribute("method", "post");
	if(document.getElementById("operacion").selectedIndex==0){
		alert('seleccione una operacion');
		return false;
		}else {
			document.uploadForm.action=document.getElementById("operacion")[document.getElementById("operacion").selectedIndex].value
		}
		document.uploadForm.submit();
	
	}
</script>
</head>
<body>
<a href="<c:url value="/jsp/logout.jsp"/>">Salir</a>

<FORM name="uploadForm"
	ENCTYPE="multipart/form-data" ACTION="" METHOD="POST">
<br>
<br>
<center>
<h3>Servicios TSYS Archivos</h3>
</center>
<br>
<center>

<table border="2">
	<tr>
		
		<td colspan="2">
		<p align="center">
		<B>Ingresar archivo .txt con los datos separados por |</B>
		</p>
		
		</td>
	</tr>
	<tr>
		<td><b>Seleccionar archivo para cargar:</b></td>
		<td><INPUT NAME="F1" TYPE="file"></td>
	</tr>
	<tr>
		<td><b>Que operacion desea hacer:</b></td>
		<td>
			<SELECT name="operacion" id="operacion">
				<option value="0">--Seleccionar--</option>
				<c:if test="${request.isUserInRole('USR_TSYS')}">
					<option value="UploadWRFDFile">Update Card Guard Action</option>
				</c:if>
				<option value="UploadPromoFile">Enviar promociones</option>
				<option value="UploadRfcFile">Actualizar RFC</option>
				<option value="UploadAgentFile">Actualizar agente</option>
				<option value="UploadCreditLimitFile">Limite de credito</option>
				<option value="UploadUcode3File">Actualizar Ucode3</option>
				<option value="UploadTypeCodeFile">Actualizar Type Code</option>
				<option value="UploadUcode2File">Actualizar Ucode2</option>
				<option value="UploadInsLoanAcctFile">Actualizar InsLoanAcctNum</option>
				<option value="UploadExpDateFile">Actualizar Fecha Expiracion</option>
				<option value="UploadMsgFileICM">Enviar Mensajes ICM</option>
				
				<option value="UploadTs2CreditLimitFile">Limite de credito TS2</option>
				<option value="UploadReqAcctTermTransferFile">ReqAcctTermTransfer (MATT)</option>
				<option value="UploadPPNGI">Generar PPNGI</option>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<p align="right">
			<INPUT TYPE="button" VALUE="Send File" onclick="validarFormaUpload()">
		</p>
		</td>
	</tr>
</table>
</center>
</FORM>
</body>
</html>