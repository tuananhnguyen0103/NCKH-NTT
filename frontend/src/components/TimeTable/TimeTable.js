import React, { useEffect, useState } from "react";

import ModernDatepicker from 'react-modern-datepicker';
import axios from 'axios';
import dayjs from 'dayjs';
import { toast } from 'react-toastify';
import { Document, Page } from 'react-pdf/dist/esm/entry.webpack5';
import Cookies from 'js-cookie';
// import PDFViewer from 'pdf-viewer-reactjs'
const TimeTable = () => {


    const [currentTimetable, setCurrentTimetable] = useState(null);
    const toastId = React.useRef(null);
  
 

    const token = Cookies.get('token')


    var a = new Date();

    var dd = String(a.getDate()).padStart(2, '0');
    var mm = String(a.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = a.getFullYear();
    const onSubmitHandler = (event) => {
        event.preventDefault();
        var imagefile = document.querySelector('#file-upload').files[0];

        if (!imagefile)
            return toast("Vui lòng chọn file", { type: toast.TYPE.ERROR, timeout: 3000 });;
        const toastId = {};
        var formData = new FormData();

        formData.append("files", imagefile);
        formData.append('startDate', start)
        formData.append('endDate', end)
        axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/uploadTimeTable?token='+token, formData, {
            onUploadProgress: (progressEvent) => {

                if (progressEvent.lengthComputable) {
                    // this.setState({ progress: (progressEvent.loaded / progressEvent.total)*100 });

                    toastId.current = toast("Đang tải thời khoá biểu lên", { type: toast.TYPE.INFO, autoClose: false });


                }
            },
            withCredentials: true,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(function (response) {

                toast.update(toastId.current, {
                    render: "Đăng thành công",
                    type: toast.TYPE.SUCCESS,
                    autoClose: 5000
                });;
                console.log(response);
            })
            .catch(function (error) {

                // With a string
                console.log(error.response.data.data);
                toast.update(toastId.current, {
                    render: error.response.data.data,
                    type: toast.TYPE.ERROR,
                    autoClose: 5000
                });
            });
    }

    const [start, onChangeStart] = useState(dd + "-" + mm + "-" + yyyy);
    const [end, onChangeEnd] = useState(dd + "-" + mm + "-" + yyyy);




    useEffect(() => {
        axios({
            method: 'get',
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/getCurrentTimeTable?token="+Cookies.get('token'),
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                console.log(response);
                setCurrentTimetable(response.data);
            
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
    
    },[])
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
    document.title = "Quản lý thời khoá biểu";



    return (
        <>

            <div className="breadcrumbs-area">
                <h3>Thời khoá biểu</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Cập nhật thời khoá biểu</li>
                </ul>
            </div>

            <div className="card height-auto">
                <div className="card-body">
                    <div className="heading-layout1">
                        <div className="item-title">
                            <h3>Cập nhật thời khoá biểu</h3>
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
                            <div className="col-xl-6 col-lg-6 col-12 form-group">
                                <label>Ngày bắt đầu</label>
                                <ModernDatepicker
                                className="form-control air-datepicker"
                                    date={start}
                                    format={'DD-MM-YYYY'}
                                    showBorder
                                    onChange={date => {
                                        const data = date.toString();
                                        onChangeStart(data)
                                    }}
                                    placeholder={'Select a date'}
                                    minDate={new Date()}
                                />
                            </div>


                            <div className="col-xl-6 col-lg-6 col-12 form-group">
                                <label>Ngày kết thúc</label>
                                <ModernDatepicker
                                    date={end}
                                className="form-control air-datepicker"

                                    format={'DD-MM-YYYY'}
                                    showBorder
                                    onChange={date => {
                                        const data = date.toString();
                                        onChangeEnd(data)
                                    }}
                                    placeholder={'Select a date'}
                                    minDate={new Date()}
                                />
                            </div>



                            <label htmlFor="file-upload" className="btn-fill-lmd radius-30 text-light bg-true-v">
                                Chọn tệp
                                <i className="fas fa-cloud-upload-alt mg-l-10"></i>
                            </label>
                            <input accept=".pdf,.jpg,.jpeg,.png" id="file-upload" type="file" />


                        </div>
                        <div className="col-12 form-group mg-t-8 button-time" >
                            <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                            <button type="button" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark" data-toggle="modal" data-target="#exampleModal">
                               Xem thời khoá biểu hiện tại
                            </button>
                        </div>
                    </form>
                </div>
            
               
            </div>


            <div className="modal fade " id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-lg" role="document">
                    <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Thời khoá biểu hiện tại</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        {currentTimetable==null?<h1>Hiện chưa có thời khoá biểu</h1>
                        :
                        <Document className="mw-100"
                            file={currentTimetable} >
                              <Page pageNumber={1} />
                        </Document>}

                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                    </div>
                </div>
                </div>
        </>
    );
}

export default TimeTable;
