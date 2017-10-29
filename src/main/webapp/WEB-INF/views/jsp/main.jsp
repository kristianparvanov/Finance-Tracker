<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.financeTracker.model.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main | Finance Tracker</title>
<script src="<c:url value="/js/Chart.bundle.js" />"  type ="text/javascript"></script>
<script src="<c:url value="/js/utils.js" />"  type ="text/javascript"></script>
</head>
<body>
	
	<div>
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	
	<div class="content-wrapper">
		 <section class="content-header">
			<h2>Current balance across all accounts: <c:out value="${ balance }"></c:out></h2>
			<h1>All accounts</h1>
		</section>
		
		<section class="content">
			<div class="row">
			<c:forEach items="${ accounts }" var="account">
				<div class="col-lg-3 col-xs-6">
					<!-- small box -->
					<c:if test="${fn:contains(account.name, 'card')}">
			            <div class="small-box bg-yellow">
	            	</c:if>
	              	<c:if test="${fn:contains(account.name, 'Cash')}">
	            		<div class="small-box bg-green">
	            	</c:if>
	            	<c:if test="${fn:contains(account.name, 'Bank')}">
	            		<div class="small-box bg-red">
	            	</c:if>
	            	<c:if test="${!fn:contains(account.name, 'card') && !fn:contains(account.name, 'Cash') && !fn:contains(account.name, 'Bank')}">
	            		<div class="small-box bg-aqua">
	            	</c:if>
			            <div class="inner">
			              <h3><i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${account.amount}" minFractionDigits="2"/></h3>
			              <p><c:out value="${account.name}"></c:out></p>
			            </div>
			            <div class="icon">
			            	<c:if test="${fn:contains(account.name, 'card')}">
			            	<div><i class="ion ion-card"></i></div>
			            	</c:if>
			              	<c:if test="${fn:contains(account.name, 'Cash')}">
			            		<div><i class="ion ion-cash"></i></div>
			            	</c:if>
			            	<c:if test="${fn:contains(account.name, 'Bank')}">
			            		<div><i class="ion ion-social-usd"></i></div>
			            	</c:if>
			            	<c:if test="${!fn:contains(account.name, 'card') && !fn:contains(account.name, 'Cash') && !fn:contains(account.name, 'Bank')}">
			            		<div><i class="ion ion-pie-graph"></i></div>
			            	</c:if>
			            </div>
			            <a href="account/${account.accountId}" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
			          </div>
				</div>
			</c:forEach>
			
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
		          <div class="small-box bg-aqua">
		            <div class="inner">
		              <h3>Add</h3>
		              <p>Add new Account</p>
		            </div>
		            <div class="icon">
		             <div> <i class="ion ion-plus"></i></div>
		            </div>
		            <a href="addAccount" class="small-box-footer">Get started <i class="fa fa-arrow-circle-right"></i></a>
			          </div>
				</div>
			</div>
			
			<div>
				<c:set var="transactions" value="${ transactionsValues }" />
				<c:set var="transactions" value="${ transactionsValues }" />
			    <c:set var="transactionsCategories" value="${ transactionsCategories }" />
			    
				<div style="width:100%; height: 70%">
			        <canvas id="canvas"></canvas>
			    </div>
			    
				<div id="canvas-holder" style="width:100%">
        			<canvas id="chart-area" ></canvas>
   				</div>
				<script>
					var presets = window.chartColors;
					var utils = Samples.utils;
					var values = '${transactions}';
					var dates = '${transactionsDates}';
					var cats = '${transactionsCategories}';
					
					values = values.replace(/[\[\]']+/g,'')
					dates = dates.replace(/[\[\]']+/g,'')
					cats = cats.replace(/[\{\}']+/g,'')
					
					var allTrans = [];
					$.each(values.split(","), function(i,e){
						allTrans.push(e);
					});
					
					var allDates = [];
					$.each(dates.split(","), function(i,e){
						allDates.push(e);
					});
					
					var MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
				    var config = {
				        type: 'line',
				        data: {
				            labels: allDates,
				            datasets: [{
				                label: "Cashflow",
				                backgroundColor: utils.transparentize(presets.blue),
				                borderColor: window.chartColors.blue,
				                data: allTrans,
				                fill: true,
				            }]
				        },
				        options: {
				            responsive: true,
				            title:{
				                display:true,
				                text:'Balance chart'
				            },
				            tooltips: {
				                mode: 'index',
				                intersect: false,
				            },
				            hover: {
				                mode: 'nearest',
				                intersect: true
				            },
				            scales: {
				                xAxes: [{
				                    display: true,
				                    scaleLabel: {
				                        display: true,
				                        labelString: 'Month'
				                    }
				                }],
				                yAxes: [{
				                    display: true,
				                    scaleLabel: {
				                        display: true,
				                        labelString: 'Value'
				                    }
				                }]
				            }
				        }
				    };
				    
				    var randomScalingFactor = function() {
				        return Math.round(Math.random() * 100);
				    };
				    
				    var catsKeyValuePairs = [];
				    var catNames = [];
				    var catValues = [];
					catsKeyValuePairs = cats.split(",");
					//alert(catsKeyValuePairs);
					for (var i = 0; i < catsKeyValuePairs.length; i++) {
						var kv = catsKeyValuePairs[i].split("=");
						catValues.push(kv[0]);
						catNames.push(kv[1]);
					}
					
					alert(catNames);
					alert(catValues);

				    var config2 = {
				        type: 'doughnut',
				        data: {
				            datasets: [{
				                //data: [ 48,25,68,7,98,45,48,98,87 ],
				                data: catValues,
				                backgroundColor: [
				                    window.chartColors.red,
				                    window.chartColors.orange,
				                    window.chartColors.yellow,
				                    window.chartColors.green,
				                    window.chartColors.blue,
				                    window.chartColors.red,
				                    window.chartColors.orange,
				                    window.chartColors.yellow,
				                    window.chartColors.green,
				                    window.chartColors.blue,
				                    window.chartColors.red,
				                    window.chartColors.orange,
				                    window.chartColors.yellow,
				                    window.chartColors.green,
				                    window.chartColors.blue,
				                ],
				                label: 'Dataset 1'
				            }],
				           // labels: [
				             //   "Red",
				              //  "Orange",
				             //   "Yellow",
				             //   "Green",
				             //   "Blue","Green1",
				             //   "Blue3","Green2",
				            //    "Blue4"
				            //]
				            labels: catNames
				        },
				        options: {
				            responsive: true,
				            legend: {
				                position: 'top',
				            },
				            title: {
				                display: true,
				                text: 'Chart.js Doughnut Chart'
				            },
				            animation: {
				                animateScale: true,
				                animateRotate: true
				            }
				        }
				    };

				    window.onload = function() {
				    	var ctx = document.getElementById("canvas").getContext("2d");
				        window.myLine = new Chart(ctx, config);
				        var ctx2 = document.getElementById("chart-area").getContext("2d");
				        window.myDoughnut = new Chart(ctx2, config2);
				    };
				    
				</script>
			</div>
			
			
			
				   
			</div>
	 	</section>
	</div>
	
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
	<!-- chartJS utils -->
	<script src="<c:url value="/js/utils.js" />"  type ="text/javascript"></script>

</body>
</html>