//
//  APIService.swift
//  TheHoccSinhDienTu
//
//  Created by Tondz on 6/1/22.
//  Copyright © 2022 Tondz. All rights reserved.
//

import Foundation
import SwiftyJSON
class APIService{
    
    func login(id:Int,password:String){
        print("run api")
        guard let url = URL(string: Common.BASE_URL+"login")else{
            print("ERROR")
            return
        }
        var urlRequest = URLRequest(url:url)
        urlRequest.httpMethod = "POST"
        urlRequest.setValue(Common.JSON_APP, forHTTPHeaderField: Common.CONTENT_TYPE)
        
        let jsonDictionary = NSMutableDictionary()
        jsonDictionary.setValue(id, forKey: "id")
        jsonDictionary.setValue(password, forKey: "password")
        
        let jsonData: Data
        do{
            jsonData = try JSONSerialization.data(withJSONObject: jsonDictionary, options: JSONSerialization.WritingOptions())
            urlRequest.httpBody = jsonData
        }catch{
            print("error")
        }
        
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        let task = session.dataTask(with: urlRequest) { (data:Data?, response:URLResponse?, error:Error?) in
            guard error == nil else{
                print("error get")
                print(error!)
                return
            }
            guard let data = data else{
                print("error did not receive data")
                return
            }
            guard let response = response as? HTTPURLResponse, (200 ..< 299) ~= response.statusCode else {
                print("Error: HTTP request failed")
                return
            }
            do{
                guard let jsonObject = try JSONSerialization.jsonObject(with: data) as? [String: Any] else {
                    print("Error: Cannot convert data to JSON object")
                    return
                }
                guard let prettyJsonData = try? JSONSerialization.data(withJSONObject: jsonObject, options: .prettyPrinted) else {
                    print("Error: Cannot convert JSON object to Pretty JSON data")
                    return
                }
                guard let prettyPrintedJson = String(data: prettyJsonData, encoding: .utf8) else {
                    print("Error: Could print JSON in String")
                    return
                }
                
               
                let json = JSON(prettyPrintedJson)
                print(json)
            }catch {
                print("Error: Trying to convert JSON data to string")
                return
            }
        }
    
        task.resume()
    }
}
