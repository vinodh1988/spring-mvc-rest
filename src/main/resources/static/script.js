
$(document).ready(
  
		function(){
			
			$('#city').keyup(
				function(){
					  
					  let pattern=$('#city').val();
					  console.log(pattern);
					   $.get("http://localhost:8889/api/cities/"+pattern,function(cities){
						  
						   let code="";
						   for(let x in cities){
							   code+="<tr>";
							   code+="<td>"+cities[x].name+"</td>";
							   code+="<td>"+cities[x].district+"</td>";
							   code+="<td>"+cities[x].population+"</td>";
							   code+="</tr>";
						   }
						   $('#tdata').html(code);
					   });
				}	
			);
			
			$('#country').change(
					function(){
						  
						  let code=$('#country').val();
						  
						   $.get("http://localhost:8889/api/allcities/"+code,function(cities){
							  
							   let code="";
							   for(let x in cities){
								   code+="<tr>";
								   code+="<td>"+cities[x].name+"</td>";
								   code+="<td>"+cities[x].district+"</td>";
								   code+="<td>"+cities[x].population+"</td>";
								   code+="</tr>";
							   }
							   $('#tdata').html(code);
						   });
					}	
				);
			
		}
		
);