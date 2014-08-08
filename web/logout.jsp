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

<div class="jumbotron col-md-6 col-md-offset-3">
	<h2 class="text-center">Eeppinen drinkkiarkisto</h2>
	<br>
	<form role="form" action="landing.jsp" method="post">
		<div class="form-group">
			<label>Username</label>
			<input type="text" name="username" placeholder="Username" class="form-control">
		</div>
	
		<div class="form-group">
			<label>Password</label>
			<input type="password" name="password" placeholder="Password" class="form-control">
		</div>
		<button type="submit" class="btn btn-default pull-right">Login</button>
	</form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
