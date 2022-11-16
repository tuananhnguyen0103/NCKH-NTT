import React,{useEffect, useState} from "react";
import $ from 'jquery'
import { toast } from 'react-toastify';
import axios from 'axios';
import Cookies from 'js-cookie';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
const Messenger = () => {


    const toastId = React.useRef(null);

    const token = Cookies.get('token')
    const [listContacts,setListContacts]=useState([])
    const [selected,setSelected]=useState('all')
    const [messenger,setMessenger]=useState('')


    const SelectContact=()=>{
        const items=[];
        if(listContacts.length>0)
        for (let i = 0; i < listContacts.length; i++) {
            let sdt;
            if(listContacts[i].SDTCha || listContacts[i].SDTMe)
            {
                 sdt= listContacts[i].SDTCha==null ? listContacts[i].SDTMe : listContacts[i].SDTCha
                 items.push(<option key={i} value={sdt}>{listContacts[i].idHocSinh}-{listContacts[i].hoTen} </option>);
            }
            else
            {
                items.push(<option disabled key={i} >{listContacts[i].idHocSinh}-{listContacts[i].hoTen}-Không có thông tin liên lạc </option>);

            }

            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
       }

       return items;

    }
    useEffect(() => {
        console.log(toastId)
        toastId.current = toast("Đang lấy thông tin liên lạc...", { type: toast.TYPE.INFO,autoClose: 5000 });

       axios({
           method: 'get',
           url: process.env.REACT_APP_BACKEND_URL + "/teacher/getContactStudent?token="+token,
           responseType: 'json',
           withCredentials: true
       })
           .then(function (response) {
            setListContacts(response.data);
               console.log("get")
               toast.update(toastId.current, {
                   render: "Xong",
                   type: toast.TYPE.SUCCESS,
                   //Here the magic
                   autoClose: 3000,
                   className: 'rotateY animated'
                 })
           }).catch(function (err) {
               toast.update(toastId.current, {
                   render: err,
                   type: toast.TYPE.SUCCESS,
                   //Here the magic
                   autoClose: 5000,
                   className: 'rotateY animated'
                 })
           }).finally(function () {
           });
   }, []);


   const onSubmitHandler= async (event) => {
       event.preventDefault();



      await axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/sendsms', {
        "selected": selected,
        "messenger":messenger,
        "token": token
    },{withCredentials: true,
        onUploadProgress:(event) => {
            toastId.current =  toast("Đang gửi", {type: toast.TYPE.INFO, autoClose: 5000 });;

        }})
        .then(function (response) {
            console.log(response);
            toast.update(toastId.current, {
                render: "Gửi thành công",
                type: toast.TYPE.SUCCESS,
                autoClose: 5000
            });
        })
        .catch(function (error) {
            console.log(error);
            toast.update(toastId.current, {
                render: "Có lỗi xảy ra vui lòng thử lại ",
                type: toast.TYPE.ERROR,
                autoClose: 5000
            });
        });



   }



    document.title = "Gửi tin nhắn"
    return (
        <>
            <div className="breadcrumbs-area">
                <h3>Messaging</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Compose Message</li>
                </ul>
            </div>

            <div className="row">

                <div className="col-xl-12">
                    <div className="card">
                        <div className="card-body">
                            <div className="heading-layout1">
                                <div className="item-title">
                                    <h3>Gửi tin nhắn</h3>
                                </div>
                                <div className="dropdown">
                                    <a className="dropdown-toggle" href="#" role="button"
                                        data-toggle="dropdown" aria-expanded="false">...</a>

                                    <div className="dropdown-menu dropdown-menu-right">
                                        <a className="dropdown-item" href="#"><i className="fas fa-times text-orange-red"></i>Close</a>
                                        <a className="dropdown-item" href="#"><i className="fas fa-cogs text-dark-pastel-green"></i>Edit</a>
                                        <a className="dropdown-item" href="#"><i className="fas fa-redo-alt text-orange-peel"></i>Refresh</a>
                                    </div>
                                </div>
                            </div>
                            <form  onSubmit={onSubmitHandler}  className="new-added-form">
                                <div className="row">

                                    <div className=" col-12 form-group">
                                        <label>Người nhận liên hệ *</label>
                                        <select onChange={isCommune => {
                                                setSelected(isCommune.target.value)
                                            }}  className="form-control" >
                                        <option value="all">Tất cả</option>

                                            {

                                                SelectContact()

                                            }
                                        </select>

                                    </div>
                                    <div className="col-12 form-group">
                                        <label>Message</label>
                                        <textarea onChange={isCommune => {

                                        setMessenger(isCommune.target.value)
                                    }}   className="textarea form-control" name="message" id="form-message" cols="10"
                                            rows="9"></textarea>
                                    </div>
                                    <div className="col-12 form-group mg-t-8">
                                        <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                                        <button type="reset" className="btn-fill-lg bg-blue-dark btn-hover-yellow">Reset</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>


            </div>
        </>
    );
};





export default Messenger;
