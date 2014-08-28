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
				<div class="row">
					<div class="col-md-6">
						<label>Drink name</label>
						<input type="text" name="name" value="${drink.name}" class="form-control">
						<label>Description</label>
						<input type="text" name="description" value="${drink.description}" class="form-control">
						<label>Image URL</label>
						<input type="text" name="imageUrl" value="${drink.imageUrl}" class="form-control">
					</div>
					<div class="col-md-6">
						<label>Ingredients</label>
						<c:forEach var="i" begin="0" end="9">
							<input type="text" name="ingredients${i}" value="${drink.ingredients[i]}" class="form-control"><br>
						</c:forEach>
					</div>
				</div>
				<br>
				<input type="hidden" name="drinkId" value="${drink.id}"> <input type="submit" name="button" class="btn btn-default pull-left" value="${type}">
			</form>
		</div>
	</div>
</t:template>