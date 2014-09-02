<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template>
	<div class="jumbotron col-md-6 col-md-offset-3">
		<h2 class="text-center">Eeppinen drinkkiarkisto</h2>
		<br>
		<form role="form" action="login" method="post">
			<div class="form-group">
				<label>Username</label> <input type="text" name="username" value="${username}" placeholder="Username" class="form-control">
			</div>

			<div class="form-group">
				<label>Password</label> <input type="password" name="password" value="${password}" placeholder="Password" class="form-control">
			</div>

			<button type="submit" class="btn btn-default pull-right" name="loginButton">Login</button>
		</form>
	</div>
</t:template>
