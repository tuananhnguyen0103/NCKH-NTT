import React, { useEffect, useState } from "react";

import ModernDatepicker from 'react-modern-datepicker';
import axios from 'axios';
import dayjs from 'dayjs';
import { toast } from 'react-toastify';
import { Document, Page } from 'react-pdf/dist/esm/entry.webpack5';
import readXlsxFile from 'read-excel-file';
import Cookies from 'js-cookie';
// import PDFViewer from 'pdf-viewer-reactjs'
const Xlsxadd = () => {


    const toastId = React.useRef(null);
  
 




    const onSubmitHandler = (event) => {
        event.preventDefault();
        var imagefile = document.querySelector('#file-upload').files[0];

        if (!imagefile)
            return toast("Vui lòng chọn file", { type: toast.TYPE.ERROR, timeout: 3000 });;
        const toastId = {};
        const token = Cookies.get('token')
        readXlsxFile(imagefile).then((rows) => {
            console.log(JSON.stringify(rows))

             axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/insertstudentXlsx?token='+token, 
             { 
                data:JSON.stringify(rows),
               
             }, {
            onUploadProgress: (progressEvent) => {

                if (progressEvent.lengthComputable) {
                    // this.setState({ progress: (progressEvent.loaded / progressEvent.total)*100 });

                    toastId.current = toast("Đang tải file", { type: toast.TYPE.INFO, autoClose: 5000 });


                }
            },
            withCredentials: true,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(function (response) {

                toast.update(toastId.current, {
                    render: "Thêm thành công",
                    type: toast.TYPE.SUCCESS,
                    autoClose: 5000
                });;
                console.log(response);
            })
            .catch(function (error) {

                // With a string
                console.log(error);
                toast.update(toastId.current, {
                    render: error.response.data.data,
                    type: toast.TYPE.ERROR,
                    autoClose: 5000
                });
            });
            // `rows` is an array of rows
            // each row being an array of cells.
          }).catch((e)=>{
            toast("chỉ hỗ trợ xlsx (phiên bản exel 2010 trở lên)", { type: toast.TYPE.ERROR, timeout: 3000 });;
          })
       
    }


 
    // super(props);
    // this.state = { start: new Date(), end: new Date()};
    // this.myRef = React.createRef(null);
    // this.onChangeStart = this.onChangeStart.bind(this);
    // this.onChangeEnd = this.onChangeEnd.bind(this);
    // this.onSubmitHandler = this.onSubmitHandler.bind(this);
    // onChangeStart(event) {

    //     const start = event.toString()
    //     this.setState({ start: start });

    // };
    // onChangeEnd(event) {
    //     const end = event.toString()

    //     this.setState({ end: end });

    // }
    document.title = "Thêm học sinh qua file";



    return (
        <>

            <div className="breadcrumbs-area">
                <h3>Thêm học sinh qua file</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Thêm học sinh qua file</li>
                </ul>
            </div>

            <div className="card height-auto">
                <div className="card-body">
                    <div className="heading-layout1">
                        <div className="item-title">
                            <h3>Thêm học sinh qua file</h3>
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
                    <form className="new-added-form" onSubmit={onSubmitHandler}>
                        <div className="row">


                            <label htmlFor="file-upload" className="btn-fill-lmd radius-30 text-light bg-true-v">
                                Chọn tệp
                                <i className="fas fa-cloud-upload-alt mg-l-10"></i>
                            </label>
                            <input accept=".xlsx,.xls" id="file-upload" type="file" />


                        </div>
                        <div className="col-12 form-group mg-t-8 button-time" >
                            <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                           
                        </div>
                    </form>
                </div>
            
               
            </div>

        </>
    );
}

export default Xlsxadd;