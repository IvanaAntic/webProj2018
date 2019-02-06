var url="http://localhost:8080/webRA70/rest";
	
$(document).ready(function(){
	loadArticles();
	loadRestaurants();
	$("#login").click(function(){
		formData=JSON.stringify({
			username: $("#inputFormLogin [name='usernameLogin']").val(),
			password: $("#inputFormLogin [name='passwordLogin']").val()
		});
		console.log(formData);
		$.ajax({
			url : url + "/users/login",
			type : "POST",
			data : formData,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				console.log(data.username);
				if(!data.username){
					alert('Greska prilikom logovanja!');
				}else{
					console.log("========================");
					console.log(data);
					localStorage.setItem('logged-user', JSON.stringify(data));
					console.log(localStorage);
					console.log("========================");					
					alert('You have successfully logged in.');
					window.location.href = "mainpage.html";
				}
			},
			error : function(error) {
				console.log(error);
				console.log("greska");
			}
			
			
			
		});
		
	});
	
	
	
/*		REGISTRACIJA		*/
	
	$("#register").click(function(){
		
		 
		 formData = JSON.stringify({
				email: $("#inputForm [name='emailRegistration']").val(),
				username: $("#inputForm [name='usernameRegistration']").val(),
				name: $("#inputForm [name='nameRegistration']").val(),
				lastname: $("#inputForm [name='surnameRegistration']").val(),
				password: $("#inputForm [name='passwordRegistration']").val(),
				number: $("#inputForm [name='phoneNumberRegistration']").val(),
		 });
		

				 $.ajax({
					url: url + "/users/register",
					type: "POST",
					data: formData,
					contentType: "application/json",
					datatype: "json",
					success: function(data) {
							alert("Uspesna registracija.");
					  },
					 error: function(error) {
				            alert("Email is already used!");
				        }
					});
				 
	
		$('#inputR').modal('toggle');
		 
	});
	 function loadArticles(){
			$.ajax({ 
				url: "http://localhost:8080/webRA70/rest/articles/all",
				type: "GET",
			    success: function(data){
			
				for( i=0;i<data.length;i++){
					console.log(data.length);
				
					newRow="<tr><td>"+data[i].name+"</td>"+
					 "<td>"+data[i].price+"</td>"+
					 "<td>"+data[i].description+"</td>"+
					 "<td>"+data[i].type+"</td>"+
					 "<td>"+convert(data[i].quantity,data[i].type)+"</td>"
					+"</tr>";
					$("#formTable").append(newRow);
				}
			    }
			});
		}
	 
		function convert(q,t){
			if(t=="food"){
				q=q+" g";
				return q;
			}else{ 
					q=q+" ml";
					return q;
			
			}
		
		}
		function loadRestaurants(){
			$.ajax({
				url: "http://localhost:8080/webRA70/rest/restaurants/all",
				type:"GET",
				success: function(data){
					for(i=0;i<data.length;i++){
						console.log(data[i].address);
						newRow="<tr>"+
							"<td class=\"name\">"+data[i].name+"</td>"+
							"<td class=\"adress\">"+data[i].address+"</td>"+   
							"<td  class=\"type\">"+data[i].type+"</td>"
							
						+"</tr>";
						$("#formTableRestaurant").append(newRow);
						
					}
				}
			});
		}
		
		
		$(document).on('click',"#searchRestaurant",function(e){
			$("#result").empty();
		});

		$(document).on('click',"#searchArticle",function(e){
			$("#resultA").empty();
		});

		$(document).on('click',"#searchArticleButton",function(e){
			e.preventDefault();
			$("#resultA").empty();
			var name=$("#AN").val();
			var price=$("#priceA").val();
			var type=$("#selectArticleSearch").val();
			var restaurantId=$("#selectRestaurantToSearch").val();
			formData=JSON.stringify({
				name:name,
				price:price,
				type:type,
				restaurantId:restaurantId
			});
			
			console.log(formData);
			
			$.ajax({
				url: "http://localhost:8080/webRA70/rest/articles/search",
				type:"POST",
				data:formData,
				contentType: "application/json",
				datatype: "json",
				success: function(message){
					alert("pretrayi artikal");
					$("#resultA").empty();
					for(i=0;i<message.length;i++){
						newRow="<label> name: "+message[i].name+" cena: "+message[i].price+" type: "+message[i].type+"</label>";
						
						$("#resultA").append(newRow);
					}
				}
			});
		});
		

		$(document).on('click',"#searchRestaurantSave",function(e){
			$("#result").empty();
			alert('searchRestaurantSave unos!');
			var name=$("#nameSearch").val();
			var address=$("#addressSearch").val();
			var type=$("#selectSearch").val();
			console.log(name+"---"+address+" -- "+type);
			
			formData=JSON.stringify({
				name:name,
				address:address,
				type:type
			});
			
		alert(formData);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/restaurants/searchRes",
			type:"POST",
			data:formData,
			contentType: "application/json",
			datatype: "json",
			success: function(message){
				$("#result").empty();
				for(i=0;i<message.length;i++){
					newRow="<label> name: "+message[i].name+" address: "+message[i].address+" type: "+message[i].type+"</label>";
					
					$("#result").append(newRow);
				}
				
		
			}
		
		});
		});
		
		
		
		
});