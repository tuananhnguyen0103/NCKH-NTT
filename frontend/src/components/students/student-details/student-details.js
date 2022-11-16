import React, { useEffect, useState } from "react";
import axios from 'axios';
import Cookies from 'js-cookie'
import { toast } from 'react-toastify';
import domtoimage from "dom-to-image-more";
import JsFileDownloader from 'js-file-downloader';
// import Cookies from 'js-cookie';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useParams
} from "react-router-dom";

import printJS from 'print-js'

const StudentDetails = () => {


    const printstudentInfo =async () => {
     
        const fileUrl=await htmlToBase64();
        
 printJS({printable: fileUrl, type: 'image', header: 'Thông tin học sinh '+studentDetail.hoTen})
        // printJS(fileUrl, 'image')
    }
    const htmlToBase64=async () => {
         const html = document.getElementById("info");
        const url =       
        domtoimage
            .toPng(html)
            .then(function (fileUrl) {
                // download(dataUrl, 'dist');
              return fileUrl;
            })
            .catch(function (error) {
                console.error("oops, something went wrong!", error);
            });
         return url;
    }

    const downLoadImage = async () => {
        const fileUrl=await htmlToBase64();
        new JsFileDownloader({ 
            url: fileUrl,
            filename:studentDetail.idHocSinh+"-"+studentDetail.hoTen+'.png'
          })
          .then(function () {
            // Called when download ended
          })
          .catch(function (error) {
            // Called when an error occurred
          });
       
    }
    const [studentDetail, setstudentDetail] = useState({});
    document.title = studentDetail.hoTen;
    const token = Cookies.get('token')
    const parms = useParams();
    useEffect(() => {
        axios({
            method: 'get',
            url: process.env.REACT_APP_BACKEND_URL + "/student/getByid/" + parms.id+'?token='+token,
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                console.log(response);
                setstudentDetail(response.data);
            });
    }, [])
    return (
        <>
            <div className="breadcrumbs-area">
                <h3>Students</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Chi tiết học sinh</li>
                </ul>
            </div>

            <div className="card height-auto" id='info'>
                <div className="card-body">
                    <div className="heading-layout1">
                        <div className="item-title">
                            <h3>Thông tin học sinh</h3>
                        </div>
                        {/* <div className="dropdown">
                                <a className="dropdown-toggle" href="#" role="button" 
                                data-toggle="dropdown" aria-expanded="false">...</a>
        
                                <div className="dropdown-menu dropdown-menu-right">
                                    <a className="dropdown-item" href="#"><i className="fas fa-times text-orange-red"></i>Close</a>
                                    <a className="dropdown-item" href="#"><i className="fas fa-cogs text-dark-pastel-green"></i>Edit</a>
                                    <a className="dropdown-item" href="#"><i className="fas fa-redo-alt text-orange-peel"></i>Refresh</a>
                                </div>
                            </div> */}
                    </div>
                    <div className="single-info-details">
                        <div className="item-img">
                            <img src={process.env.REACT_APP_BACKEND_STOGARE + "/" + studentDetail.anhThe} alt="student" />
                        </div>
                        <div className="item-content">
                            <div className="header-inline item-header">
                                <h3 className="text-dark-medium font-medium">{studentDetail.hoTen}</h3>
                                <div className="header-elements">
                                    <ul>
                                        <li><a href="#"><i className="far fa-edit"></i></a></li>
                                        <li className="btn"  onClick={printstudentInfo}><a ><i className="fas fa-print"></i></a></li>
                                        <li  className="btn"   onClick={downLoadImage}><a ><i className="fas fa-download"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                            <p>Aliquam erat volutpat. Curabiene natis massa sedde lacu stiquen sodale
                                word moun taiery.Aliquam erat volutpaturabiene natis massa sedde  sodale
                                word moun taiery.</p>
                            <div className="info-table table-responsive">
                                <table className="table text-nowrap">
                                    <tbody>
                                        <tr>
                                            <td>Họ tên:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.hoTen}</td>
                                        </tr>
                                        <tr>
                                            <td>Giới tính:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.gioiTinh}</td>
                                        </tr>
                                        <tr>
                                            <td>Họ tên bố:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.tenCha}</td>
                                        </tr>
                                        <tr>
                                            <td>Họ tên mẹ:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.tenMe}</td>
                                        </tr>
                                        <tr>
                                            <td>Ngày sinh:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.ngaySinh}</td>
                                        </tr>
                                        <tr>
                                            <td>Quê quán:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.queQuanHuyen}/{studentDetail.queQuanTinh}</td>
                                        </tr>
                                        <tr>
                                            <td>Nghề nghiệp cha:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.ngheNghiepCha}</td>
                                        </tr>
                                        <tr>
                                            <td>Nghề nghiệp mẹ:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.ngheNghiepMe}</td>
                                        </tr>
                                        {/* <tr>
                                                <td>E-mail:</td>
                                                <td className="font-medium text-dark-medium"><a href="https://www.radiustheme.com/cdn-cgi/l/email-protection" className="__cf_email__" data-cfemail="e9838c9a9a80889b869a8ca98e84888085c78a8684">[email&#160;protected]</a></td>
                                            </tr> */}
                                        <tr>
                                            <td>Năm nhập học :</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.namNhapHoc}</td>
                                        </tr>
                                        <tr>
                                            <td>Tên lớp:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.tenLop}</td>
                                        </tr>
                                        <tr>
                                            <td>Pantsu:</td>
                                            <td className="font-medium text-dark-medium">Pink</td>
                                        </tr>
                                        <tr>
                                            <td>Roll:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.idHocSinh}</td>
                                        </tr>
                                        <tr>
                                            <td>Nơi thường trú:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.noiThuongTru}</td>
                                        </tr>
                                        <tr>
                                            <td>Số điện thoại:</td>
                                            <td className="font-medium text-dark-medium">{studentDetail.SDT}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default StudentDetails