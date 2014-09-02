<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="addDrinkList">
	<div class="container-fluid">
		<p><a href="admin">Back to administration</a></p>
		
		<div class="page-header">
			<h1>Import drink recipes</h1>
		</div>

		<form role="form" action="addDrinkList" method="post">
			<div class="form-group">
				<label>File name or url</label>
				<input type="text" name="filename" class="form-control">
				<button type="submit" class="btn btn-default pull-left" name="importButton">Import</button>
			</div>
		</form>
	</div>
</t:template>
