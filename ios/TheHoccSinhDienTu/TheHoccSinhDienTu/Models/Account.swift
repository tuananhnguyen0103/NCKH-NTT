//
//  Account.swift
//  TheHoccSinhDienTu
//
//  Created by Tondz on 5/31/22.
//  Copyright © 2022 Tondz. All rights reserved.
//

import Foundation
class Account{
    var account:String;
    var idHocSinh: Int;
    var idGiaoVien: Int;
    var password: String;
    var history: String;
    var idAccount: Int;
    init()  {
        account = "";
        idHocSinh = -1;
        idGiaoVien = -1;
        password = "";
        history = "";
        idAccount = -1;
    }
    
}
