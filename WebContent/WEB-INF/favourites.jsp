<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="favourites">
	<div class="container-fluid">
		<h1>Your favourite drinks</h1>

		<form role="form" action="favourites.jsp">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Drink name</th>
						<th>Description</th>
						<th>Ingredients</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><a href="drink.jsp">Jallu</a></td>
						<td>Opiskelijan herkku</td>
						<td>Jallu, jallu ja jallu</td>
						<td><input type="submit" name="btn1" class="btn btn-default" value="Remove"></td>
					</tr>
					<tr>
						<td><a href="drink.jsp">Jallu</a></td>
						<td>Opiskelijan herkku</td>
						<td>Jallu, jallu ja jallu</td>
						<td><input type="submit" name="btn2" class="btn btn-default" value="Remove"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</t:template>
