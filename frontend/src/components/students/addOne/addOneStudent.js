
import React, { useEffect, useState } from "react";

import axios from 'axios';
import ModernDatepicker from 'react-modern-datepicker';
import { toast } from 'react-toastify';
import adresseApi from '../../../lib/addressApi'
import Cookies from 'js-cookie';
// import  bootstrapselect from 'bootstrap-select';
const AddOneStudent = () => {




    const token = Cookies.get('token')
    // star infor student
    // const [idLop,setidLop] =useState(null)
    const [ngaySinh,setNgaySinh] =useState("chọn ngày")

    const [soDienThoai,setSoDienThoai] =useState(null)

    const [danhSachTinhThanh,setDanhSachTinhThanh]=useState([])
    const [danhSachHuyen,setDanhSachHuyen]=useState([])
    const [danhSachPhuong,setdanhSachPhuong]=useState([])
    const [Tinh,setTinh] =useState(null)
    const [huyen,setHuyen] =useState(null)
    const [xa,setXa] =useState(null)
    const [thonXom,setthonXom] =useState(null)
    const [noiSinh,setNoiSinh] =useState(null)
    const [noiThuongTru,setNoiThuongTru] =useState(null)
    const [danToc,setdanToc] =useState(null)
    const [doanVien, setDoanVien] = useState(null)
    const [luuBanNamTruoc, setluuBanNamTruoc] = useState(null)
    const [doiVien, setDoiVien] = useState(null)
    const [quocTich, setQuocTich] = useState(null)
    const [tonGiao, settonGiao] = useState(null)
    const [doiTuongChinhSach,setDoiTuongChinhSach] = useState(null)
    const [gioiTinh,setGioiTinh] = useState(null)
    const [fullNameStudent,setFullNameStudent] =useState(null)
    const [namNhapHoc,setNamNhapHoc] = useState(null)
    
    // end info student

    // start info parent
    const [danTocCha, setDanTocCha] = useState(null)
    const [tenCha, setTenCha] = useState(null)
    const [ngheNghiepCha, setNgheNghiepCha] = useState(null)
    const [namSinhCha, setNamSinhCha] = useState(null)
    const [danTocMe, setDanTocMe] = useState(null)
    const [tenMe, setTenMe] = useState(null)
    const [NgheNghiepMe, setNgheNghiepMe] = useState(null)
    const [namSinhMe, setNamSinhMe] = useState(null)
    const [hoTenNguoiGiamHo, setHoTenNguoiGiamHo] = useState(null)
    const [ngheNghiepNGH, setNgheNghiepNGH] = useState(null)
    const [namSinhNGH, setNamSinhNGH] = useState(null)
    // end info parent

    


    const SelectProvince=()=>{
        const items=[];
        if(danhSachTinhThanh.length>0)
        for (let i = 0; i < danhSachTinhThanh.length; i++) {             
            items.push(<option key={danhSachTinhThanh[i].code} value={danhSachTinhThanh[i].code}>{danhSachTinhThanh[i].name}</option>);   
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
       }
     
       return items;
    }


    const SelectWard=()=>{
        const items=[];
        if(danhSachPhuong.length>0)
        for (let i = 0; i < danhSachPhuong.length; i++) {             
            items.push(<option key={danhSachPhuong[i].code} value={danhSachPhuong[i].code}>{danhSachPhuong[i].name}</option>);   
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
       }
     
       return items;
    }


    const SelectDistrict=()=>{

        const items=[];
        if(danhSachHuyen.length>0)
        for (let i = 0; i < danhSachHuyen.length; i++) {             
            items.push(<option key={danhSachHuyen[i].code} value={danhSachHuyen[i].code}>{danhSachHuyen[i].name}</option>);   
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
       }
     
       return items;
    }
   
    const onSelectWard=(e) => {
        var index = e.nativeEvent.target.selectedIndex;

       setXa(e.nativeEvent.target[index].text)
    }


    const onSelectCommune=(e) => {
        adresseApi.onSelectCommune(e,setdanhSachPhuong,setHuyen)
        // var index = e.nativeEvent.target.selectedIndex;

        // setHuyen(e.nativeEvent.target[index].text)
        // console.log(e)
        // axios({
        //     method: 'get',
        //     url: 'https://api.mysupership.vn/v1/partner/areas/commune?district='+e.target.value,
        //     responseType: 'json'
        // })
        //     .then(function (response) {
        //         setdanhSachPhuong(response.data.results)
        //     }).catch(function (err) {
        //         console.log(err)
        //     }).finally(function () {
        //     });
    }

    const onSelectProvince=(e) =>{
        
        adresseApi.onSelectProvince(e,setDanhSachHuyen,setTinh)
        // var index = e.nativeEvent.target.selectedIndex;
        // setTinh(e.nativeEvent.target[index].text)
        // axios({
        //     method: 'get',
        //     url: 'https://api.mysupership.vn/v1/partner/areas/district?province='+e.target.value,
        //     responseType: 'json'
        // })
        //     .then(function (response) {
        //         setDanhSachHuyen(response.data.results)
        //     }).catch(function (err) {
                
        //     }).finally(function () {
        //     });
    }

    useEffect(() =>{
    
        adresseApi.getProvince(setDanhSachTinhThanh)
        // axios({
        //     method: 'get',
        //     url: 'https://api.mysupership.vn/v1/partner/areas/province',
        //     responseType: 'json'
        // })
        //     .then(function (response) {
             
        //         setDanhSachTinhThanh(response.data.results)
        //     }).catch(function (err) {
                
        //     }).finally(function () {
        //     });
     //  
    },[])

    const resetForm=()=>{
        document.querySelector("form").reset()
    }
    const onSubmitHandler = async (event) => {
        event.preventDefault()
        var imagefile = document.querySelector('#image-student-upoad').files[0];

        if (!imagefile)
            return toast("Vui lòng chọn file", { type: toast.TYPE.ERROR, timeout: 3000 });;
            
        const toastId = {};
        

        var dataOfStudent = new FormData();
        dataOfStudent.append("ngaySinh",ngaySinh);
        dataOfStudent.append("SDT",soDienThoai);
        dataOfStudent.append("queQuanTinh",Tinh);
        dataOfStudent.append("queQuanHuyen",huyen);
        dataOfStudent.append("queQuanXa",xa);
        dataOfStudent.append("queQuanThonXom",thonXom);
        dataOfStudent.append("noiSinh",noiSinh);
        dataOfStudent.append("noiThuongTru",noiThuongTru);
        dataOfStudent.append("danToc",danToc);
        dataOfStudent.append("doanVien",doanVien);
        dataOfStudent.append("luuBanNamTruoc",luuBanNamTruoc);
        dataOfStudent.append("doiVien",doiVien);
        dataOfStudent.append("tonGiao",tonGiao);
        dataOfStudent.append("doiTuongChinhSach",doiTuongChinhSach);
        dataOfStudent.append("gioiTinh",gioiTinh);
        dataOfStudent.append("hoTen",fullNameStudent);
        dataOfStudent.append("namNhapHoc",namNhapHoc);
        dataOfStudent.append("anhThe", imagefile);
        // parent info
        dataOfStudent.append("danTocCha",danTocCha)
        dataOfStudent.append("tenCha",tenCha)
        dataOfStudent.append("ngheNghiepCha",ngheNghiepCha)
        dataOfStudent.append("namSinhCha",namSinhCha)
        dataOfStudent.append("danTocMe",danTocMe)
        dataOfStudent.append("tenMe",tenMe)
        dataOfStudent.append("ngheNghiepMe",NgheNghiepMe)
        dataOfStudent.append("namSinhMe",namSinhMe)
        dataOfStudent.append("quocTich",quocTich)
        
        dataOfStudent.append("hoTenNguoiGiamHo",hoTenNguoiGiamHo)
        dataOfStudent.append("ngheNghiepNGH",ngheNghiepNGH)
        dataOfStudent.append("namSinhNGH",namSinhNGH)
        dataOfStudent.append("token",token)

        axios.post(process.env.REACT_APP_BACKEND_URL+'/teacher/InsertSingleStudent',dataOfStudent, {
            // onUploadProgress là sự kiện khi upload 
            onUploadProgress:(processEvents)=>{
            if(processEvents.lengthComputable){ // Check xem có đăng tải lên không?
                
                console.log(processEvents.loaded / processEvents.total *100 )
                toastId.current = toast("Đang tải thông tin học sinh", { type: toast.TYPE.INFO, autoClose: 5000 });
            }
            // console.log()
        },withCredentials: true,
        headers:{
            'Content-Type': 'multipart/form-data'
        }
    }).then(function(response) {
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
    return (
        <>
            <div className="breadcrumbs-area">
                <h3>Thêm học sinh</h3>
                <ul>
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>Thêm học sinh</li>
                    
                </ul>
            </div>
            
            <div className="card height-auto">
                <div className="card-body">
                    <div className="heading-layout1">
                        <div className="col-12 item-title">
                            <h3>Thông tin học sinh</h3>
                            <hr></hr>
                        </div>
                        
                    </div>
                    <form onSubmit={onSubmitHandler} className="new-added-form">
                        <div className="row">
  
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Họ tên *</label>
                                <input  type="text" 
                                  onChange={
                                      value=>setFullNameStudent(value.target.value)
                                    }
                                placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Quốc tịch *</label>
                                <input  type="text" 
                                  onChange={
                                      value=>setQuocTich(value.target.value)
                                    }
                                placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Dân tộc *</label>
                                <input  type="text" 
                                  onChange={value=>setdanToc(value.target.value)}
                                placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Giới tính *</label>
                              
                                <select   onChange={isSex => {
                                  setGioiTinh(isSex.target.value)}} className="form-control" >
                                <option value="">Chọn giới tính *</option>
                                    <option value="Nam">Nam</option>
                                    <option value="Nữ">Nữ</option>
                                    <option value="Khác">Khác</option>
                                </select>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Ngày sinh *</label>
                            

                            <ModernDatepicker
                                className="form-control air-datepicker"

                                    format={'DD-MM-YYYY'}
                                    showBorder
                                    onChange={date => {
                                        const data = date.toString();
                                        console.log(data)
                                        setNgaySinh(data)
                                    }}
                                    placeholder={ngaySinh}
                                    maxDate={new Date()}
                                />
                                <i className="far fa-calendar-alt"></i>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Tỉnh *</label>
                                <select onChange={isProvince => {
                                       onSelectProvince(isProvince)
                                      
                                    }}  className="form-control" >
                                <option value="">Chọn Tỉnh thành *</option>
                                    {
                                       
                                       SelectProvince()
                                      
                                    }
                                </select>

                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Huyện *</label>
                                <select onChange={isDistrict => {
                                    console.log(isDistrict)
                                     onSelectCommune(isDistrict)
                                    }}  className="form-control" >
                                <option value="">Chọn Huyện *</option>
                                    {
                                       
                                       SelectDistrict()
                                      
                                    }
                                </select>

                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Phường/ Xã *</label>
                                <select onChange={isCommune => {
                                  
                                    onSelectWard(isCommune)
                                    }}  className="form-control" >
                                <option value="">Chọn Phường/ Xã *</option>
                                    {
                                       
                                       SelectWard()
                                      
                                    }
                                </select>

                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Thôn *</label>
                                <input onChange={value=>setthonXom(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Nơi sinh *</label>
                                <input onChange={value=>setNoiSinh(value.target.value)} type="text" placeholder="" className="form-control" />                            
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Nơi thường trú *</label>
                                <input onChange={value=>setNoiThuongTru(value.target.value)} type="text" placeholder="" className="form-control" />     
                            </div>    
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Đoàn viên *</label>
                                <select onChange={isDoanVien => {
                                  
                                  setDoanVien(isDoanVien.target.value)}}
                                   className="form-control">                            
                                    <option value="">Chọn *</option>
                                    <option value="1">Có</option>
                                    <option value="0">Không</option>
                                </select>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Lưu ban năm trước *</label>
                                <select onChange={isLuuBan => {
                                  
                                  setluuBanNamTruoc(isLuuBan.target.value)}}
                                   className="form-control">                            
                                    <option value="">Chọn *</option>
                                    <option value="1">Có</option>
                                    <option value="0">Không</option>
                                </select>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Đội viên *</label>
                                <select onChange={isDoiVien => {
                                  
                                  setDoiVien(isDoiVien.target.value)}}
                                   className="form-control">                            
                                    <option value="">Chọn *</option>
                                    <option value="1">Có</option>
                                    <option value="0">Không</option>
                                </select>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Tôn giáo *</label>
                                <input onChange={value=>settonGiao(value.target.value)} type="text" placeholder="" className="form-control" />  
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Đối tượng chính sách *</label>
                                <select onChange={isChinhSach => {
                                  
                                  setDoiTuongChinhSach(isChinhSach.target.value)}}
                                   className="form-control">                            
                                    <option value="">Chọn *</option>
                                    <option value="1">Có</option>
                                    <option value="0">Không</option>
                                </select>
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Năm nhập học</label>
                                <input onChange={value=>setNamNhapHoc(value.target.value)}  type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Số điện thoại</label>
                                <input onChange={value=>setSoDienThoai(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div  className="col-12 item-title">
                                <h3>Thông tin phụ huynh</h3>
                                <hr></hr>
                            </div>
                            
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Họ tên cha</label>
                                <input onChange={value=>setTenCha(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Năm sinh cha</label>
                                <input onChange={value=>setNamSinhCha(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Dân tộc cha</label>
                                <input onChange={value=>setDanTocCha(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Nghề nghiệp cha</label>
                                <input onChange={value=>setNgheNghiepCha(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Họ tên mẹ</label>
                                <input onChange={value=>setTenMe(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Năm sinh mẹ</label>
                                <input onChange={value=>setNamSinhMe(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Dân tộc mẹ</label>
                                <input onChange={value=>setDanTocMe(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Nghề nghiệp mẹ</label>
                                <input onChange={value=>setNgheNghiepMe(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Họ tên NGH</label>
                                <input onChange={value=>setHoTenNguoiGiamHo(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Năm sinh NGH</label>
                                <input onChange={value=>setNamSinhNGH(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-xl-3 col-lg-6 col-12 form-group">
                                <label>Nghề nghiệp NGH</label>
                                <input onChange={value=>setNgheNghiepNGH(value.target.value)} type="text" placeholder="" className="form-control" />
                            </div>
                            <div className="col-lg-6 col-12 form-group mg-t-30">
                                <label className="text-dark-medium">Upload Student Photo (150px X 150px)</label>
                                <input type="file" id="image-student-upoad" className="form-control-file" />
                            </div>

                            <div className="col-12 form-group mg-t-8">
                                <button type="submit" className="btn-fill-lg btn-gradient-yellow btn-hover-bluedark">Save</button>
                                <button type="reset" onClick={resetForm} className="btn-fill-lg bg-blue-dark btn-hover-yellow">Reset</button>
                            </div>


                        </div>
                    </form>

                </div>
            </div>
        </>
    )
}


export default AddOneStudent;