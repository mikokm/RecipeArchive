<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eeppinen drinkkiarkisto</title>

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

<div class="container-fluid">
	<h1>Create a new user</h1>
	<a href="users.jsp">Back to administration</a>

	<div class="form-group">
		<form role="form" action="addUser.jsp" method="post">
			<label>User name</label>
			<input type="text" name="name" class="form-control">
			<label>Password</label>
			<input type="password" name="password" class="form-control">
			<br>
			<input type="submit" name="addButton" class="btn btn-default pull-right" value="Add the user">
		</form>
	</div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
