<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	

	<!--=============================================== form ===========================================-->
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
		
			<h1>Paramètres du compte</h1>
			
            <div class="row">
                <div class="col-md-offset-1 col-md-5 col-sm-5"> 
                    <div class="panel panel-default" >
                        <div class="panel-heading ">- Informations personnelles - </div>
                        <div class="panel-body">
                            Email : abdel-ilah.tamditi@amdm.fr<br/>
                            Numéro de place attribuér : 133
                            <br>
                            <hr>
                            <small>ici, vous avez la possibilité de modifier votre adresse email et/ou votre numéro de place.</small>
                            <br/><br/>
                            <a href="/infosCompte.ftl" class="btn btn-primary tailleText">Modifier</a>	
                        </div>
                    </div>
                </div>
                
                <div class="col-md-5 col-sm-5">
                    <div class="panel panel-default" >
                        <div class="panel-heading">- Historiques d'utilisations - </div>
                        <div class="panel-body">
                            historiques de réservation<br>
                            historiques de partage.
                            <br><br>
                            <hr>
                            <small>Vous avez la possibilité de consulter l'historique de votre activité de partage et/ou de réservation.</small>
                            <br><br>
                            <a href="/historiques.ftl" class="btn btn-primary tailleText">Visualiser</a>	                            
                        </div>
                    </div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-offset-1 col-md-5 col-sm-5"> 
                    <div class="panel panel-default" >
                        <div class="panel-heading ">- Mot de passe -</div>
                        <div class="panel-body">
                           <small>Choisissez un mot de passe assez long en mélangeant caractères, chiffres et caractères spéciaux. Il est conseillé de le changer régulièrement.</small>
                            <br><br>
                            <a href="/protected/search" class="btn btn-primary tailleText">Changer</a>                            
                        </div>
                    </div>
                </div>
                
                <div class="col-md-5 col-sm-5">
                    <div class="panel panel-default" >
                        <div class="panel-heading">- Suppression du compte -</div>
                        <div class="panel-body">
                            <small>Vous avez la possibilité de supprimer votre compte.</small>
                            <br><br><br>
                            <a href="/protected/unregister" class="btn btn-primary tailleText" data-confirm='Etes-vous sûr de vouloir supprimer votre compte ?' >Supprimer</a>
                        </div>
                    </div> 
                </div>
            </div>

		</div>
	</section>
	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	<script src="/js/confirm.js"></script>
</body>
</html>
