<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="admin">
	<div class="container-fluid">
		<h1>Administration</h1>

		<p><a href="addDrinkList">Import drinklist</a></p>
		<p><a href="createUser">Create a new user</a></p>

		<form role="form" action="users" method="post">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>User name</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Jallu</td>
						<td><input type="submit" name="btn1" class="btn btn-default" value="Reset password"></td>
						<td><input type="submit" name="btn2" class="btn btn-default" value="Delete user"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</t:template>