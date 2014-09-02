<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="action"%>
<%@attribute name="type"%>

<t:template page="createDrink">
	<div class="container-fluid">
		<div class="page-header">
			<h1>${type}</h1>
		</div>

		<form role="form" action="modifyDrink" method="post">
			<div class="form-group">
				<div class="row">
					<div class="col-md-6">
						<label>Drink name</label>
						<input type="text" name="name" value="${drink.name}" class="form-control">
						<label>Description</label>
						<input type="text" name="description" value="${drink.description}" class="form-control">
						<label>Image URL</label>
						<input type="text" name="imageUrl" value="${drink.imageUrl}" class="form-control">
					</div>
					<div class="col-md-6">
						<label>Ingredients</label>
						<c:forEach var="i" begin="0" end="9">
							<input type="text" name="ingredients${i}" value="${drink.ingredients[i]}" class="form-control"><br>
						</c:forEach>
					</div>
				</div>
			</div>	
			<input type="hidden" name="drinkId" value="${drink.id}">
			<button type="submit" class="btn btn-default pull-left" name="${action}Button">${type}</button>
		</form>
	</div>
</t:template>