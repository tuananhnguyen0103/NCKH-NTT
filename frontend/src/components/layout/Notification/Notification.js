import React from "react";
class Notification extends React.Component {

    render() {
        return (
           <>
                  <li className="navbar-item dropdown header-notification">
                        <a className="navbar-nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown"
                            aria-expanded="false">
                            <i className="far fa-bell"></i>
                            <div className="item-title d-md-none text-16 mg-l-10">Notification</div>
                            <span>8</span>
                        </a>

                        <div className="dropdown-menu dropdown-menu-right">
                            <div className="item-header">
                                <h6 className="item-title">03 Notifiacations</h6>
                            </div>
                            <div className="item-content">
                                <div className="media">
                                    <div className="item-icon bg-skyblue">
                                        <i className="fas fa-check"></i>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="post-title">Complete Today Task</div>
                                        <span>1 Mins ago</span>
                                    </div>
                                </div>
                                <div className="media">
                                    <div className="item-icon bg-orange">
                                        <i className="fas fa-calendar-alt"></i>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="post-title">Director Metting</div>
                                        <span>20 Mins ago</span>
                                    </div>
                                </div>
                                <div className="media">
                                    <div className="item-icon bg-violet-blue">
                                        <i className="fas fa-cogs"></i>
                                    </div>
                                    <div className="media-body space-sm">
                                        <div className="post-title">Update Password</div>
                                        <span>45 Mins ago</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
           </>
        );
    }
}
export default Notification;