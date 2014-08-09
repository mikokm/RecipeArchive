<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Landing</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">

<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#links">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="landing.jsp">Eeppinen drinkkiarkisto</a>
		</div>
		<div class="collapse navbar-collapse" id="links">
			<ul class="nav navbar-nav navbar-left">
				<li><a href="favourites.jsp">Favourite drinks</a></li>
				<li><a href="drinks.jsp">Find drinks</a></li>
				<li><a href="createDrink.jsp">Create a new drink</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout.jsp">Logout</a></li>
			</ul>
		</div>
	</div>
</nav>

<div>
	<h1>Welcome back [name]!</h1>
	There has been [count] drink recipes added since your last visit! See them <a href="latest.jsp">here</a>.
</div>

<div>
	<h2>Administration</h2>
	Click <a href="users.jsp">here</a> to open administration page.
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
