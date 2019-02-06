$(document).ready(function(){
	$("#adminPanel").hide();
	 
	var url="http://localhost:8080/webRA70/rest";
	var storageItem = localStorage.getItem('logged-user'); //je oblika string ima sve podatke o logovanom
	var arr=[];
	
	console.log("svi podaci o logovanom");
	console.log(storageItem);
	
	$("kupac").hide();
	$("#dobavljacShow").hide();
	$("#adminOrdersPanel").hide();
	
	if(!storageItem){
		window.location.href="index.html";
	}else{
	var userobj=jQuery.parseJSON(storageItem);
	console.log(userobj.role);
	$("#namelogged").html(userobj.name);

	 loadResToAdd();
		if(userobj.role=="kupac"){
			loadArticlesUsers();
			loadRestaurantsUsers();
			addUserPanel();
			loadFav();
			loadUserOrders();
			 loadCart();
			 loadResToAdd();
			$("kupac").show();
			
			//$("#kolonadva").hide();
			$("#kolona2").hide();
			$("#kolona3").hide();
			$("#ve").hide();
		
		}else if(userobj.role=="administrator"){
			//Dodavanje, izmena i brisanje restorana –
			$("#kolona2").show();
			$("#adminPanel").show();
			$("#adminOrdersPanel").show();
			loadArticles();
			 loadRestaurants();
			 loadVechicles();
			 loadResToAdd();
			addAdminPanel();
			loadOrders();
		}else if(userobj.role=="dobavljac"){
			$("#hideDobavljac").hide();
			loadPreuzimanjePorudzbina();
			$("#dobavljacShow").show();
			loadDostavautoku();
			
		}
	
	
	
	$("#logout-button").click(function(){
	logout();
	});
	}
	
	$("#saveRestaurant").click(function(){
		console.log("happend");
		formData= JSON.stringify({
			name: $("#addRestaurantForm [name='name']").val(),
			address: $("#addRestaurantForm [name='address']").val(),
			type: $("#selectUserRole").val()
		});
		
		$.ajax({
			url:  "http://localhost:8080/webRA70/rest/restaurants/save",
			type: 'POST',
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success:function(data){
				alert("saved restaurant to base");
				
				loadRestaurants();
			}
		});
	});
	
	
	$("#saveArticle").click(function(){
			formData=JSON.stringify({
				name:$("#articlename").val(),
					price:$("#price").val(),
					description:$("#description").val(),
					quantity:$("#quantity").val(),
					type:$("#selectArticle").val(),
					restaurantId:$("#selectRestaurantToAdd").val()
			});
			console.log("--------------");
			console.log(formData);
			$.ajax({
				url:"http://localhost:8080/webRA70/rest/articles/save",
				type: 'POST',
				data: formData,
				contentType: "application/json",
				datatype: "json",
				success:function(data){
					
					alert("saved article to base");
					loadArticles();
				}
		
			});
			
	});
	

	
	
	
	$("#saveVehicle").click(function(){
		formData=JSON.stringify({
			mark:$("#mark").val(),
			model:$("#model").val(),
			type:$("#typeVehicle").val(),
			registration:$("#registration").val(),
			yearOfDistribution:$("#godiste").val(),
			used:$("#usage").val(),
			note:$("#note").val()
		});
		console.log("--------------");
		console.log(formData);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/vechicles/add",
			type: 'POST',
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success:function(){
				alert("saved vehicle to base");
				
			},
			error: function(error){
				console.log(error);
				
			}
	
		});
	$("#addVehicle").modal("toggle");
});

	function loadPreuzimanjePorudzbina(){
		console.log("USLI");
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/users/allpreuzimanje",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					
					 getArticleNamePorudzbina(data[i].idArticle,data[i].status,data[i].amount,data[i].idStavka)
					
					
				}
			}
		});
	}
	
	function loadDostavautoku(){
		console.log("USLI");
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/users/alldostavautoku",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					
					getArticleNameDostavljanje(data[i].idArticle,data[i].status,data[i].amount,data[i].idStavka)
					
				}
			}
		});
	}
	
	
	function loadCart(){
		
			console.log("------------load Cart------------------------------");
			
			for(i=0;i<arr.length;i++){
			newRow="<tr>" +
					"<td>"+arr[i].idArticle+"</td>" +
					"<td>"+arr[i].amount+ "</td>"
				+"</tr>"
				console.log(newRow);
				$("#cartTable").append(newRow);
			}
		
		
	}
	
	function logout(){
		localStorage.removeItem('logged-user');
		$.ajax({
			url: url + '/users/logout',
			type: 'POST',
			complete: function(data){	
				window.location.href="index.html"
			}
		});		
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
						"<td  class=\"type\">"+data[i].type+"</td>"+	
						"<td>"+"<input class=\"btn btn-info\" type=\"button\" value=\"Change\" data-toggle=\"modal\" data-target=\"#editRestaurant\">"+"</td>" +
						"<td>"+"<input class=\"btn btn-danger\" type=\"button\" value=\"DELETE\" id=\"obrisi\">"+"</td>"
						+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"+
						"<td><a href='restaurants/editRes/'" + data[i].id + "' class='card-link addEditBtn'>EDIT Button</a></td>"
	
					+"</tr>";
					$("#formTableRestaurant").append(newRow);
					
				}
			}
		});
	}
	
	$(document).on('click', '.addEditBtn', function(e){
		e.preventDefault();
		console.log("okokoko");
		//url:"http://localhost:8080/webRA70/rest/restaurants/editRes",
		$('#editRestaurant').modal('toggle');
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var id = $('#id').val();
		
		$('#name').val(tr_parent.find(".name").html());
		var name = $('#name').val();
		
		//$('#type').val(tr_parent.find(".type").html());
		var type =tr_parent.find(".type").html()
		var adress =tr_parent.find(".adress").html()
		
		$('#editName').val(tr_parent.find(".name").html());
		var nameEdit = $('#editName').val();
		console.log(nameEdit);
		$('#editAdress').val(tr_parent.find(".adress").html());
		var adressEdit = $('#editAdress').val();
		
		//$('#editSelectRes').val(tr_parent.find(".type").html());
		var tipEdit = $('#editSelectRes').val();
		console.log("---edit----------");
		console.log(nameEdit);
		console.log(adressEdit);
		console.log(tipEdit);
		var url = $(this).attr("href");
		console.log(url);
		

		$("#editSaveRestaurant").click(function(){
		
		console.log(tipEdit);
			formData = JSON.stringify({
			address: $("#editAdress").val(),
             name: $("#editName").val(),
             id: id,
			type: $('#editSelectRes').val()
			});
			
		
		
				$.ajax({
					url:"http://localhost:8080/webRA70/rest/restaurants/editRes",
					type:"POST",
					data:formData,
					contentType: "application/json",
					datatype: "json",
					success: function(message){
						alert(message);
					},
					 error: function(){
							alert('Neispravan unos!');
						}
				});
				
				$("#editRestaurant").modal("toggle");
		
			
         });
		
	});
	
	
	$(document).on('click', '.addEditArticle', function(e){
		e.preventDefault();
		
		$('#editArticleModal').modal('toggle');
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var id = $('#id').val();
		console.log("---edit article---");
		console.log(id);
		
		$('#articlenameEdit').val(tr_parent.find(".name").html());
		var name = $('#articlenameEdit').val();
		
		$('#priceEdit').val(tr_parent.find(".price").html());
		var price = $('#priceEdit').val();
		

		$('#descriptionEdit').val(tr_parent.find(".description").html());
		var description = $('#descriptionEdit').val();
		
		$('#quantityEdit').val(tr_parent.find(".quantity").html());
		var name = $('#quantityEdit').val();
		$('#selectArticleEdit').val(tr_parent.find(".type").html());
		var type = $('#selectArticleEdit').val();
		
		console.log(name);

		console.log(price);

		console.log(description);

		console.log(quantity);
		console.log(type);
		
		$("#saveArticleEdit").click(function(){
			formData=JSON.stringify({
				id:id,
				name:$('#articlenameEdit').val(),
				price:$('#priceEdit').val(),
				description:$('#descriptionEdit').val(),
				quantity:$('#quantityEdit').val(),
				type:$('#selectArticleEdit').val(),
				
			});

			$.ajax({
				url:"http://localhost:8080/webRA70/rest/articles/editArticle",
				type:"POST",
				data:formData,
				contentType: "application/json",
				datatype: "json",
				success: function(message){
					alert(message);
				},
				 error: function(){
						alert('Neispravan unos!');
					}
			});
			
			$("#editArticleModal").modal("toggle");
	
		
		});
		
	
	});
	
	
	function loadOrders(){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/order/allAdmin",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					console.log("usli u all admin");
					newRow="<tr>"+
					"<td class=\"status\">"+data[i].status+"</td>"+
					"<td class=\"customerId\">"+data[i].customerId+"</td>"+
					"<td class=\"delivererId\">"+data[i].delivererId+"</td>"+
						"<td class=\"price\">"+data[i].price+"</td>"+
						"<td class=\"amount\">"+data[i].amount+"</td>"+   
						"<td  class=\"note\">"+data[i].note+"</td>"+	
						"<td>"+"<input class=\"btn btn-info\" type=\"button\" value=\"Change\" data-toggle=\"modal\" data-target=\"#editOrder\">"+"</td>" +
						"<td>"+"<input class=\"btn btn-danger\" type=\"button\" value=\"DELETE\" id=\"obrisiOrder\">"+"</td>"
						+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].idStavka+"</td>"
					+"</tr>";
					$("#formTableOrders").append(newRow);
					
				}
			}
		});
	}
	function loadUserOrders(){
		
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/order/all",
			type:"GET",
			success: function(data){
				console.log(data.length);
				for(i=0;i<data.length;i++){
					getArticleName(data[i].idArticle,data[i].status,data[i].amount,data[i].price)
				
				}
			}
		});
		
		
	}
	
	
	function getArticleName(idArticle,status,amount,price){
		console.log("data");
		console.log(idArticle);
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/order/"+idArticle,
			type:"POST",
			success: function(data){
	
					newRow="<tr><td>"+data.name+ "</td>"+
					"<td>"+data.price+ "</td>"+
					"<td>"+data.description+ "</td>"+
					"<td>"+data.type+"</td>"+
					"<td>"+status+ "</td>"+
					"<td>"+amount+ "</td>"+
					"<td>"+price+ "</td>"+
					"</tr>";
					console.log("usli u funkcijju"+newRow);
				$("#ordersUser").append(newRow);
			
			}
		});
	}
	function getArticleNamePorudzbina(idArticle,status,amount,id){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/order/"+idArticle,
			type:"POST",
			success: function(data){
	
					newRow="<tr><td>"+data.name+ "</td>"+
					"<td>"+data.price+ "</td>"+
					"<td>"+data.description+ "</td>"+
					"<td>"+data.type+"</td>"+
					"<td>"+status+ "</td>"+
					"<td>"+amount+ "</td>"+
					"<td>"+"<input class=\"btn btn-info\" type=\"button\" value=\"Preuzimi\"  id=\"preuzmi\">"+"</td>"
					+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+id+"</td>"+
					"</tr>";
					
			
				$("#por").append(newRow);
			}
		});
	}
	function getArticleNameDostavljanje(idArticle,status,amount,id){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/order/"+idArticle,
			type:"POST",
			success: function(data){
	
					newRow="<tr><td>"+data.name+ "</td>"+
					"<td>"+data.price+ "</td>"+
					"<td>"+data.description+ "</td>"+
					"<td>"+data.type+"</td>"+
					"<td>"+status+ "</td>"+
					"<td id=\"sakrij\" >"+"<a href=\"#\" id=\"dostavljeno\">"+"dostavljeno</a></td>"
					+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+id+"</td>"+
					"</tr>";
					
			
				$("#dostavautoku").append(newRow);
			}
		});
	}
	function loadResToAdd(){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/restaurants/all",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					newRow=" <option value="+data[i].id+">"+data[i].name+"</option>";
					$("#selectRestaurantToAdd").append(newRow);
					$("#selectRestaurantToSearch").append(newRow);
				}
				newRow+="<option value=\"0\">none</option>";
				$("#selectRestaurantToSearch").append(newRow);
			}
		});
	}
	function loadArticles(){
		$.ajax({ 
			url: "http://localhost:8080/webRA70/rest/articles/all",
			type: "GET",
		    success: function(data){
		
			for( i=0;i<data.length;i++){
				console.log(data.length);
			
				newRow="<tr><td class=\"name\">"+data[i].name+"</td>"+
				 "<td class=\"price\">"+data[i].price+"</td>"+
				 "<td class=\"description\">"+data[i].description+"</td>"+
				 "<td class=\"type\">"+data[i].type+"</td>"+
				 "<td class=\"quantity\">"+convert(data[i].quantity,data[i].type)+"</td>"+
				 "<td><a href='articles/editArticle/'" + data[i].id + "' class='card-link addEditArticle'>EDIT Button</a></td>"+
				 "<td>"+"<input class=\"btn btn-danger deleteItemA\" type=\"button\" value=\"DELETE\">"+"</td>"
				 +" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
				+"</tr>";
				$("#formTableArticle").append(newRow);
			}
		    }
		});
	}
	function loadArticlesUsers(){
		$.ajax({ 
			url: "http://localhost:8080/webRA70/rest/articles/all",
			type: "GET",
		    success: function(data){
		
			for( i=0;i<data.length;i++){
				console.log(data.length);
			
				newRow="<tr><td class=\"name\" id=\"name\">"+data[i].name+"</td>"+
				 "<td>"+data[i].price+"</td>"+
				 "<td>"+data[i].description+"</td>"+
				 "<td class=\"type\" id=\"type\"  >"+data[i].type+"</td>"+
				 "<td>"+convert(data[i].quantity,data[i].type)+"</td>"+
				 "<td>"+"<input type=\"number\" min=\"1\" max=\"25\" value=\"1\" class=\"cart\" id=\"cart\"></input>"+"</td>"+
				 "<td>"+"<input class=\"btn btn-info\" type=\"button\" id=\"addorder\" value=\"Add to Cart\">"+"</td>" +
				 " <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
				
				+"</tr>";
				$("#formTableArticle").append(newRow);
			}
		    }
		});
	}
	function loadRestaurantsUsers(){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/restaurants/all",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					console.log(data[i].address);
					newRow="<tr>"+
						"<td>"+data[i].name+"</td>"+
						"<td>"+data[i].address+"</td>"+
						"<td>"+data[i].type+"</td>"+
						 "<td>"+"<input class=\"btn btn-info\" type=\"button\" id=\"addFav\" value=\"Add to favourite\">"+"</td>" +
						 " <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
					+"</tr>";
					$("#formTableRestaurant").append(newRow);
					
				}
			}
		});
	}
	
	function loadVechicles(){
		$.ajax({
			url: "http://localhost:8080/webRA70/rest/vechicles/all",
			type:"GET",
			success: function(data){
				for(i=0;i<data.length;i++){
					newRow="<tr><td>"+data[i].mark+"</td>"+
					 "<td>"+data[i].model+"</td>"+
					 "<td>"+data[i].type+"</td>"+
					 "<td>"+data[i].registration+"</td>"+
					 "<td>"+data[i].yearOfDistribution+"</td>"+
					 "<td>"+data[i].used+"</td>"+
					 "<td>"+data[i].note+"</td>"+
					 "<td><input class=\"btn btn-info\" type=\"button\" value=\"Change \">"+"</td>" +
					 "<td>"+"<input class=\"btn btn-danger deleteItemV\" type=\"button\" value=\"DELETE\">"+"</td>"
					 +" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
					+"</tr>";
						
				
					$("#formTableVechicle").append(newRow);
					
				}
			}
		});
	}
	function loadFav(){
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/favourites/all",
			type:"GET",
			success:function(data){
				for(i=0;i<data.length;i++){
					newRow="<tr>" +
							"<td>"+data[i].name+"</td>" +
							"<td>"+data[i].address+"</td>" +
							"<td>"+data[i].type+"</td>" +
							"</tr>"
					$("#favRes").append(newRow);
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

	function addAdminPanel(){
		newRow="<input class=\"btn btn-warning\" type=\"button\" value=\"Change ROLE\" data-toggle=\"modal\" data-target=\"#changeRole\"><br><br>"+
		" <input class=\"btn btn-success\" type=\"button\" value=\"Add article\" data-toggle=\"modal\" data-target=\"#addArticle\"></input>"+
		"<input class=\"btn btn-success\" type=\"button\" value=\"Add restaurant\" data-toggle=\"modal\" data-target=\"#addRestaurant\"></input>"+
		"<input class=\"btn btn-success\" type=\"button\" value=\"Add Vehicle\" data-toggle=\"modal\" data-target=\"#addVehicle\"></input>";

		$("#adminPanel").append(newRow);
	}
	
	
	function addUserPanel(){
		newRow="<input class=\"btn btn-success\" type=\"button\" value=\"Favourites List\" data-toggle=\"modal\" data-target=\"#favList\"><br><br>"+
			"<input class=\"btn btn-success\" type=\"button\" value=\"Orders List\" data-toggle=\"modal\" data-target=\"#orderList\"><br><br>";
		
		$("#userPanel").append(newRow);
	}	

	$(document).on('click', '#obrisi', function(e){
		e.preventDefault();
		alert("da li ste sigurni da zelite da obrisete ovaj Restoran");
		//+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var id = $('#id').val();
		console.log(id);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/restaurants/delete/"+id,
			type:"DELETE",
			success: function(message){
				alert(message);
				//ukloni i na strani 
				tr_parent.remove()
			
			}
		});
			
		
	} );
	
	

	$(document).on('click', '.deleteItemA', function(e){
		e.preventDefault();
		alert("da li ste sigurni da zelite da obrisete Artikal");
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var id = $('#id').val();
		console.log(id);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/articles/delete/"+id,
			type:"DELETE",
			success: function(message){
				alert(message);
				//ukloni i na strani 
				tr_parent.remove()
			
			}
		});
	} );
	
	
	$(document).on('click', '.deleteItemV', function(e){
		e.preventDefault();
		alert("da li ste sigurni da zelite da obrisete Vozilo");
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var id = $('#id').val();
		console.log(id);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/vechicles/delete/"+id,
			type:"DELETE",
			success: function(message){
				alert(message);
				//ukloni i na strani 
				tr_parent.remove();
			
			}
		});
	} );
	
	
	$(document).on('click','#changeUserRole',function(e){
		e.preventDefault();
		//alert("Da li ste sigurni da zelite da izmenite ulogu");
		var username=$("#username").val();
		var role=$("#selectUserRole").val();
		console.log(username);
		console.log(role);
		dataType=JSON.stringify({
			username:username,
			role:role
		});
		
		console.log(dataType);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/users/changeRole/"+username+"/"+role,
			type:"POST",
			success: function(message){
				alert(message);
			}

		});
		
	$("#changeRole").modal("toggle");
			
	});
/*	
	$(document).on('click',"#editSaveRestaurant",function(e){
		console.log("tu smo u editSaveres");
		tr_parent = $(this).closest("tr");
		console.log(tr_parent);
		$('#editName').val(tr_parent.find(".name").html());
		$('#editAdress').val(tr_parent.find(".adress").html());
		$('#editSelectRes').val(tr_parent.find(".type").html());
		console.log(tr_parent.find(".name").html());
		var name=$("#editName").val();
		var address=$("#editAdress").val();
		var type=$("#editSelectRes").val();
		console.log(name);
		console.log(address);
		console.log(type);
		formData=JSON.stringify({
			name:name,
			address:address,
			type:type
		});
		console.log(formData);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/restaurants/editRes",
			type:"POST",
			data:formData,
			contentType: "application/json",
			datatype: "json",
			success: function(message){
				alert(message);
			},
			 error: function(){
					alert('Neispravan unos!');
				}
		});
		
		$("#editRestaurant").modal("toggle");
	});
	
	*/
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
		
		var name=$("#nameSearch").val();
		var address=$("#addressSearch").val();
		var type=$("#selectSearch").val();
		console.log(name+"---"+address+" -- "+type);
		
		formData=JSON.stringify({
			name:name,
			address:address,
			type:type
		});
		
	
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
	
	
	
	
	
	
	
	$(document).on('click',"#addFav",function(e){
		
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var idRes = $('#id').val();
		console.log(idRes);
		
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/restaurants/favourites/"+idRes,
			type:"POST",
			success: function(message){
				alert(message);
				loadFav();
			}
		});
	});
	
	
	
	$(document).on('click',"#addorder",function(e){
		
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var idArticle = $('#id').val();
		console.log(idArticle);
		var cart=$(this).closest("tr").find(".cart").val();
		//var name=$(this).closest("tr").find(".name").val();
		
		$('#name').val(tr_parent.find(".name").html());
		var name = $('#name').val();
		$('#type').val(tr_parent.find(".type").html());
		var type = $('#type').val();
		
		
		dataForm={
			idArticle:idArticle,
			amount:cart,
			
		};
		
		formData=JSON.stringify({
			idArticle:idArticle,
			amount:cart,
			
		});
		
		arr.push(dataForm);
		console.log(arr[0]);
		console.log(arr[0].amount);
		console.log("-------------");
		$("#cartTable").empty();
		 loadCart();
			$.ajax({
				url:"http://localhost:8080/webRA70/rest/order/stavka",
				type:"POST",
				data:formData,
				contentType: "application/json",
				datatype: "json",
				success: function(){
					alert("Dodato u korpu");
					loadUserOrders();
				}
			});
	});
	
	
	$(document).on('click',"#poruci",function(e){
		e.preventDefault();
		alert("Da li ste sigurni da zelite da porucite");
		formData=JSON.stringify({
			stavka:arr
		});
		
		
		console.log("--------poruci-----");
		console.log(formData);
		
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/order/addOne",
			type:"POST",
			data:formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				alert("Poruceno");
				arr=[];
				$("#cartList").modal("toggle");
			}
		});
	});
	
	
	
	
	
	$(document).on('click',"#preuzmi",function(e){
		
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var idStavka = $('#id').val();
		console.log(idStavka);
		
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/order/utoku/"+idStavka,
			type:"POST",
			contentType: "application/json",
			datatype: "json",
			success: function(message){
				alert(message);
				$("#por").empty();
				$("#dostavautoku").empty();
				loadPreuzimanjePorudzbina();
				loadDostavautoku();
			}
		});
	});
	
	$(document).on('click',"#dostavljeno",function(e){
		
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var idStavka = $('#id').val();
		console.log(idStavka);
		
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/order/dostavljeno/"+idStavka,
			type:"POST",
			contentType: "application/json",
			datatype: "json",
			success: function(){
				alert("Dostavljeno je obrisi sa strane");
				$("#dostavautoku").empty();
				loadDostavautoku();
			}
		});
	});
	
	$(document).on('click',"#obrisiOrder",function(e){
		alert("da li ste sigurni da zelite da obrisete");
		tr_parent = $(this).closest("tr");
		$('#id').val(tr_parent.find(".id").html());
		var idStavka = $('#id').val();
		console.log(idStavka);
		$.ajax({
			url:"http://localhost:8080/webRA70/rest/order/delete/"+idStavka,
			type:"DELETE",
			success: function(){
				$("#formTableOrders").empty();
				loadOrders();
			}
		});
	});
	
	
});

