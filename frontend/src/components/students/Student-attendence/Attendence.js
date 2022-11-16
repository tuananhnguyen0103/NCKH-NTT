
import React, { useEffect, useState } from "react";
import axios from "axios";
import './Attendance.css'
import ReactPaginate from 'react-paginate';
import { toast } from 'react-toastify';
import ModernDatepicker from 'react-modern-datepicker';
import { io } from "socket.io-client";
import Cookies from 'js-cookie'


const updateStateAttendence = (idDiemDanh, trangThai) => {
    const toastId = {};
    const token = Cookies.get('token')
    const dataAttendence = {
        idDiemDanh: idDiemDanh,
        trangThai: trangThai,
        token: token
    };
    axios.post(process.env.REACT_APP_BACKEND_URL + '/teacher/UpdateStateAttendanceByTeacher', dataAttendence, {
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

const Attendence = () => {


    const [tenHocSinh, setTenHocSinh] = useState("")
    const [trangThaiDiemDanh, setTrangThaiDiemDanh] = useState(null)
    const socket = io(process.env.REACT_APP_WEBSOCKET);
   
    socket.on("connect", () => {
        console.log(socket.id); 
      });

      const user=JSON.parse(Cookies.get('user'))
      
    socket.on(user.ttl+"att", (data) => {

         console.log(data)
         document.getElementById(data.idDiemDanh).value=data.trangThai
        }
         );
    function AttendenceInfo({ currentItems }) {
        const SelectState = () => {
            return (
                <>
                    <option className="dropdown-item text-danger state-change" value="0"  >&#xf2d3;</option>
                    <option className="dropdown-item text-success state-change" value="1" >&#xf00c;Có</option>
                    <option className="dropdown-item text-warning state-change" value="2">&#xf024;Muộn</option>
                    <option className="dropdown-item text-danger state-change" value="3" >&#xf071;Vắng</option>
                </>
            )
        }
        return (
            <>
                {
                    currentItems && currentItems.map((item) => (
                        <tr key={item.idHocSinh.toString()}>

                            <td className="text-left">{item.hoTen}</td>
                            {
                                item.data.map((date) => (
                                    <td key={date.id.toString()}>
                                        {
                                            
                                                <select
                                                
                                                id={date.idDiemDanh.toString()}
                                                style={{
                                                        color:
                                                            date.status==1?
                                                                "green":
                                                                date.status==2?
                                                                "orange":
                                                                date.status==3?
                                                                "red":
                                                                "purple"
                                                            }} defaultValue={date.status}

                                                    onChange={(isStated) => {
                                       
                                                        const tar=isStated.target
                                           
                                                       tar.attributes[0].nodeValue=`dropdown-item state-change ${tar.children[tar.selectedIndex].classList[1]}`;
                                                        updateStateAttendence(date.idDiemDanh, tar.value)

                                                    }
                                                    } className="dropdown-item state-change" >
                                                    <SelectState></SelectState>
                                                </select>
                                              
                                        }
                                    </td>
                                ))
                            }
                        </tr>

                    ))
                }
            </>
        )
    }

    ////////////////////////

    var a = new Date();

    var dd = String(a.getDate()).padStart(2, '0');
    var mm = String(a.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = a.getFullYear();




    const [ordinalData, setOrdinalData] = useState([]);

    const [attendenceList, setAttendenceList] = useState([]);
    // const [selectDate, setselectDate] = useState(dd+'-'+mm+'-'+yyyy);
    const [selectDate, setselectDate] = useState(mm + '-' + yyyy);


    const search=() => {


        const arrayCopy = [...ordinalData] //spread operator


        setAttendenceList(arrayCopy.filter((item) => {
            return item.hoTen.toLowerCase().indexOf(tenHocSinh.toLowerCase()) >= 0
        }))


    }







    // useEffect(()=>{
    //     attendenceList.filter((item)=>{
    //         return item.hoTen.indexOf(tenHocSinh) >= 0
    // })
    // },[tenHocSinh]);
    // get parameter
    useEffect(() => {
            const token = Cookies.get('token')
        axios({
            method: 'post',
            data:
            {
                date: selectDate,
                token:token
            },
            url: process.env.REACT_APP_BACKEND_URL + "/teacher/getAttendance",
            responseType: 'json',
            withCredentials: true
        })
            .then(function (response) {
                setOrdinalData(response.data)
                setAttendenceList(response.data)

                console.log(response.data)
                console.log(attendenceList)
                // setAttendenceList(data);
            });
    }, [selectDate]);

    // start useEffect handle AttendenceList

    // useEffect(() => {
    //         //todo
    // },[attendenceList])
    // end

    document.title = "Quản lý điểm danh";
    return (<>
        <div className="breadcrumbs-area">
            <h3>Điểm danh học sinh</h3>
            <ul>
                <li>
                    <a href="/">Home</a>
                </li>
                <li>Điểm danh</li>
            </ul>
        </div>

        <div className="row">

            <div className="col-12">
                <div className="card">
                    <div className="card-body">
                        <div className="heading-layout1">
                            <div className="item-title">
                                <h3>Điểm danh học sinh</h3>
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
                        <form className="new-added-form">
                            <div className="row">

                                <div className="col-xl-3 col-lg-6 col-12 form-group">
                                    <ModernDatepicker
                                        date={selectDate}
                                        format={'MM-YYYY'}
                                        showBorder
                                        onChange={date => {
                                            setselectDate(date)
                                        }
                                        }
                                        placeholder={'Select a date'}

                                    />
                                </div>
                                {/* <div className="col-12 form-group mg-t-8">
                                    <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                                    <button type="reset" className="btn-fill-lg bg-blue-dark btn-hover-yellow">Reset</button>
                                    <button type="reset" className="btn-fill-lg bg-blue-dark btn-hover-yellow">Reset</button>
                                </div> */}
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div className="col-12">
                <div className="card">
                    <div className="card-body">
                        <div className="heading-layout1">
                            <div className="item-title">
                                <h3>Danh sách điểm danh vào tháng {selectDate}</h3>
                            </div>
                            <div className="col-4-xxxl col-xl-4 col-lg-3 col-12 form-group">
                                <input type="text" value={tenHocSinh}
                                    onChange={
                                        value => setTenHocSinh(value.target.value)
                                    }
                                    placeholder="Tìm kiếm học sinh ..." className="form-control" />
                                    <button type="button" onClick={search} className="btn-fill-lg bg-blue-dark btn-hover-yellow">Tìm</button>

                            </div>

                        </div>
                        <div className="table-responsive">
                            <table className="table bs-table table-striped table-bordered text-nowrap">
                                <thead>
                                    <tr>
                                        <th className="text-left">Students</th>
                                        <th>1</th>
                                        <th>2</th>
                                        <th>3</th>
                                        <th>4</th>
                                        <th>5</th>
                                        <th>6</th>
                                        <th>7</th>
                                        <th>8</th>
                                        <th>9</th>
                                        <th>10</th>
                                        <th>11</th>
                                        <th>12</th>
                                        <th>13</th>
                                        <th>14</th>
                                        <th>15</th>
                                        <th>16</th>
                                        <th>17</th>
                                        <th>18</th>
                                        <th>19</th>
                                        <th>20</th>
                                        <th>21</th>
                                        <th>22</th>
                                        <th>23</th>
                                        <th>24</th>
                                        <th>25</th>
                                        <th>26</th>
                                        <th>27</th>
                                        <th>28</th>
                                        <th>29</th>
                                        <th>30</th>
                                        <th>31</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <AttendenceInfo currentItems={attendenceList} />
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </>)

}

export default Attendence;
