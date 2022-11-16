import React,{useEffect, useState} from "react";
import $ from 'jquery'
import { toast } from 'react-toastify';
import axios from 'axios';
import Cookies from 'js-cookie';
// import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useParams
} from "react-router-dom";
var object = {
    idXinNghiHoc: "",
    tenHocSinh: "",
    noiDungXinNghi : "",
    SDTCha : "",
    SDTMe : "",
    noiDungGiaoVien: "",
    xacNhan: 0
}
const updateStateDayOffs = (idXinNghiHoc,messenger, sdt) => {
    const token = Cookies.get('token')
    const toastId = {};
    const dataDayOffs = {
        idXinNghiHoc: idXinNghiHoc, 
        text: messenger,
        number:sdt,
        token:token
    };
    console.log(dataDayOffs);
    // event.preventDefault();
    axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/CallParentTeacher', 
    dataDayOffs, {
        // onUploadProgress là sự kiện khi upload 
        onUploadProgress: (processEvents) => {
            if (processEvents.lengthComputable) { // Check xem có đăng tải lên không?

                console.log(processEvents.loaded / processEvents.total * 100)
                toastId.current = toast("Đang tải thông tin học sinh", { type: toast.TYPE.INFO, autoClose: 5000 });
            }
            // console.log()
        }, withCredentials: true,
    }).then(function (response) {
        toast.update(toastId.current, {
            render: "Đăng thành công",
            type: toast.TYPE.SUCCESS,
            autoClose: 5000
        });;
        console.log(response);
    }).catch(function (error) {

        // With a string
        console.log(error);
        // toast.update(toastId.current, {
        //     render: error.response.data.data,
        //     type: toast.TYPE.ERROR,
        //     autoClose: 5000
        // });
    });
}

const RequestDayOffDetails = () => {

    const [data,setData] = useState({})
    const [listContacts,setListContacts]=useState([])
    const [selected,setSelected]=useState('all')
    const [messenger,setMessenger]=useState('')
    const [tenHocSinh, setTenHocSinh] = useState("")
    const toastId = React.useRef(null);

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
    const parms = useParams();
    useEffect(() => {
        console.log(toastId)
        toastId.current = toast("Đang lấy thông tin ...", { type: toast.TYPE.INFO,autoClose: 5000 });

       axios({
           method: 'get',
           url: process.env.REACT_APP_BACKEND_URL + "/teacher/GetDetailsDayOffStudentByIdDayOff/" + parms.id+"?token="+Cookies.get('token'),
           responseType: 'json',
           withCredentials: true
       })
           .then(function (response) {
            //    console.log(response)
               console.log(response.data[0])
            //    console.log(response.data[0].hoTen)
                setListContacts(response.data);
            //     console.log(listContacts)
            //    console.log("get")
            //    setData(response.data[0])
            //    console.log(data)
            //    setTenHocSinh(response.data[0].hoTen)
            //    console.log(tenHocSinh)
            object.idXinNghiHoc = response.data[0].idXinNghiHoc
            object.tenHocSinh = response.data[0].hoTen
            object.noiDungXinNghi = response.data[0].noiDung
            object.SDTCha= response.data[0].SDTCha
            object.SDTMe = response.data[0].SDTMe
            object.xacNhan = response.data[0].xacNhan
            console.log(object)
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


//    const onSubmitHandler= async (event) => {
//        event.preventDefault();



//       await axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/sendsms', {
//         "selected": selected,
//         "messenger":messenger
//     },{withCredentials: true,
//         onUploadProgress:(event) => {
//             toastId.current =  toast("Đang gửi", {type: toast.TYPE.INFO, autoClose: 5000 });;

//         }})
//         .then(function (response) {
//             console.log(response);
//             toast.update(toastId.current, {
//                 render: "Gửi thành công",
//                 type: toast.TYPE.SUCCESS,
//                 autoClose: 5000
//             });
//         })
//         .catch(function (error) {
//             console.log(error);
//             toast.update(toastId.current, {
//                 render: "Có lỗi xảy ra vui lòng thử lại ",
//                 type: toast.TYPE.ERROR,
//                 autoClose: 5000
//             });
//         });



//    }



    document.title = "Gửi tin nhắn"
    return (
        <>
            <div className="breadcrumbs-area">
                <h3>Messaging</h3>
                <ul>
                    <li>
                        <a href="index.html">Home</a>
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
                            <form   className="new-added-form">
                                <div className="row">

                                    <div className=" col-12 form-group">
                                        <label>Học sinh nghỉ học *</label>
                                        <input  type="text" value= {object.tenHocSinh}
                                            // onChange={value=>setdanToc(value.target.value)}
                                            placeholder="" className="form-control" disabled /> 
                                    </div>
                                    <div className=" col-6 form-group">
                                        <label>Họ tên cha *</label>
                                        <input  type="text" value= {object.tenHocSinh}
                                            // onChange={value=>setdanToc(value.target.value)}
                                            placeholder="" className="form-control" disabled /> 
                                    </div>
                                    <div className=" col-6 form-group">
                                        <label>SĐT Cha *</label>
                                        <input  type="text" value= {object.SDTCha}
                                            // onChange={value=>setdanToc(value.target.value)}
                                            placeholder="" className="form-control" disabled /> 
                                    </div>
                                    <div className=" col-6 form-group">
                                        <label>Họ tên mẹ *</label>
                                        <input  type="text" value= {object.tenHocSinh}
                                            // onChange={value=>setdanToc(value.target.value)}
                                            placeholder="" className="form-control" disabled /> 
                                    </div>
                                    <div className=" col-6 form-group">
                                        <label>SĐT Mẹ *</label>
                                        <input  type="text" value= {object.SDTMe}
                                            // onChange={value=>setdanToc(value.target.value)}
                                            placeholder="" className="form-control" disabled /> 
                                    </div>
                                    <div className="col-12 form-group">
                                        <label>Message</label>
                                        <textarea onChange={isCommune => {

                                        setMessenger(isCommune.target.value)
                                    }}   className="textarea form-control" name="message" id="form-message" cols="10"
                                            rows="9"></textarea>
                                    </div>
                                    <div className="col-12 form-group mg-t-8">
                                        <button type="button" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark" onClick={(isStated) => {
                                            updateStateDayOffs(object.idXinNghiHoc,messenger, object.SDTCha)
                                        }}>Gọi cho bố </button>
                                        <button type="button" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark" onClick={(isStated) => {
                                            updateStateDayOffs(object.idXinNghiHoc,messenger ,object.SDTMe)
                                        }}>Gọi cho mẹ</button>
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





export default RequestDayOffDetails;
