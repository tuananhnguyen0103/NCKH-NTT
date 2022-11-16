//
//  ViewController.swift
//  TheHoccSinhDienTu
//
//  Created by Tondz on 5/31/22.
//  Copyright © 2022 Tondz. All rights reserved.
//

import UIKit
class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        loadNavBottom()
    
    }
    func loadNavBottom(){
    var body: some View {
    TabView {
        Text("")
            .tabItem {
                Image(systemName: "home.fill")
                Text("Trang chủ")
        }
        Text("")
            .tabItem {
                Image(systemName: "person.fill")
                Text("Thời khóa biểu")
        }
        Text("")
            .tabItem {
                Image(systemName: "mappin.circle.fill")
                Text("Thông báo")
        }
        Text("")
            .tabItem {
                Image(systemName: "mappin.circle.fill")
                Text("Cá nhân")
        }
    }
}
    }

}

