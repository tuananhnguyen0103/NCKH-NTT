import React, { useState } from "react";
import Messenger from "../Messenger/Messenger"
import axios from 'axios';
import Notification from "../Notification/Notification";
import Cookies from 'js-cookie'
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';

import { toast } from 'react-toastify';

   const logout=()=>{
    Cookies.remove('token');
    window.location.reload();
   }
    const Header=() =>{
        const user=JSON.parse(Cookies.get('user'))
        return (
            <div className="navbar navbar-expand-md header-menu-one bg-light">
            
                <div className="nav-bar-header-one">
                    <div className="header-logo">
                        <a href="/">
                            <img src="assets/img/logo.png" alt="logo" />
                        </a>
                    </div>
                    <div className="toggle-button sidebar-toggle">
                        <button type="button" className="item-link">
                            <span className="btn-icon-wrap">
                                <span></span>
                                <span></span>
                                <span></span>
                            </span>
                        </button>
                    </div>
                </div>
                <div className="d-md-none mobile-nav-bar">
                    <button className="navbar-toggler pulse-animation" type="button" data-toggle="collapse" data-target="#mobile-navbar" aria-expanded="false">
                        <i className="far fa-arrow-alt-circle-down"></i>
                    </button>
                    <button type="button" className="navbar-toggler sidebar-toggle-mobile">
                        <i className="fas fa-bars"></i>
                    </button>
                </div>
                <div className="header-main-menu collapse navbar-collapse" id="mobile-navbar">
                    <ul className="navbar-nav">
                        <li className="navbar-item header-search-bar">
                            <div className="input-group stylish-input-group">
                                <span className="input-group-addon">
                                    <button type="submit">
                                        <span className="flaticon-search" aria-hidden="true"></span>
                                    </button>
                                </span>
                                <input type="text" className="form-control" placeholder="Find Something . . ." />
                            </div>
                        </li>
                    </ul>
                    <ul className="navbar-nav">
                        <li className="navbar-item dropdown header-admin">
                            <a className="navbar-nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown"
                                aria-expanded="false">
                                <div className="admin-title">
                                    <h5 className="item-title">{user.hoTen}</h5>
                                    <span>Admin</span>
                                </div>
                                <div className="admin-img">
                                    <img src={`${process.env.REACT_APP_BACKEND_STOGARE}/${user.anh}`}  width="50" height="50" className="d-inline-block align-top" alt="Admin" />
                                </div>
                            </a>
                            <div className="dropdown-menu dropdown-menu-right">
                                <div className="item-header">
                                    <h6 className="item-title">{user.hoTen}</h6>
                                </div>
                                <div className="item-content">
                                    <ul className="settings-list">
                                        <li> <Link  to={'/teacher/doi-mat-khau'}  className="nav-link"><i className="flaticon-user"></i>Đổi mật khẩu</Link></li>

                                        <li><a onClick={logout} href="#"><i className="flaticon-turn-off"></i>Đăng xuất</a></li>
                                    </ul>
                                </div>
                            </div>
                        </li>

                        <Messenger></Messenger>

                        <Notification></Notification>
                        {/* <li className="navbar-item dropdown header-language">
                        <a className="navbar-nav-link dropdown-toggle" href="#" role="button" 
                        data-toggle="dropdown" aria-expanded="false"><i className="fas fa-globe-americas"></i>EN</a>
                        <div className="dropdown-menu dropdown-menu-right">
                            <a className="dropdown-item" href="#">English</a>
                            <a className="dropdown-item" href="#">Spanish</a>
                            <a className="dropdown-item" href="#">Franchis</a>
                            <a className="dropdown-item" href="#">Chiness</a>
                        </div>
                    </li> */}
                    </ul>
                </div>
            </div>

        );
    }

export default Header;