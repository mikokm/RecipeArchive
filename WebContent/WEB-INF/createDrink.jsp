<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="createDrink">
<div class="container-fluid">
	<h1>Create a new drink</h1>

	<div class="form-group">
		<form role="form" action="createDrink" method="post">
			<label>Drink name</label>
			<input type="text" name="name" class="form-control">
			<label>Description</label>
			<input type="text" name="description" class="form-control">
			<label>Ingredients</label>
			<input type="text" name="ingredients" class="form-control">
			<label>Picture URL</label>
			<input type="text" name="image" class="form-control">
			<br>
			<input type="submit" name="addButton" class="btn btn-default pull-right" value="Add the drink">
		</form>
	</div>
</div>
</t:template>