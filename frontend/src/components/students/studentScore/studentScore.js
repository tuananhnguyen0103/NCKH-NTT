import React, { useEffect, useState } from "react";
import axios from 'axios';
import ReactPaginate from 'react-paginate';
import { toast } from 'react-toastify';
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Error403 from '../../error/403'
import Cookies from 'js-cookie';



function StudentInfo({ currentItems }) {
   
    // console.log(currentItems);
    return (
        <>
            {currentItems &&
                currentItems.map((item,index) => (
                    <tr key={index.toString()}>
                        <td>{item.hoTen}</td>
                        <td>{item["Toán"]}</td>
                        <td>{item["Vật lí"]}</td>
                        <td>{item["Hóa học"]}</td>
                        <td>{item["Sinh học"]}</td>
                        <td>{item["Tin học"]}</td>
                        <td>{item["Ngữ văn"]}</td>
                        <td>{item["Lịch sử"]}</td>
                        <td>{item["Địa lý"]}</td>
                        <td>{item["Công nghệ"]}</td>
                        <td>{item["Ngoại ngữ 1"]}</td>
                        <td>{item["GDGQ - AN"]}</td>
                        <td>{item["Thể dục"]==1?' Đạt':"Trượt"}</td>
                        <td>{item["GDCD"]}</td>
                        <td>{item["TBM"]}</td>
                    </tr>
                ))}
        </>
    );
}











const StudentCompoment = ({ itemsPerPage }) => {
const toastId = React.useRef(null);
  
    const [kyHoc, setKyHoc] = useState(1);
    const [namHoc, setNamHoc] = useState(new Date().getFullYear() - 1);
    const [SearchConductkyHoc, setSearchConductKyHoc] = useState(1);
    const [SearchConductnamHoc, setSearchConductNamHoc] = useState(new Date().getFullYear() - 1);

    // We start with an empty list of items.
    const [currentItems, setCurrentItems] = useState(null);
    const [pageCount, setPageCount] = useState(0);
    // Here we use item offsets; we could also use page offsets
    // following the API or data you're working with.
    const [itemOffset, setItemOffset] = useState(0);
    const [studentList, setStudentList] = useState([]);

    const [vaid, setVaid] = useState(false);
    const onSubmitSearch = function (e) {
        e.preventDefault();
        const toastId ={};
        const token = Cookies.get('token')
        // console.log(id)
        toastId.current = toast("Đang Thực hiện...", { type: toast.TYPE.INFO,autoClose: 5000 });
    
        axios({
            method: 'post',
            url: process.env.REACT_APP_BACKEND_URL + '/teacher/GetAllScoreStudentInSemesterByClass',
            data: {
                // idHocSinh: selectedIdHocSinh,
                namHoc: namHoc,
                kiHoc: kyHoc,
                token: token
            },
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                setStudentList(response.data)
                console.log(response)
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
    
    useEffect(() => {
       
        const endOffset = itemOffset + itemsPerPage;
        console.log(`Loading items from ${itemOffset} to ${endOffset}`);
        setCurrentItems(studentList.slice(itemOffset, endOffset));
        setPageCount(Math.ceil(studentList.length / itemsPerPage));


    }, [studentList]);
 

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
    const yearList = [];
    for (var i = new Date().getFullYear() - 6; i <= new Date().getFullYear(); i++) {
        yearList.push(<option value={i} key={i}>{i}</option>
        );
    }
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
                                <a href={process.env.REACT_APP_BACKEND_URL+'/teacher/ListStudentWithinScore.xlsx'} className="dropdown-item" ><i 
                                    className="fa fa-file-excel text-dark-pastel-green"></i>Tải file mẫu exel</a>
                                <Link to="/StudentAddScoreXlsx" className="dropdown-item" href="#"><i
                                    className="fas fa-plus text-dark-pastel-green"></i>Thêm điểm excel</Link>
                                <a className="dropdown-item" href="#"><i
                                    className="fas fa-redo-alt text-orange-peel"></i>Refresh</a>
                            </div>
                        </div>
                    </div>
                    <form onSubmit={onSubmitSearch} className="mg-b-20">
                            <div className="row gutters-8">
                                <div className="col-4-xxxl col-xl-4 col-lg-3 col-12 form-group">


                                    <select onChange={e => {
                                        console.log(e.target.value);
                                        setKyHoc(e.target.value)
                                    }}
                                        className="form-control">
                                        <option disabled selected="selected">Chọn kỳ học</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                    </select>
                                </div>
                                <div className="col-4-xxxl col-xl-3 col-lg-3 col-12 form-group">


                                    <select onChange={e => {

                                        setNamHoc(e.target.value)
                                    }}
                                        className="form-control">
                                        <option disabled selected="selected">Chọn năm học</option>

                                        {

                                            yearList
                                        }

                                    </select>
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
                                    {/* <th>
                                        <div className="form-check">
                                            <input type="checkbox" className="form-check-input checkAll" />
                                            <label className="form-check-label">Mã học sinh</label>
                                        </div>
                                    </th> */}
                                    <th>Họ tên</th>
                                    
                                    <th>Toán</th>
                                    <th>Vật Lý</th>
                                    <th>Hóa học</th>
                                    <th>Sinh học</th>
                                    <th>Tin học</th>
                                    <th>Ngữ Văn</th>
                                    <th>Lịch Sử</th>
                                    <th>Địa Lý</th>
                                    <th>Công Nghệ</th>
                                    <th>Tiếng Anh</th>
                                    <th>GDQP-AN</th>
                                    <th>Thể dục</th>
                                    <th>GDCD</th>
                                    <th>TBM</th>
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


}

export default StudentCompoment;
