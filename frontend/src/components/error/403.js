import React, { useState } from "react";

import './error.css'

document.title = "Lỗi";
  
    const Error401=(props) =>{
        return (
        <>
        <section className="page_404">
	<div className="container">
		<div className="row">	
		<div className="col-sm-12 ">
		<div className="col-sm-10 col-sm-offset-1  text-center">
		<div className="four_zero_four_bg">
			<h1 className="text-center ">403</h1>
		
		
		</div>
		
		<div className="contant_box_404">
		<h3 className="h2">
			Hmm, Bạn không có quyền xem trang này vì :
		</h3>
		
		<p>{props.name}</p>
		
		<a href="/" className="link_404">Về trang chủ</a>
	</div>
		</div>
		</div>
		</div>
	</div>
</section>
        </>
        );
    }

export default Error401;