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
							<td><a href="drink?drinkId=${drink.id}"><c:out value="${drink.name}" /></a></td>
							<td><c:out value="${drink.description}" /></td>
							<td>
							<c:if test="${(page == 'favourites') || (page == 'drinklist' && !drink.favourite)}">
								<form role="form" action="favourites" method="post">
									<input type="hidden" name="drinkId" value="${drink.id}">
									<input type="submit" name="${buttonName}" class="btn btn-default" value="${buttonText}">
								</form>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</t:template>
