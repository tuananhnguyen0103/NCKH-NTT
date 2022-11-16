import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import './index.css';
import Cookies from 'js-cookie'
import reportWebVitals from './reportWebVitals';
import Layout from './components/layout/layout';
import { ToastContainer, toast } from 'react-toastify';
import Login from './components/auth/Login'
import 'react-toastify/dist/ReactToastify.css';
import jwt_decode from "jwt-decode";
import { io } from "socket.io-client";
const root = ReactDOM.createRoot(document.getElementById('wrapper'))

const socket = io(process.env.REACT_APP_WEBSOCKET);
const checkLogin = () => {
if (Cookies.get('token') && Cookies.get('user')) {
    try{
      let currentDate = new Date();
      let decodedToken = jwt_decode(Cookies.get('token'));
      console.log(decodedToken)
      if (decodedToken.exp * 1000 < currentDate.getTime()) {
        Cookies.remove('token')
        Cookies.remove('user')
        return false;
      } else {
        const user=JSON.parse(Cookies.get('user'))
        socket.on(user.ttl+"att", (data) => {

          console.log(data)
          alert(data.data)
         }
          );
        return true;
      }
    }
    catch(e){
      Cookies.remove('token')
      Cookies.remove('user')
      return false;

    }

   
  }
  else {
    return false;
  }

}
if ( checkLogin()) {
  root.render(

    <BrowserRouter>
      <Layout>

      </Layout>
      <ToastContainer />
    </BrowserRouter>
  );
}
else {
  root.render(
    <>  <Login />
      <ToastContainer />
    </>

  );
}


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
//                      _oo0oo_
//                     o8888888o
//                     88" . "88
//                     (| -_- |)
//                     0\  =  /0
//                   ___/`---'\___
//                 .' \\|     |# '.
//                / \\|||  :  |||# \
//               / _||||| -:- |||||- \
//              |   | \\\  -  #/ |   |
//              | \_|  ''\---/''  |_/ |
//              \  .-\__  '-'  ___/-. /
//            ___'. .'  /--.--\  `. .'___
//         ."" '<  `.___\_<|>_/___.' >' "".
//        | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//        \  \ `_.   \_ __\ /__ _/   .-` /  /
//    =====`-.____`.___ \_____/___.-`___.-'=====
//                      `=---='
//
//
//  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//      please do not BUG