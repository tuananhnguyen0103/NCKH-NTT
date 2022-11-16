import React, { useEffect, useState } from "react";
import axios from 'axios';
import ReactPaginate from 'react-paginate';
import { toast } from 'react-toastify';
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Error403 from '../../error/403'
import Cookies from 'js-cookie';
// componentDidMount(){
//     document.title = "Tất cả học sinh"
//     axios({
//         method: 'get',
//         url: process.env.REACT_APP_BACKEND_URL+"/teacher/HomeroomClasses",
//         responseType: 'json',
//         withCredentials: true
//       })
//         .then(function (response) {
//         console.log(response);
//         });
//   }

const unlock = function (id) {
    const toastId ={};
    const token = Cookies.get('token')
    console.log(id)
    toastId.current = toast("Đang Thực hiện...", { type: toast.TYPE.INFO,autoClose: 5000 });

    axios({
        method: 'get',
        url: process.env.REACT_APP_BACKEND_URL + "/teacher/unlockFaceId/"+id+'?token='+token,
        responseType: 'json',
        withCredentials: true
    })
        .then(function (response) {
         
            console.log(response.data.data    )
            toast.update(toastId.current, {
                render: response.data.data,
                type: toast.TYPE.SUCCESS,
                //Here the magic  
                autoClose: 3000,
                className: 'rotateY animated'
              })
        }).catch(function (err) {
            toast.update(toastId.current, {
                render: err,
                type: toast.TYPE.ERROR,
                //Here the magic
                autoClose: 5000,
                className: 'rotateY animated'
              })
        }).finally(function () {
        });
}

function StudentInfo({ currentItems }) {
   
    // console.log(currentItems);
    return (
        <>
            {currentItems &&
                currentItems.map((item) => (
                    <tr key={item.idHocSinh.toString()}>
                        <td>
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label">#{item.idHocSinh}</label>
                            </div>
                        </td>
                        <td className="text-center"><img src={`${process.env.REACT_APP_BACKEND_STOGARE}/${item.anhThe}`} alt="student" /></td>
                        <td>{item.hoTen}</td>
                        <td>{item.gioiTinh}</td>
                        <td>{item.khoaAnh==1?'Đã khoá':"Đã mở" }</td>
                        <td>{item.ngaySinh}</td>
                        <td>{item.noiThuongTru}</td>
                        <td>{item.SDT}</td>
                        {/* <td><a href="https://www.radiustheme.com/cdn-cgi/l/email-protection" className="__cf_email__" data-cfemail="b1dad0cbd8d7d0d9d8dc8882f1d6dcd0d8dd9fd2dedc">[email&#160;protected]</a></td> */}
                        <td>
                            <div className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown"
                                    aria-expanded="false">
                                    <span className="flaticon-more-button-of-three-dots"></span>
                                </a>
                                <div className="dropdown-menu dropdown-menu-right">
                                    {item.khoaAnh==1?
                                    <a onClick={() => unlock(item.idHocSinh)}  className="dropdown-item btn" ><i
                                        className="fa fa-unlock text-dark-pastel-green"></i>Mở khoá FaceId</a>
                                        :
                                        <a onClick={() => unlock(item.idHocSinh)}  className="dropdown-item btn" ><i
                                        className="fa fa-lock text-dark-pastel-red"></i>Khoá FaceId</a> }
                                    

                                     <Link  to={'/studentDetails/'+item.idHocSinh}  className="dropdown-item btn"><i className="fa fa-info-circle text-dark-pastel-green" aria-hidden="true"></i>Xem chi tiết</Link>
                                    <a className="dropdown-item" href="#"><i
                                        className="fas fa-redo-alt text-orange-peel"></i>Refresh</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                ))}
        </>
    );
}











const StudentCompoment = ({ itemsPerPage }) => {
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


    }, [studentList]);
 
    useEffect(() => {
         console.log(toastId)
         toastId.current = toast("Đang lấy dữ liệu học sinh...", { type: toast.TYPE.INFO,autoClose: 5000 });
      
        axios({
            method: 'get',
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/HomeroomClasses?token="+Cookies.get('token'),
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

    document.title = "Quản lý học sinh"

    if(vaid)
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
                                            <label className="form-check-label">Mã học sinh</label>
                                        </div>
                                    </th>
                                    <th>Ảnh</th>
                                    <th>Họ tên</th>
                                    <th>Giới tính</th>
                                    <th>Khoá FaceId</th>
                                    <th>Ngày sinh</th>
                                    <th>Nơi thường trú</th>
                                    <th>Số điện thoại</th>

                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>

                                <StudentInfo currentItems={currentItems} />
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
    else{
        return(<Error403 name="Bạn chưa chủ nhiệm lớp nào"/>)

    }


}

export default StudentCompoment;
