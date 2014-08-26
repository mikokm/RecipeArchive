<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="createDrink">
<div class="container-fluid">
	<c:choose>
		<c:when test="${drink == null}">
			<c:set var="action" value="createDrink" />
			<c:set var="type" value="Create a new drink" />
		</c:when>
		<c:otherwise>
			<c:set var="action" value="updateDrink" />
			<c:set var="type" value="Modify drink" />
		</c:otherwise>
	</c:choose>
	
	<div class="page-header">	
		<h1>${type}</h1>
	</div>

	<div class="form-group">
		<form role="form" action="${action}" method="post">
			<label>Drink name</label>
			<input type="text" name="name" value="${drink.name}" class="form-control">
			<label>Description</label>
			<input type="text" name="description" value="${drink.description}" class="form-control">
			<label>Ingredients</label>
			<input type="text" name="ingredients" value="" class="form-control">
			<label>Image URL</label>
			<input type="text" name="imageUrl" value="${drink.imageUrl}" class="form-control">
			<br>
			<input type="hidden" name="drinkId" value="${drink.id}">
			<input type="submit" name="button" class="btn btn-default pull-right" value="${type}">
		</form>
	</div>
</div>
</t:template>