import React from "react";
import $ from 'jquery'
// const Chart = require('chart.js/auto');
import  Chart  from "chart.js/auto";
import { toast } from 'react-toastify';
import axios from 'axios';
import Cookies from 'universal-cookie';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.myRef = React.createRef(null);
        this.state = { id: '', password: '' };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.loginSubmit = this.loginSubmit.bind(this);
        document.title = "Đăng nhập";

    }


    

    
    handleInputChange(event) {
        const target = event.target;
        const name = target.name;
        
        this.setState({ [name]: target.value });
    }
    loginSubmit(event) {
        const toastId = this.myRef;
        event.preventDefault()
        axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/login', {
            id: this.state.id,
            password: this.state.password
        },{withCredentials: true,
            onUploadProgress:(event) => {
                toastId.current =  toast("Đang đăng nhập", {type: toast.TYPE.INFO, autoClose: 5000 });;

            }})
            .then(function (response) {
                const cookies = new Cookies();
                cookies.set('token',response.data.token);
                // console.log(cookies.get('myCat')); // Pacman
                cookies.set('user',response.data);
                console.log(response);
                toast.update(toastId.current, {
                    render: "Đăng nhập thành công",
                    type: toast.TYPE.SUCCESS,
                    autoClose: 5000
                });
                window.location.reload(); 
            })
            .catch(function (error) {
                console.log(error);
                toast.update(toastId.current, {
                    render: "Sai tài khoản hoặc mật khẩu",
                    type: toast.TYPE.ERROR,
                    autoClose: 5000
                });
            });
    }
    componentDidMount() {
    
    }
    componentWillMount() {

    }
    render() {
        return (
         <>    <div className="login-page-wrap">
         <div className="login-page-content">
             <div className="login-box">
                 <div className="item-logo">
                     <img src="assets/img/logo2.png" alt="logo"/>
                 </div>
                 <form onSubmit={this.loginSubmit} className="login-form">
                     <div className="form-group">
                         <label>Username</label>
                         <input 
                            name="id"
                            required
                            onChange={this.handleInputChange}
                         type="text" placeholder="Enter usrename" className="form-control"/>
                         <i className="far fa-envelope"></i>
                     </div>
                     <div className="form-group">
                         <label>Password</label>
                         <input
                                required
                                onChange={this.handleInputChange}
                                name='password'
                         type="password" placeholder="Enter password" className="form-control"/>
                         <i className="fas fa-lock"></i>
                     </div>
                     <div className="form-group d-flex align-items-center justify-content-between">
                         <div className="form-check">
                             <input type="checkbox" className="form-check-input" id="remember-me"/>
                             <label htmlFor="remember-me" className="form-check-label">Remember Me</label>
                         </div>
                         <a href="#" className="forgot-btn">Forgot Password?</a>
                     </div>
                     <div className="form-group">
                         <button type="submit" className="login-btn">Login</button>
                     </div>
                 </form>
                 <div className="login-social">
                     <p>or sign in with</p>
                     <ul>
                         <li><a href="#" className="bg-fb"><i className="fab fa-facebook-f"></i></a></li>
                         <li><a href="#" className="bg-twitter"><i className="fab fa-twitter"></i></a></li>
                         <li><a href="#" className="bg-gplus"><i className="fab fa-google-plus-g"></i></a></li>
                         <li><a href="#" className="bg-git"><i className="fab fa-github"></i></a></li>
                     </ul>
                 </div>
             </div>
             <div className="sign-up">Don't have an account ? <a href="#">Signup now!</a></div>
         </div>
     </div></>
        );
    }
}
export default Login;