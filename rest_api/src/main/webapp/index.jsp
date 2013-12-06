<%@ page language="java" %>
<%@ page session="false" %>
<html>
<body>

	<h1>File Upload with Jersey jsp</h1>
 
	<form action="rest/files" method="post" enctype="multipart/form-data" >
 
	   <p>
		Select a file : <input type="file" name="file" size="45" />
	   </p>
 		
<input type="hidden" name="metadata" value='{"Size" : "20", "key" : "yy" }'/>
		

	   <input type="submit" value="Upload It" />
	 
	</form>
 
</body>
</html>