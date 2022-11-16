import React from "react"
import Header from "./Header/Header"
import Error404 from '../error/Error404'

import Footer from "./Footer/Footer"
import { BrowserRouter as BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Sidebar from './sidebar/sidebar'
import Index from '../index'
import Student from "../students/Student/Student";
import Attendence from "../students/Student-attendence/Attendence";
import Messenger from "../Messenger/Messenger"
import TimeTable from "../TimeTable/TimeTable";
import Xlsxadd from '../students/xlsx/Xlsxadd'
import StudentDetails from '../students/student-details/student-details'
import AddOneStudent from '../students/addOne/addOneStudent'
import HanhKiem from "../students/HanhKiem/HanhKiem";
import ChangePassword from '../teacher/changePassword/changePassword'
import RequestDayOff from "../students/requestDayoff/requestDayoff";
import RequestDayOffDetails from "../students/requestDayOff-details/requestDayOff-details"
import StudentScore from "../students/studentScore/studentScore"
import AddScoreXlsx from "../students/addScoreXlsx/addScoreXlsx"
class Layout extends React.Component {

 
  render(){
    return (
        <>
        <Header />
        <div className="dashboard-page-one">
             <Sidebar/>
            <div className="dashboard-content-one">
               
                    <Routes>
                        <Route  path='/' element={<Index/>} />
                        <Route  path='tat-ca-hoc-sinh' element={<Student itemsPerPage={15}/>}/>
                        <Route  path='diemHocSinh' element={<StudentScore itemsPerPage={10}/>}/>
                        <Route  path='theo-doi-diem-danh' element={<Attendence/>}/>
                        <Route  path='gui-tin-nhan' element={<Messenger/>}/>
                        <Route  path='thoi-khoa-bieu' element={<TimeTable/>}/>
                        <Route  path='*' element={<Error404/>}/>
                        <Route  path='StudentDetails/:id' element={<StudentDetails/>} />
                        <Route  path='StudentAddXlsx' element={<Xlsxadd/>} />
                        <Route  path='StudentAddOne' element={<AddOneStudent/>} />
                        <Route  path='DanhSachXinNghiHoc' element={<RequestDayOff itemsPerPage={15}/>} />
                        <Route  path='ChiTietXinNghiHoc/:id' element={<RequestDayOffDetails/>} />
                        <Route  path='teacher/doi-mat-khau' element={<ChangePassword/>} />
                        <Route  path='teacher/quan-ly-hanh-kiem' element={<HanhKiem itemsPerPage={15}/>} />
                        <Route  path='StudentAddScoreXlsx' element={<AddScoreXlsx/>} />
                    </Routes>
              
                <Footer/>
               
            </div>
        </div>
        
        
      </>
    )
  }
}
export default Layout;