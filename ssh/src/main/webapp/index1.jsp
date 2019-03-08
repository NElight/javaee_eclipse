<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <%--List<String>遍历--%>
                    <table width="242" height="256" border="1" cellpadding="1"
                        cellspacing="1">
                        <caption>
                            List&ltString&gt遍历
                        </caption>
                        <tr>
                            <%--
                        说明：<s:iterator id="别名" value="后台List变量名">
                              <s:if test="#别名!=null">
                            --%>
                            <s:iterator id="map" value="listmap">
                                <td>
                                    <s:property value = "#map"/>
                                </td>
                                <td>
                                
                                </td>
                            </s:iterator>
                        </tr>
                    </table>
  </body>
</html>