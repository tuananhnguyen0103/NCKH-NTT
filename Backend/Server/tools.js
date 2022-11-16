   // new Date object
   let date_ob = new Date();
   // current date
   date_ob.toLocaleString('vi-VN', {
    timeZone: 'Asia/Ho_Chi_Minh',
    })
   // adjust 0 before single digit date
   let date = ("0" + date_ob.getDate()).slice(-2);

   // current month
   let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);

   // current year
   let year = date_ob.getFullYear();

   // current hours
   let hours = date_ob.getHours();

   // current minutes
   let minutes = date_ob.getMinutes();

   // current seconds
   let seconds = date_ob.getSeconds();

   //this function return date format  you can change it to any format you want
   // now it return DD-MM-YYYY
   const getDate = function() {

       // prints date in YYYY-MM-DD format
       return date + "-" + month + "-" + year

   }
   const getMonth = function() {
       return month + "-" + year
   }


   const getDateToSecond = function() {

       // prints date in YYYY-MM-DD format
       return hours + ":" + minutes + ":" + seconds + "/" + date + "-" + month + "-" + year

   }
   module.exports = {
       hours,
       minutes,
       year,
       month,
       getDate,
       getMonth,
       getDateToSecond
   }