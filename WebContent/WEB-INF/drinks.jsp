<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="drinks">
	<div class="container-fluid">
		<h1>Available drinks</h1>

		<label>Filter drink recipes:</label> <input id="filter" type="text" class="form-control" placeHolder="">

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
						<td><input type="submit" name="btn1" class="btn btn-default" value="Add to favourites"></td>
					</tr>
					<tr>
						<td><a href="drink.jsp">Jallu</a></td>
						<td>Opiskelijan herkku</td>
						<td>Jallu, jallu ja jallu</td>
						<td><input type="submit" name="btn2" class="btn btn-default" value="Add to favourites"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</t:template>
