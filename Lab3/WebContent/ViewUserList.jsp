<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.BeanUser,java.util.List,java.util.ArrayList" %>
    
<% 
List<BeanUser> users = null;
List<String> following = null;
if (request.getAttribute("users")!=null) {
	users = (List<BeanUser>)request.getAttribute("users");
}

BeanUser user = null;
if (request.getAttribute("user")!=null) {
	user = (BeanUser)request.getAttribute("user");
	following = user.getFollowing();
}

String lastAction = "allUsers";
if(request.getAttribute("lastAction") != null){
	lastAction = (String)request.getAttribute("lastAction");
}
%>

<script type="text/javascript">
$(document).ready(function() {  
	$(".follow").click(function(event){
		event.stopPropagation(); //Prevent go to user profile
		var target = event.target;
		var userN = $(target.parentElement.parentElement).attr("username");
    	$.post('FollowController',{action: "followUser", username: userN },function(data,status){
    		if(status != 401){
    			$.get("UserListController",{action:"${lastAction}"},function(data,status){
    				if(status != 401){
    					$('#content').html(data);
    				}
    			});
 			}
		});	
   	});
	
	$(".unfollow").click(function(event){
		event.stopPropagation(); //Prevent go to user profile
		var target = event.target;
		var userN = $(target.parentElement.parentElement).attr("username");
    	$.post('FollowController',{action: "unFollow", username: userN },function(data,status){
    		if(status != 401){
    			$.get("UserListController",{action:"${lastAction}"},function(data,status){
    				if(status != 401){
    					$('#content').html(data);
    				}
    			});
 			}
		});		
   	});
	
	$(".user-list-item").click(function(event){
		var username = $(this).find(".media-body").attr("username");
		$("#content").load("ProfileController?userProfileUsername="+username);
	});
});
</script>

<div class="container col-md-4 col-sm-4 col-lg-4  col-sm-offset-3 col-md-offset-3 col-lg-offset-3">
	<% if(users.size() == 0){ %>
		<div class="card">
		    <div class="card-header danger-color-dark white-text">
		        Users not found
		    </div>
		    <div class="card-block">
		        <h4 class="card-title">There are no users</h4>
		        <p class="card-text">There are no users of the kind you are looking for.</p>
		    </div>
		</div>
	<%}%>    
	<% for(BeanUser userInList : users){ %>
	    <div class="media mb-1 list-group-item user-list-item">
	        <a class="media-left waves-light">
	            <img class="rounded-circle" src="https://mdbootstrap.com/img/Photos/Avatars/avatar-13.jpg" alt="Generic placeholder image">
	        </a>
	        <div class="media-body" username="<%=userInList.getUsername()%>">
	            <h4 class="media-heading" id="username"><%=userInList.getUsername() %>
	            	<%if(following != null){ %>
	            		<%if(following.contains(userInList.getUsername())) {%>
			            	<a class="btn btn-danger btn-sm unfollow">UnFollow</a>
			            <%}else{ %>
			            	<a class="btn btn-success btn-sm follow">Follow</a>
			            <%}%>
			        <%} %>    
	            </h4> 
	            <ul class="metrics inline-ul">
	                <li title="tweets"><i class="fa fa-twitter fa-lg" aria-hidden="true"></i><span class="badge badge-primary badge-pill"><%=userInList.getUserTweetsSize() %></span></li>
	                <li title="followers"><i class="fa fa-users fa-lg" aria-hidden="true"></i><span class="badge badge-primary badge-pill"><%=userInList.getFollowersSize() %></span></li>
	                <li title="following"><i class="fa fa-user-plus fa-lg" aria-hidden="true"></i><span class="badge badge-primary badge-pill"><%=userInList.getFollowingSize() %></span></li>
	            </ul>
	            <h6>Last tweet</h6>
	            <p><%=userInList.getLastTweetText() %></p>
	        </div>
	    </div>
	   <% } %>
</div>
