<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  <#include "nav.ftl">
  
  <div class="jumbotron">
    <div class="container">
      <div class="row"></div>
      <form method="post" role="form">
        <div class="form-group">Nous allons réinitialiser votre compte. Vous recevrez par mail un lien afin de redéfinir votre mot de passe.</div>
        <div class="form-group">
	        <label for="email">Email</label>
	        <input type="email class="form-control" id="email"/>
        </div>
        <div class="form-group">
			<input type="submit" class="btn btn-success" value="Reinitialiser"/>
		</div>
      </form>
      </div>
    </div>
  </div>
</body>
</html>
