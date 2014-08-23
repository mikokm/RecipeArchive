<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="addDrinkList">
	<div class="container-fluid">
		<p><a href="admin">Back to administration</a></p>
		<h1>Import drink recipes</h1>

		<div class="form-group">
			<form role="form" action="addDrinkList" method="post">
				<label>File name or url</label> <input type="text" name="filename" class="form-control">
				<br>
				<input type="submit" name="addButton" class="btn btn-default pull-right" value="Import">
			</form>
		</div>
	</div>
</t:template>
