<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="scriptFile.js"></script>
<link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
<div id="HomeBodyFirst">
		<h2 class="HomePageH2" margin-top="40px" align="center">Welcome, <span id="UNAME"></span></h2></html>
		<script>checkCookie();</script>	
		<div>		<span><input type="button" class="HomePageViewBookings" name="viewBookings" value="View Bookings"  onclick="viewBook()">
		<form method="post">
			<input type="submit" value="Log Out" id="HomePageLogOutButton" onclick="LogOutButton()">
	 </form></span>
		</div>
<br>
<p id="pid"><h2 class="HomePageH2" > &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;  Choose your Journey</h2></p>
</div>
<form method=post onsubmit="showAvailableTrainAjax(); return false">
    <div class="HomePageTemp"></div>
    <div class="HomePageDiv">
		<div id="ChooseJourney" >	
			<select id="org"  name="Origin" class="HomePageSelect" onclick="EnableCheckButton()" required><option  class="HomePageOption" value="" disabled selected>Select Origin</option><option value="Chennai" class="HomePageOption">Chennai</option><option value="KanyaKumari"class="HomePageOption">KanyaKumari</option><option value="Coimbatore" class="HomePageOption">Coimbatore</option><option value="Salem" class="HomePageOption">Salem</option><option value="Erode" class="HomePageOption">Erode</option><option value="Trichy" class="HomePageOption">Trichy</option><option value="HomePageOption" class="HomePageOption">Tripur</option><option value="Kancheepuram" class="HomePageOption">Kancheepuram</option><option value="Thirunelveli" class="HomePageOption">Thirunelveli</option></select>    
			<input type="button" onclick="HomePageInterchangePlace()" class="HomePageInterchange" value="&harr;">
			<select name="Destination" id="dest" class="HomePageSelect" onclick="EnableCheckButton()" required=""><option class="HomePageOption" value="" disabled selected>Select Destination</option><option   value="Chennai" class="HomePageOption">Chennai</option><option  value="KanyaKumari" class="HomePageOption">KanyaKumari</option><option value="Coimbatore" class="HomePageOption">Coimbatore</option><option value="Salem" class="HomePageOption">Salem</option><option value="Erode" class="HomePageOption">Erode</option><option value="Trichy" class="HomePageOption">Trichy</option><option value="Tripur" class="HomePageOption">Tripur</option><option value="Kancheepuram" class="HomePageOption">Kancheepuram</option><option value="Thirunelveli" class="HomePageOption">Thirunelveli</option></select> 
			<input type="date" name="Date" id="DateOfJourney" class="HomePageDate"  min="" max="" required onclick="HomePageDisablePastDate()">
			<input type="submit" value="Check Availability" class="HomePageCheckTrainButton" id="HomePageCheckTrainButton">
			<p id="incorrectInput" hidden=true></p>
		</div>
</form>
<p id="id1"></p>
</div>
<div id="HomeBodySecond">
<br>
<h3 class="CheckPNRStatusText"> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp; Check PNR Status</h3></p>
<form onsubmit="checkPNR();return false">
<input type="number" name="pnr" placeholder="Enter PNR Number" min=1 id="HomePageEnterPnrTextBox" required>
<input type="submit" value="Check Now" id="HomePageCheckPnrButton" >
</form>
</div>

<!-- AVAILABLE TRAIN BODY -->


<div id="AvailableTrainBodyId" hidden>
	<h3 align="center" style="font-family:arial" id="AvailableTrainHeading">Available Train(s)</h3>						
	<div class="AvailableTrainTemp"></div>
	<form onsubmit="GetPassengerInfoButton(); return false;">
	<div class="AvailableTrainDiv1Container" id="AvailableTrainDiv1Container">
        <!--<h4 style=\"color:aliceblue\">-->
	</br></div></h4>
	<div id="chooseTrainAlert" hidden></div>
	<div class="AvailableTrainDiv2">
	</div>
	  <h4 style="color:aliceblue;font-family:arial;" class="AvailableTrainSeatsText">No.of Seats</h4>
	  <input type="number" name="nos" id="nos" class="AvailableTrainSeatsTextField" placeholder="No. of Seats"  min="1" max="4" required></br></br>
	  <input type="submit" value="Book Now" class="AvailableTrainBookNowButton">
	</form>
</div><!--available train body  end-->

<!-- GET PASSENGER INFO BODY -->
<div id="UserAndSeatsPage" hidden>
<p id="PassenerDetailsHeading">Passenger Details</p>
<form onsubmit="GetPassengerInfoAPPEND(); return false;">
<div id="Usf">
</div>
<input type="submit" value="Book Tickets" class="BooKTicketButton">
</form>
<input type="text" id="PASSENGERNAMES" name="names" value="tempName" hidden>
<input type="text" id="PASSENGERAGES" name="ages" value="tempAge" hidden>
</div>


<!--bookTicketAndChoosePayment PAGE-->

<div id="bookTicketAndChoosePayment" hidden>
		<h2 class="bookTicketAndChoosePaymentH2">Train Details</h2>
		<div class="bookTicketAndChoosePaymentCONTAINER">
			<h3>Train Id :  <span id="bookTicketAndChoosePaymentTrainId"></span>  &nbsp &nbsp &nbsp &nbsp    Train Name : <span id="bookTicketAndChoosePaymentTrainName"></span></h3>
		  <h3>Origin :  <span id="bookTicketAndChoosePaymentOrigin"></span> &nbsp &nbsp &nbsp &nbsp Destination : <span id="bookTicketAndChoosePaymentDestination"></span></h3>
			<h3>Departure Time :  <span id="bookTicketAndChoosePaymentDepartureTime"></span>&nbsp; &nbsp;
				Date of Journey : <span id="bookTicketAndChoosePaymentDate"></span></br></br>
				Amount to pay :Rs.   <span id="bookTicketAndChoosePaymentAmountToPay"></span>
		</div>
				<div class="tab" id="tabid">
						<button class="tablinks" onclick="ChoosePaymentMethod(event, 'DebitCard')">Debit Card</button>
						<button class="tablinks" onclick="ChoosePaymentMethod(event, 'CreditCard')">Credit Card</button>
						<button class="tablinks" onclick="ChoosePaymentMethod(event, 'UPI')">UPI</button>
					</div>
					
					<div id="DebitCard" class="tabcontent">
									<form onsubmit="ConfirmBooking(); return false;">
												Debit Card Number<br>
												<input type="text" pattern="[0-9]{16}" id="DebitCardNumber" required  style="width:232px;"><br><br>
												Expiry Date &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; CVV<br>
												<input type="number" min=1 max=12 id="DebitCardDate" required><span style="font-size: large">/</span>
												<input type="number" min=2019 max=2030 id="DebitCardYear" required>&emsp;&emsp;&emsp;&emsp;
												<input type="password" pattern="[0-9]{3}" style="width:50px;" minlength=3 maxlength=3 required><br><br>
												<input type="checkbox" id="DebitCardChecked">save card for next time
												<input type="submit" name="DebitCard" class="TypeOfPayment" value="Pay">
									</form>
					</div>
					
					<div id="CreditCard" class="tabcontent">
						<form onsubmit="ConfirmBooking(); return false;">
						Credit Card Number<br>
												<input type="text" pattern="[0-9]{16}" id="CreditCardNumber" minlength=16 maxlength=16 style="width:232px;" required ><br><br>
												Expiry Date &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; CVV<br>
												<input type="number" min=1 max=12 required id="CreditCardDate"><span style="font-size: large">/</span>
												<input type="number" min=2019 max=2030 required id="CreditCardYear">&emsp;&emsp;&emsp;&emsp;
												<input type="password" pattern="[0-9]{3}" style="width:50px;" minlength=3 maxlength=3 required><br><br>
												<input type="checkbox" id="CreditCardChecked">save card for next time
												<input type="submit" name="CreditCard" class="TypeOfPayment" value="Pay">
						</form>
					</div>
					
					<div id="UPI" class="tabcontent">
						Enter UPI ID
									<form onsubmit="ConfirmBooking(); return false;">
										<input type="email" required><br><br>
										Enter UPI PIN
										<input type="password" pattern="[0-9]{6}" style="width:50px;" id="UPIID" minlength=4 maxlength=6 required><br><br>
										<input type="submit" name="UPI" class="TypeOfPayment" value="Pay">
									</form>
					</div>
		<h3>
</div>

<!--BOOKED PAGE -->
<div class="BookedPageDiv1" id="BookedPageDiv1" hidden>
		<h3 align="center" class="BookedPageH3">
				<p style="margin-bottom:0.25px;">Booking Successful</p></br>
SeatNo :<span id="BookedPageSeatNo"></span></br>
PNR: <span id="BookedPageSeatPNR"></span> </br>
Origin : <span id="BookedPageOrigin"></span></br>
Destination : <span id="BookedPageDestination"></span></br>
Date of Journey : <span id="BookedPageDoj"></span></br>
Departure Time : <span id="BookedPageSeatDepartureTime"></span>
<form method="post" ></br><a href="SelectOrgDes.jsp" class="BookedPageA">Home Page</a>
	<!-- <input type="button" value="Cancel" id="CancelButton" onclick="cancelfunc()"> -->
	<button onclick="window.print()" id="PrintTicket">Print Ticket<span class="glyphicon glyphicon-print"></span></button>
	</form>
	</h3> 
</div>

<!-- CHECKING PNR  -->
<div id="checkPNRPage" hidden>
<div class="checkPNRdiv">
		<h3 class="checkPNRh3">
			<p>PNR Status </p></br>
			PNR: <span id="checkPNRpnr"></span></br></br>
			No.of.seats :<span id="checkPNRNos"></span></br>
			Origin : <span id="checkPNROrigin"></span></br>
			Destination : <span id="checkPNRDestination"></span></br>
			Date of Journey : <span id="checkPNRDoj"></span></br>
			Daparture Time : <span id="checkPNRDepartureTime"></span><br>
			Payment Mode : <span id="checkPNRTop"></span>
 </h3>
</div>
<div id="checkPNRPassengerInfoContainer">
	</div>	    
<a href="SelectOrgDes.jsp" class="checkPNRGoToHomePage" id="checkPNRGoToHomePageId">Home Page</a>
<input type="button" onclick="cancelfunc()" value="Cancel Ticket" id="checkPNRCancelButton">
</div>
<input name="cancelPassnegerINFO" type="text" hidden value="">
<div id="viewBookingsContainer" hidden>
<h2 style="text-align:center;font-family:arial;color:aliceblue;">Your Last Booking(s)</h2>
<div id="viewBookingsList"></div>
</div>
		<div id="myModal" class="modal" style="display: none">
		<!-- Modal content -->
		<div class="modal-content">
			<span class="close" onclick="hideAlert()">&times;</span>
			<p><span>Rs.<span id="cancelAmount"></span> will refunded to XXXX XXXX XXXX <span id="last4number"></span>  in 2 to 3 working days</p>
		</div>
	
	</div>
</body>
</html>
 