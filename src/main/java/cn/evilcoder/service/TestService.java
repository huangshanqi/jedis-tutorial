package cn.evilcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by huangshanqi on 2016/6/12.
 */
@Service
public class TestService {
    public String test(String str) {
        System.out.println(str);
        return str + "-back";
    }
}
