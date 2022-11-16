import React, { useEffect, useState } from "react";
import axios from 'axios';
import ReactPaginate from 'react-paginate';
import { toast } from 'react-toastify';
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Error403 from '../../error/403'
import Cookies from 'js-cookie';


const updateStateDayOffs = (idXinNghiHoc, trangThai) => {
    const token = Cookies.get('token')
    const toastId = {};
    const dataDayOffs = {
        idXinNghiHoc: idXinNghiHoc,
        trangThai: trangThai,
        token:token
    };
    axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/UpdatedStateConfirmedParentToDayOffById', dataDayOffs, {
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

const ListData = ({ currentItems }) => {
    return (
        <>
            {currentItems &&
                currentItems.map((item, index) => (
                    <tr key={item.idHocSinh.toString()}>


                        <td>{(index + 1)}</td>
                        <td>{item.hoTen}</td>
                        <td>{item.noiDung}</td>
                        <td>{item.ngayTao}</td>
                        <td>{item.xacNhan==0?"Chưa gọi":item.xacNhan==1?"Cho phép":item.xacNhan==2?"Từ chối":"Không trả lời"}</td>

                        <td>
                            <div className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                    <span className="flaticon-more-button-of-three-dots"></span>
                                </a>
                                <div className="dropdown-menu dropdown-menu-right">
                                <Link  to={'/ChiTietXinNghiHoc/'+item.idXinNghiHoc} className="dropdown-item" > 
                                    <i className="fas fa-exclamation text-orange-peel"></i> Chi tiết </Link>
                                        
                                    
                                    <a className="dropdown-item" href="#" 
                                        onClick={(isStated) => {
                                            updateStateDayOffs(item.idXinNghiHoc, 1)
                                        }}
                                    >
                                        <i className="fas fa-check alert-success"></i> Chấp nhận</a>
                                    <a className="dropdown-item" href="#" 
                                        onClick={(isStated) => {
                                            updateStateDayOffs(item.idXinNghiHoc, 2)
                                        }} 
                                    >
                                        <i className="fas fa-times  bg-pink2"></i>  Từ chối</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                ))}
        </>
    );
}




const RequestDayOff = ({ itemsPerPage }) => {
    const toastId = React.useRef(null);

    // We start with an empty list of items.
    const [currentItems, setCurrentItems] = useState(null);
    const [pageCount, setPageCount] = useState(0);
    // Here we use item offsets; we could also use page offsets
    // following the API or data you're working with.
    const [itemOffset, setItemOffset] = useState(0);
    const [studentList, setStudentList] = useState([]);

    const [vaid, setVaid] = useState(false);

    useEffect(() => {

        const endOffset = itemOffset + itemsPerPage;
        console.log(`Loading items from ${itemOffset} to ${endOffset}`);
        setCurrentItems(studentList.slice(itemOffset, endOffset));
        setPageCount(Math.ceil(studentList.length / itemsPerPage));

        console.log(currentItems)
    }, [studentList]);

    useEffect(() => {
        console.log(toastId)
        toastId.current = toast("Đang lấy dữ liệu học sinh...", { type: toast.TYPE.INFO, autoClose: 5000 });

        axios({
            method: 'get',
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/GetAllDayOffStudentByClass?token="+ Cookies.get('token'),
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                setStudentList(response.data);
                console.log("get")
                setVaid(true)
                toast.update(toastId.current, {
                    render: "Xong",
                    type: toast.TYPE.SUCCESS,
                    //Here the magic  
                    autoClose: 3000,
                    className: 'rotateY animated'
                })
            }).catch(function (err) {
                console.log(err)
                toast.update(toastId.current, {
                    render: err.response.data,
                    type: toast.TYPE.error,
                    //Here the magic
                    autoClose: 5000,
                    className: 'rotateY animated'
                })
            }).finally(function () {
            });
    }, []);

    useEffect(() => {

        console.log("use")

        // Fetch items from another resources.
        const endOffset = itemOffset + itemsPerPage;
        setCurrentItems(studentList.slice(itemOffset, endOffset));

    }, [itemOffset, itemsPerPage]);

    // Invoke when user click to request another page.
    const handlePageClick = (event) => {
        const newOffset = (event.selected * itemsPerPage) % studentList.length;
        // console.log(
        //     `User requested page number ${event.selected}, which is offset ${newOffset}`
        // );
        setItemOffset(newOffset);
    };

    document.title = "Danh sách xin nghỉ học"

    if (vaid)
        return (
            <>

                <div className="breadcrumbs-area">
                    <h3>Students</h3>
                    <ul>
                        <li>
                            <a href="/">Home</a>
                        </li>
                        <li>All Students</li>
                    </ul>
                </div>

                <div className="card height-auto">
                    <div className="card-body">
                        <div className="heading-layout1">
                            <div className="item-title">
                                <h3>All Students Data</h3>
                            </div>
                            <div className="dropdown">
                                <a className="dropdown-toggle" href="#" role="button" data-toggle="dropdown"
                                    aria-expanded="false">...</a>

                                <div className="dropdown-menu dropdown-menu-right">
                                    <Link to="/StudentAddXlsx" className="dropdown-item" href="#"><i
                                        className="fa fa-file-excel text-dark-pastel-green"></i>Thêm qua exel</Link>
                                    <Link to="/StudentAddOne" className="dropdown-item" href="#"><i
                                        className="fas fa-plus text-dark-pastel-green"></i>Thêm 1 học sinh</Link>
                                    <a className="dropdown-item" href="#"><i
                                        className="fas fa-redo-alt text-orange-peel"></i>Refresh</a>
                                </div>
                            </div>
                        </div>
                        <form className="mg-b-20">
                            <div className="row gutters-8">
                                <div className="col-3-xxxl col-xl-3 col-lg-3 col-12 form-group">
                                    <input type="text" placeholder="Search by Roll ..." className="form-control" />
                                </div>
                                <div className="col-4-xxxl col-xl-4 col-lg-3 col-12 form-group">
                                    <input type="text" placeholder="Search by Name ..." className="form-control" />
                                </div>
                                <div className="col-4-xxxl col-xl-3 col-lg-3 col-12 form-group">
                                    <input type="text" placeholder="Search by Class ..." className="form-control" />
                                </div>
                                <div className="col-1-xxxl col-xl-2 col-lg-3 col-12 form-group">
                                    <button type="submit" className="fw-btn-fill btn-gradient-yellow">SEARCH</button>
                                </div>
                            </div>
                        </form>
                        <div className="table-responsive">
                            <table className="table display data-table text-nowrap">
                                <thead>
                                    <tr>
                                        <th>
                                            <div className="form-check">
                                                <input type="checkbox" className="form-check-input checkAll" />
                                                <label className="form-check-label">STT</label>
                                            </div>
                                        </th>
                                        {/* <th>Stt</th> */}
                                        <th>Họ tên</th>
                                        <th>Nội dung xin phép</th>
                                        <th>Ngày gửi</th>
                                        <th>Trạng thái</th>
                                        <th>Hành động</th>

                                    </tr>
                                </thead>
                                <tbody>

                                    <ListData currentItems={currentItems} />
                                </tbody>


                            </table>
                            <ReactPaginate
                                nextLabel="next >"
                                onPageChange={handlePageClick}
                                pageRangeDisplayed={5}
                                marginPagesDisplayed={5}
                                pageCount={pageCount}
                                previousLabel="< previous"
                                pageClassName="page-item"
                                pageLinkClassName="page-link"
                                previousClassName="page-item"
                                previousLinkClassName="page-link"
                                nextClassName="page-item"
                                nextLinkClassName="page-link"
                                breakLabel="..."
                                breakClassName="page-item"
                                breakLinkClassName="page-link"
                                containerClassName="pagination"
                                activeClassName="active"
                                renderOnZeroPageCount={null}
                            />
                        </div>
                    </div>
                </div>
            </>
        );
    else {
        return (<Error403 name="Bạn chưa chủ nhiệm lớp nào" />)

    }


}

export default RequestDayOff;
