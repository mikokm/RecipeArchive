<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="createDrink">
<div class="container-fluid">
	<div class="page-header">
		<h1>Create a new drink</h1>
	</div>
		
	<c:set var="drink" value="${sessionScope.activeDrink}"/>
	
	<div class="form-group">
		<form role="form" action="createDrink" method="post">
			<label>Drink name</label>
			<input type="text" name="name" value="${drink.name}" class="form-control">
			<label>Description</label>
			<input type="text" name="description" value="${drink.description}" class="form-control">
			<label>Ingredients (for example 10 sugar, 20 water)</label>
			<input type="text" name="ingredients" value="" class="form-control">
			<label>Picture URL</label>
			<input type="text" name="image" value="${drink.url}" class="form-control">
			<br>
			<input type="submit" name="createButton" class="btn btn-default pull-right" value="Create a new drink">
			<input type="submit" name="modifyButton" class="btn btn-default pull-right" value="Modify drink">
		</form>
	</div>
</div>
</t:template>