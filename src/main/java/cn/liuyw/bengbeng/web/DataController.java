package cn.liuyw.bengbeng.web;

import cn.liuyw.bengbeng.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;

/**
 * Created by liuyw on 17/8/31.
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private Environment env;

    @GetMapping("/testProfile")
    public Result testProfile(){
        return Result.ok(env.getProperty("profile"));
    }


    public static void main(String[] args) {
        try{
//            String a = "%7B%22delayMoney%22%3A%5B%7B%22settleId%22%3A%2276%22%2C%22extraMoney%22%3A%22100.0%22%2C%22remark%22%3A%22gg%22%7D%2C%7B%22settleId%22%3A%2277%22%2C%22extraMoney%22%3A%22100.0%22%2C%22remark%22%3A%22bj%22%7D%2C%7B%22settleId%22%3A%2278%22%2C%22extraMoney%22%3A%22100.0%22%2C%22remark%22%3A%22bn%22%7D%5D%7D";

//            String a = "%257B%2522delayMoney%2522%253A%255B%257B%2522settleId%2522%253A%252276%2522%252C%2522extraMoney%2522%253A%2522110%2522%252C%2522remark%2522%253A%2522%2522%257D%252C%257B%2522settleId%2522%253A%252277%2522%252C%2522remark%2522%253A%2522%2522%257D%252C%257B%2522settleId%2522%253A%252278%2522%252C%2522extraMoney%2522%253A%25220%2522%252C%2522remark%2522%253A%2522%2522%257D%255D%257D";

            String a = "%7B%22delayMoney%22%3A%5B%7B%22settleId%22%3A%2276%22%2C%22extraMoney%22%3A%22110%22%2C%22remark%22%3A%22%22%7D%2C%7B%22settleId%22%3A%2277%22%2C%22remark%22%3A%22%22%7D%2C%7B%22settleId%22%3A%2278%22%2C%22extraMoney%22%3A%220%22%2C%22remark%22%3A%22%22%7D%5D%7D";

            System.out.println(URLDecoder.decode(URLDecoder.decode(a,"UTF-8"),"UTF-8"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
