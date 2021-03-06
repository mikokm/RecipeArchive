<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="page"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eeppinen drinkkiarkisto</title>

<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/custom.css" rel="stylesheet">

<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
</head>
<body>
	<c:if test="${page != null}">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#links">
						<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="landing">Eeppinen drinkkiarkisto</a>
				</div>
				<div class="collapse navbar-collapse" id="links">
					<ul class="nav navbar-nav navbar-left">
						<li <c:if test="${page == 'favourites'}"> class="active"</c:if>><a href="favourites">Favourite drinks</a></li>
						<li <c:if test="${page == 'drinklist'}"> class="active"</c:if>><a href="drinklist">Find drinks</a></li>
						<li <c:if test="${page == 'createDrink'}"> class="active"</c:if>><a href="createDrink">Create a new drink</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</c:if>
	
	<div class="row">
	<c:if test="${pageError != null}">
		<div class="col-md-2 col-md-offset-5 text-center">
			<h1>Error!</h1>
			<p>${pageError}</p>
		</div>
	</c:if>
	</div>
	<jsp:doBody />
</body>
</html>
