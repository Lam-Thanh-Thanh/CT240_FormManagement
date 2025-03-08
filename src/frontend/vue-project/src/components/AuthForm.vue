<template>
  <!-- Form Container -->
  <div class="form-container">
        <div class="col col-1">
            <div class="image-layer">
                <img src="@/assets/img/white-outline.png" class="form-image-main">
                <img src="@/assets/img/dots.png" class="form-image dots">
                <img src="@/assets/img/coin.png" class="form-image coin">
                <img src="@/assets/img/spring.png" class="form-image spring">
                <img src="@/assets/img/rocket.png" class="form-image rocket">
                <img src="@/assets/img/cloud.png" class="form-image cloud">
                <img src="@/assets/img/stars.png" class="form-image starts">
            </div>
            <p class="featured-words">You Are Few Minutes Way to Boost Your Skills With <span>Ludifex</span></p>
        </div>
        <div class="col col-2">
            <div class="btn-box">
                <button class="btn btn-1" id="login">Sign In</button>
                <button class="btn btn-2" id="register">Sign Up</button>
            </div>
            
            <!-- Login Form Container -->
            <div class="login-form">
                <div class="form-title">
                    <span>Sign In</span>
                </div>
                <div class="form-inputs">
                    <div class="input-box">
                        <input type="text" class="input-field" placeholder="Username" required>
                        <i class="bx bx-user icon"></i>
                    </div>
                    <div class="input-box">
                        <input type="password" class="input-field" placeholder="Password" required>
                        <i class="bx bx-lock-alt icon"></i>
                    </div>
                    <div class="forgot-pass">
                        <a href="#">Forgot Password?</a>
                    </div>
                    <div class="input-box">
                        <button class="input-submit">
                            <span>Sign In</span>
                            <i class="bx bx-right-arrow-alt"></i>
                        </button>
                    </div>
                </div>
                <div class="social-login">
                    <i class="bx bxl-google"></i>
                    <i class="bx bxl-facebook"></i>
                    <i class="bx bxl-twitter"></i>
                    <i class="bx bxl-github"></i>

                </div>
            </div>
            <!-- Register Form Container -->
            <div class="register-form">
                <div class="form-title">
                    <span>Create Account</span>
                </div>
                <div class="form-inputs">
                    <div class="input-box">
                        <input type="text" class="input-field" placeholder="Email" required>
                        <i class="bx bx-envelope icon"></i>
                    </div>
                    <div class="input-box">
                        <input type="text" class="input-field" placeholder="Username" required>
                        <i class="bx bx-user icon"></i>
                    </div>
                    <div class="input-box">
                        <input type="password" class="input-field" placeholder="Password" required>
                        <i class="bx bx-lock-alt icon"></i>
                    </div>
                    <div class="forgot-pass">
                        <a href="#">Forgot Password?</a>
                    </div>
                    <div class="input-box">
                        <button class="input-submit">
                            <span>Sign Up</span>
                            <i class="bx bx-right-arrow-alt"></i>
                        </button>
                    </div>
                </div>
                <div class="social-login">
                    <i class="bx bxl-google"></i>
                    <i class="bx bxl-facebook"></i>
                    <i class="bx bxl-twitter"></i>
                    <i class="bx bxl-github"></i>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
export default {
  data() {
    return {
      isRegister: false,
      email: '',
      password: '',
      username: ''
    };
  },
  methods: {
    toggleMode() {
      this.isRegister = !this.isRegister;
    },
    handleLogin() {
      console.log('Logging in with', this.email, this.password);
    },
    handleRegister() {
      console.log('Registering with', this.username, this.email, this.password);
    }
  }
};
</script>

<style  scoped>

    /* POPPINS FONT */
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

/* ====== BASE ===== */
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
}

/* ====== Body ===== */
body{
    background: url("@/assets/img/1.jpg");
    background-position: center;
    background-size: cover;
    background-attachment: fixed;
    background-repeat: no-repeat;

    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    padding: 0 20px;
}

/* ====== Form container ===== */
.form-container{
    display: flex;
    width: 1000px;
    height: 600px;
    border: 3px solid rgba(255,255,255,0.3);
    border-radius: 30px;
    backdrop-filter: blur(20px);
    overflow: hidden;
}

/* ====== First Column ===== */
.col-1{
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 55%;
    background: rgba(255,255,255,0.3);
    backdrop-filter: blur(30px);
    border-radius: 0 30% 20% 0;
    transition: border-radius .3s;
}
.image-layer{
    position: relative;
}
.form-image-main{
    width: 400px;
    animation: scale-up 3s ease-in-out alternate infinite;
}
.form-image{
    position: absolute;
    left: 0;
    width: 400px;
}

/* ====== Form Image Animation ===== */
.coin{
    animation: scale-down 3s ease-in-out alternate infinite;
}
.spring{
    animation: scale-down 3s ease-in-out alternate infinite;
}
.dots{
    animation: scale-up 3s ease-in-out alternate infinite;
}
.rocket{
    animation: up-down 3s ease-in-out alternate infinite;
}
.cloud{
    animation: left-right 3s ease-in-out alternate infinite;
}
.starts{
    animation: scale-down 3s ease-in-out alternate infinite;
}
@keyframes left-right {
    to{
        transform: translateX(10px);
    }
}
@keyframes up-down {
    to{
        transform: translateY(10px);
    }
}
@keyframes scale-down {
    to{
        transform: scale(0.95);
    }
}
@keyframes scale-up {
    to{
        transform: scale(1.05);    
    }
}


/* ====== Featured Words ===== */
.featured-words{
    text-align: center;
    color: #fff;
    width: 300px;
}
.featured-words span{
    font-weight: 600;
    color: #21264D;
}

/* ====== Second Column ===== */
.col-2{
    position: relative;
    width: 45%;
    padding: 20px;
    overflow: hidden;
}
.btn-box{
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 20px;
}
.btn{
    font-weight: 500;
    padding: 5px 30px;
    border: none;
    border-radius: 30px;
    background: rgba(255,255,255,0.2);
    color: #fff;
    box-shadow: 0 5px 10px rgba(0,0,0,0.2);
    cursor: pointer;
    transition: .2s;
}
.btn-1{
    background: #21264D;
}
.btn:hover{
    opacity: 0.85;
}
/*  ======= Login Form ========  */
.login-form{
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    padding: 0 4vw;
    transition: .3s;
}
/*  ======= Register Form ========  */
.register-form{
    position: absolute;
    left: -50%;
    transform: translateX(-50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    padding: 0 4vw;
    transition: .3s;
}
.register-form .form-title{
    margin-block: 40px 20px;
}

.form-title{
    margin: 40px 0;
    color: #fff;
    font-size: 28px;
    font-weight: 600;
}
.form-inputs{
    width: 100%;
}
.input-box{
    position: relative;
}
.input-field{
    width: 100%;
    height: 55px;
    padding: 0 15px;
    margin: 10px 0;
    color: #fff;
    background: rgba(255,255,255,0.2);
    border: none;
    border-radius: 10px;
    outline: none;
    backdrop-filter: blur(20px);
    box-shadow: 0 0 10px rgba(0,0,0,0.2);
}
::placeholder{
    color: #fff;
    font-size: 15px;
}
.input-box .icon{
    position: absolute;
    top: 50%;
    right: 15px;
    transform: translateY(-50%);
    color: #fff;
}
.forgot-pass{
    display: flex;
    justify-content: right;
    gap: 5px;
}
.forgot-pass a{
    color: #fff;
    text-decoration: none;
    font-size: 14px;
}
.forgot-pass a:hover{
    text-decoration: underline;
}
.input-submit{
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    width: 100%;
    height: 55px;
    padding: 0 15px;
    margin: 10px 0;
    color: #fff;
    background-color: #21264D;
    border: none;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0,0,0,0.2);
    cursor: pointer;
    transition: .3s;
}
.input-submit:hover{
    gap: 15px;
}
.social-login{
    display: flex;
    gap: 20px;
    margin-top: 20px;
}
.social-login i{
    display: flex;
    align-items: center;
    justify-content: center;
    height: 40px;
    width: 40px;
    color: #fff;
    background: rgba(255,255,255,0.2);
    border-radius: 50%;
    cursor: pointer;
    box-shadow: 0 0 10px rgba(0,0,0,0.2);
    transition: .2s;
}
.social-login i:hover{
    transform: scale(0.9);
}

/* ======== Responsive ======== */
@media (max-width: 892px){
    .form-container{
        width: 400px;
    }
    .col-1{
        display: none;
    }
    .col-2{
        width: 100%;
    }
}

</style>
