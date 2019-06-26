
//HOME PAGE FUNCTION FILE:SelectOrgDes.jsp
function checkCookie(){
	// window.location.href="Login.jsp";
	var x=document.cookie;
	var x=x.split("=").pop();
	var url=window.location.href;
	var currentPage=url.split("/").pop();
	if(x==""){
		if(currentPage=="SelectOrgDes.jsp"){
			window.location.href="Login.jsp"
		}
		if(currentPage=="Login.jsp"){
		}
	}
	else{
		if(currentPage=="Login.jsp"){
			window.location.href="SelectOrgDes.jsp"
		}
		if(currentPage=="SelectOrgDes.jsp"){
			var uname=document.cookie;
			uname=uname.split("@");
			document.getElementById("UNAME").innerHTML=uname[0].split("=").pop();
		}
	}
}
function HomePageInterchangePlace(){
var x=document.getElementById("org");
var y=document.getElementById("dest");
var a = x.selectedIndex;
var b = y.selectedIndex;
x.selectedIndex = b;
y.selectedIndex = a;

}
function HomePageDisablePastDate()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy=today.getFullYear();
	if(dd<10){
		dd="0"+dd;
	}
	if(mm<10){
		document.getElementById("DateOfJourney").setAttribute("min",yyyy+"-0"+mm+"-"+dd);
		mm=mm+1;
		document.getElementById("DateOfJourney").setAttribute("max",yyyy+"-0"+mm+"-"+dd);
	}
	else{
		document.getElementById("DateOfJourney").setAttribute("min",yyyy+"-"+mm+"-"+dd);
		var month=mm+1;
		if(mm>12){
			yyyy+=1;
			mm=month-12;
		}
		document.getElementById("DateOfJourney").setAttribute("max",yyyy+"-"+mm+"-"+dd);
	}
	EnableCheckButton();
	
}
//SHOW AVAILABLE TRAIN AJAX FUNCTION
function showAvailableTrainAjax()
{
	var origin=document.getElementById("org").value;
	var destination=document.getElementById("dest").value;
	var Date=document.getElementById("DateOfJourney").value;
	var x=new XMLHttpRequest();
	x.open("GET","AvailableTrain?Origin="+origin+"&Destination="+destination+"&Date="+Date,true);
	//x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send();
	x.onreadystatechange=function(){
	    if(x.readyState==4 && x.status==200){
			var strJson=JSON.parse(x.responseText);
			var stringifyJson =JSON.stringify(strJson);
			console.log(stringifyJson);
			if(strJson["error"]==null){
				sessionStorage.setItem("TrainJson", stringifyJson);
				document.getElementById("HomeBodyFirst").hidden=true;
				document.getElementById("HomeBodySecond").hidden=true;
				var len=strJson["size"];
				document.getElementById("AvailableTrainDiv1Container").innerHTML="";
				var stationBetween=strJson["trainTrack"];
				var AllTrainStationArray=stationBetween.split("!");
				var counter=0;
        for(var i=0;i<len;i++){
            document.getElementById("AvailableTrainDiv1Container").innerHTML+="<div class=\"AvailableTrainDiv1\"id=\"AvailableTrainDiv1"+i+"\" onclick=\"selectRadio("+i+")\"><input type=\"radio\" name =\"selecttrain\" value="+i+"  hidden>Train Id -"+ strJson["key"+i]["TrainId"]+"&emsp;Train Name -"+ strJson["key"+i]["TrainName"] +"&emsp;Origin -"+ strJson["key"+i]["Origin"]+"  &emsp; Destination - "+strJson["key"+i]["Destination"]  +"  &emsp;Departure Time - "+strJson["key"+i]["DepartureTime"]
            +"</br></br>"
            +"Seats Available : AC Tier -"+strJson["key"+i]["SeatsInAcTier"]+" &nbsp; &nbsp; General Tier - "+strJson["key"+i]["SeatsInGeneralTier"]
            +"</br></br>"
						+"AC Tier Fare : Rs."+strJson["key"+i]["AcFare"]+" &nbsp &nbsp General Tier Fare : Rs."+strJson["key"+i]["GeneralFare"]+"<br><br>"
						+"<input type=\"radio\" value=\"1\"  name=\"tier\" hidden><span class=\"tierRadio\" onclick=\"selectTier("+counter+","+0+","+len+")\" required><span id=\"tickTrain"+counter+0+"\" hidden>&#10004;</span>AC Tier</span> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp"
						+"<input type=\"radio\" value=\"2\" name=\"tier\" hidden><span class=\"tierRadio\" onclick=\"selectTier("+counter+","+1+","+len+")\" required><span id=\"tickTrain"+counter+1+"\" hidden>&#10004;</span>General Tier</span><br></div>";
						var	stationArray=AllTrainStationArray[i].split("/");
						counter=counter+2;
						document.getElementById("AvailableTrainDiv1"+i).innerHTML+="<br><span>|-----"+stationArray[0]+"-----";
						for(var j=1;j<stationArray.length-1;j++){
						document.getElementById("AvailableTrainDiv1"+i).innerHTML+="|----- "+stationArray[j]+"-----";
						}
						document.getElementById("AvailableTrainDiv1"+i).innerHTML+="|</span>";
				}
			document.getElementById("AvailableTrainBodyId").hidden=false;
			document.getElementById("AvailableTrainBodyId").hidden=false;  
			document.getElementById("checkPNRPage").hidden=true;
			document.getElementById("viewBookingsContainer").hidden=true;
			document.getElementById("ChooseJourney").style="overflow: hidden;background-color:#2E4053;position: fixed;top: 0;left:250px;	border-radius:20px;padding:10px"
			}
			else{
				document.getElementById("incorrectInput").innerHTML=strJson["error"];
				document.getElementById("incorrectInput").hidden=false;
				document.getElementById("HomePageCheckTrainButton").disabled=true;
				if(document.getElementById("HomePageCheckTrainButton").disabled==true){
					document.getElementById("HomePageCheckTrainButton").style.cursor="not-allowed";
				}
				document.getElementById("AvailableTrainDiv1Container").innerHTML="";
				document.getElementById("AvailableTrainBodyId").hidden=true;
			}
        }
	}
}

//   GET PASSENGER INFO BUTTON

function GetPassengerInfoButton(){
	var flag=0;
	var chosen=document.getElementsByName("tier");
	for(var i=0;i<chosen.length;i++){
		if(chosen[i].checked==true){
			flag=1;
		}
	}
	if(flag==1){
		document.getElementById("ChooseJourney").hidden=true;
		var nos=document.getElementById("nos").value;
		var x=new XMLHttpRequest();
		x.open("GET","RedirectPassengerForm?nos="+nos,true);
		x.send();
		x.onreadystatechange=function(){
		    if(x.readyState==4 && x.status==200){
				for(var i =0;i<nos;i++){
					document.getElementById("Usf").innerHTML+=x.responseText;
				}
				document.getElementById("AvailableTrainBodyId").hidden=true;
		        document.getElementById("UserAndSeatsPage").hidden=false;
			}
		}
	}
	else{
		document.getElementById("chooseTrainAlert").innerHTML="<br><span id=\"chooseTrainAlertBody\">Choose Train and Tier to Proceed</span>";
		document.getElementById("chooseTrainAlert").hidden=false;
	}
}

//  GETTING INFO OF PASSENGERS

function GetPassengerInfoAPPEND(){
	sessionStorage.setItem("top","DebitCard");
	document.getElementById("CreditCard").style.display="none";
	document.getElementById("UPI").style.display="none";
	var passengerName=document.getElementsByName("pname");
	var pName="";
	for(var i=0;i<passengerName.length;i++){
		pName=pName+passengerName[i].value+"/";
	}
	var passengerAge=document.getElementsByName("page");
	var pAge="";
	for(var i=0;i<passengerAge.length;i++){
		pAge=pAge+passengerAge[i].value+"/";
	}
	document.getElementById("PASSENGERNAMES").value=pName;
	document.getElementById("PASSENGERAGES").value=pAge;
	document.getElementById("UserAndSeatsPage").hidden=true;
	document.getElementById("bookTicketAndChoosePayment").hidden=false;

	var strJ=sessionStorage.getItem("TrainJson");
	var strJson=JSON.parse(strJ);
	var radios = document.getElementsByName('selecttrain');
	var  radioValue=0;
	for (var i = 0, length = radios.length; i < length; i++) {
    	if (radios[i].checked) {
			radioValue=radios[i].value;
			break;
    	}
	}	
	console.log(radioValue+"rv");
	document.getElementById("bookTicketAndChoosePaymentTrainName").innerHTML=strJson["key"+radioValue]["TrainName"];
	document.getElementById("bookTicketAndChoosePaymentTrainId").innerHTML=strJson["key"+radioValue]["TrainId"];
	document.getElementById("bookTicketAndChoosePaymentOrigin").innerHTML=strJson["key"+radioValue]["Origin"];
	document.getElementById("bookTicketAndChoosePaymentDestination").innerHTML=strJson["key"+radioValue]["Destination"];
	document.getElementById("bookTicketAndChoosePaymentDepartureTime").innerHTML=strJson["key"+radioValue]["DepartureTime"];
	document.getElementById("bookTicketAndChoosePaymentDate").innerHTML=strJson["key"+radioValue]["Date"];
	var  tierValue=0;
	var tier = document.getElementsByName('tier');
	for (var i = 0;i < tier.length; i++) {
    	if (tier[i].checked) {
			tierValue=tier[i].value;
			break;
		}
	}
	var fare=0;
	var nos=document.getElementById("nos").value;
	sessionStorage.setItem("tierValue",tierValue);
	sessionStorage.setItem("radioValue",radioValue);
	if(tierValue==1){
		fare=strJson["key"+radioValue]["AcFare"];
	}
	if(tierValue==2){
		fare=strJson["key"+radioValue]["GeneralFare"];
	}
	document.getElementById("bookTicketAndChoosePaymentAmountToPay").innerHTML=fare*nos;
}

// CONFIRM BOOKING BUTTON

function ConfirmBooking(){
	var passengername=document.getElementById("PASSENGERNAMES").value;
	var passengerage=document.getElementById("PASSENGERAGES").value;
	var selectedTrain=sessionStorage.getItem("TrainJson");
	var selectedTrainJson=JSON.parse(selectedTrain);
	var tierValue=sessionStorage.getItem("tierValue");
	var radioValue=sessionStorage.getItem("radioValue");
	var selecttrain=selectedTrainJson["key"+radioValue]["selecttrain"];
	var top=sessionStorage.getItem("top");
	var cardnumber,cardYear,cardMonth,cardChecked;
	var x=new XMLHttpRequest();
	if(top=="DebitCard"){
		cardnumber=document.getElementById("DebitCardNumber").value;
		cardMonth=document.getElementById("DebitCardDate").value;
		cardYear=document.getElementById("DebitCardYear").value;
		if(document.getElementById("DebitCardChecked").checked){
			cardChecked="YES";
		}
		else{
			cardChecked="NO"
		}
		x.open("GET","ConfirmBooking?selecttrain="+selecttrain+"&tierValue="+tierValue+"&TypeOfPayment="+top+"&pname="+passengername+"&page="+passengerage+"&cardnumber="+cardnumber+"&cardmonth="+cardMonth+"&cardyear="+cardYear+"&cardchecked="+cardChecked,true);
	}
	else if(top=="CreditCard"){
		cardnumber=document.getElementById("CreditCardNumber").value;
		cardMonth=document.getElementById("CreditCardDate").value;
		cardYear=document.getElementById("CredititCardYear").value;
		if(document.getElementById("CreditCardChecked").checked){
			cardChecked="YES";
		}
		else{
			cardChecked="NO"
		}
		x.open("GET","ConfirmBooking?selecttrain="+selecttrain+"&tierValue="+tierValue+"&TypeOfPayment="+top+"&pname="+passengername+"&page="+passengerage+"&cardnumber="+cardnumber+"&cardmonth="+cardMonth+"&cardyear="+cardYear+"&cardchecked="+cardChecked,true);
	}
	else if(top=="UPI"){
		var upiId=document.getElementById("UPIID").value;
		x.open("GET","ConfirmBooking?selecttrain="+selecttrain+"&tierValue="+tierValue+"&TypeOfPayment="+top+"&pname="+passengername+"&page="+passengerage+"&upiid="+upiId,true);
	}
	var passengername=document.getElementById("PASSENGERNAMES").value;
	var passengerage=document.getElementById("PASSENGERAGES").value;
	x.send();
	x.onreadystatechange=function(){
	    if(x.readyState==4 && x.status==200){
			document.getElementById("bookTicketAndChoosePayment").hidden=true;
			document.getElementById("BookedPageDiv1").hidden=false;
			var resJson=x.responseText;
			var BookedJson=JSON.parse(resJson);
			document.getElementById("BookedPageSeatNo").innerHTML=BookedJson["seat"];
			document.getElementById("BookedPageSeatPNR").innerHTML=BookedJson["pnr"];
			document.getElementById("BookedPageOrigin").innerHTML=BookedJson["Origin"];
			document.getElementById("BookedPageDestination").innerHTML=BookedJson["Destination"];
			document.getElementById("BookedPageDoj").innerHTML=BookedJson["doj"];
			document.getElementById("BookedPageSeatDepartureTime").innerHTML=BookedJson["DepartureTime"];
		}
	}
}

//   CHECK PNR  BUTTON

function checkPNR(){
	document.getElementById("ChooseJourney").hidden=true;
	document.getElementById("HomeBodyFirst").hidden=true;
	document.getElementById("HomeBodySecond").hidden=true;
	var pnr=document.getElementById("HomePageEnterPnrTextBox").value;
	var x=new XMLHttpRequest();
	x.open("GET","checkPNR?pnr="+pnr,true);
	x.send();
	x.onreadystatechange=function(){
	    if(x.readyState==4 && x.status==200){
			var resJson=x.responseText;
			var pnrJson=JSON.parse(resJson)
			if(pnrJson["error"]==null){
				var nos=pnrJson["nos"];
				document.getElementById("HomeBodyFirst").hidden=true;
				document.getElementById("HomeBodySecond").hidden=true;
				document.getElementById("checkPNRPage").hidden=false;
				document.getElementById("checkPNRpnr").innerHTML=pnrJson["pnr"];
				document.getElementById("checkPNRNos").innerHTML=pnrJson["nos"];
				document.getElementById("checkPNROrigin").innerHTML=pnrJson["Origin"];
				document.getElementById("checkPNRDepartureTime").innerHTML=pnrJson["DepartureTime"];
				document.getElementById("checkPNRDestination").innerHTML=pnrJson["Destination"];
				document.getElementById("checkPNRDoj").innerHTML=pnrJson["Date"];
				// document.getElementById("DepartureTime").innerHTML=pnrJson["DepartureTime"];
				document.getElementById("checkPNRTop").innerHTML=pnrJson["top"];
				// console.log"dt"+(pnrJson["DepartureTime"]);	
				for(var i=0;i<nos;i++){
					console.log("pi "+pnrJson["passengerInfo"]["cancelPassenger"+i]);
					document.getElementById("checkPNRPassengerInfoContainer").innerHTML+="<div id=\"checkPNRPassengerInfo\" class=\"checkPNRPassengerInfo\"><input type=\"checkbox\" name=\"cancelPassenger\" value="+pnrJson["passengerInfo"]["cancelPassenger"+i]+" required>" 
														+"Passenger Name :"+pnrJson["passengerInfo"]["passengerName"+i]+"</br>"
														+"Passenger Age :"+pnrJson["passengerInfo"]["passengerAge"+i]+"</br>"
														+"Seat No :"+pnrJson["passengerInfo"]["seatNo"+i]+"</br>"
														+"<input name=\"uio\" type=\"text\"  value="+pnrJson["passengerInfo"]["status"+i]+" hidden>"
														+"Fare : "+pnrJson["passengerInfo"]["fare"+i]+"<br>"
														+"Status :<span name=\"id1\">"+pnrJson["passengerInfo"]["status"+i]+"<br>"
														+"</span></div>";
				}
				var y=document.getElementsByName("uio");
				var count=0;
				for(var k=0;k<y.length;k++){
					if(y[k].value=="Canceled"){
					document.getElementsByName("cancelPassenger")[k].checked = false;
					document.getElementsByName("cancelPassenger")[k].disabled = true;
					}
					if(document.getElementsByName("cancelPassenger")[k].disabled==true){
						count=count+1;
					}
				}
				if(count==nos){
					document.getElementById("checkPNRCancelButton").disabled=true;
					if(document.getElementById("checkPNRCancelButton").disabled==true){
						document.getElementById("checkPNRCancelButton").style.cursor="not-allowed";
					}
				}
			}
			else{
				document.getElementById("HomeBodyFirst").hidden=false;
				document.getElementById("HomeBodySecond").hidden=false;
				document.getElementById("incorrectInput").hidden=false;
				document.getElementById("ChooseJourney").hidden=false;
				document.getElementById("incorrectInput").innerHTML=pnrJson["error"];
				console.log(pnrJson["error"]);
			}
		}
	}
}
function cancelfunc(){
    var name="";
    var t=document.getElementsByName("cancelPassenger");
    for(var i=0;i<t.length;i++){
    	  var pasVal = document.getElementsByName("cancelPassenger");
        if(pasVal[i].checked===true){
    	  name=name+pasVal[i].value+"/";
    	  }
		}
    var xhttp=new XMLHttpRequest();
    xhttp.open("POST","CancelTicket",true);
    xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhttp.send("cnclPas="+name);
    xhttp.onreadystatechange=function(){
        if(xhttp.status==200 && xhttp.readyState==4){
					var canceledMessage = JSON.parse(xhttp.responseText);
					var p=document.getElementsByName("cancelPassenger");
					var count=0;
        	for(var i=0;i<p.length;i++){
        	  var pa = document.getElementsByName("cancelPassenger");
              if(pa[i].checked===true){
							document.getElementsByName("id1")[i].innerHTML=canceledMessage["message"];
							document.getElementById("cancelAmount").innerHTML=canceledMessage["Fare"];
							var cardno=canceledMessage["CardNo"];
							document.getElementById("last4number").innerHTML=cardno[12]+cardno[13]+cardno[14]+cardno[15];
							console.log(cardno[15]+"z");
							pa[i].checked=false;
        			pa[i].disabled=true;
			  }
			  count++;
			}
			if(count==p.length){
				// document.getElementById("checkPNRCancelButton").disabled=true;
			}
        }
		}
		document.getElementById("myModal").style.display="block";
}
function selectRadio(Select){
	document.getElementById("chooseTrainAlert").hidden=true;
	var temp=document.getElementsByClassName("AvailableTrainDiv1");
	for(var x=0;x<temp.length;x++){
	document.getElementsByClassName("AvailableTrainDiv1")[x].style.background="#839192";
	document.getElementsByClassName("AvailableTrainDiv1")[x].style.color="aliceblue";
	}
	document.getElementsByName("selecttrain")[Select].checked=true;
	document.getElementById("AvailableTrainDiv1"+Select).style.background='#b3cde0';
  document.getElementById("AvailableTrainDiv1"+Select).style.color="black";
}
function selectTier(tier,number,len){
	for(var i=0;i<=len;i+=2){
		document.getElementById("tickTrain"+i+"0").hidden=true;
		document.getElementById("tickTrain"+i+"1").hidden=true;
	}
	document.getElementById("chooseTrainAlert").hidden=true;
	document.getElementById("tickTrain"+tier+number).hidden=false;
	document.getElementsByName("tier")[tier+number].checked=true;
}
function LogOutButton(){
	document.cookie="email=";
}
function EnableCheckButton(){
	document.getElementById("incorrectInput").hidden=true;
	document.getElementById("HomePageCheckTrainButton").disabled=false;
	if(document.getElementById("HomePageCheckTrainButton").disabled==false){
		document.getElementById("HomePageCheckTrainButton").style.cursor="pointer";
	}
}
function viewBook(){
	document.getElementById("ChooseJourney").hidden=true;
	document.getElementById("AvailableTrainBodyId").hidden=true;
	document.getElementById("viewBookingsContainer").hidden=false;
	var x=new XMLHttpRequest();
	x.open("GET","viewBookings",true);
	//x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	x.send();
	x.onreadystatechange=function(){
	    if(x.readyState==4 && x.status==200){
			var strJson = JSON.parse(x.responseText);
			if(strJson["error"]==null){
				document.getElementById("HomeBodyFirst").hidden=true;
				document.getElementById("HomeBodySecond").hidden=true;
				var len=strJson["size"];
				document.getElementById("AvailableTrainBodyId").hidden=true;
            	for(var i=0;i<len;i++){
            	    document.getElementById("viewBookingsList").innerHTML+="<div class=\"viewBookingsDiv\">"
						+"<h3 align=\"center\" class=\"viewBookingsH3\">" +"PNR:"+strJson["Booking"+i]["pnr"+i]+"</br>"
						+"No.of.seats :"+strJson["Booking"+i]["nos"+i]+"</br>"
						+"Origin : " +strJson["Booking"+i]["Origin"+i]+"</br>"
						+"Destination : "+strJson["Booking"+i]["Destination"+i]+"</br>"
						+"Date of Journey : "+strJson["Booking"+i]["doj"+i]+"</br>Payment Mode : "+strJson["Booking"+i]["top"+i]+"</h3></div>";
					document.getElementById("ChooseJourney").style="overflow: hidden;background-color:#2E4053;position: fixed;top: 0;left:250px;	border-radius:20px;padding:10px"
					document.getElementById("checkPNRGoToHomePageId").hidden=false;
				}
			}
			else{
				document.getElementById("incorrectInput").innerHTML=strJson["error"];
				document.getElementById("incorrectInput").hidden=false;
				document.getElementById("AvailableTrainDiv1Container").innerHTML="";
				document.getElementById("AvailableTrainBodyId").hidden=true;
			}
    }
	}	
}
function selectRadioForPaymentDebitCard(item){
	 document.getElementsByName("TypeOfPayment")[item].checked=true;
	 document.getElementById("DebitCard").hidden=false;
	 document.getElementById("UPI").hidden=true;
	 document.getElementById("CreditCard").hidden=true;
}
function selectRadioForPaymentCreditCard(item){
	document.getElementsByName("TypeOfPayment")[item].checked=true;
	document.getElementById("CreditCard").hidden=false;
	document.getElementById("DebitCard").hidden=true;
	document.getElementById("UPI").hidden=true;
}
function selectRadioForPaymentUPI(item){
	document.getElementsByName("TypeOfPayment")[item].checked=true;
	document.getElementById("UPI").hidden=false;
	document.getElementById("CreditCard").hidden=true;
	document.getElementById("DebitCard").hidden=true;
}
function ChoosePaymentMethod(evt, TypeOfPayment) {
		console.log(TypeOfPayment);
		document.getElementById("DebitCard").style.display="none";
		document.getElementById("CreditCard").style.display="none";
		document.getElementById("UPI").style.display="none";
		document.getElementById(TypeOfPayment).style.display="block";
		var top=TypeOfPayment;
		sessionStorage.setItem("top",top);
}
function hideAlert() {
	var modal = document.getElementById("myModal");
  modal.style.display = "none";
}
window.onclick = function(event) {
  if (event.target == modal) {
		var modal = document.getElementById("myModal");
    modal.style.display = "none";
  }
}