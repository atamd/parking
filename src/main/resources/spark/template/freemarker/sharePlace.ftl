<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
  <script  type="text/html"  src="/js/bootstrap-datepicker.js"></script>
  <link rel="stylesheet"  type="text/html" href="/stylesheets/datepicker.css"/>
<script>
	if (top.location != location) {
    top.location.href = document.location.href ;
  }
  
		$( document ).ready(function() {
		//http://www.eyecon.ro/bootstrap-datepicker/
			window.prettyPrint && prettyPrint();



        // disabling dates
        var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

        $('#dpd1').val((nowTemp.getDate() + 1) + '/' + (nowTemp.getMonth()+1) + '/' +  nowTemp.getFullYear());
       

        var checkin = $('#dpd1').datepicker({
          onRender: function(date) {
            return date.valueOf() <= now.valueOf() ? 'disabled' : '';
          }
        }).on('changeDate', function(ev) {
          if (ev.date.valueOf() > checkout.date.valueOf()) {
            var newDate = new Date(ev.date)
            newDate.setDate(newDate.getDate());
            checkout.setValue(newDate);
          }
          checkin.hide();
          $('#dpd2')[0].focus();
        }).data('datepicker');
        var checkout = $('#dpd2').datepicker({
          onRender: function(date) {
            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
          }
        }).on('changeDate', function(ev) {
          checkout.hide();
        }).data('datepicker');
		});
			</script>
</head>

<body>
  <#include "nav.ftl">


<div class="container">
    <div class="row">
    <form method="post" role="form">
        <div class='col-sm-6'>
           <div class="well text-center">
           			<input type="hidden" name="number" value="${placeNumber}"/>
			        Je lib&egrave;re la place n&deg;<strong>${placeNumber}</strong> pour la (ou les) journ&eacute;e(s) <br/>
			        du <input type="text" class="span2" value="" data-date-format="dd/mm/yyyy" id="dpd1" length="10" size="10" name="dateDebut"/> au
			         <input type="text" class="span2" value="" data-date-format="dd/mm/yyyy" id="dpd2" length="10" size="10" name="dateFin"/>
			      <br/>
			            <input type="submit" class="btn btn-ok" value="Valider"/>
          </div>
        </div>
	</form>
    </div>
</div>

</body>
</html>