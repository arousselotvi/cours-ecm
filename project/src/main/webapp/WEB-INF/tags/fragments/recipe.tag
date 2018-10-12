<%@ tag body-content="empty" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="texttaglib" uri="../../tld/text.tld" %>

<%@ attribute name="recipe" required="true" rtexprvalue="true" type="fr.cmm.domain.Recipe"%>

<div class="row">
    <div class="col-xs-12 col-sm-4">
        <div class="thumbnail">
            <img src="/image/${recipe.imageId}" alt="${fn:escapeXml(recipe.title)}">
        </div>
    </div>
    <div class="col-xs-12 col-sm-8">
        <div class = "containerTitle">
            <div class = "myTitle">
                <h1>${fn:escapeXml(recipe.title)}</h1>
            </div>
            <div class = "myEditButton" style= "    bottom: 16px;
                                                    position: absolute;
                                                    right: 0;">
                <button type="button">Edit</button>
            </div>
        </div>
        <div class="restOfBody">
            <p>${fn:escapeXml(recipe.intro)}</p>
            <c:forEach var="recipeTags" items="${recipe.tags}">
                <li>
                    <span class="label label-primary">"${recipeTags}"</span>
                </li>
            </c:forEach>
            <p><fmt:formatDate value = "${recipe.date}" pattern = "dd MMM yyyy"/></p>
            <c:if test="${not empty recipe.ingredients}">
                <ul>
                    <c:forEach var="ingredient" items="${recipe.ingredients}">
                        <li>${fn:escapeXml(ingredient.name)} : ${fn:escapeXml(ingredient.quantity)} ${fn:escapeXml(ingredient.unit)}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <p>${texttaglib:text(recipe.text)}</p>
        </div>
    </div>
</div>