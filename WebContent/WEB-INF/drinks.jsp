<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="drinks">
	<div class="container-fluid">
		<div class="page-header">
			<h1>Available drinks</h1>
		</div>
		
		<form role="form" action="favourites">
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
							<td><a href="drink?id=${drink.id}"><c:out value="${drink.name}" /></a></td>
							<td><c:out value="${drink.description}" /></td>
							<td><input type="submit" name="btn1" class="btn btn-default" value="Add to favourites"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</t:template>
