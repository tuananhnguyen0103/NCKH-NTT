import React, { useEffect, useState } from "react";
import axios from 'axios';
import ReactPaginate from 'react-paginate';
import { toast } from 'react-toastify';
import { BrowserRouter as h, Link } from 'react-router-dom';
import Error403 from '../../error/403'
import Cookies from 'js-cookie';


const RowHanhKiem = function ({ currentItems, state, setIdhocSinh }) {
    return (
        <>
            {currentItems &&
                currentItems.map((item) => (
                    <tr key={item.idHocSinh}>
                        <td>
                            <div className="form-check">
                                <input type="checkbox" className="form-check-input" />
                                <label className="form-check-label">#{item.idHocSinh}</label>
                            </div>
                        </td>
                        <td>{item.hoTen}</td>
                        {
                            item.diemHanhKiem > 80 ?
                                <td ><a className=" text-success">{item.diemHanhKiem}</a></td>
                                :
                                <td><a className=" text-danger">{item.diemHanhKiem}</a></td>
                        }

                        <td>
                            <div className="dropdown">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                    <span className="flaticon-more-button-of-three-dots"></span>
                                </a>
                                <div className="dropdown-menu dropdown-menu-right">
                                    <a className="dropdown-item" href="#" data-toggle="modal" data-target="#exampleModal" onClick={() => { state(item.idHanhKiem) }} ><i className="fas fa-edit text-dark-pastel-green"></i>Chỉnh sửa</a>
                                    <a className="dropdown-item" href="#" data-toggle="modal" data-target="#exampleModal2" onClick={() => { setIdhocSinh(item.idHocSinh) }} ><i className="fas fa-history text-dark-pastel-green"></i>Xem lịch sử</a>

                                </div>
                            </div>
                        </td>
                    </tr>
                ))}
        </>
    );
}

const RowConduct = function ({ ConductHistory }) {
    return (
        <>
            {ConductHistory &&
                ConductHistory.map((item) => (
                    <tr key={item.idLoi}>
                        <td>
                            #{item.idHocSinh}
                        </td>
                        <td>{item.hoTen}</td>
                        {
                            item.loaiDiem == 1 ?
                                <td ><a className=" text-success">+ {item.diem}</a></td>
                                :
                                <td><a className=" text-danger">- {item.diem}</a></td>
                        }
                        <td>{item.noiDung}</td>
                        <td>{item.ghiChu}</td>
                        <td>{item.created_date_time}</td>


                    </tr>
                ))}
        </>
    );
}

const HanhKiem = ({ itemsPerPage }) => {
    const token = Cookies.get('token')
    const [currentItems, setCurrentItems] = useState(null);
    const toastId = React.useRef(null);

    const [listdata, setListdata] = useState([]);
    const [pageCount, setPageCount] = useState(0);
    const [itemOffset, setItemOffset] = useState(0);
    const [selectedIdHanhKiem, setselectedIdHanhKiem] = useState(null);
    const [loaiDiem, setLoaiDiem] = useState(0);
    const [diem, setDiem] = useState(0);
    const [ghiChu, setGhiChu] = useState("");
    const [noiDung, setNoiDung] = useState("");
    const [kyHoc, setKyHoc] = useState(1);
    const [namHoc, setNamHoc] = useState(new Date().getFullYear() - 1);
    const [tenHocSinh, setTenHocSinh] = useState("");

    const [selectedIdHocSinh, setselectedIdHocSinh] = useState(null);


    const [SearchConductkyHoc, setSearchConductKyHoc] = useState(1);
    const [SearchConductnamHoc, setSearchConductNamHoc] = useState(new Date().getFullYear() - 1);

    const [ConductHistory, setConductHistory] = useState([])



    useEffect(() => {

    }, [ConductHistory])
    const GetAllConductDetailsStudentByYearAndSemester = (e) => {
        e.preventDefault();
        axios({
            method: 'post',
            // url: process.env.REACT_APP_BACKEND_URL + "/",
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/GetAllConductDetailsStudentByYearAndSemester",
            data: {
                idHocSinh: selectedIdHocSinh,
                namHoc: SearchConductnamHoc,
                kiHoc: SearchConductkyHoc,
                token: token
            },
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                console.log(response.data)
                setConductHistory(response.data);
                // setListdata(response.data);

                console.log("get")
                setVaid(true)
                toast.update(toastId.current, {
                    render: "Xong",
                    type: toast.TYPE.SUCCESS,
                    //Here the magic  
                    autoClose: 3000,
                    classNameName: 'rotateY animated'
                })
            }).catch(function (err) {
                console.log(err)
                toast.update(toastId.current, {
                    render: err.response.data,
                    type: toast.TYPE.error,
                    //Here the magic
                    autoClose: 5000,
                    classNameName: 'rotateY animated'
                })
            }).finally(function () {
            });
    }
    const onSubmitHandler = async (e) => {
        e.preventDefault();
        const data = {
            "idHanhKiem": selectedIdHanhKiem,
            "diem": diem,
            "loaiDiem": loaiDiem,
            "noiDung": noiDung,
            "ghiChu": ghiChu,
            "token":token
        }
        await axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/InsertConductDetailsStudent', data, {
            withCredentials: true,
            onUploadProgress: (event) => {
                toastId.current = toast("Đang gửi", { type: toast.TYPE.INFO, autoClose: 5000 });;

            }
        })
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
        //todo
    }
    const onSubmitSearch = (e) => {
        toastId.current = toast("Đang tìm...", { type: toast.TYPE.INFO, autoClose: 5000 });

        e.preventDefault();
        const checkName = (name) => {
            console.log(name.hoTen.indexOf(tenHocSinh))
            return (name.hoTen.toLowerCase().indexOf(tenHocSinh.toLowerCase()) !== -1)
        }
        axios({
            method: 'post',
            // url: process.env.REACT_APP_BACKEND_URL + "/",
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/GetAllConductStudentByIdClassYearAndSemster",
            data: {
                kiHoc: kyHoc,
                namHoc: namHoc,
                token:token
            },
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                if (response.data.length > 0 && tenHocSinh != "") {
                    console.log(response.data)
                    setListdata(response.data.filter(checkName));
                }
                else if (response.data.length > 0) {
                    setListdata(response.data);

                }

                // setListdata(response.data);

                console.log("get")
                setVaid(true)
                toast.update(toastId.current, {
                    render: "Xong",
                    type: toast.TYPE.SUCCESS,
                    //Here the magic  
                    autoClose: 3000,
                    classNameName: 'rotateY animated'
                })
            }).catch(function (err) {
                console.log(err)
                toast.update(toastId.current, {
                    render: err.response.data,
                    type: toast.TYPE.error,
                    //Here the magic
                    autoClose: 5000,
                    classNameName: 'rotateY animated'
                })
            }).finally(function () {
            });
    }

    useEffect(() => {

        const endOffset = itemOffset + 15;
        console.log(`Loading items from ${itemOffset} to ${endOffset}`);
        setCurrentItems(listdata.slice(itemOffset, endOffset));
        setPageCount(Math.ceil(listdata.length / itemsPerPage));


    }, [listdata]);



    // Invoke when user click to request another page.
    const handlePageClick = (event) => {

        const newOffset = (event.selected * itemsPerPage) % listdata.length;
        // console.log(
        //     `User requested page number ${event.selected}, which is offset ${newOffset}`
        // );
        setItemOffset(newOffset);
    };


    useEffect(() => {

        console.log("use")

        // Fetch items from another resources.
        const endOffset = itemOffset + itemsPerPage;
        setCurrentItems(listdata.slice(itemOffset, endOffset));

    }, [itemOffset, itemsPerPage]);



    const [vaid, setVaid] = useState(false);



    useEffect(() => {
        toastId.current = toast("Đang lấy dữ liệu hạnh kiểm...", { type: toast.TYPE.INFO, autoClose: 5000 });

        axios({
            method: 'post',
            // url: process.env.REACT_APP_BACKEND_URL + "/",
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/GetAllConductStudentByIdClassYearAndSemster",
            data: {
                kiHoc: 1,
                namHoc: new Date().getFullYear(),
                token:token
            },
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                console.log(response.data)
                setListdata(response.data);
                // setListdata(response.data);

                console.log("get")
                setVaid(true)
                toast.update(toastId.current, {
                    render: "Xong",
                    type: toast.TYPE.SUCCESS,
                    //Here the magic  
                    autoClose: 3000,
                    classNameName: 'rotateY animated'
                })
            }).catch(function (err) {
                console.log(err)
                toast.update(toastId.current, {
                    render: err.response.data,
                    type: toast.TYPE.error,
                    //Here the magic
                    autoClose: 5000,
                    classNameName: 'rotateY animated'
                })
            }).finally(function () {
            });
    }, []);




    document.title = "Quản lý hạnh kiểm"
    const yearList = [];
    for (var i = new Date().getFullYear() - 6; i <= new Date().getFullYear(); i++) {
        yearList.push(<option value={i} key={i}>{i}</option>
        );
    }

    if (vaid)
        return (
            <>

                <div className="breadcrumbs-area">
                    <h3>Hạnh kiểm</h3>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>Quản lý hạnh kiểm</li>
                    </ul>
                </div>

                <div className="card height-auto">
                    <div className="card-body">
                        <div className="heading-layout1">
                            <div className="item-title">
                                <h3>Danh sách hạnh kiểm</h3>
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
                        <form onSubmit={onSubmitSearch} className="mg-b-20">
                            <div className="row gutters-8">
                                <div className="col-3-xxxl col-xl-3 col-lg-3 col-12 form-group">
                                    <input value={tenHocSinh} onChange={(e) => { setTenHocSinh(e.target.value) }} type="text" placeholder="Tên học sinh ..." className="form-control" />
                                </div>
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
                            <table className="table data-table text-nowrap">
                                <thead>
                                    <tr>

                                        <th>
                                            <div className="form-check">
                                                <label className="form-check-label">ID</label>
                                            </div>
                                        </th>
                                        <th >Tên học sinh</th>
                                        <th >Điểm hạnh kiểm</th>

                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <RowHanhKiem state={setselectedIdHanhKiem} setIdhocSinh={setselectedIdHocSinh} currentItems={currentItems} />
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
                <div className="modal fade" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLabel">Thêm hạnh kiểm</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form id="submitConduct" onSubmit={onSubmitHandler}>
                                    <div className="form-group">
                                        <label>Điểm</label>
                                        <input min={0} max={100} onChange={value => setDiem(value.target.value)} type="number" placeholder="" className="form-control" />
                                    </div>
                                    <div className="form-group">
                                        <label>Nội dung</label>
                                        <input onChange={value => setNoiDung(value.target.value)} type="text" placeholder="" className="form-control" />
                                    </div>
                                    <div className="form-group">
                                        <label>Ghi chú</label>
                                        <input onChange={value => setGhiChu(value.target.value)} type="text" placeholder="" className="form-control" />
                                    </div>
                                    <div className="form-group">
                                        <label>Loại điểm</label>
                                        <select onChange={loaiDiem => {

                                            setLoaiDiem(loaiDiem.target.value)
                                        }}
                                            className="form-control">
                                            <option value="">Chọn *</option>
                                            <option value="1">Tăng</option>
                                            <option value="0">Giảm</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                <button form="submitConduct" type="submit" className="btn btn-primary">Tạo</button>
                            </div>
                        </div>
                    </div>
                </div>









                <div className="modal fade" id="exampleModal2" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLabel2">Lịch sử hạnh kiểm</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form id="submitHistory" onSubmit={GetAllConductDetailsStudentByYearAndSemester}>
                                    <div className="form-group">
                                        <label>Chọn kỳ học</label>

                                        <select onChange={e => {
                                            console.log(e.target.value);
                                            setSearchConductKyHoc(e.target.value)
                                        }}

                                            className="form-control">
                                            <option value="search" disabled>Chọn kỳ học</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                        </select>
                                    </div>
                                    <div className="form-group">
                                        <label>Chọn năm học</label>
                                        <select onChange={e => {

                                            setSearchConductNamHoc(e.target.value)
                                        }}
                                            className="form-control">
                                            <option disabled selected="selected">Chọn năm học</option>

                                            {

                                                yearList
                                            }

                                        </select>
                                    </div>

                                </form>


                                <table className="table data-table text-nowrap">
                                    <thead>
                                        <tr>

                                            <th>
                                                <div className="form-check">
                                                    <label className="form-check-label">ID</label>
                                                </div>
                                            </th>
                                            <th >Tên học sinh</th>
                                            <th >Điểm thay đổi</th>
                                            <th>Nội dung</th>
                                            <th>Ghi chú</th>

                                            <th>Ngày tạo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <RowConduct ConductHistory={ConductHistory} />
                                    </tbody>
                                </table>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                <button form="submitHistory" type="submit" className="btn btn-primary">Xem lịch sử</button>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        );
    else {
        return (<Error403 name="Bạn chưa chủ nhiệm lớp nào" />)

    }


}

export default HanhKiem;
