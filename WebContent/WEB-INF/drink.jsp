<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="drink">
	<div class="container-fluid">
		<a href="drinks">Return to drinks</a>

		<h1><c:out value="${drink.name}" /></h1>

		<div class="row">
			<div class="container col-md-3">
				<img alt="<c:out value="${drink.name}" />" src="<c:out value="${drink.url}" />">
			</div>

			<div class="container col-md-9">
				<h2>Description</h2>
				<p><c:out value="${drink.description}" /></p>

				<h2>Ingredients</h2>
				<ul>
				<c:forEach var="entry" items="${drink.ingredients}">
						<li>${entry.value}cl <c:out value="${entry.key}" /></li>
				</c:forEach></ul>
			</div>
		</div>
	</div>
</t:template>