import React, { useEffect, useState } from "react";

import ModernDatepicker from 'react-modern-datepicker';
import axios from 'axios';
import dayjs from 'dayjs';
import { toast } from 'react-toastify';
import { Document, Page } from 'react-pdf/dist/esm/entry.webpack5';
import Cookies from 'js-cookie';
// import PDFViewer from 'pdf-viewer-reactjs'
const ChangePassword = () => {


    const [password, setPassword] = useState(null);
    const [newPassword, setnewPassword] = useState(null);

    const [Cfpassword, setCfPassword] = useState(null);

  
    const token = Cookies.get('token')
    const toastId = React.useRef(null);


    document.title = "Thay đổi mật khẩu";


    const onSubmitHandler= async (event) => {
        event.preventDefault();
 

        if(newPassword!=Cfpassword)
        { 
            return   toastId.current = toast("Mật khẩu xác nhận không khớp", { type: toast.TYPE.ERROR, autoClose: 5000 });

        }
        axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/changePassword', {
            oldPassword:password,
            password: newPassword,
            token:token
        },{withCredentials: true,
            onUploadProgress:(event) => {
                toastId.current =  toast("Đang tải", {type: toast.TYPE.INFO, autoClose: 5000 });;

            }})
            .then(function (response) {
                console.log(response);
                toast.update(toastId.current, {
                    render: "Đổi mật khẩu thành công",
                    type: toast.TYPE.SUCCESS,
                    autoClose: 5000
                });
            })
            .catch(function (error) {
                if(error.response.status===500)
                { 
                    return   toast.update(toastId.current, {
                        render:"Phiên đăng nhập không hợp lệ",
                        type: toast.TYPE.ERROR,
                        autoClose: 5000
                    });
                }
                console.log(error);
                toast.update(toastId.current, {
                    render: error.response.data,
                    type: toast.TYPE.ERROR,
                    autoClose: 5000
                });
            });
    }
    return (
        <>

<>
            <div className="breadcrumbs-area">
                <h3>Đổi mật khẩu</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Đổi mật khẩu</li>
                    
                </ul>
            </div>
            
            <div className="card height-auto">
                <div className="card-body">
                    <div className="heading-layout1">
                        <div className="col-12 item-title">
                            <h3>Thông tin học sinh</h3>
                            <hr></hr>
                        </div>
                        
                    </div>
                    <form onSubmit={onSubmitHandler} className="new-added-form">
                        <div className="row">

                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Mật khẩu hiện tại *</label>
                                <input required onChange={value=>setPassword(value.target.value)} type="password" placeholder="" className="form-control" />                            
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Mật khẩu mới *</label>
                                <input required="1" onChange={value=>setnewPassword(value.target.value)} type="password" placeholder="" className="form-control" />     
                            </div>    
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Xác nhận mật khẩu mới *</label>
                                <input required="1" onChange={value=>setCfPassword(value.target.value)} type="password" placeholder="" className="form-control" />     
                            </div>    

                        </div>
                        <div className="col-12 form-group mg-t-8 button-time" >
                            <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                        
                        </div>
                    </form>

                </div>
            </div>
        </>






        </>
    );
}

export default ChangePassword;