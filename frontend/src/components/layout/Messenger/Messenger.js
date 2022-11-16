import React from "react";
class Messenger extends React.Component {
  
  render() {
    return (
     <>
               <li className="navbar-item dropdown header-message">
                        <a className="navbar-nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown"
                            aria-expanded="false">
                            <i className="far fa-envelope"></i>
                            <div className="item-title d-md-none text-16 mg-l-10">Message</div>
                            <span>5</span>
                        </a>

                        <div className="dropdown-menu dropdown-menu-right">
                            <div className="item-header">
                                <h6 className="item-title">05 Message</h6>
                            </div>
                            <div className="item-content">
                                <div className="media">
                                    <div className="item-img bg-skyblue author-online">
                                        <img src="assets/img/figure/student11.png" alt="img"/>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="item-title">
                                            <a href="#">
                                                <span className="item-name">Maria Zaman</span> 
                                                <span className="item-time">18:30</span> 
                                            </a>  
                                        </div>
                                        <p>What is the reason of buy this item. 
                                        Is it usefull for me.....</p>
                                    </div>
                                </div>
                                <div className="media">
                                    <div className="item-img bg-yellow author-online">
                                        <img src="assets/img/figure/student12.png" alt="img"/>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="item-title">
                                            <a href="#">
                                                <span className="item-name">Benny Roy</span> 
                                                <span className="item-time">10:35</span> 
                                            </a>  
                                        </div>
                                        <p>What is the reason of buy this item. 
                                        Is it usefull for me.....</p>
                                    </div>
                                </div>
                                <div className="media">
                                    <div className="item-img bg-pink">
                                        <img src="assets/img/figure/student13.png" alt="img"/>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="item-title">
                                            <a href="#">
                                                <span className="item-name">Steven</span> 
                                                <span className="item-time">02:35</span> 
                                            </a>  
                                        </div>
                                        <p>What is the reason of buy this item. 
                                        Is it usefull for me.....</p>
                                    </div>
                                </div>
                                <div className="media">
                                    <div className="item-img bg-violet-blue">
                                        <img src="assets/img/figure/student11.png" alt="img"/>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="item-title">
                                            <a href="#">
                                                <span className="item-name">Joshep Joe</span> 
                                                <span className="item-time">12:35</span> 
                                            </a>  
                                        </div>
                                        <p>What is the reason of buy this item. 
                                        Is it usefull for me.....</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>

     </>
    );
  }
}
export default Messenger;