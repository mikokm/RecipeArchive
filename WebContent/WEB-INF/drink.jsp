<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="drink">
	<div class="container-fluid">
		<div class="page-header">
			<h1><c:out value="${drink.name}" /></h1>
		</div>

		<div class="row">
			<div class="container col-md-3">
				<img alt="<c:out value="${drink.name}" />" src="<c:out value="${drink.imageUrl}" />">
			</div>

			<div class="container col-md-9">
				<h3>Description</h3>
				<p><c:out value="${drink.description}" /></p>

				<h3>Added by</h3>
				<p><c:out value="${drink.owner}" /></p>

				<h3>Addition date</h3>
				<p><c:out value="${drink.date}" /></p>

				<h3>Ingredients</h3>
				<ul>
				<c:forEach var="entry" items="${drink.ingredients}">
				<li><c:out value=" ${entry}" /></li>
				</c:forEach></ul>
			</div>
		</div>
		<br>
	<c:if test="${drink.ownerId == sessionScope.user.id || sessionScope.user.admin == true}">
		<form class="form-inline" role="form" action="modifyDrink" method="post">
			<div class="form-group">
				<input type="hidden" name="drinkId" value="${drink.id}">
				<button type="submit" name="modifyButton" class="btn btn-default">Modify drink</button>
				<button type="submit" name="deleteButton" class="btn btn-default">Delete drink</button>
			</div>
		</form>
	</c:if>
	</div>
</t:template>