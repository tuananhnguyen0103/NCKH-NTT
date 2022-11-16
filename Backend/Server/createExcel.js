const xlsx = require('node-xlsx').default;

const createFile = async (listStudent) => {


  const HEADER_ROW = [
    'STT'
    ,
    'Id Học Sinh'
    ,
    'Họ và Tên'
    ,
    'Lớp'
    ,
    'Toán'
    ,
    'Lý'
    ,
    'Hóa'
    ,
    'Sinh'
    ,
    'Tin',
    'Ngữ Văn'
    ,
    'Lịch Sử',
    'Địa Lý',
    'Ngoại Ngữ'
    ,
    'Công Nghệ'
    ,
    'GDQP - AN'
    ,
    'Thể dục'
    ,
    'GDCD'

  ]
  const data = [
    HEADER_ROW
  ];
  for (let i = 0; i < listStudent.length; i++) {
    const data_row = [
      i + 1
      , listStudent[i].idHocSinh
      , listStudent[i].hoTen
      , listStudent[i].idThongTinLop
      ,
      //Toán 1 
      0
      ,

      // Vật Lý 2 
      0
      ,

      // Hóa học 3
      0
      ,

      // Sinh học 4
      0
      ,

      // Tin học 5
      0
      ,

      // Ngữ văn 6
      0
      ,

      // Lịch sử 7
      0
      ,

      // Địa lý 8
      0
      ,

      // Ngoại ngữ 9
      0
      ,

      // Công nghệ 10
      0
      ,

      // GDQP - AN 11
      0
      ,

      0 // Thể dục 
      ,

      // GDCD 13
      0

    ]
    data.push(data_row)
  }

  var buffer = xlsx.build([{ name: 'DanhSachHocSinh', data: data }]);
  return buffer;
}
module.exports = {
  createFile
};