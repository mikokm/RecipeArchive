<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="page"%>
<%@attribute name="pageHeader"%>
<%@attribute name="buttonName"%>
<%@attribute name="buttonText"%>

<t:template page="${page}">
	<div class="container-fluid">
		<div class="page-header">
			<h1>${pageHeader}</h1>
		</div>
		
		<form role="form" action="favourites" method="post">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Drink name</th>
						<th>Description</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="drink" items="${drinks}">
						<tr>
							<td><a href="drink?drinkId=${drink.id}"><c:out value="${drink.name}"/></a></td>
							<td><c:out value="${drink.description}"/></td>
							<td>
							<c:if test="${(page == 'favourites') || (page == 'drinklist' && !drink.favourite)}">
								<button type="submit" class="btn btn-default" name="${buttonName}" value="${drink.id}">${buttonText}</button>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</t:template>
