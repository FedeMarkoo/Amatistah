<div class="login-dropdown">
	<button type="button" class="login-dropdown" data-toggle="dropdown"
		id="loginButton" id="loginButton" aria-haspopup="true"
		aria-expanded="true">Login</button>
	<div class="dropdown-menu dropdown-menu-right" id="loginDropdown">
		<div class="card card-body">
			<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
			<div class="container">
				<div class="row">
					<div class="mx-auto">
						<div id="first">
							<div class="myform form ">
								<div class="logo mb-3">
									<div class="text-center">
										<h1>Login</h1>
									</div>
								</div>
								<form action="userAcces/login" method="get" name="login"
									id="loginForm" novalidate="novalidate">
									<div class="form-group">
										<label for="exampleInputEmail1">Nombre de usuario</label> <input
											type="email" name="userName" class="form-control"
											id="userName" aria-describedby="userNameHelp"
											placeholder="Ingrese usuario">
									</div>
									<div class="form-group">
										<label for="exampleInputEmail1">Contraseña</label> <input
											type="password" name="password" id="password"
											class="form-control" aria-describedby="emailHelp"
											placeholder="Ingrese contraseña">
									</div>
									<div class="form-group">
										<input type="checkbox" name="isGoogle" id="isGoogle"
											class="form-control" hidden="true">
									</div>
									<div class="form-group">
										<input type="text" name="email" id="email"
											class="form-control" hidden="true">
									</div>
									<div class="form-group">
										<p class="text-center">
											By signing up you accept our <a href="#">Terms Of Use</a>
										</p>
									</div>
									<div class="col-md-12 text-center ">
										<button type="submit"
											class=" btn btn-block mybtn btn-primary tx-tfm">Login</button>
									</div>
									<div class="col-md-12 ">
										<div class="login-or">
											<hr class="hr-or">
											<span class="span-or">or</span>
										</div>
									</div>
									<div class="col-md-12 mb-3">
										<div class="g-signin2" data-onsuccess="onSignIn"></div>
										<script type="text/javascript">
											function onSignIn(googleUser) {
												$.post(
																"userAcces/signGoogle",
																{
																	userName : googleUser
																			.getBasicProfile()
																			.getName(),
																	email : googleUser
																			.getBasicProfile()
																			.getEmail()
																},
																function() {
																	/* alert("success"); */
																})
														.fail(function() {
															/*alert("error");*/
														})
											}
										</script>
									</div>
									<div class="form-group">
										<p class="text-center">
											Don't have account?
											<button onclick="fadeInSecond()" type="button" id="signup">Sign
												up here</button>
										</p>
									</div>
								</form>

							</div>
						</div>
						<div id="second">
							<div class="myform form ">
								<div class="logo mb-3">
									<div class="col-md-12 text-center">
										<h1>Registrarse</h1>
									</div>
								</div>
								<form action="userAcces/signUp" name="registration" method="post"
									novalidate="novalidate">
									<div class="form-group">
										<label for="exampleInputEmail1">UserName</label> <input
											type="text" name="userNameRegister" class="form-control"
											id="userNameRegister" aria-describedby="emailHelp"
											placeholder="Enter Firstname">
									</div>
									<div class="form-group">
										<label for="exampleInputEmail1">Email address</label> <input
											type="email" name="emailRegister" class="form-control"
											id="emailRegister" aria-describedby="emailHelp"
											placeholder="Enter email">
									</div>
									<div class="form-group">
										<label for="exampleInputEmail1">Password</label> <input
											type="password" name="passwordRegister" id="passwordRegister"
											class="form-control" aria-describedby="emailHelp"
											placeholder="Enter Password">
									</div>
									<div class="col-md-12 text-center mb-3">
										<button type="submit"
											class=" btn btn-block mybtn btn-primary tx-tfm">Get
											Started For Free</button>
									</div>
									<div class="col-md-12 ">
										<div class="form-group">
											<p class="text-center">
												<button id="signin" type="button" onclick="fadeInFirst()	">Already
													have an account?</button>
											</p>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$("#loginDropdown").click(function() {
					var oldNode = document.getElementById("loginDropdown");
					var newNode = oldNode.cloneNode(true);
					oldNode.parentNode.insertBefore(newNode, oldNode);
					oldNode.parentNode.removeChild(oldNode);

					$("#loginButton").click(function() {
						if ($("#loginDropdown")[0].classList.contains("show")){
							$("#loginDropdown").removeClass("show");
						}else{
							$("#loginDropdown").addClass("show");
						}
					});
				});

				function fadeInFirst() {
					$("#first").fadeIn("fast");
					$("#first")[0].style["opacity"] = 1;
					$("#second")[0].style["display"] = "none";
				}

				function fadeInSecond() {
					$("#first")[0].style["display"] = "none";
					$("#second").fadeIn("fast");
					$("#second")[0].style["opacity"] = 1;
				}

				$(function() {
					$("form[name='login']").validate({
						rules : {

							email : {
								required : true,
								email : true
							},
							password : {
								required : true,

							}
						},
						messages : {
							email : "Please enter a valid email address",

							password : {
								required : "Please enter password",

							}

						},
						submitHandler : function(form) {
							form.submit();
						}
					});
				});

				$(function() {

					$("form[name='registration']")
							.validate(
									{
										rules : {
											firstname : "required",
											lastname : "required",
											email : {
												required : true,
												email : true
											},
											password : {
												required : true,
												minlength : 5
											}
										},

										messages : {
											firstname : "Please enter your firstname",
											lastname : "Please enter your lastname",
											password : {
												required : "Please provide a password",
												minlength : "Your password must be at least 5 characters long"
											},
											email : "Please enter a valid email address"
										},

										submitHandler : function(form) {
											form.submit();
										}
									});
				});
			</script>
		</div>
	</div>
</div>