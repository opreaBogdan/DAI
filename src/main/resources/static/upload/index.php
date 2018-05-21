<?php
require_once 'idiorm.php';
 
ORM::configure('mysql:host=localhost;dbname=tema');
ORM::configure('username','root');
ORM::configure('password','');
ORM::get_db();

if(!isset($_SESSION)) {
    session_start();
};

echo 
'<!doctype html>
<html>


<head>
    <link rel="stylesheet" type="text/css" href="style.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <title>Un articol</title>
    <meta name="description" content="Acest articol este important si bine scris">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</head>

<body>

	
    <div class="city">

        <header>
            <h1>Photography is art</h1>
        </header>
        <div class="continut">
            <div class="coloanaDreapta">
				<div class="loginnDIV" style="display:none;">
				<form action="./" id="login-form" method="POST" >
					<label for="username">Username:</label>
					<input type="text" class="aux" id="username" name="username"><br><br>
					<label for="Password">Password:</label>
					<input type="password" class="aux" id="password" name="password"><br><br>
					<input type="submit" class="login_button" id = "btn_login" name="login" value="Login">
				</form>
				';
				
echo'
				</div>
				<div class="SIGNUPDIV" style="display:none;">
				<form action="./" id="signup-form" method="POST" >
					<label for="usernamee">Username:</label>
					<input type="text" class="aux" id="user" name="user"><br><br>
					<label for="pass">Password:</label>
					<input type="password" class="aux" id="pass" name="pass"><br><br>
					<label for="pass">Nume Album:</label>
					<input type="text" class="aux" id="album_name" name="album_name"><br><br>
					<input type="submit" class="login_button" id="btn_signup" name="SINGUP" value="SignUP">
				</form>
				</div>

				<div class="PRIMA_PAGINA">
					<p id="avem"> Not logged in </p>
					<p id="initial_text"> WELCOME! </p>
					<iframe width="854" height="480" src="https://www.youtube.com/embed/8TbWgwBz2EI" frameborder="0" allowfullscreen></iframe>
					<p id="ordine_link"> </p>
				</div>

				<div class="uploadDiv" style="display:none;">
					<form id="upload-image" action="./" method="post" enctype="multipart/form-data">
							<label class="custom1">Select Your Image
							<input type="file" id="file_id" name="file"  id="file" required />
							</label>
							<br/>
							<input type="submit" value="Upload" class="login_button" />
					</form>
				</div>
				<div class="principalDIV" style="display:none;">
                <article id="principal">
                    <header>
                        <p id="n_foto">Nume Fotograf</h2>
                    </header>
					<div class="eLogat" style="display:none;">';
						echo' <p id="cacat1"> Not logged in <p>';
						echo '
						
					</div>

					
					
                    <img class="mySlides" id="imageID" src="" alt="Poza" >
                   
                    <button class="butoane" id="b_stanga"> > </button>
                    <button class="butoane" id="b_dreapta"> < </button>
                   

                    <p class="Comment"> Poza Curenta</p>
                   
				    
					<button type="button" id="showFeed" class="feed"> ShowFeedback </button>
                    <section class="show_feed" style="display:none;">
                        <header>
                            <h3>Feedback</h3>
                        </header>
						
						<nav>
                            <ul id="lista_feed">
                                
                            </ul>
                        </nav>


						<div class="feedback" style="display:none;">
							<div class="stars">
							<form action="" id="to_reset_form">
								<input class="star star-5" id="star-5" type="radio" name="star"/>
								<label class="star star-5" for="star-5"></label>
								<input class="star star-4" id="star-4" type="radio" name="star"/>
								<label class="star star-4" for="star-4"></label>
								<input class="star star-3" id="star-3" type="radio" name="star"/>
								<label class="star star-3" for="star-3"></label>
								<input class="star star-2" id="star-2" type="radio" name="star"/>
								<label class="star star-2" for="star-2"></label>
								<input class="star star-1" id="star-1" type="radio" name="star"/>
								<label class="star star-1" for="star-1"></label>
								<input id="comment1" type="text" name="comm"/>
							</form>

							<button type="button" id="addFeedback" class="feed"> Add FeedBack </button>
							</div>
						</div>
                    </section>
                </article>
            </div>
			</div>
            <div class="coloanaStanga">

                
                   
						<select class="dropdown" id="drp">
							<option value="menu" class="dropdown-content">Menu</option>
                        	<option value="main" class="dropdown-content">MainPage</option>
						</select>
                   
                
					<div class="butoane">
						
        				<button type="button" id="myPhotos" class="myPhotosButtonas">Fotografiile mele</button>
        				<button type="button" id="addImg"> Adauga Imagini</button>
        				<button type="button" id="addComm" > Adauga Comentariu</button>
        				<button type="button" id="signUp" >SingUp</button>
        				<button type="button" id="logIn"  >LogIn</button>
        				<button type="button" id="logOut" >LogOut</button>
					
					<script>
						$.ajaxSetup({
    						cache: false
						});
						$( document ).ready(function() {
							$("#lista_feed").empty();
							current_user = "none";
							var array_photos = [];
							var array_clicked = [];
							var index = 0;
							var last_p = "none";
							console.log("SUNT success");
							

							$("#drp").on("change", function() {
								var x = $(this).find(":selected").val();
    							if(x == "main")
								{
									$(".loginnDIV").hide();
									$(".principalDIV").hide();
									$(".SIGNUPDIV").hide();
									$(".uploadDiv").hide();
									$(".PRIMA_PAGINA").show();
									$("#drp").val("menu");
									$("#lista_feed").empty();
									$(".show_feed").hide();
								}
							});

							function myDisplay() {
								$("#lista_feed").empty();
								var form_data = new FormData();  
								
								form_data.append("fotog", array_photos[index]);
								
								$.ajax
								({
									url: "show_feedback.php", // Url to which the request is send
									type: "POST",             // Type of request to be send, called as method
									data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
									contentType: false,       // The content type used when sending data to the server.
									cache: false,             // To unable request pages to be cached
									processData:false,        // To send DOMDocument or non processed data file it is set to false
									success: function(data)   // A function to be called if request succeeds
									{
										if(data != "Nu")
										{
											var obj = $.parseJSON(data);
											var arrayLength = obj.feedback.length;
											for (var i = 0; i < arrayLength; i++) 
											{

												var ul = document.getElementById("lista_feed");
												var li = document.createElement("li");
												var a = document.createElement("article");
												var head = document.createElement("header");
												var hh = document.createElement("h4");
												var par = document.createElement("p");
												head.textContent = obj.feedback[i].user + "  said:";
												hh.textContent = "		   Rated at:" + obj.feedback[i].stele;
												par.textContent = obj.feedback[i].comm;
												
												
												head.appendChild(hh);
												a.appendChild(head);
												a.append(par);
												li.appendChild(a);
												ul.appendChild(li);
											}
										}
										$(".show_feed").show();
									
									},

										
								});
							}


							$("#addFeedback").click(function() {
								if( current_user != "none")
								{
									$("#lista_feed").empty();
									comm=$("input#comment1").val();
									var form_data = new FormData();  
									var nr_stele = 0;

									if (document.getElementById("star-1").checked) {   
										nr_stele = 1;
									}
									if (document.getElementById("star-2").checked) {   
										nr_stele = 2;
									}
									if (document.getElementById("star-3").checked) {   
										nr_stele = 3;
									}
									if (document.getElementById("star-4").checked) {   
										nr_stele = 4;
									}
									if (document.getElementById("star-5").checked) {   
										nr_stele = 5;
									}

									form_data.append("user", current_user);
									form_data.append("foto", array_photos[index]);
									form_data.append("comm", comm);
									form_data.append("stea", nr_stele);

									$.ajax
									({
										url: "feedback.php", // Url to which the request is send
										type: "POST",             // Type of request to be send, called as method
										data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
										contentType: false,       // The content type used when sending data to the server.
										cache: false,             // To unable request pages to be cached
										processData:false,        // To send DOMDocument or non processed data file it is set to false
										success: function(data)   // A function to be called if request succeeds
										{
											if(data == "sf")
											{
												myDisplay();
												$("#to_reset_form")[0].reset();
											}
										},

										
									});
								}
							});

							$("#showFeed").click(function()
							{
								myDisplay();
							});
							
							$("#logIn").click(function() 
							{
								$(".show_feed").hide();
								$(".loginnDIV").show();
								$(".principalDIV").hide();
								$(".SIGNUPDIV").hide();
								$(".uploadDiv").hide();
								$(".PRIMA_PAGINA").hide();
								$("#lista_feed").empty();
								$(".show_feed").hide();
							});

							$("#myPhotos").click(function() 
							{

								if(current_user != "none")
								{ 
									$("#lista_feed").empty();
									var form_data = new FormData();                  
									form_data.append("user", current_user);

									$.ajax({
										url: "my_photos.php", // Url to which the request is send
										type: "POST",             // Type of request to be send, called as method
										data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
										contentType: false,       // The content type used when sending data to the server.
										cache: false,             // To unable request pages to be cached
										processData:false,        // To send DOMDocument or non processed data file it is set to false
										success: function(data)   // A function to be called if request succeeds
										{
											var obj = $.parseJSON(data);
											$(".loginnDIV").hide();
											$(".principalDIV").show();
											$(".SIGNUPDIV").hide();
											$(".uploadDiv").hide();
											$(".PRIMA_PAGINA").hide();
											$(".show_feed").hide();
											if(data != "Nu")
											{
												document.getElementById("imageID").src = obj.photolist[0];
												array_photos = obj.photolist;
												document.getElementById("n_foto").innerHTML = current_user + "\'s photos";
											}
										},

										
									});
								}
								else
								{
									alert("Trebuie sa fii autentificat pentru aceasta operatie!");
								}
							});

							$("#addImg").click(function() 
							{
								if(current_user != "none")
								{ 
									$("#lista_feed").empty();
									$(".uploadDiv").show();
									$(".loginnDIV").hide();
									$(".principalDIV").hide();
									$(".SIGNUPDIV").hide();
									$(".PRIMA_PAGINA").hide();
									$(".show_feed").hide();
								}
								else
								{
									alert("Trebuie sa fii autentificat pentru aceasta operatie!");
								}
							});

							$("#b_stanga").click(function() 
							{
								
								console.log("buton stanga %d");
								if(array_photos.length > 1)
								{ 
									
									if(index + 1 < array_photos.length)
									{
										$("#lista_feed").empty();
										$(".show_feed").hide();
										index++;
									}
									document.getElementById("imageID").src = array_photos[index];
								}
							});

							$("#b_dreapta").click(function() 
							{
								console.log("buton dreapta %d");

								if(array_photos.length > 1)
								{ 
									if(index > 0)
									{
										$("#lista_feed").empty();
										$(".show_feed").hide();
										index--;
									}
									document.getElementById("imageID").src = array_photos[index];
								}
							});

							$("#signUp").click(function() {
								$("#lista_feed").empty();
								$(".loginnDIV").hide();
								$(".principalDIV").hide();
								$(".SIGNUPDIV").show();
								$(".uploadDiv").hide();
								$(".PRIMA_PAGINA").hide();
								$(".show_feed").hide();
							});

							
        					$("a").click(function(event) {
								$("#lista_feed").empty();
            					event.preventDefault();	
								fotograf=$(this).text();
								var form_data = new FormData();                  
								form_data.append("fotograf", fotograf);
								$.ajax({
									url: "load_fotograf.php", // Url to which the request is send
									type: "POST",             // Type of request to be send, called as method
									data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
									contentType: false,       // The content type used when sending data to the server.
									cache: false,             // To unable request pages to be cached
									processData:false,        // To send DOMDocument or non processed data file it is set to false
									success: function(data)   // A function to be called if request succeeds
									{
										var obj = $.parseJSON(data);
										$(".loginnDIV").hide();
										$(".principalDIV").show();
										$(".SIGNUPDIV").hide();
										$(".uploadDiv").hide();
										$(".PRIMA_PAGINA").hide();
										if(data != "Nu")
										{
											document.getElementById("imageID").src = obj.photolist[0];
											array_photos = obj.photolist;
											document.getElementById("n_foto").innerHTML = fotograf + "\'s photos";
										}

									


										if(current_user != "none")
										{
											var index = array_clicked.indexOf(fotograf);
											if (index > -1) {
 											   array_clicked.splice(index, 1);
											}

											array_clicked.push(fotograf);
											
											var curent =  "Ordine fotografi urmariti: \n" + array_clicked.toString() + "  ";
											var obj = $( "p#ordine_link" ).text(curent);
											obj.html(obj.html().replace(/\n/g,"<br/>"));
										}
										
									},
        						});
							});
   							 

							$("#login-form").submit(function(e) {
									$("#lista_feed").empty();
   									username=$("input#username.aux").val();
									password=$("input#password.aux").val();

		  							$.ajax({
		   								type: "POST",
		   								url: "fixtures.php",
										data: "name="+username+"&pwd="+password,
		   								success: function(html){    
											if(html.indexOf("false") == -1)    
											{
												console.log("Recieved authentication success");												
												
												$("#login-form")[0].reset();
												current_user = html;
												var textul = "Logged in as " + html;
												$("p#avem").text(textul);

												$(".loginnDIV").hide();
												$(".principalDIV").hide();
												$(".uploadDiv").hide();
												$(".SIGNUPDIV").hide();
												$(".feedback").show();
												$(".PRIMA_PAGINA").show();
												$(".show_feed").hide();
												alert("Welcome, " + html + "!");
											}
											else if(html=="false")   
											{
												
												console.log("Recieved authentication error");
												alert("Autentificare esuata!");	
												$("p#cacat1").text("Not logged in");												
												$("#login-form")[0].reset();
												
												
											}
		   								},
		   					
		  							});
									return false;
  								
 							});

							$("#signup-form").submit(function(e) {
								$("#lista_feed").empty();
								console.log("Incepe singup error");
   									username=$("input#user.aux").val();
									password=$("input#pass.aux").val();
									album=$("input#album_name.aux").val();
		  							$.ajax({
		   								type: "POST",
		   								url: "signup.php",
										data: "name="+username+"&pwd="+password +"&album="+album,
		   								success: function(response) {  
											if(response=="1")    
											{
												alert("Inregistrare reusita!");												
												$("#login-form")[0].reset();
												$("#signup-form")[0].reset();
												$(".loginnDIV").show();
												$(".principalDIV").hide();
												$(".SIGNUPDIV").hide();
												$(".uploadDiv").hide();
												$(".show_feed").hide();

 												var ul = document.getElementById("lista1");
  												var li = document.createElement("li");
												var a = document.createElement("a");
  												a.textContent = username;
												var to_set = "index.php;
												a.setAttribute("href", to_set);
												li.appendChild(a);
  												li.setAttribute("id", username); // added line
  												ul.appendChild(li);
												ul.enhanceWithin();
												ul.hide();
												ul.show();
											}
											else if(response=="2")   
											{
												alert("Username deja existent!");												
												$("#login-form")[0].reset();
												$("#signup-form")[0].reset();
											}
		   								},
		   					
		  							});
									return false;
  								
 							});

							$("#upload-image").on("submit",(function(e) {
								
								e.preventDefault();
								$("#message").empty();
								var file_data = $("#file_id").prop("files")[0];
    							var form_data = new FormData();                  
    							form_data.append("file", file_data);
								form_data.append("user", current_user);

								$.ajax({
									url: "upload_file.php", // Url to which the request is send
									type: "POST",             // Type of request to be send, called as method
									data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
									contentType: false,       // The content type used when sending data to the server.
									cache: false,             // To unable request pages to be cached
									processData:false,        // To send DOMDocument or non processed data file it is set to false
									success: function(data)   // A function to be called if request succeeds
									{
										$("#lista_feed").empty();
										$(".show_feed").hide();
										alert(data);
									}
									
								});
							}));


							$("#logOut").click(function(e) {
								e.preventDefault();
								var logout1 = "logout";
								current_user = "none";
								array_clicked = [];
								$.ajax({
									type: "POST",
            						url: "logout.php",
           							
            						data: "action=" + logout1,
            						success: function(data)
									{
										$("#lista_feed").empty();
										$("p#ordine_link").text("");
										$("p#cacat1").text("Not logged in");
										console.log("Am dat logout");
										$(".loginnDIV").hide();
										$(".principalDIV").hide();
										$(".SIGNUPDIV").hide();
										$(".PRIMA_PAGINA").show();
										$(".feedback").hide();
										$(".show_feed").hide();
           								alert(data);				
            						}
       					 		});
							
							});

					});

					</script>';
					echo '
					</div>

                <div class="listaFotografi">

                    <nav>
                        <ul id="lista1">';
							$lista=ORM::for_table('users')->find_many();
							foreach($lista as $result) {
								echo("<li id=$result->nume><a href='index.php?foto=$result->nume'>$result->nume</a></li>");
							}
							echo'
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <footer>
            <p> Copyright All rights reserved to Roxana Pavelescu</p>
        </footer>
		
    </div>
</body>

</html>';
	
?>

