import axios from 'axios';

  const onSelectCommune=(e,state,set) => {
    var index = e.nativeEvent.target.selectedIndex;

    set(e.nativeEvent.target[index].text)
    console.log(e)
    axios({
        method: 'get',
        url: 'https://api.mysupership.vn/v1/partner/areas/commune?district='+e.target.value,
        responseType: 'json'
    })
        .then(function (response) {
            state(response.data.results)
        }).catch(function (err) {
            console.log(err)
        }).finally(function () {
        });
}
const onSelectProvince=(e,state,set) =>{
    var index = e.nativeEvent.target.selectedIndex;
    set(e.nativeEvent.target[index].text)
    axios({
        method: 'get',
        url: 'https://api.mysupership.vn/v1/partner/areas/district?province='+e.target.value,
        responseType: 'json'
    })
        .then(function (response) {
            state(response.data.results)
        }).catch(function (err) {
            
        }).finally(function () {
        });
}


const getProvince=(state)=>{
    axios({
        method: 'get',
        url: 'https://api.mysupership.vn/v1/partner/areas/province',
        responseType: 'json'
    })
        .then(function (response) {
         
            state(response.data.results)
        }).catch(function (err) {
            
        }).finally(function () {
        });
}


// module.exports = {
//     onSelectCommune,
//     onSelectProvince,
//     getProvince
// }
export default {
    onSelectCommune,
    onSelectProvince,
    getProvince
}